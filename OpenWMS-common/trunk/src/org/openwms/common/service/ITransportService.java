/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.exception.service.ServiceException;
import org.springframework.transaction.annotation.Transactional;

public interface ITransportService {

    /**
     * Create a new <tt>TransportUnit</tt> with the type <tt>TransportUnitType</tt> on an initial <tt>Location</tt>.
     * 
     * @param barcode
     * @param actualLocation
     * @return
     */
    TransportUnit createTransportUnit(Barcode barcode, TransportUnitType transportUnitType, LocationPK actualLocation);

    /**
     * Moves a <tt>TransportUnit</tt> identified by its <tt>Barcode</tt> to the given actual <tt>Location</tt>
     * identified by the <tt>LocationPK</tt>.
     * 
     * @param barcode
     * @param locationPk
     */
    @Transactional
    void moveTransportUnit(Barcode barcode, LocationPK locationPk) throws ServiceException;

}