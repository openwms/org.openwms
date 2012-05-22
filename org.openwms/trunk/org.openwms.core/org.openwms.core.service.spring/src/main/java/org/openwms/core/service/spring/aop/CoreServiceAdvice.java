/*
 * openwms.org, the Open Warehouse Management System.
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
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service.spring.aop;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.util.logging.LoggingCategories;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A CoreServiceAdvice is in conjunction with an AOP aspect for Core services.
 * <p>
 * So far it is used to translate all exceptions into a
 * {@link ServiceRuntimeException} and tracing of methods time consumption.
 * Activation is done in XML instead of using Springs AOP annotations.
 * </p>
 * <p>
 * The advice can be referenced by name {@value #COMPONENT_NAME}.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.core.service.exception.ServiceRuntimeException
 */
@Component(CoreServiceAdvice.COMPONENT_NAME)
public class CoreServiceAdvice {
    // FIXME [scherrer] : rename class to ...Aspect
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingCategories.CALL_STACK_LOGGING);
    private static final Logger EXC_LOGGER = LoggerFactory.getLogger(LoggingCategories.SERVICE_EXCEPTION);
    /** Springs component name. */
    public static final String COMPONENT_NAME = "coreServiceAdvice";

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
            return pjp.proceed();
        } finally {
            if (LOGGER.isDebugEnabled() && sw != null) {
                sw.stop();
                LOGGER.debug("[S]<< " + pjp.toShortString() + " took about [ms]: " + sw.getTime());
            }
        }
    }

    /**
     * Called after an exception is thrown by classes of the Core service layer.
     * If the exception is not of type {@link ServiceRuntimeException} it is
     * wrapped by a new {@link ServiceRuntimeException}.
     * <p>
     * Turn tracing to level WARN to log the root cause.
     * </p>
     * 
     * @param ex
     *            The root exception that is thrown
     */
    public void afterThrowing(Throwable ex) {
        if (EXC_LOGGER.isErrorEnabled()) {
            EXC_LOGGER.error("[S] Service Layer Exception: " + ex);
        }
        if (ex instanceof ServiceRuntimeException) {
            throw (ServiceRuntimeException) ex;
        }
        throw new ServiceRuntimeException(ex);
    }
}