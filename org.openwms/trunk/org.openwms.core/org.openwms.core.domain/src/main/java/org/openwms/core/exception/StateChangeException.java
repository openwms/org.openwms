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
package org.openwms.core.exception;

/**
 * A StateChangeException indicates that it is not allowed to switch the state
 * of an entity.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class StateChangeException extends DomainModelException {

    private static final long serialVersionUID = 7844172843875393420L;

    /**
     * Create a new StateChangeException.
     * 
     * @param message
     *            Detail message
     */
    public StateChangeException(String message) {
        super(message);
    }

    /**
     * Create a new StateChangeException.
     * 
     * @param cause
     *            Root cause
     */
    public StateChangeException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new StateChangeException.
     * 
     * @param message
     *            Detail message
     * @param cause
     *            Root cause
     */
    public StateChangeException(String message, Throwable cause) {
        super(message, cause);
    }
}
