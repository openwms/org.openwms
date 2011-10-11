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

import org.openwms.core.service.exception.ServiceRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A CoreServiceAdvice.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Component("coreServiceAdvice")
public class CoreServiceAdvice {

    private final static Logger logger = LoggerFactory.getLogger(CoreServiceAdvice.class);

    /**
     * FIXME [scherrer] Comment this
     * 
     * @param ex
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
