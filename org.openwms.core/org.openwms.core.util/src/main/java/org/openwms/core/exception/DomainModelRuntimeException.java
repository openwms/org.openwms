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
package org.openwms.core.exception;

/**
 * A DomainModelRuntimeException is an unchecked top-level exception for all unhandled runtime exceptions of the domain model layer.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.2
 */
public class DomainModelRuntimeException extends RuntimeException {

    private static final long serialVersionUID = -6747177840025814903L;

    /**
     * Create a new DomainModelRuntimeException.
     */
    public DomainModelRuntimeException() {

    }

    /**
     * Create a new DomainModelRuntimeException.
     * 
     * @param message
     *            Detail message
     */
    public DomainModelRuntimeException(String message) {
        super(message);
    }

    /**
     * Create a new DomainModelRuntimeException.
     * 
     * @param cause
     *            Root cause
     */
    public DomainModelRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new DomainModelRuntimeException.
     * 
     * @param message
     *            Detail message
     * @param cause
     *            Root cause
     */
    public DomainModelRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}