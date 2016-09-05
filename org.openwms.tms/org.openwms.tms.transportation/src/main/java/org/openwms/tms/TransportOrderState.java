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

/**
 * A TransportOrderState.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public enum TransportOrderState {

    /** Status of new created {@code TransportOrder}s. */
    CREATED(10),

    /** Status of a full initialized {@code TransportOrder}, ready to be started. */
    INITIALIZED(20),

    /** A started and active{@code TransportOrder}, ready to be executed. */
    STARTED(30),

    /** Status to indicate that the {@code TransportOrder} is paused. Not active anymore. */
    INTERRUPTED(40),

    /** Status to indicate a failure on the {@code TransportOrder}. Not active anymore. */
    ONFAILURE(50),

    /** Status of a aborted {@code TransportOrder}. Not active anymore. */
    CANCELED(60),

    /** Status to indicate that the {@code TransportOrder} completed successfully. */
    FINISHED(70);

    private final int order;

    private TransportOrderState(int sortOrder) {
        this.order = sortOrder;
    }

    /**
     * Get the order.
     *
     * @return the order.
     */
    public int getOrder() {
        return order;
    }
}
