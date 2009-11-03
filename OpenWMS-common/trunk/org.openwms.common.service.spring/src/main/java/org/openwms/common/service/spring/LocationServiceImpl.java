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

/**
 * A LocationServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationServiceImpl extends EntityServiceImpl<Location, Long> implements LocationService<Location> {
	public LocationServiceImpl() {}

	public List<Location> getAllLocations() {
		return ((LocationDao) dao).getAllLocations();
	}
}
