/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration;

import org.openwms.common.domain.LocationGroup;

/**
 * 
 * A LocationGroupDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface LocationGroupDao extends GenericDao<LocationGroup, Long> {

	public final String NQ_FIND_ALL = "LocationGroup.findAll";
	public final String NQ_FIND_BY_NAME = "LocationGroup.findByName";

}
