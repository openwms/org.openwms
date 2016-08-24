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
package org.openwms.common.transport;

import java.util.List;

import org.openwms.common.location.LocationType;

/**
 * A TransportUnitTypeService offers methods to deal with {@link TransportUnitType}s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
interface TransportUnitTypeService {

    /**
     * Returns a List of all {@link TransportUnitType}s.
     * 
     * @return A list of all {@link TransportUnitType}s
     */
    List<TransportUnitType> findAll();

    /**
     * Create a new {@link TransportUnitType}.
     * 
     * @param transportUnitType
     *            The type to be created
     * @return A new created {@link TransportUnitType} instance.
     */
    TransportUnitType create(TransportUnitType transportUnitType);

    /**
     * Delete already persisted {@link TransportUnitType} instances.
     * 
     * @param transportUnitTypes
     *            A collection of instances to be deleted.
     */
    void deleteType(TransportUnitType... transportUnitTypes);

    /**
     * Save an already existing instance of {@link TransportUnitType}.
     * 
     * @param transportUnitType
     *            The instance to be updated
     * @return The updated instance
     */
    TransportUnitType save(TransportUnitType transportUnitType);

    /**
     * Update the List of {@link org.openwms.common.domain.TypePlacingRule}s for the given {@link TransportUnitType} type.
     * 
     * @param type
     *            The {@link TransportUnitType} to update.
     * @param newAssigned
     *            A new List of {@link LocationType}s that are allowed for the {@link TransportUnitType}.
     * @param newNotAssigned
     *            A List of {@link LocationType}s. All {@link org.openwms.common.domain.TypePlacingRule}s will be removed which have one of
     *            this {@link LocationType}s and the requested {@link TransportUnitType} type.
     * @return The updated {@link TransportUnitType}.
     */
    TransportUnitType updateRules(String type, List<LocationType> newAssigned, List<LocationType> newNotAssigned);

    /**
     * Return a List of all {@link org.openwms.common.domain.Rule}s that belong to this {@link TransportUnitType} type.
     * 
     * @param transportUnitType
     *            The {@link TransportUnitType} to search for.
     * @return The requested List or <code>null</code> if no {@link Rule} was found.
     */
    List<Rule> loadRules(String transportUnitType);
}