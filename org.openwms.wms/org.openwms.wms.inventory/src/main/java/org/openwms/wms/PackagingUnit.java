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
package org.openwms.wms;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.core.exception.DomainModelRuntimeException;
import org.openwms.core.values.CoreTypeDefinitions;
import org.openwms.core.values.UnitType;
import org.openwms.wms.inventory.Product;
import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * A PackagingUnit is the actual assignment of a {@link Product} and a quantity.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
@Entity
@Table(name = "WMS_PACKAGING_UNIT")
public class PackagingUnit extends BaseEntity implements Serializable {

    /** Carrying {@code TransportUnit}. */
    // @ManyToOne
    @Column(name = "C_TRANSPORT_UNIT")
    private String transportUnitId;

    /** Carrying {@link LoadUnit}. */
    @ManyToOne
    @JoinColumn(name = "C_LOAD_UNIT")
    private LoadUnit loadUnit;

    /** Unique identifier within the {@link LoadUnit}. May be {@literal null} */
    @Column(name = "C_LABEL", unique = true, nullable = true)
    private String label;

    /** The packaged {@link Product}. */
    @ManyToOne
    @JoinColumn(name = "C_PRODUCT", referencedColumnName = "C_SKU")
    private Product product;

    /** State of this PackagingUnit. */
    @Column(name = "C_AV_STATE")
    @Enumerated(EnumType.STRING)
    private AvailabilityState availabilityState;

    /** Current quantity. */
    @org.hibernate.annotations.Type(type = "org.openwms.persistence.ext.hibernate.UnitUserType")
    @org.hibernate.annotations.Columns(columns = {@Column(name = "C_QUANTITY_TYPE"),
            @Column(name = "C_QUANTITY", length = CoreTypeDefinitions.QUANTITY_LENGTH)})
    private UnitType quantity;

    /** Used to control the putaway strategy. */
    @Column(name = "C_FIFO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fifoDate;

    /** Dear JPA... */
    protected PackagingUnit() {
    }

    /**
     * Create a new PackagingUnit.
     *
     * @param lu The {@link LoadUnit} where this PackingUnit is carried in
     * @param qty The quantity
     */
    public PackagingUnit(LoadUnit lu, UnitType qty) {
        Assert.notNull(lu);
        Assert.notNull(qty);
        assignInitialValues(lu);
        this.quantity = qty;
        this.product = lu.getProduct();
    }

    /**
     * Create a new PackagingUnit.
     *
     * @param lu The {@link LoadUnit} where this PackingUnit is carried in
     * @param qty The quantity
     * @param product A Product to assign
     */
    public PackagingUnit(LoadUnit lu, UnitType qty, Product product) {
        Assert.notNull(lu);
        Assert.notNull(qty);
        Assert.notNull(product);
        assignInitialValues(lu);
        this.quantity = qty;
        if (lu.getProduct() == null) {
            this.product = product;
            this.loadUnit.setProduct(product);
        } else {
            // check afterwards if both Products matches
            this.product = lu.getProduct();
        }
    }

    private void assignInitialValues(LoadUnit lu) {
        this.loadUnit = lu;
        this.loadUnit.addPackagingUnits(this);
        this.transportUnitId = lu.getTransportUnit();
    }

    private void verify() {
        if (!this.product.equals(this.loadUnit.getProduct())) {
            throw new DomainModelRuntimeException("It is not allowed to have different Products on the LoadUnit ["
                    + this.loadUnit.getProduct() + "] and the PackagingUnit [" + this.getProduct() + "]");
        }
        verifyQuantity(this.quantity);
    }

    /**
     * Get the TransportUnit from the {@link LoadUnit}.
     */
    @PostLoad
    void postLoad() {
        this.transportUnitId = this.loadUnit.getTransportUnit();
    }

    /**
     * Get the transportUnitId.
     *
     * @return the transportUnitId.
     */
    public String getTransportUnitId() {
        return transportUnitId;
    }

    /**
     * Get the loadUnit.
     *
     * @return the loadUnit.
     */
    public LoadUnit getLoadUnit() {
        return loadUnit;
    }

    /**
     * Get the label.
     *
     * @return the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label.
     *
     * @param label The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
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
     * Set the product.
     *
     * @param product The product to set.
     */
    protected void setProduct(Product product) {
        this.product = product;
    }

    /**
     * Get the availabilityState.
     *
     * @return the availabilityState.
     */
    public AvailabilityState getAvailabilityState() {
        return availabilityState;
    }

    /**
     * Set the availabilityState.
     *
     * @param availabilityState The availabilityState to set.
     */
    protected void setAvailabilityState(AvailabilityState availabilityState) {
        this.availabilityState = availabilityState;
    }

    /**
     * Get the quantity.
     *
     * @return the quantity.
     */
    public UnitType getQuantity() {
        return quantity;
    }

    /**
     * Set the quantity.
     *
     * @param qty The quantity to set.
     */
    protected void setQuantity(UnitType qty) {
        verifyQuantity(qty);
        this.quantity = qty;
    }

    /**
     * Throw an exception when the quantity {@code qty} is {@literal null} or of negative value.
     */
    private void verifyQuantity(UnitType qty) {
        if (qty == null || qty.getMeasurable().isNegative()) {
            throw new IllegalArgumentException("Not allowed to set the quantity of a PackagingUnit less than 0");
        }
    }

    /**
     * Get the fifoDate.
     *
     * @return the fifoDate.
     */
    public Date getFifoDate() {
        return new Date(fifoDate.getTime());
    }

    /**
     * Return a combination of TransportUnit, LoadUnit and Label.
     * <p>
     * For example: 0000000000000BARCODE/TOP_RIGHT/123456789
     */
    @Override
    public String toString() {
        return this.getTransportUnitId() + "/" + this.getLoadUnit().getPhysicalPosition() + "/" + this.getLabel();
    }
}