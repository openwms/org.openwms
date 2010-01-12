/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.spring;

import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.integration.LocationDao;
import org.openwms.common.service.LocationService;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Transactional
public class LocationServiceImpl extends EntityServiceImpl<Location, Long> implements LocationService<Location> {
	public LocationServiceImpl() {}

	@Transactional(readOnly = true)
	public List<Location> getAllLocations() {
		logger.debug("getAllLocations called");
		return ((LocationDao) dao).getAllLocations();
	}
}
