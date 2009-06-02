/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.openwms.common.domain.LocationGroup;
import org.openwms.tms.domain.order.TransportOrder;

/**
 * A TransportOrderDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface TransportOrderDao extends GenericDao<TransportOrder, Long> {

	public final String NQ_FIND_ALL = "TransportOrder.findAll";
	public final String NQ_FIND_BY_ID = "TransportUnitType.findById";
	
	public int getNumberOfTransportOrders(LocationGroup locationGroup);

}
