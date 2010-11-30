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
package org.openwms.common.service.exception;

import java.io.Serializable;

/**
 * A ServiceRuntimeException. Unchecked application exception.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class ServiceRuntimeException extends RuntimeException implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 3091182786979000919L;

    /**
     * Create a new ServiceRuntimeException.
     */
    public ServiceRuntimeException() {}

    /**
     * Create a new ServiceRuntimeException with a message text.
     * 
     * @param message
     *            Message text as String
     */
    public ServiceRuntimeException(String message) {
        super(message);
    }

    /**
     * Create a new ServiceRuntimeException with a cause exception.
     * 
     * @param cause
     *            The cause exception
     */
    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new ServiceRuntimeException with a message text and the cause
     * exception.
     * 
     * @param message
     *            Message text as String
     * @param cause
     *            The cause exception
     */
    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

}
