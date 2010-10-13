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
 * A RemovalNotAllowedException. Used to indicate that a service is not allowed
 * to remove an entity.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 * @since 0.1
 */
public class RemovalNotAllowedException extends ServiceException implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = -5592508830188199188L;

    /**
     * Create a new RemovalNotAllowedException.
     * 
     */
    public RemovalNotAllowedException() {
        super();
    }

    /**
     * Create a new RemovalNotAllowedException.
     * 
     * @param message
     *            Message text as String
     */
    public RemovalNotAllowedException(String message) {
        super(message);
    }

    /**
     * Create a new RemovalNotAllowedException.
     * 
     * @param cause
     *            The cause exception
     */
    public RemovalNotAllowedException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new RemovalNotAllowedException.
     * 
     * @param message
     *            Message text as String
     * @param cause
     *            The cause exception
     */
    public RemovalNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

}
