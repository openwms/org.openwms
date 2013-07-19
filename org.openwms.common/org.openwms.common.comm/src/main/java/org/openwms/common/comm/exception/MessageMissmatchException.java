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
package org.openwms.common.comm.exception;

/**
 * A MessageMissmatchException indicates that an incoming message is not in
 * expected format.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class MessageMissmatchException extends MessageProcessingException {

    private static final long serialVersionUID = -2992101871707320349L;

    /**
     * Create a new MessageMissmatchException.
     */
    public MessageMissmatchException() {

    }

    /**
     * Create a new MessageMissmatchException.
     * 
     * @param message
     *            Detail message
     */
    public MessageMissmatchException(String message) {
        super(message);
    }

    /**
     * Create a new MessageMissmatchException.
     * 
     * @param cause
     *            Root cause
     */
    public MessageMissmatchException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new MessageMissmatchException.
     * 
     * @param message
     *            Detail message
     * @param cause
     *            Root cause
     */
    public MessageMissmatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
