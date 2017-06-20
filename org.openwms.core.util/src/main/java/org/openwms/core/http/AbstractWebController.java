/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.io.Serializable;

/**
 * A AbstractWebController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
public abstract class AbstractWebController {

    private static final Logger EXC_LOGGER = LoggerFactory.getLogger(LoggingCategories.PRESENTATION_LAYER_EXCEPTION);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private Validator validator;

    /**
     * All general exceptions thrown by services are caught here and translated into http conform responses with a status code {@code 500
     * Internal Server Error}.
     *
     * @param ex The exception occurred
     * @return A response object wraps the server result
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Serializable>> handleException(Exception ex) {
        EXC_LOGGER.error("[P] Presentation Layer Exception: " + ex.getLocalizedMessage(), ex);
        if (ex instanceof BehaviorAwareException) {
            BehaviorAwareException bae = (BehaviorAwareException) ex;
            return bae.toResponse(bae.getData());
        }
        if (ex instanceof BusinessRuntimeException) {
            BusinessRuntimeException bre = (BusinessRuntimeException) ex;
            return new ResponseEntity<>(new Response<>(ex.getMessage(), bre.getMsgKey(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), new String[]{bre.getMsgKey()}), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (ex instanceof HttpBusinessException) {
            HttpBusinessException e = (HttpBusinessException) ex;
            return new ResponseEntity<>(new Response<>(ex.getMessage(), e.getHttpStatus().toString()), e.getHttpStatus());
        }
        if (ex instanceof ValidationException) {
            return new ResponseEntity<>(new Response<>(ex.getMessage(), HttpStatus.BAD_REQUEST.toString()),
                    HttpStatus.BAD_REQUEST);
        }
        if (ex instanceof TechnicalRuntimeException) {
            TechnicalRuntimeException be = (TechnicalRuntimeException) ex;
            return new ResponseEntity<>(new Response<>(ex.getMessage(), HttpStatus.BAD_GATEWAY.toString()),
                    HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>(new Response<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.toString()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Transforming {@link MethodArgumentNotValidException} {@link ValidationException} into server error {@code 500 Internal Server Error}
     * responses with according validation error message text.
     *
     * @return A response object wraps the server result
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, ValidationException.class})
    public ResponseEntity<Response> handleValidationException() {
        return new ResponseEntity<>(new Response(translate(ExceptionCodes.VALIDATION_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR.toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the messageSource.
     *
     * @param key The error code to search message text for
     * @param objects Any arguments that are passed into the message text
     * @return the messageSource.
     */
    protected String translate(String key, Object... objects) {
        return messageSource.getMessage(key, objects, null);
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
        Response result = new Response(msg, msgKey, code.toString(), params);
        //result.add(linkTo(this.getClass()).withSelfRel());
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
        Response result = new Response(msg, msgKey, code.toString(), params);
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
     * @return The complete appended URL
     */
    protected String getLocationForCreatedResource(HttpServletRequest req, String objId) {
        StringBuffer url = req.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{objId}/").toString());
        return template.expand(objId).toASCIIString();
    }
}
