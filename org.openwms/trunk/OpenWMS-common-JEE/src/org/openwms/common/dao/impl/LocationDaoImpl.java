/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.impl;

import javax.ejb.Stateless;

import org.openwms.common.dao.LocationDao;
import org.openwms.common.domain.Location;

/**
 * A LocationDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Stateless(name = "LocationDao")
public class LocationDaoImpl extends AbstractGenericDao<Location, Long> implements LocationDao {

	@Override
	String getFindAllQuery() {
		return LocationDao.NQ_FIND_ALL;
	}

	@Override
	String getFindByUniqueIdQuery() {
		return LocationDao.NQ_FIND_BY_UNIQUE_QUERY;
	}
}
