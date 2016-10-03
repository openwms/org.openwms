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
package org.openwms.tms.routing;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * A ActionRepository.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
interface ActionRepository extends JpaRepository<Action, Long> {

    @Query("select a from Action a where a.route = :route and a.locationKey is not null and a.locationKey = :locationKey and a.enabled = true")
    Optional<Action> findByRouteAndLocationKey(@Param("route") Route route, @Param("locationKey") String locationKey);

    @Query("select a from Action a where a.route = :route and a.locationGroupName is not null and a.locationGroupName = :locationGroupName and a.enabled = true")
    Optional<Action> findByRouteAndLocationGroupName(@Param("route") Route route, @Param("locationGroupName") String locationGroupName);
}
