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
 * An IllegalConfigurationValueException is thrown to signal an invalid or unexpected configured value, usually derived from a configuration
 * file that is maintained by developers or users.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class IllegalConfigurationValueException extends RuntimeException {

    private static final long serialVersionUID = 9135752163552012062L;

    /**
     * Create a new IllegalConfigurationValueException.
     */
    public IllegalConfigurationValueException() {}

    /**
     * Create a new IllegalConfigurationValueException.
     * 
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public IllegalConfigurationValueException(String message) {
        super(message);
    }

    /**
     * Create a new IllegalConfigurationValueException.
     * 
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     *            indicates that the cause is nonexistent or unknown.)
     */
    public IllegalConfigurationValueException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new IllegalConfigurationValueException.
     * 
     * @param message
     *            the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     * @param cause
     *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A <tt>null</tt> value is permitted, and
     *            indicates that the cause is nonexistent or unknown.)
     */
    public IllegalConfigurationValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
