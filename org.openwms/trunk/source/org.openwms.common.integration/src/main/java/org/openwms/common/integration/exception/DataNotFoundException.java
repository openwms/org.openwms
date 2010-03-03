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
package org.openwms.common.integration.exception;

import java.io.Serializable;

/**
 * A DataNotFoundException.
 * <p>
 * This type of {@link DataException} signals that the expected data was not
 * found in the persistence layer.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.integration.exception.DataException
 */
public class DataNotFoundException extends DataException {

    private static final long serialVersionUID = 2524686849100170713L;

    /**
     * Create a new DataNotFoundException with a message text and the cause
     * exception.
     * 
     * @param message
     *            - Message text as String
     * @param cause
     *            - The cause exception
     */
    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Create a new DataNotFoundException with a message text.
     * 
     * @param message
     *            - Message text as String
     */
    public DataNotFoundException(String message) {
        super(message);
    }

    /**
     * Create a new DataNotFoundException with the id of the expected Entity.
     * 
     * @param id
     *            - Id of the expected Entity
     */
    public DataNotFoundException(Serializable id) {
        super("Entity class not found in persistence layer, id: " + id);
    }

}