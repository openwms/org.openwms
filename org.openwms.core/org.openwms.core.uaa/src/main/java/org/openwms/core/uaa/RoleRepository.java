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
package org.openwms.core.uaa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A RoleRepository offers functionality to find and modify {@link Role} entity classes.
 *
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version 0.2
 * @since 0.1
 */
interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Remove a collection of {@link SecurityObject}s or {@link Grant}s from all Roles. This method is useful to unassign {@link Grant}s
     * before they're going to be removed.
     *
     * @param securityObjects The collection of {@link SecurityObject}s to be unassigned
     * @since 0.2
     */
    void removeFromRoles(List<? extends SecurityObject> securityObjects);
}