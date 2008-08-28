/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.common.dao.IGenericDAO;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.exception.service.ServiceException;
import org.openwms.common.service.ITransportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportService.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Service
public class TransportService implements ITransportService {

    protected Log LOG = LogFactory.getLog(this.getClass());
    private IGenericDAO<TransportUnit, Barcode> transportUnitDao;
    private IGenericDAO<Location, LocationPK> locationDao;

    public TransportService(IGenericDAO<TransportUnit, Barcode> entityDao, IGenericDAO<Location, LocationPK> locationDao) {
	this.transportUnitDao = entityDao;
	this.locationDao = locationDao;
    }

    public TransportUnit createTransportUnit(Barcode barcode, TransportUnitType transportUnitType,
	    LocationPK actualLocationPk) {
	// FIXME: implement
	return null;
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
		throw new ServiceException("Location with id [" + actualLocation + "] not found");
	    }
	    transportUnit = transportUnitDao.findByUniqueId(barcode);
	    if (transportUnit == null) {
		throw new ServiceException("TransportUnit with id [" + barcode + "] not found");
	    }
	    transportUnit.setActualLocation(actualLocation);
	    transportUnitDao.persist(transportUnit);
	}
	catch (RuntimeException e) {
	    // FIXME
	    e.printStackTrace();
	}
    }

}
