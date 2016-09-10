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

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * A ObjectFactory.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Configurable
class ObjectFactory {

    @Autowired
    private static EntityManager em;

    public static Barcode createBarcode(String barcodeId) {
        return new Barcode(barcodeId);
    }

    /**
     * Create a new TransportUnit.
     *
     * @param unitId The unique id of the Barcode
     * @return The instance
     */
    public static TransportUnit createTransportUnit(String unitId) {
        return new TransportUnit(unitId);
    }

    /**
     * Create a new TransportUnit.
     *
     * @param unitId The unique id of the Barcode
     * @param tut The TransportUnitType to set
     * @return The instance
     */
    public static TransportUnit createTransportUnit(String unitId, TransportUnitType tut) {
        TransportUnit tu = new TransportUnit(unitId);
        tu.setTransportUnitType(tut);
        return tu;
    }

    /**
     * Create a new TransportUnit.
     *
     * @param unitId The unique id of the Barcode
     * @param transportUnitTypeBk The business key of the TransportUnitType
     * @return The instance
     */
    public static TransportUnit createTransportUnit(String unitId, String transportUnitTypeBk) {
        TransportUnit tu = new TransportUnit(unitId);
        tu.setTransportUnitType(em.createQuery("select tu from TransportUnitType tu where tu.type = ?1", TransportUnitType.class).setParameter(1, transportUnitTypeBk).getSingleResult());
        return tu;
    }

    /**
     * Factory method to create a new TransportUnitType.
     *
     * @param type The business key
     * @return The instance
     */
    public static TransportUnitType createTransportUnitType(String type) {
        return new TransportUnitType(type);
    }
}
