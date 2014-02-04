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

import java.util.List;

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.service.exception.EntityNotFoundException;

/**
 * A RoleService provides business functionality regarding the handling with {@link Role}s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.Role
 */
// TODO [scherrer] : Remove to direct references to business and technical key, because it shall be up to model to
// define these keys.
public interface RoleService {

    /**
     * Return a {@link Role} identified by the business key, the <tt>name</tt> of the Role.
     * 
     * @param name
     *            The role's name
     * @return The Role or <code>null</code>
     * @throws EntityNotFoundException
     *             when no Role with BK <tt>name</tt> found
     */
    Role findByBK(String name);

    /**
     * Return a list of all existing {@link Role}s.
     * 
     * @return A list of {@link Role}s or an empty list when no {@link Role}s were found
     */
    List<Role> findAll();

    /**
     * Save an already existed {@link Role} and return the saved instance.
     * 
     * @param role
     *            The {@link Role} to be saved
     * @return The saved {@link Role}
     */
    Role save(Role role);

    /**
     * Remove a {@link Role}.
     * 
     * @param id
     *            The technical unique identifier of the {@link Role} instance to be removed.
     */
    void remove(Long id);

    /**
     * Remove a {@link Role}.
     * 
     * @param name
     *            The role's name
     */
    void removeByBK(String name);
}