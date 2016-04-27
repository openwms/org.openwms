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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

import org.openwms.common.TransportUnit;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.values.CoreTypeDefinitions;
import org.openwms.core.domain.values.UnitType;
import org.openwms.core.exception.DomainModelRuntimeException;
import org.openwms.core.util.validation.AssertUtils;
import org.openwms.wms.inventory.Product;

/**
 * A PackagingUnit is the actual assignment of a {@link Product} and a quantity.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "WMS_PACKAGING_UNIT")
public class PackagingUnit extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = 8969188598565357329L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /** Carrying {@link TransportUnit}. */
    // @ManyToOne
    // @JoinColumn(name = "C_TRANSPORT_UNIT")
    @Transient
    private TransportUnit transportUnit;

    /** Carrying {@link LoadUnit}. */
    @ManyToOne
    @JoinColumn(name = "C_LOAD_UNIT")
    private LoadUnit loadUnit;

    /**
     * Unique identifier within the {@link LoadUnit}. Can be <code>null</code>
     */
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
    @org.hibernate.annotations.Columns(columns = { @Column(name = "C_QUANTITY_TYPE"),
            @Column(name = "C_QUANTITY", length = CoreTypeDefinitions.QUANTITY_LENGTH) })
    private UnitType quantity;

    /** Used to control the putaway strategy. */
    @Column(name = "C_FIFO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fifoDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CREATED_DT")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CHANGED_DT")
    private Date changedDate;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /**
     * Accessed by persistence provider.
     */
    protected PackagingUnit() {
        super();
    }

    /**
     * Create a new PackagingUnit.
     * 
     * @param lu
     *            The {@link LoadUnit} where this PackingUnit is carried in
     */
    public PackagingUnit(LoadUnit lu, UnitType qty) {
        AssertUtils.notNull(lu);
        AssertUtils.notNull(qty);
        assignInitialValues(lu);
        this.quantity = qty;
        this.product = lu.getProduct();
    }

    /**
     * Create a new PackagingUnit.
     * 
     * @param lu
     *            The {@link LoadUnit} where this PackingUnit is carried in
     * @param product
     *            A Product to assign
     */
    public PackagingUnit(LoadUnit lu, UnitType qty, Product product) {
        AssertUtils.notNull(lu);
        AssertUtils.notNull(qty);
        AssertUtils.notNull(product);
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
        this.transportUnit = lu.getTransportUnit();
    }

    /**
     * Set the creation date.
     * 
     * Verify:
     * <ul>
     * <li>Same Product</li>
     * </ul>
     */
    @PrePersist
    void prePersist() {
        verify();
        this.createdDate = new Date();
    }

    /**
     * Set the changed date.
     * 
     * Verify:
     * <ul>
     * <li>Same Product</li>
     * </ul>
     */
    @PreUpdate
    void preUpdate() {
        verify();
        this.changedDate = new Date();
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
        this.transportUnit = this.loadUnit.getTransportUnit();
    }

    /**
     * @see org.openwms.core.domain.DomainObject#isNew()
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * @see org.openwms.core.domain.DomainObject#getVersion()
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * @see org.openwms.core.domain.DomainObject#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Get the transportUnit.
     * 
     * @return the transportUnit.
     */
    public TransportUnit getTransportUnit() {
        return transportUnit;
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
     * @param label
     *            The label to set.
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
     * @param product
     *            The product to set.
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
     * @param availabilityState
     *            The availabilityState to set.
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
     * @param quantity
     *            The quantity to set.
     */
    protected void setQuantity(UnitType qty) {
        verifyQuantity(qty);
        this.quantity = qty;
    }

    /**
     * Throw an exception when the quantity <code>qty</code> is
     * <code>null</code> or of negative value.
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
     * Get the createdDate.
     * 
     * @return the createdDate.
     */
    public Date getCreatedDate() {
        return new Date(createdDate.getTime());
    }

    /**
     * Get the changedDate.
     * 
     * @return the changedDate.
     */
    public Date getChangedDate() {
        return new Date(changedDate.getTime());
    }

    /**
     * Return a combination of TransportUnit, LoadUnit and Label.
     * 
     * For example: 0000000000000BARCODE/TOP_RIGHT/123456789
     */
    @Override
    public String toString() {
        return this.getTransportUnit() + "/" + this.getLoadUnit().getPhysicalPosition() + "/" + this.getLabel();
    }
}