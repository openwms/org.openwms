/*
 * Copyright 2005-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.http;

import org.ameba.LoggingCategories;
import org.ameba.exception.BehaviorAwareException;
import org.ameba.exception.BusinessRuntimeException;
import org.ameba.exception.TechnicalRuntimeException;
import org.ameba.http.AbstractBase;
import org.ameba.http.Response;
import org.openwms.core.exception.ExceptionCodes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.net.URI;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * A AbstractWebController.
 *
 * @author Heiko Scherrer
 */
public abstract class AbstractWebController {

    private static final Logger EXC_LOGGER = LoggerFactory.getLogger(LoggingCategories.PRESENTATION_LAYER_EXCEPTION);
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BehaviorAwareException.class)
    protected ResponseEntity<Response<?>> handleBehaviorAwareException(BehaviorAwareException bae) {
        EXC_LOGGER.error("[P] Presentation Layer Exception: " + bae.getLocalizedMessage(), bae);
        return new ResponseEntity<>(Response.newBuilder()
                .withMessage(bae.getMessage())
                .withMessageKey(bae.getMessageKey())
                .withHttpStatus(String.valueOf(bae.getStatus().value()))
                .withObj(bae.getData())
                .build(),
                bae.getStatus()
        );
    }

    @ExceptionHandler(BusinessRuntimeException.class)
    protected ResponseEntity<Response<?>> handleBusinessRuntimeException(BusinessRuntimeException bre) {
        EXC_LOGGER.error("[P] Presentation Layer Exception: " + bre.getLocalizedMessage(), bre);
        ResponseStatus annotation = bre.getClass().getAnnotation(ResponseStatus.class);
        HttpStatus status = annotation != null ? annotation.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(Response.newBuilder()
                .withMessage(bre.getMessage())
                .withMessageKey(bre.getMessageKey())
                .withHttpStatus(String.valueOf(status.value()))
                .withObj(bre.getData())
                .build(),
                status
        );
    }

    @ExceptionHandler(HttpBusinessException.class)
    protected ResponseEntity<Response<?>> handleHttpBusinessException(HttpBusinessException hbe) {
        EXC_LOGGER.error("[P] Presentation Layer Exception: " + hbe.getLocalizedMessage(), hbe);
        return new ResponseEntity<>(Response.newBuilder()
                .withMessage(hbe.getMessage())
                .withHttpStatus(String.valueOf(hbe.getHttpStatus().value()))
                .build(),
                hbe.getHttpStatus()
        );
    }

    @ExceptionHandler(TechnicalRuntimeException.class)
    protected ResponseEntity<Response<?>> handleTechnicalRuntimeException(TechnicalRuntimeException tre) {
        if (tre.getCause() instanceof BehaviorAwareException) {
            return handleBehaviorAwareException((BehaviorAwareException) tre.getCause());
        }
        if (tre.getCause() instanceof BusinessRuntimeException) {
            return handleBusinessRuntimeException((BusinessRuntimeException) tre.getCause());
        }
        if (tre.getCause() instanceof HttpBusinessException) {
            return handleHttpBusinessException((HttpBusinessException) tre.getCause());
        }
        EXC_LOGGER.error("[P] Presentation Layer Exception: {}", tre.getLocalizedMessage(), tre);
        return new ResponseEntity<>(Response.newBuilder()
                .withMessage(tre.getMessage())
                .withMessageKey(tre.getMessageKey())
                .withHttpStatus(String.valueOf(HttpStatus.BAD_GATEWAY.value()))
                .withObj(tre.getData())
                .build(),
                HttpStatus.BAD_GATEWAY
        );
    }

    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Response<?>> IllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(
                Response.newBuilder()
                        .withMessage(ex.getMessage())
                        .withMessageKey(ExceptionCodes.INVALID_PARAMETER_ERROR)
                        .withHttpStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    protected ResponseEntity<Response<?>> handleValidationException() {
        return new ResponseEntity<>(
                Response.newBuilder()
                        .withMessage(translate(ExceptionCodes.VALIDATION_ERROR))
                        .withMessageKey(ExceptionCodes.VALIDATION_ERROR)
                        .withHttpStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Response<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> properties = ex.getConstraintViolations().stream().map(c -> c.getPropertyPath().toString()).collect(Collectors.toList());
        return new ResponseEntity<>(
                Response.newBuilder()
                        .withMessage(translate(ExceptionCodes.VALIDATION_ERROR, properties))
                        .withMessageKey(ExceptionCodes.VALIDATION_ERROR)
                        .withHttpStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response<?>> handleException(Exception ex) {
        EXC_LOGGER.error("[P] Presentation Layer Exception: " + ex.getLocalizedMessage(), ex);
        return new ResponseEntity<>(Response.newBuilder()
                .withMessage(ex.getMessage())
                .withMessageKey(ExceptionCodes.TECHNICAL_RT_ERROR)
                .withHttpStatus(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    /**
     * Get the messageSource.
     *
     * @param key The error code to search message text for
     * @param objects Any arguments that are passed into the message text
     * @return the messageSource.
     */
    protected String translate(String key, Object... objects) {
        return messageSource.getMessage(key, objects, Locale.getDefault());
    }

    /**
     * Build a response object that signals a not-okay response with a given status {@code code}.
     *
     * @param <T> Some type extending the AbstractBase entity
     * @param code The status code to set as response code
     * @param msg The error message passed to the caller
     * @param msgKey The error message key passed to the caller
     * @param params A set of Serializable objects that are passed to the caller
     * @return A ResponseEntity with status {@code code}
     */
    protected <T extends AbstractBase> ResponseEntity<Response<T>> buildResponse(HttpStatus code, String msg, String msgKey, T... params) {
        Response result = Response.newBuilder().withMessage(msg).withMessageKey(msgKey).withHttpStatus(code.toString()).withObj(params).build();
        return new ResponseEntity<>(result, code);
    }

    /**
     * Build a response object that signals a not-okay response with a given status {@code code} and with given http headers.
     *
     * @param <T> Some type extending the AbstractBase entity
     * @param code The status code to set as response code
     * @param msg The error message passed to the caller
     * @param msgKey The error message key passed to the caller
     * @param headers The map of headers.
     * @param params A set of Serializable objects that are passed to the caller
     * @return A ResponseEntity with status {@code code}
     */
    protected <T extends AbstractBase> ResponseEntity<Response<T>> buildResponse(HttpStatus code, String msg, String msgKey, MultiValueMap<String, String> headers, T... params) {
        Response result = Response.newBuilder().withMessage(msg).withMessageKey(msgKey).withHttpStatus(code.toString()).withObj(params).build();
        return new ResponseEntity<>(result, headers, code);
    }

    /**
     * Build an response object that signals a success response to the caller.
     *
     * @param <T> Some type extending the AbstractBase entity
     * @param params A set of Serializable objects that are passed to the caller
     * @return A ResponseEntity with status {@link HttpStatus#OK}
     */
    protected <T extends AbstractBase> ResponseEntity<Response<T>> buildOKResponse(T... params) {
        return buildResponse(HttpStatus.OK, "", "", params);
    }

    /**
     * Build a response object that signals a not-okay response with a given status {@code code}.
     *
     * @param <T> Some type extending the AbstractBase entity
     * @param code The status code to set as response code
     * @param msg The error message passed to the caller
     * @param msgKey The error message key passed to the caller
     * @param params A set of Serializable objects that are passed to the caller
     * @return A ResponseEntity with status {@code code}
     */
    protected <T extends AbstractBase> ResponseEntity<Response<T>> buildNOKResponseWithKey(HttpStatus code, String msg, String msgKey, T... params) {
        return buildResponse(code, msg, msgKey, params);
    }

    /**
     * Build a response object that signals a not-okay response with a given status {@code code}.
     *
     * @param <T> Some type extending the AbstractBase entity
     * @param code The status code to set as response code
     * @param msg The error message passed to the caller
     * @param params A set of Serializable objects that are passed to the caller
     * @return A ResponseEntity with status {@code code}
     */
    protected <T extends AbstractBase> ResponseEntity<Response<T>> buildNOKResponse(HttpStatus code, String msg, T... params) {
        return buildResponse(code, msg, "", params);
    }

    /**
     * Append the ID of the object that was created to the original request URL and return it.
     *
     * @param req The HttpServletRequest object
     * @param objId The ID to append
     * @return The complete appended URL as String
     */
    protected String getLocationForCreatedResource(HttpServletRequest req, String objId) {
        return getLocationURIForCreatedResource(req, objId).toASCIIString();
    }

    /**
     * Append the ID of the object that was created to the original request URL and return it.
     *
     * @param req The HttpServletRequest object
     * @param objId The ID to append
     * @return The complete appended URL
     */
    protected URI getLocationURIForCreatedResource(HttpServletRequest req, String objId) {
        return new UriTemplate(req.getRequestURL().append("/{objId}/").toString()).expand(objId);
    }
}
