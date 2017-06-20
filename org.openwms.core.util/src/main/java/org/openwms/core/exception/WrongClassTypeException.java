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
 * A WrongClassTypeException.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class WrongClassTypeException extends RuntimeException {

    private static final long serialVersionUID = -3585376916183338194L;

    /**
     * Create a new <code>WrongClassTypeException</code> with a message text.
     */
    public WrongClassTypeException() {
        super();
    }

    /**
     * Create a new <code>WrongClassTypeException</code> with a message text.
     * 
     * @param message
     *            Message text as String
     */
    public WrongClassTypeException(String message) {
        super(message);
    }

    /**
     * Create a new <code>WrongClassTypeException</code> with the root exception.
     * 
     * @param cause
     *            The root exception
     */
    public WrongClassTypeException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new <code>WrongClassTypeException</code> with a message text and the root exception.
     * 
     * @param message
     *            Message text as String
     * @param cause
     *            The root exception
     */
    public WrongClassTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}