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
package org.openwms.core.service;

import org.openwms.core.domain.system.usermanagement.User;

/**
 * An UserHolder exposes an {@link User} object to its clients. The main purpose
 * of this interface is the strict disjunction between the API client and the
 * implementation class, that is kept in the impl bundle.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: 1484 $
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.User
 */
public interface UserHolder {

    /**
     * Return the shared {@link User} object.
     * 
     * @return The wrapped {@link User}
     */
    User getUser();
}
