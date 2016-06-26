/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.tms;

import java.util.List;

import org.openwms.common.location.LocationGroup;
import org.openwms.common.transport.TransportUnit;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.values.TransportOrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * A TransportOrderDao - Adds specific functionality concerning {@link TransportOrder} Entity classes.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.GenericDao
 * @see org.openwms.tms.domain.order.TransportOrder
 */
public interface TransportOrderDao extends JpaRepository<TransportOrder, Long> {

    /**
     * Get all active {@link TransportOrder}s that have the target destination to this locationGroup.
     * 
     * @param locationGroup
     *            The group to search for
     * @return The number of all active {@link TransportOrder}s
     */
    int getNumberOfTransportOrders(LocationGroup locationGroup);

    /**
     * Find and retrieve a list of {@link TransportOrder}s, searched by a list of their ids.
     * 
     * @param ids
     *            A list of technical keys of the orders to search for
     * @return The list of {@link TransportOrder}s.
     */
    List<TransportOrder> findByIds(List<Long> ids);

    /**
     * Find a list of {@link TransportOrder}s within defined states for a given {@link TransportUnit}. The implementation does never return
     * <code>null</code>. In case no {@link TransportOrder}s are found an empty List is returned.
     * 
     * @param transportUnit
     *            The {@link TransportUnit} to search for
     * @param states
     *            An arbitrary array of states
     * @return A list of {@link TransportOrder}s or an empty list.
     */
    @Query("select to from TransportOrder to where to.transportUnit = :transportUnit and to.state in (:states)")
    List<TransportOrder> findForTUinState(TransportUnit transportUnit, TransportOrderState... states);

}
