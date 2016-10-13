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
package org.openwms.common.comm;

/**
 * A MessageMismatchException indicates that an incoming message is not in expected format.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public class MessageMismatchException extends MessageProcessingException {

    /** Create a new MessageMismatchException. */
    public MessageMismatchException() {
    }

    /**
     * Create a new MessageMismatchException.
     *
     * @param message Detail message
     */
    public MessageMismatchException(String message) {
        super(message);
    }

    /**
     * Create a new MessageMismatchException.
     *
     * @param cause Root cause
     */
    public MessageMismatchException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new MessageMismatchException.
     *
     * @param message Detail message
     * @param cause Root cause
     */
    public MessageMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
