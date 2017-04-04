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
package org.openwms.wms.shipping;

import org.openwms.common.values.Target;
import org.openwms.wms.order.OrderPositionSplit;
import org.openwms.wms.order.OrderStartMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A ShippingSplit.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "WMS_SHP_ORDER_POS_SPLIT")
public class ShippingOrderPositionSplit extends OrderPositionSplit<ShippingOrder, ShippingOrderPosition> implements Serializable {

    private static final long serialVersionUID = -9125592694773354568L;

    /** Target picking position. */
    @ManyToOne
    @JoinColumn(name = "C_PICK_POSITION")
    private Target pickPosition;

    /** Defines how an Order is started. */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_START_MODE", nullable = false)
    private OrderStartMode startMode;

    /**
     * Used by the JPA provider.
     */
    protected ShippingOrderPositionSplit() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ShippingOrderPositionSplit(ShippingOrderPosition position, Integer sId) {
        super(position, sId);
        this.startMode = position.getStartMode();
    }

    /**
     * Get the pickPosition.
     * 
     * @return the pickPosition.
     */
    public Target getPickPosition() {
        return pickPosition;
    }

    /**
     * Get the startMode.
     * 
     * @return the startMode.
     */
    public OrderStartMode getStartMode() {
        return startMode;
    }

    /**
     * Used to set the priority on all Splits. Called from the
     * ShippingOrderPosition.
     * 
     * @param prio
     *            The priority to set
     */
    void setSplitPriority(int prio) {
        setSplitPriority(prio);
    }
}