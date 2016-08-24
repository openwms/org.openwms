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
import org.springframework.data.jpa.repository.Query;

/**
 * A SecurityObjectRepository is used to find, modify and delete {@link SecurityObject}s.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
interface SecurityObjectRepository extends JpaRepository<SecurityObject, Long> {

    /**
     * Find and return all {@link SecurityObject}s that belong to a given {@code Module}.
     *
     * @param moduleName The name of the {@code Module}
     * @return a list of {@link SecurityObject}s. {@literal null} might be possible as well, see the particular implementation
     */
    @Query("select g from Grant g where g.name like :moduleName")
    List<Grant> findAllOfModule(String moduleName);

    @Query("select g from Grant g")
    List<Grant> findAllGrants();
}