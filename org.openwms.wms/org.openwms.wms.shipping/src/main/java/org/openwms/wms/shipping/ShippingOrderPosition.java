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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openwms.common.values.Target;
import org.openwms.core.values.CoreTypeDefinitions;
import org.openwms.core.values.UnitType;
import org.openwms.wms.inventory.Product;
import org.openwms.wms.order.OrderPosition;
import org.openwms.wms.order.OrderPositionSplit;
import org.openwms.wms.order.OrderStartMode;
import org.springframework.util.Assert;

/**
 * A ShippingOrderPosition.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "WMS_SHP_ORDER_POSITION")
public class ShippingOrderPosition extends OrderPosition<ShippingOrder, ShippingOrderPosition> implements Serializable {

    private static final long serialVersionUID = -3011264258887431785L;

    /** The ordered {@link Product}. */
    @ManyToOne
    @JoinColumn(name = "C_PRODUCT", referencedColumnName = "C_SKU")
    private Product product;

    /** The quantity that was originally demanded, ordered. */
    @org.hibernate.annotations.Type(type = "org.openwms.persistence.ext.hibernate.UnitUserType")
    @org.hibernate.annotations.Columns(columns = { @Column(name = "C_QUANTITY_TYPE_O", length = 255, nullable = false),
            @Column(name = "C_QTY_ORDERED", length = CoreTypeDefinitions.QUANTITY_LENGTH, nullable = false) })
    private UnitType qtyOrdered;

    /** The quantity that could be allocated. */
    @org.hibernate.annotations.Type(type = "org.openwms.persistence.ext.hibernate.UnitUserType")
    @org.hibernate.annotations.Columns(columns = { @Column(name = "C_QUANTITY_TYPE_A", length = 255, nullable = true),
            @Column(name = "C_QTY_ALLOC", length = CoreTypeDefinitions.QUANTITY_LENGTH, nullable = true) })
    private UnitType qtyAllocated;

    /** Date when the OrderPosition will be tried to allocated next time. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_NEXT_ALLOC")
    private Date nextAllocationDate;

    /** Defines how an Order is started. */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TO_START_MODE")
    private OrderStartMode startMode;

    /** Number positionsSplits, this OrderPosition has. */
    @Column(name = "C_NO_POS_SPLITS")
    private int noPositionSplits = 0;

    /** All Splits that are necessary to fulfill this OrderPosition. */
    @OneToMany(mappedBy = "position", targetEntity = OrderPositionSplit.class)
    private Set<ShippingOrderPositionSplit> orderPositionSplits = new HashSet<ShippingOrderPositionSplit>();

    /** Target location where the order should be delivered to. */
    @ManyToOne
    @JoinColumn(name = "C_TARGET_LOCATION")
    private Target target;

    /**
     * Query to find all <code>ShippingOrderPosition</code>s.<br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_ALL = "ShippingOrderPosition.findAll";
    /**
     * Query to find <strong>one</strong> <code>ShippingOrderPosition</code> by
     * its orderPositionKey. <li>
     * Query parameter name <strong>orderPositionKey</strong> : The unique
     * orderPositionKey of the <code>ShippingOrderPosition</code> to search for.
     * </li><br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_POSITION_KEY = "ShippingOrderPosition.findByPosKey";
    public static final String QP_FIND_WITH_POSITION_KEY = "orderPositionKey";

    /**
     * Used by the JPA provider.
     */
    protected ShippingOrderPosition() {}

    /**
     * Create a new ShippingOrderPosition.
     * 
     * @param ord
     *            The Order
     * @param posNo
     *            The position number
     * @param qtyOrd
     *            The ordered quantity
     * @param prod
     *            The ordered Product
     */
    public ShippingOrderPosition(ShippingOrder ord, Integer posNo, UnitType qtyOrd, Product prod) {
        super(ord, posNo);
        Assert.notNull(qtyOrd);
        Assert.notNull(prod);
        this.qtyOrdered = qtyOrd;
        this.product = prod;
        this.startMode = ord.getStartMode();
    }

    /**
     * Get the product.
     * 
     * @return the product.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Get the qtyOrdered.
     * 
     * @return the qtyOrdered.
     */
    public UnitType getQtyOrdered() {
        return qtyOrdered;
    }

    /**
     * Get the qtyAllocated.
     * 
     * @return the qtyAllocated.
     */
    public UnitType getQtyAllocated() {
        return qtyAllocated;
    }

    /**
     * FIXME [scherrer] Comment this
     * 
     * @return
     */
    public boolean isAllocated() {
        int res = this.getQtyAllocated().getMeasurable().compareTo(this.getQtyOrdered().getMeasurable());
        return res < 0 ? true : false;
    }

    /**
     * Set the priority.
     * 
     * @param prio
     *            The priority to set.
     */
    @Override
    public void setPriority(int prio) {
        if (this.orderPositionSplits != null && !this.orderPositionSplits.isEmpty()) {
            for (ShippingOrderPositionSplit split : this.orderPositionSplits) {
                split.setSplitPriority(prio);
            }
        }
        super.setPriority(prio);
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
     * Get the nextAllocationDate.
     * 
     * @return the nextAllocationDate.
     */
    public Date getNextAllocationDate() {
        return nextAllocationDate;
    }

    /**
     * Set the nextAllocationDate.
     * 
     * @param nextAllocationDate
     *            The nextAllocationDate to set.
     */
    public void setNextAllocationDate(Date nextAllocationDate) {
        this.nextAllocationDate = nextAllocationDate;
    }

    /**
     * Get the orderPositionSplits.
     * 
     * @return the orderPositionSplits.
     */
    public Set<ShippingOrderPositionSplit> getShippingOrderPositionSplits() {
        return Collections.unmodifiableSet(orderPositionSplits);
    }

    /**
     * Add one or more {@link ShippingOrderPositionSplit}s to this
     * ShippingOrderPosition.
     * 
     * @param positionSplits
     *            {@link ShippingOrderPositionSplit}s to add
     * @return <code>true</code> if this set changed as a result of the call
     */
    public boolean addShippingOrderPostionSplits(ShippingOrderPositionSplit... positionSplits) {
        boolean res = this.orderPositionSplits.addAll(Arrays.asList(positionSplits));
        // CHECK [scherrer] : What happens if result == false??
        this.noPositionSplits += positionSplits.length;
        return res;
    }

    /**
     * Remove one or more {@link ShippingOrderPositionSplit}s from this
     * ShippingOrderPosition.
     * 
     * @param positionSplits
     *            The {@link ShippingOrderPositionSplit}s to remove.
     */
    public boolean removeShippingOrderPostionSplits(ShippingOrderPositionSplit... positionSplits) {
        boolean res = this.orderPositionSplits.removeAll(Arrays.asList(positionSplits));
        // CHECK [scherrer] : What happens if result == false??
        this.noPositionSplits -= positionSplits.length;
        return res;
    }

    private List<ShippingOrderPositionSplit> getSorted(Comparator<? super ShippingOrderPositionSplit> comparator) {
        Collections.sort(new ArrayList<ShippingOrderPositionSplit>(this.orderPositionSplits), comparator);
        return new ArrayList<ShippingOrderPositionSplit>(this.orderPositionSplits);
    }

    /**
     * FIXME [scherrer] Comment this
     * 
     * @return
     */
    public int getNextSplitNumber() {
        return this.getShippingOrderPositionSplits().size() == 0 ? 1 : this.getSorted(new SortBySplitIdAsc()).get(0)
                .getSplitId().getSplitNo();
    }

    private class SortBySplitIdAsc implements Comparator<ShippingOrderPositionSplit> {
        /**
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(ShippingOrderPositionSplit o1, ShippingOrderPositionSplit o2) {
            // TODO [scherrer] Auto-generated method stub
            return 0;
        }
    }
}