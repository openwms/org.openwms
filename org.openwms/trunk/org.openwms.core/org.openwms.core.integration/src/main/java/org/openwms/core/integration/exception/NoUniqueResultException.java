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
package org.openwms.core.integration.exception;

/**
 * A NoUniqueResultException.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class NoUniqueResultException extends DataException {

    private static final long serialVersionUID = -7411247631016989719L;

    /**
     * Create a new <code>NoUniqueResultException</code>.
     */
    public NoUniqueResultException() {}

    /**
     * Create a new <code>NoUniqueResultException</code> with a message text.
     * 
     * @param message
     *            Message text as String
     */
    public NoUniqueResultException(String message) {
        super(message);
    }

    /**
     * Create a new <code>NoUniqueResultException</code> with the root
     * exception.
     * 
     * @param cause
     *            The root exception
     */
    public NoUniqueResultException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new <code>NoUniqueResultException</code> with a message text and
     * the root exception.
     * 
     * @param message
     *            Message text as String
     * @param cause
     *            The root exception
     */
    public NoUniqueResultException(String message, Throwable cause) {
        super(message, cause);
    }

}
