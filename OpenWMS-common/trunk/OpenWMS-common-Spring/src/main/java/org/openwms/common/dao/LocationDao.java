/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.util.List;

import org.openwms.common.domain.Location;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * A LocationDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface LocationDao extends GenericDao<Location, Long> {

	public final String NQ_FIND_ALL = "Location.findAll";
	public final String NQ_FIND_ALL_EAGER = "Location.findAllEager";
	public final String NQ_FIND_BY_UNIQUE_QUERY = "Location.findByLocationPK";

	@Transactional(readOnly = true)
	List<Location> getAllLocations();

}
