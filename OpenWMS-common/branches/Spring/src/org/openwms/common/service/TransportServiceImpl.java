/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.common.dao.GenericDao;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.TransportService;
import org.openwms.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Required;
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

	protected Log logger = LogFactory.getLog(this.getClass());
	private GenericDao<TransportUnit, Long> transportUnitDao;
	private GenericDao<Location, Long> locationDao;

	@Required
	public void setLocationDao(GenericDao<Location, Long> locationDao) {
		this.locationDao = locationDao;
	}

	@Required
	public void setTransportUnitDao(GenericDao<TransportUnit, Long> transportUnitDao) {
		this.transportUnitDao = transportUnitDao;
	}

	/**
	 * {@inheritDoc}
	 */
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