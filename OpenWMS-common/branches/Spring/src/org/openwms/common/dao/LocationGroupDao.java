/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.LocationGroup;

/**
 * A LocationGroupDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface LocationGroupDao extends GenericDAO<LocationGroup, Long> {

    public final String NQ_FIND_ALL = "LocationGroup.findAll";
    public final String NQ_FIND_BY_NAME = "LocationGroup.findByName";

}
