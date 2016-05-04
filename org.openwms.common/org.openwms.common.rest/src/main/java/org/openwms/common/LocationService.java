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
package org.openwms.common;

import java.util.List;

import org.openwms.core.system.Message;

/**
 * A LocationService offers some useful methods regarding the general handling of {@link Location}s.
 * <p>
 * This interface is declared generic typed that implementation classes can use any extension of {@link Location}s.
 * </p>
 * 
 * @param <T>
 *            Any kind of {@link Location}
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface LocationService<T extends Location> {

    /**
     * Return a list of all {@link Location}s not sorted and not filtered in natural order.
     * 
     * @return All {@link Location}s as a list
     */
    List<T> getAllLocations();

    /**
     * Removes a list of {@link Message}s from a Location.
     * 
     * @param id
     *            The technical key of the Location
     * @param messages
     *            The messages to be removed
     * @return The updated Location
     */
    Location removeMessages(Long id, List<Message> messages);

    /**
     * Return a list of all {@link LocationType}s not sorted and not filtered in natural order.
     * 
     * @return All {@link LocationType}s as a list
     */
    List<LocationType> getAllLocationTypes();

    /**
     * Create a new {@link LocationType}.
     * 
     * @param locationType
     *            The type to be created
     */
    void createLocationType(LocationType locationType);

    /**
     * Delete already persisted {@link LocationType} instances.
     * 
     * @param locationTypes
     *            A list of all instances to be deleted.
     */
    void deleteLocationTypes(List<LocationType> locationTypes);

    /**
     * Saves a {@link LocationType}.
     * 
     * @param locationType
     *            The type to save
     * @return The saved type
     */
    LocationType saveLocationType(LocationType locationType);
}