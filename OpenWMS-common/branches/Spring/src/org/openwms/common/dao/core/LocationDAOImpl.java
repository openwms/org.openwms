/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import org.openwms.common.dao.LocationDao;
import org.openwms.common.domain.Location;

/**
 * A LocationDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDAOImpl extends AbstractGenericJpaDAO<Location, Long> implements LocationDao {

    @Override
    String getFindAllQuery() {
	return LocationDao.NQ_FIND_ALL;
    }

    @Override
    String getFindByUniqueIdQuery() {
	return LocationDao.NQ_FIND_BY_UNIQUE_QUERY;
    }
}
