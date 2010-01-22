/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.service;

import org.apache.log4j.Logger;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.integration.TransportOrderDao;
import org.openwms.common.service.TransportService;
import org.openwms.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Service
public class TransportServiceImpl implements TransportService {

    protected Logger logger = Logger.getLogger(this.getClass());
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
    @Transactional
    public TransportUnit createTransportUnit(Barcode barcode, TransportUnitType transportUnitType,
            LocationPK actualLocationPk) {
        TransportUnit transportUnit = transportUnitDao.findByUniqueId(barcode);
        Location location = locationDao.findByUniqueId(actualLocationPk);
        if (transportUnit != null) {
            throw new ServiceException("TransportUnit with id " + barcode + " still exists");
        }
        if (location == null) {
            throw new ServiceException("Location " + actualLocationPk + " doesn't exists");
        }
        transportUnit = new TransportUnit(barcode);
        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(location);
        transportUnitDao.persist(transportUnit);
        return transportUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void moveTransportUnit(Barcode barcode, LocationPK newLocationPk) throws ServiceException {
        TransportUnit transportUnit = transportUnitDao.findByUniqueId(barcode);
        if (transportUnit == null) {
            throw new ServiceException("TransportUnit with id " + barcode + " not found");
        }
        Location actualLocation = locationDao.findByUniqueId(newLocationPk);
        // if (actualLocation == null) {
        // throw new ServiceException("Location with id " + newLocationPk +
        // " not found");
        // }
        transportUnit.setActualLocation(actualLocation);
        // try {
        transportUnitDao.save(transportUnit);
        // }
        // catch (RuntimeException e) {
        // throw new ServiceException("Cannot move TransportUnit with barcode "
        // + barcode + " to location "
        // + newLocationPk, e);
        // }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTransportsToLocationGroup(LocationGroup locationGroup) {
        return transportOrderDao.getNumberOfTransportOrders(locationGroup);
    }
}