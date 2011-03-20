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

/**
 * A RoleService provides business functionality regarding {@link Role}s.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface RoleService {

    /**
     * Return a list of all persisted {@link Role}s.
     * 
     * @return A list of {@link Role}s or an empty list when no {@link Role}s
     *         exist.
     */
    List<Role> findAll();

    /**
     * Save an already existed {@link Role} and return the updated instance.
     * 
     * @param role
     *            The {@link Role} to save.
     * @return The saved {@link Role}.
     */
    Role save(Role role);

    /**
     * Remove a list of {@link Role}s from the persistent storage.
     * 
     * @param roles
     *            The list of {@link Role}s to remove.
     */
    void remove(List<Role> roles);

}
