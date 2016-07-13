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

import org.openwms.common.location.LocationPK;

/**
 * A TransportService offers functionality to create, read, update and delete {@link TransportUnit}s. Additionally it defines useful methods
 * regarding the general handling with {@link TransportUnit}s.
 *
 * @param <T> Any kind of {@link TransportUnit}
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
interface TransportUnitService<T extends TransportUnit> {

    /**
     * Create a new {@link TransportUnit} with the type {@link TransportUnitType} placed on an initial {@code Location}. The new {@link
     * TransportUnit} has the given {@link Barcode} as identifier.
     *
     * @param barcode {@link Barcode} of the new {@link TransportUnit}
     * @param transportUnitType The type of the new {@link TransportUnit}
     * @param actualLocation The {@code Location} where the {@link TransportUnit} is placed on
     * @return The new created {@link TransportUnit} instance
     */
    T create(Barcode barcode, TransportUnitType transportUnitType, LocationPK actualLocation);

    /**
     * Move a {@link TransportUnit} identified by its {@link Barcode} to the given target {@code Location} identified by the {@code
     * LocationPK}.
     *
     * @param barcode {@link Barcode} of the {@link TransportUnit} to move
     * @param targetLocationPK Unique identifier of the target {@code Location}
     */
    // FIXME [scherrer] : Use Target instead
    void moveTransportUnit(Barcode barcode, LocationPK targetLocationPK);

    /**
     * Delete already persisted {@link TransportUnit}s from the persistence storage. It is not allowed in all cases to delete a {@link
     * TransportUnit} , potentially an active {@code TransportOrder} exists or Inventory is still linked with the {@code transportUnit}.
     *
     * @param transportUnits The collection of {@link TransportUnit}s to delete
     */
    void deleteTransportUnits(List<T> transportUnits);

    /**
     * Returns a List of all {@link TransportUnit}s.
     *
     * @return A List of all {@link TransportUnit}s
     * @deprecated Move to UI specific interface
     */
    @Deprecated
    List<T> findAll();

    /**
     * Find and return a {@link TransportUnit} with a particular {@link Barcode} .
     *
     * @param barcode {@link Barcode} of the {@link TransportUnit} to search for
     * @return The {@link TransportUnit} or <code>null</code> when no {@link TransportUnit} with this <code>barcode</code> exists
     * @deprecated Move to UI specific interface
     */
    @Deprecated
    T findByBarcode(Barcode barcode);
}