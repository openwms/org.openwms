/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.jpa;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.integration.LocationGroupDao;

/**
 * A LocationGroupDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationGroupDaoImpl extends AbstractGenericJpaDao<LocationGroup, Long> implements LocationGroupDao {

	@PostConstruct
	public void init() {
		logger.debug("LocationGroupDao bean initialized");
	}

	@Override
	protected String getFindAllQuery() {
		return LocationGroupDao.NQ_FIND_ALL;
	}

	@Override
	protected String getFindByUniqueIdQuery() {
		return LocationGroupDao.NQ_FIND_BY_NAME;
	}

	@Override
	protected void beforeUpdate(LocationGroup entity) {
		entity.setLastUpdated(new Date());
	}

}
