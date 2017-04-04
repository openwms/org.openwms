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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;

import org.openwms.core.values.CoreTypeDefinitions;
import org.openwms.core.values.UnitType;
import org.openwms.wms.inventory.Product;
import org.openwms.wms.order.OrderPosition;
import org.springframework.util.Assert;

/**
 * A ReceivingOrderPosition.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "WMS_REC_ORDER_POSITION")
@NamedQueries({
        @NamedQuery(name = ReceivingOrderPosition.NQ_FIND_ALL, query = "select rop from ReceivingOrderPosition rop"),
        @NamedQuery(name = ReceivingOrderPosition.NQ_FIND_POSITION_KEY, query = "select rop from ReceivingOrderPosition rop where rop.positionId = :"
                + ReceivingOrderPosition.QP_FIND_WITH_POSITION_KEY + " order by rop.positionId") })
public class ReceivingOrderPosition extends OrderPosition<ReceivingOrder, ReceivingOrderPosition> implements Serializable {

    private static final long serialVersionUID = -9027230827826634833L;

    @org.hibernate.annotations.Type(type = "org.openwms.persistence.ext.hibernate.UnitUserType")
    @org.hibernate.annotations.Columns(columns = { @Column(name = "C_QUANTITY_TYPE", length = 255, nullable = false),
            @Column(name = "C_QTY_EXPECTED", length = CoreTypeDefinitions.QUANTITY_LENGTH, nullable = false) })
    private UnitType qtyExpected;
    /** The ordered {@link Product}. */
    @ManyToOne
    @JoinColumn(name = "C_PRODUCT", referencedColumnName = "C_SKU")
    private Product product;

    /**
     * Query to find all <code>ReceivingOrderPosition</code>s.<br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_ALL = "ReceivingOrderPosition.findAll";
    /**
     * Query to find <strong>one</strong> <code>ReceivingOrderPosition</code> by
     * its orderPositionKey. <li>
     * Query parameter name <strong>orderPositionKey</strong> : The unique
     * orderPositionKey of the <code>ReceivingOrderPosition</code> to search
     * for.</li><br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_POSITION_KEY = "ReceivingOrderPosition.findByPosKey";
    public static final String QP_FIND_WITH_POSITION_KEY = "orderPositionKey";

    /**
     * Used by the JPA provider.
     */
    protected ReceivingOrderPosition() {}

    /**
     * Create a new ReceivingOrderPosition.
     * 
     * @param ord
     *            The order
     * @param posNo
     *            The position number
     * @param qtyExp
     *            The expected quantity
     * @param prod
     *            The ordered Product
     */
    public ReceivingOrderPosition(ReceivingOrder ord, Integer posNo, UnitType qtyExp, Product prod) {
        super(ord, posNo);
        Assert.notNull(qtyExp);
        Assert.notNull(prod);
        this.qtyExpected = qtyExp;
        this.product = prod;
    }

    /**
     * Get the qtyExpected.
     * 
     * @return the qtyExpected.
     */
    public UnitType getQtyExpected() {
        return qtyExpected;
    }

    /**
     * Set the qtyExpected.
     * 
     * @param qtyExpected
     *            The qtyExpected to set.
     */
    public void setQtyExpected(UnitType qtyExpected) {
        this.qtyExpected = qtyExpected;
    }

    /**
     * Get the product.
     * 
     * @return the product.
     */
    public Product getProduct() {
        return product;
    }
}