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
package org.openwms.core.integration.aop;

import org.apache.commons.lang.time.StopWatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.openwms.core.integration.exception.IntegrationRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A CoreIntegrationAdvice is used in conjunction with an AOP aspect to around
 * repositories and services within the integration layer.
 * <p>
 * So far it is used to translate exceptions into a
 * {@link IntegrationRuntimeException}s and to log time consumption of public
 * methods. Activation is done in XML instead of using Springs AOP annotations.
 * </p>
 * <p>
 * The advice can be referenced by name {@value #COMPONENT_NAME}.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.core.integration.exception.IntegrationRuntimeException
 */
@Component(CoreIntegrationAdvice.COMPONENT_NAME)
public class CoreIntegrationAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreIntegrationAdvice.class);
    /** Springs component name. */
    public static final String COMPONENT_NAME = "coreIntegrationAdvice";

    /**
     * Called around any method invocation to log time consumption of each
     * method call.
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
            LOGGER.debug("---->> calling: " + pjp.toShortString());
        }
        try {
            return pjp.proceed();
        } finally {
            if (LOGGER.isDebugEnabled() && sw != null) {
                sw.stop();
                LOGGER.debug("<<---- took [ms]: " + sw.getTime());
            }
        }
    }

    /**
     * Called after an exception is thrown within the integration layer. If the
     * exception is not of type {@link IntegrationRuntimeException} it is
     * wrapped by a new {@link IntegrationRuntimeException}.
     * <p>
     * Set tracing to level WARN to log the root cause.
     * </p>
     * 
     * @param ex
     *            The root exception that is thrown
     */
    public void afterThrowing(Throwable ex) {
        if (LOGGER.isWarnEnabled()) {
            LOGGER.warn("Integration layer exception: " + ex);
        }
        if (ex instanceof IntegrationRuntimeException) {
            return;
        }
        throw new IntegrationRuntimeException(ex);
    }
}