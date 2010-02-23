/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.service.impl;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.TransportOrderService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 * A TransportService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Service
public class TransportServiceImpl extends EntityServiceImpl<TransportUnit, Long> implements TransportOrderService {

    private GenericDao<TransportUnit, Long> transportUnitDao;
    private GenericDao<Location, Long> locationDao;
    private TransportOrderDao transportOrderDao;

    @Required
    public void setLocationDao(GenericDao<Location, Long> locationDao) {
        this.locationDao = locationDao;
    }

    @Required
    public void setTransportUnitDao(GenericDao<TransportUnit, Long> transportUnitDao) {
        this.transportUnitDao = transportUnitDao;
    }

    @Required
    public void setTransportOrderDao(TransportOrderDao transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTransportsToLocationGroup(LocationGroup locationGroup) {
        return transportOrderDao.getNumberOfTransportOrders(locationGroup);
    }
}