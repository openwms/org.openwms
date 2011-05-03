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
package org.openwms.core.service.voter;

import org.openwms.core.service.exception.ServiceException;

/**
 * A DeniedException. Is thrown by {@link DecisionVoter}s in case a business
 * action is not allowed to be fulfilled.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class DeniedException extends ServiceException {

    private static final long serialVersionUID = -7312236498183224626L;

    /**
     * Create a new DeniedException.
     * 
     * @param message
     *            Detail message
     */
    public DeniedException(String message) {
        super(message);
    }

    /**
     * Create a new DeniedException.
     * 
     * @param cause
     *            Root cause
     */
    public DeniedException(Throwable cause) {
        super(cause);
    }

    /**
     * Create a new DeniedException.
     * 
     * @param message
     *            Detail message
     * @param cause
     *            Root cause
     */
    public DeniedException(String message, Throwable cause) {
        super(message, cause);
    }

}
