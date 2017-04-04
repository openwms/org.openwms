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
package org.openwms.wms.order;

import org.openwms.wms.WMSTypes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * An OrderPositionSplitKey.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Embeddable
public class OrderPositionSplitKey implements Serializable {

    private static final long serialVersionUID = 6106057549053462471L;

    /**
     * The order id of the <code>AbstractOrder</code> instance, the
     * <code>OrderPositionSplit</code> belongs to.
     */
    @Column(name = "C_ORDER_ID_K", length = WMSTypes.ORDER_ID_LENGTH, nullable = false)
    private String orderId;

    /**
     * The position number of the <code>OrderPosition</code> instance, the
     * <code>OrderPositionSplit</code> belongs to.
     */
    @Column(name = "C_POSITION_NO_K", length = WMSTypes.POSITION_NO_LENGTH, nullable = false)
    private Integer positionNo;

    /**
     * The split number is a unique index within a single
     * <code>AbstractOrder</code> and an <code>OrderPosition</code> instance.
     */
    @Column(name = "C_SPLIT_NO_K", length = WMSTypes.POSITION_NO_LENGTH, nullable = false)
    private Integer splitNo;

    /**
     * Used by the JPA Provider.
     */
    protected OrderPositionSplitKey() {
        super();
    }

    /**
     * Create a new OrderPositionKey with orderId and positionNo.
     * 
     * @param ordId
     *            order id
     * @param posNo
     *            position number
     * @param sNo
     *            split number
     */
    public OrderPositionSplitKey(String ordId, Integer posNo, Integer sNo) {
        super();
        this.orderId = ordId;
        this.positionNo = posNo;
        this.splitNo = sNo;
    }

    /**
     * Create a new OrderPositionKey with an order and a positionNo.
     * 
     * @param ord
     *            The order of this position
     * @param posNo
     *            The current position number within the order
     * @param sNo
     *            split number
     */
    public OrderPositionSplitKey(AbstractOrder<?, ?> ord, Integer posNo, Integer sNo) {
        super();
        this.orderId = ord.getOrderId();
        this.positionNo = posNo;
        this.splitNo = sNo;
    }

    /**
     * Create a new OrderPositionKey with an order and a positionNo.
     * 
     * @param pos
     *            The <code>OrderPosition</code> this split belongs to.
     * @param sNo
     *            split number
     */
    public OrderPositionSplitKey(OrderPosition<?, ?> pos, Integer sNo) {
        super();
        this.orderId = pos.getPositionId().getOrderId();
        this.positionNo = pos.getPositionId().getPositionNo();
        this.splitNo = sNo;
    }

    /**
     * Get the orderId.
     * 
     * @return the orderId
     */
    public String getOrderId() {
        return this.orderId;
    }

    /**
     * Get the positionNo.
     * 
     * @return the positionNo
     */
    public Integer getPositionNo() {
        return this.positionNo;
    }

    /**
     * Get the splitNo.
     * 
     * @return the splitNo.
     */
    public Integer getSplitNo() {
        return splitNo;
    }
}