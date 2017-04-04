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
package org.openwms.wms.receiving;

import org.openwms.core.values.UnitType;
import org.openwms.wms.order.OrderPositionKey;

import java.util.List;

/**
 * A Receiving.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface Receiving {

    /**
     * Find and retrieve a List with all <code>ReceivingOrder</code>s.
     *
     * @return A List with <code>ReceivingOrder</code>s or an empty List with when no orders exist
     */
    List<ReceivingOrder> findAllOrders();

    /**
     * Find and retrieve all <code>ReceivingOrderPosition</code>s of an <code>ReceivingOrder</code> with the <code>orderId</code>.
     *
     * @param orderId The orderId to search for
     * @return A List with <code>ReceivingOrderPosition</code>s or an empty List with when no positions exist
     */
    List<ReceivingOrderPosition> findAllPositions(String orderId);

    /**
     * FIXME [scherrer] Comment this
     *
     * @param ordId
     * @return
     */
    ReceivingOrder createOrder(String ordId);

    /**
     * FIXME [scherrer] Comment this
     *
     * @param order
     * @return
     */
    ReceivingOrder createOrder(ReceivingOrder order);

    /**
     * FIXME [scherrer] Comment this
     *
     * @param orderPositionKey
     * @param productId
     * @param quantity
     * @param barcode
     * @return
     */
    ReceivingOrderPosition createOrderPosition(OrderPositionKey orderPositionKey, String productId, UnitType quantity, String barcode);
}