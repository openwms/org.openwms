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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A CoreServiceAdvice is used as AOP aspect for Core services. So far it is
 * used for exception translation into a {@link ServiceRuntimeException} and
 * time consumption tracing. Activation is done in XML instead of using Springs
 * AOP annotations.
 * <p>
 * The advice can be referenced by name <code>coreServiceAdvice</code>
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.core.service.exception.ServiceRuntimeException
 */
@Component("coreServiceAdvice")
public class CoreServiceAdvice {

    private static final Logger logger = LoggerFactory.getLogger(CoreServiceAdvice.class);

    /**
     * Called around any service invocation to log time consumption of the
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
        if (logger.isDebugEnabled()) {
            sw = new StopWatch();
            sw.start();
            logger.debug(">> Calling " + pjp.toShortString());
        }
        try {
            return pjp.proceed();
        } finally {
            if (logger.isDebugEnabled() && sw != null) {
                sw.stop();
                logger.debug("<< took about [ms] " + sw.getTime());
            }
        }
    }

    /**
     * Called after an exception is thrown by classes of the Core service layer.
     * If the exception is not of type {@link ServiceRuntimeException} it is
     * wrapped by a new {@link ServiceRuntimeException}. Turn tracing to level
     * <p>
     * WARN to log the root cause.
     * </p>
     * 
     * @param ex
     *            The root exception that is thrown
     */
    public void afterThrowing(Throwable ex) {
        if (logger.isWarnEnabled()) {
            logger.warn("Service Layer Exception: " + ex);
        }
        if (ServiceRuntimeException.class.equals(ex)) {
            return;
        }
        throw new ServiceRuntimeException(ex);
    }
}
