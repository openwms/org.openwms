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

public class LocationGroupDaoImpl<T extends LocationGroup, ID extends Long> extends AbstractGenericJpaDAO<T, ID> implements LocationGroupDao<T,ID> {

    @Override
    String getFindAllQuery() {
	return LocationGroupDao.NQ_FIND_ALL;
    }

    @Override
    String getFindByUniqueIdQuery() {
	return LocationGroupDao.NQ_FIND_BY_NAME;
    }
    
    @Override
    protected void beforeUpdate(T entity) {
    	entity.setLastUpdated(new Date());
    }

}
