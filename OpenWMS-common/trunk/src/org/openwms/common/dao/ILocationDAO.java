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
 * A ILocationDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface ILocationDAO extends IGenericDAO<Location, LocationPK> {

    public final String NQ_FIND_ALL = "findLocationAll";
    public final String NQ_FIND_BY_UNIQUE_QUERY = "findLocationUnique";

    // TODO: Pull up all non-CRUD operations
}
