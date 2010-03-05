/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms.integration;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.integration.GenericDao;
import org.openwms.tms.domain.order.TransportOrder;

/**
 * A TransportOrderDao - Adds specific functionality concerning
 * {@link TransportOrder} Entity classes.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.integration.GenericDao
 * @see org.openwms.tms.domain.order.TransportOrder
 */
public interface TransportOrderDao extends GenericDao<TransportOrder, Long> {

    /**
     * Name of the <code>NamedQuery</code> to find all {@link TransportOrder}
     * Entities.
     */
    String NQ_FIND_ALL = "TransportOrder.findAll";

    /**
     * Name of the <code>NamedQuery</code> to find a {@link TransportOrder} by
     * the unique technical key.
     */
    String NQ_FIND_BY_ID = "TransportOrder.findById";

    /**
     * Get all active {@link TransportOrder}s that have the target destination
     * to this locationGroup.
     * 
     * @param locationGroup
     *            The group to search for
     * @return The number of all active {@link TransportOrder}s
     */
    int getNumberOfTransportOrders(LocationGroup locationGroup);

}
