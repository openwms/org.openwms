/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.Location;

/**
 * A LocationDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface LocationDao extends GenericDao<Location, Long> {

	public final String NQ_FIND_ALL = "Location.findAll";
	public final String NQ_FIND_BY_UNIQUE_QUERY = "Location.findByLocationPK";

}
