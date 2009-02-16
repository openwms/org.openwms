/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.TransportUnitType;

/**
 * A TransportUnitTypeDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface TransportUnitTypeDao extends GenericDAO<TransportUnitType, String>{

    public final String NQ_FIND_ALL = "TransportUnitType.findAll";
    public final String NQ_FIND_BY_NAME = "TransportUnitType.findByName";

}
