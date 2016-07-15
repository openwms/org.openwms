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

import java.util.Collection;

/**
 * A TransportationService offers some useful methods regarding the general handling of {@link TransportOrder}s.
 *
 * @param <T> Any kind of {@link TransportOrder}
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
public interface TransportationService<T extends TransportOrder> {

    /**
     * Returns the number of {@code TransportOrder}s that have the {@code target} as target and are in one of the {@code states}.
     *
     * @param target The target place to search TransportOrders for
     * @param states An array of TransportOrder states to filter TransportOrders for
     * @return Number of all TransportOrders in one of the {@code states} that are on the way to the {@code target}
     */
    int getNoTransportOrdersToTarget(String target, String... states);

    /**
     * Create a new {@link TransportOrder} with the given {@code target}.
     *
     * @param barcode The ID of the {@code TransportUnit} to move
     * @param target The target of the TransportOrder to move to
     * @param priority A {@link PriorityLevel} of the new {@link TransportOrder}
     * @return The newly created {@link TransportOrder}
     */
    T createTransportOrder(String barcode, String target, PriorityLevel priority);

    /**
     * Try to turn a list of {@link TransportOrder}s into the given {@code state}.
     *
     * @param bks The business keys of {@link TransportOrder}s
     * @param state The state to change all orders to
     * @return A list of business keys of that {@link TransportOrder}s that have not been changed
     */
    Collection<String> cancelTransportOrders(Collection<String> bks, TransportOrder.State state);

    /**
     * Try to redirect a list of {@link TransportOrder}s to a new target {@code LocationGroup}.
     *
     * @param bks The business keys of {@link TransportOrder}s to be redirected
     * @param target The new target of the {@link TransportOrder}s
     * @return A list of business keys of that {@link TransportOrder} that couldn't be redirected
     */
    Collection<String> redirectTransportOrders(Collection<String> bks, String target);
}