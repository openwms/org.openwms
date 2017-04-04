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

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

import org.openwms.wms.order.AbstractOrder;
import org.openwms.wms.order.OrderStartMode;

/**
 * A ShippingOrder.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@DiscriminatorValue(ShippingOrder.ORDER_TYPE)
@NamedQueries({
        @NamedQuery(name = ShippingOrder.NQ_FIND_ALL, query = "select so from ShippingOrder so order by so.orderId"),
        @NamedQuery(name = ShippingOrder.NQ_FIND_WITH_ORDERID, query = "select so from ShippingOrder so where so.orderId = :"
                + ShippingOrder.QP_FIND_WITH_ORDERID_ORDERID) })
public class ShippingOrder extends AbstractOrder<ShippingOrder, ShippingOrderPosition> implements Serializable {

    private static final long serialVersionUID = -5623285418077002865L;

    /** Defines how an Order is started. Default is {@value} */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_START_MODE", nullable = false)
    private OrderStartMode startMode = OrderStartMode.AUTOMATIC;

    /** Order type, default: {@value} */
    public static final String ORDER_TYPE = "SHIPPING";
    /**
     * Query to find all <code>ShippingOrder</code>s.<br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_ALL = "ShippingOrder.findAll";
    /**
     * Query to find an <code>ShippingOrder</code> by it's <code>orderId</code>
     * . <li>
     * Query parameter name <strong>{@value #QP_FIND_WITH_ORDERID_ORDERID}
     * </strong> : The orderId of the <code>ShippingOrder</code> to search for.</li>
     * <br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_WITH_ORDERID = "ShippingOrder.findWithOrderId";
    public static final String QP_FIND_WITH_ORDERID_ORDERID = "orderId";

    /**
     * Create a new ShippingOrder.
     * 
     * @param ordId
     */
    public ShippingOrder(String ordId) {
        super(ordId);
    }

    /**
     * Used by the JPA provider.
     */
    protected ShippingOrder() {}

    /**
     * Get the startMode.
     * 
     * @return the startMode.
     */
    public OrderStartMode getStartMode() {
        return startMode;
    }
}