/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.impl;

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
	private GenericDAO<TransportUnit, Long> transportUnitDao;
	private GenericDAO<Location, Long> locationDao;

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

	public void setLocationDao(GenericDAO<Location, Long> locationDao) {
		this.locationDao = locationDao;
	}

	public void setTransportUnitDao(GenericDAO<TransportUnit, Long> transportUnitDao) {
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
	public void moveTransportUnit(Barcode barcode, LocationPK newLocationPk) throws ServiceException {
		TransportUnit transportUnit = transportUnitDao.findByUniqueId(barcode);
		if (transportUnit == null) {
			throw new ServiceException("TransportUnit with id " + barcode + " not found");
		}
		Location actualLocation = locationDao.findByUniqueId(newLocationPk);
		// if (actualLocation == null) {
		// throw new ServiceException("Location with id " + newLocationPk + " not found");
		// }
		transportUnit.setActualLocation(actualLocation);
		// try {
		transportUnitDao.save(transportUnit);
		// }
		// catch (RuntimeException e) {
		// throw new ServiceException("Cannot move TransportUnit with barcode " + barcode + " to location "
		// + newLocationPk, e);
		// }
	}
}