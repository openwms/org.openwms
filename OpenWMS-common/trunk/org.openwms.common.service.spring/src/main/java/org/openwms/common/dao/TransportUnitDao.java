/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.TransportUnit;

/**
 * 
 * A TransportUnitDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface TransportUnitDao extends GenericDao<TransportUnit, Long> {

	public final String NQ_FIND_ALL = "TransportUnit.findAll";
	public final String NQ_FIND_BY_UNIQUE_QUERY = "TransportUnit.findByBarcode";

}
