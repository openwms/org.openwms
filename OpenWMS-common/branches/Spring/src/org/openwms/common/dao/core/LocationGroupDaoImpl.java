/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.util.Date;

import org.openwms.common.dao.LocationGroupDao;
import org.openwms.common.domain.LocationGroup;
import org.springframework.stereotype.Repository;

@Repository
public class LocationGroupDaoImpl extends AbstractGenericJpaDAO<LocationGroup, Long> implements LocationGroupDao {

	@Override
	String getFindAllQuery() {
		return LocationGroupDao.NQ_FIND_ALL;
	}

	@Override
	String getFindByUniqueIdQuery() {
		return LocationGroupDao.NQ_FIND_BY_NAME;
	}

	@Override
	protected void beforeUpdate(LocationGroup entity) {
		entity.setLastUpdated(new Date());
	}

}
