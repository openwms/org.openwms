/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.common.dao.GenericDAO;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.exception.service.ServiceException;
import org.openwms.common.service.TransportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportService.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Service
public class TransportServiceImpl implements TransportService {

    protected Log LOG = LogFactory.getLog(this.getClass());
    // FIXME: Eliminate different dao injections, use only one generic dao!
    private GenericDAO<TransportUnit, Barcode> transportUnitDao;
    private GenericDAO<Location, LocationPK> locationDao;

    // public TransportServiceImpl(GenericDAO<TransportUnit, Barcode> entityDao,
    // GenericDAO<Location, LocationPK> locationDao) {
    // this.transportUnitDao = entityDao;
    // this.locationDao = locationDao;
    // }

    public TransportUnit createTransportUnit(Barcode barcode, TransportUnitType transportUnitType,
	    LocationPK actualLocationPk) {
	// FIXME: implement
	return null;
    }

    public void setLocationDao(GenericDAO<Location, LocationPK> locationDao) {
	this.locationDao = locationDao;
    }

    public void setTransportUnitDao(GenericDAO<TransportUnit, Barcode> transportUnitDao) {
	this.transportUnitDao = transportUnitDao;
    }

    /**
     * Moves a <tt>TransportUnit</tt> identified by its <tt>Barcode</tt> to the given actual <tt>Location</tt>
     * identified by the <tt>LocationPK</tt>.
     * 
     * @param barcode
     * @param locationPk
     */
    @Transactional
    public void moveTransportUnit(Barcode barcode, LocationPK actualLocationPk) throws ServiceException {
	TransportUnit transportUnit = null;
	try {
	    Location actualLocation = locationDao.findByUniqueId(actualLocationPk);
	    if (actualLocation == null) {
		throw new ServiceException("Location with id " + actualLocationPk + " not found");
	    }
	    transportUnit = transportUnitDao.findByUniqueId(barcode);
	    if (transportUnit == null) {
		throw new ServiceException("TransportUnit with id " + barcode + " not found");
	    }
	    transportUnit.setActualLocation(actualLocation);
	    // FIXME: Persisting not working in test case, yet
	    transportUnitDao.save(transportUnit);
	}
	catch (RuntimeException e) { // FIXME
	    throw new ServiceException("Cannot move TransportUnit with barcode " + barcode + " to location "
		    + actualLocationPk, e);
	}
    }

}