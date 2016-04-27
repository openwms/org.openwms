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
package org.openwms.core.aop;

import javax.validation.ValidationException;
import javax.validation.Validator;

import org.ameba.exception.ServiceLayerException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.openwms.core.ExceptionCodes;
import org.openwms.core.util.logging.LoggingCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * A CoreServiceAspect is in conjunction with an AOP aspect for Core services.
 * <p>
 * So far it is used to translate all exceptions into a
 * {@link ServiceLayerException} and tracing of methods time consumption.
 * Activation is done in XML instead of using Springs AOP annotations.
 * </p>
 * <p>
 * The aspect can be referenced by name {@value #COMPONENT_NAME}.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see ServiceLayerException
 */
@Component(CoreServiceAspect.COMPONENT_NAME)
public class CoreServiceAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingCategories.CALL_STACK_LOGGING);
    private static final Logger EXC_LOGGER = LoggerFactory.getLogger(LoggingCategories.SERVICE_EXCEPTION);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    @Qualifier("coreSpringValidator")
    private Validator validator;

    /** Springs component name. */
    public static final String COMPONENT_NAME = "coreServiceAspect";

    /**
     * Called around any service method invocation to log time consumption of
     * each method call.
     * 
     * @param pjp
     *            the ProceedingJoinPoint object
     * @return the return value of the service method invocation
     * @throws Throwable
     *             any exception thrown by the method invocation
     */
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        StopWatch sw = null;
        if (LOGGER.isDebugEnabled()) {
            sw = new StopWatch();
            sw.start();
            LOGGER.debug("[S]>> Method call: " + pjp.toShortString());
        }
        try {
            Object[] args = pjp.getArgs();
            if (args != null && args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    if (args[i] != null) {
                        validator.validate(args[i]);
                    }
                }
            }
            return pjp.proceed();
        } finally {
            if (LOGGER.isDebugEnabled() && sw != null) {
                sw.stop();
                LOGGER.debug("[S]<< " + pjp.toShortString() + " took about [ms]: " + sw.getTotalTimeMillis());
            }
        }
    }

    /**
     * Called after an exception is thrown by classes of the Core service layer.
     * If the exception is not of type {@link ServiceLayerException} it is
     * wrapped by a new {@link ServiceLayerException}. We explicitly don't
     * re-throw unknown exceptions and throw {@link ServiceLayerException}s
     * with a general message text instead, because of the internal lowlevel
     * exception messages aren't very useful and interesting for components
     * calling the service layer.
     * <p>
     * Turn tracing to level ERROR to log the root cause.
     * </p>
     * 
     * @param ex
     *            The root exception that is thrown
     */
    public void afterThrowing(Throwable ex) {
        if (EXC_LOGGER.isErrorEnabled()) {
            EXC_LOGGER.error("[S] Service Layer Exception: " + ex.getLocalizedMessage(), ex);
        }
        if (ex instanceof ServiceLayerException) {
            throw ((ServiceLayerException) ex);
        }
        if (ex.getClass().equals(ValidationException.class)) {
            throw new ServiceLayerException(ex.getMessage());
        }

        throw new ServiceLayerException(messageSource.getMessage(ExceptionCodes.TECHNICAL_RT_ERROR, null, null));
    }
}