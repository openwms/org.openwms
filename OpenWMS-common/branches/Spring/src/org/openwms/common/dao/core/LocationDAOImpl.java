/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import org.openwms.common.dao.LocationDAO;
import org.openwms.common.domain.Location;

/**
 * A LocationDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDAOImpl extends AbstractGenericJpaDAO<Location, Long> implements LocationDAO {

    @Override
    String getFindAllQuery() {
	return LocationDAO.NQ_FIND_ALL;
    }

    @Override
    String getFindByUniqueIdQuery() {
	return LocationDAO.NQ_FIND_BY_UNIQUE_QUERY;
    }
}
