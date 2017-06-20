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
 * An InvalidPasswordException indicates that a password is not confirm with the defined rules.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public class InvalidPasswordException extends DomainModelException {

    private static final long serialVersionUID = 8109794333683306511L;

    /**
     * Create a new InvalidPasswordException.
     */
    public InvalidPasswordException() {
        super();
    }

    /**
     * Create a new InvalidPasswordException.
     * 
     * @param message
     *            The message text
     */
    public InvalidPasswordException(String message) {
        super(message);
    }

    /**
     * Create a new InvalidPasswordException.
     * 
     * @param cause
     *            The root cause of the exception
     */
    public InvalidPasswordException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new InvalidPasswordException.
     * 
     * @param message
     *            The message text
     * @param cause
     *            The root cause of the exception
     */
    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}