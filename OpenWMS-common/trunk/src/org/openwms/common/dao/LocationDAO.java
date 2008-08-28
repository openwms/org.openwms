/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;

/**
 * A LocationDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDAO extends GenericDAO<Location, LocationPK> implements ILocationDAO {

    @Override
    String getFindAllQuery() {
        return ILocationDAO.NQ_FIND_ALL;
    }
    
    @Override
    String getFindAllUniqueQuery() {
        return ILocationDAO.NQ_FIND_BY_UNIQUE_QUERY;
    }
}
