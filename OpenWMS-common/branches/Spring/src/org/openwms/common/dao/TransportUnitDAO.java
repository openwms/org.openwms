/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.TransportUnit;

/**
 * A TransportUnitDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface TransportUnitDAO extends GenericDAO<TransportUnit, Long> {

	public final String NQ_FIND_ALL = "TransportUnit.findAll";
	public final String NQ_FIND_BY_UNIQUE_QUERY = "TransportUnit.findByBarcode";

}
