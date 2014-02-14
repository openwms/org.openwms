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
package org.openwms.core.service.exception;

/**
 * A ServiceRuntimeException is an unchecked application exception thrown in service layer classes.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class ServiceRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -40522023561564642L;

    /**
     * Create a new ServiceRuntimeException with a message text.
     * 
     * @param message
     *            Detail message
     */
    public ServiceRuntimeException(String message) {
        super(message);
    }

    /**
     * Create a new ServiceRuntimeException with a cause exception.
     * 
     * @param cause
     *            Root cause
     */
    public ServiceRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new ServiceRuntimeException with a message text and the cause exception.
     * 
     * @param message
     *            Detail message
     * @param cause
     *            Root cause
     */
    public ServiceRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Throw an {@link ServiceRuntimeException} if <code>obj</code> is <code>null</code> .
     * 
     * @param obj
     *            the Object to be checked against <code>null</code>
     * @param msg
     *            the message text of the thrown exception
     * @throws ServiceRuntimeException
     *             if <code>obj</code> is <code>null</code>
     */
    public static void throwIfNull(Object obj, String msg) {
        if (obj == null) {
            throw new ServiceRuntimeException(msg);
        }
    }
}