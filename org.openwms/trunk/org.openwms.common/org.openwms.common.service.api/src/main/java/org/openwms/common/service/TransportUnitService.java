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
package org.openwms.common.service;

import java.util.List;

import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.LocationType;
import org.openwms.common.domain.Rule;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.core.service.EntityService;

/**
 * A TransportService is an extension of the {@link EntityService} interface
 * about some useful methods regarding the general handling with
 * {@link TransportUnit}s.
 * 
 * @param <T>
 *            Any kind of {@link TransportUnit}
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.EntityService
 */
public interface TransportUnitService<T extends TransportUnit> extends EntityService<TransportUnit> {

    /**
     * Create a new {@link TransportUnit} with the type
     * {@link TransportUnitType} placed on an initial <code>Location</code>. The
     * new {@link TransportUnit} has the given {@link Barcode} as identifier.
     * 
     * @param barcode
     *            {@link Barcode} of the new {@link TransportUnit}
     * @param transportUnitType
     *            The type of the new {@link TransportUnit}
     * @param actualLocation
     *            The <code>Location</code> where the {@link TransportUnit} is
     *            placed on
     * @return The new created {@link TransportUnit} instance
     */
    T createTransportUnit(Barcode barcode, TransportUnitType transportUnitType, LocationPK actualLocation);

    /**
     * Move a {@link TransportUnit} identified by its {@link Barcode} to the
     * given target <code>Location</code> identified by the {@link LocationPK}.
     * 
     * @param barcode
     *            {@link Barcode} of the {@link TransportUnit} to move
     * @param targetLocationPK
     *            Unique identifier of the target <code>Location</code>
     */
    void moveTransportUnit(Barcode barcode, LocationPK targetLocationPK);

    /**
     * Delete already persisted {@link TransportUnit}s from the persistence
     * storage. It is not allowed in all cases to delete a {@link TransportUnit}
     * , potentially an active TransportOrder exists or Inventory is still
     * linked with the <tt>transportUnit</tt>.
     * 
     * @param transportUnits
     *            The collection of {@link TransportUnit}s to delete
     */
    void deleteTransportUnits(List<T> transportUnits);

    /**
     * Returns an unsorted List of all {@link TransportUnit}s.
     * 
     * @return A List of all {@link TransportUnit}s
     */
    List<T> getAllTransportUnits();

    /**
     * Returns an unsorted List of all {@link TransportUnitType}s.
     * 
     * @return A list of all {@link TransportUnitType}s
     */
    List<TransportUnitType> getAllTransportUnitTypes();

    /**
     * Create a new {@link TransportUnitType}.
     * 
     * @param transportUnitType
     *            The type to be created
     * @return The new created {@link TransportUnitType} instance.
     */
    TransportUnitType createTransportUnitType(TransportUnitType transportUnitType);

    /**
     * Delete already persisted {@link TransportUnitType} instances.
     * 
     * @param transportUnitType
     *            A list of all instances to be deleted.
     */
    void deleteTransportUnitTypes(List<TransportUnitType> transportUnitType);

    /**
     * Save an already persisted instance of {@link TransportUnitType}.
     * 
     * @param transportUnitType
     *            The instance to be updated
     * @return The updated instance
     */
    TransportUnitType saveTransportUnitType(TransportUnitType transportUnitType);

    /**
     * Update the List of {@link org.openwms.common.domain.TypePlacingRule}s for
     * the given {@link TransportUnitType} type.
     * 
     * @param type
     *            The {@link TransportUnitType} to update.
     * @param newAssigned
     *            A new List of {@link LocationType}s that are allowed for the
     *            {@link TransportUnitType}.
     * @param newNotAssigned
     *            A List of {@link LocationType}s. All
     *            {@link org.openwms.common.domain.TypePlacingRule}s will be
     *            removed which have one of this {@link LocationType}s and the
     *            requested {@link TransportUnitType} type.
     * @return The updated {@link TransportUnitType}.
     */
    TransportUnitType updateRules(String type, List<LocationType> newAssigned, List<LocationType> newNotAssigned);

    /**
     * Return a List of all {@link org.openwms.common.domain.Rule}s that belong
     * to this {@link TransportUnitType} type.
     * 
     * @param transportUnitType
     *            The {@link TransportUnitType} to search for.
     * @return The requested List or <code>null</code> if no {@link Rule} was
     *         found.
     */
    List<Rule> loadRules(String transportUnitType);
}