/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.integration;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.integration.GenericDao;
import org.openwms.tms.domain.order.TransportOrder;

/**
 * 
 * A TransportOrderDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface TransportOrderDao extends GenericDao<TransportOrder, Long> {

    public final String NQ_FIND_ALL = "TransportOrder.findAll";
    public final String NQ_FIND_BY_ID = "TransportUnitType.findById";

    public int getNumberOfTransportOrders(LocationGroup locationGroup);

}
