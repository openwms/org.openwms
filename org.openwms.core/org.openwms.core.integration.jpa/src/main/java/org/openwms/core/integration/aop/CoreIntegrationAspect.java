/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
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
package org.openwms.core.integration.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.openwms.core.integration.exception.IntegrationRuntimeException;
import org.openwms.core.util.logging.LoggingCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A CoreIntegrationAspect is used in conjunction with an AOP aspect to around repositories and services within the integration layer.
 * <p>
 * So far it is used to translate exceptions into a {@link IntegrationRuntimeException}s and to log time consumption of public methods.
 * Activation is done in XML instead of using Springs AOP annotations.
 * </p>
 * <p>
 * The aspect can be referenced by name {@value #COMPONENT_NAME}.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 * @see org.openwms.core.integration.exception.IntegrationRuntimeException
 */
@Component(CoreIntegrationAspect.COMPONENT_NAME)
public class CoreIntegrationAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingCategories.CALL_STACK_LOGGING);
    private static final Logger EXC_LOGGER = LoggerFactory.getLogger(LoggingCategories.INTEGRATION_EXCEPTION);
    /** Springs component name. */
    public static final String COMPONENT_NAME = "coreIntegrationAspect";
    @PersistenceContext
    private EntityManager em;

    /**
     * Called around any method invocation to log time consumption of each method call.
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
            LOGGER.debug("[I]>>> Method call: " + pjp.toShortString());
        }
        try {
            return pjp.proceed();
        } finally {
            try {
                em.flush();
            } catch (Exception ex) {
                afterThrowing(ex);
            } finally {
                if (LOGGER.isDebugEnabled() && sw != null) {
                    sw.stop();
                    LOGGER.debug("[I]<<< " + pjp.toShortString() + " took [ms]: " + sw.getTime());
                }
            }
        }
    }

    /**
     * Called after an exception is thrown within the integration layer. If the exception is not of type {@link IntegrationRuntimeException}
     * it is wrapped by a new {@link IntegrationRuntimeException}.
     * <p>
     * Set tracing to level ERROR to log the root cause.
     * </p>
     * 
     * @param ex
     *            The root exception that is thrown
     */
    public void afterThrowing(Throwable ex) {
        if (EXC_LOGGER.isErrorEnabled()) {
            EXC_LOGGER.error("[I] Integration Layer Exception: " + ex);
        }
        if (ex instanceof IntegrationRuntimeException) {
            throw (IntegrationRuntimeException) ex;
        }
        throw new IntegrationRuntimeException(ex.getMessage());
    }
}