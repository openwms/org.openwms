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
import org.openwms.wms.inventory.Product;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * A LoadUnit is used to divide a {@code TransportUnit} into physical areas. It is used for separation concerns only and cannot be
 * transported without a {@code TransportUnit}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "WMS_LOAD_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = {"C_TRANSPORT_UNIT", "C_PHYSICAL_POS"}))
public class LoadUnit extends BaseEntity implements Serializable {

    /** The barcode of the {@code TransportUnit} where this {@link LoadUnit} belongs to. */
    @Column(name = "C_TRANSPORT_UNIT")
    private String transportUnit;

    /** Where this {@link LoadUnit} is located on the {@code TransportUnit}. */
    @Column(name = "C_PHYSICAL_POS")
    private String physicalPosition;

    /** Locked for allocation. */
    @Column(name = "C_LOCKED")
    private boolean locked = false;

    /** The Product that is carried in this LoadUnit. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "C_PRODUCT_ID", referencedColumnName = "C_SKU")
    private Product product;

    /** All {@link PackagingUnit}s that belong to this LoadUnit. */
    @OneToMany(mappedBy = "loadUnit")
    private Set<PackagingUnit> packagingUnits = new HashSet<PackagingUnit>();

    /** Dear JPA ... */
    protected LoadUnit() {
    }

    /**
     * Create a new LoadUnit.
     *
     * @param tu The {@code TransportUnit} where this LoadUnit stands on.
     * @param physicalPosition The physical position within the {@code TransportUnit} where this LoadUnit stands on
     */
    public LoadUnit(String tu, String physicalPosition) {
        this.transportUnit = tu;
        this.physicalPosition = physicalPosition;
    }

    /**
     * Create a new LoadUnit.
     *
     * @param tu The {@code TransportUnit} where this LoadUnit stands on.
     * @param physicalPosition The physical position within the {@code TransportUnit} where this LoadUnit stands on
     * @param product The {@link Product} to set on this LoadUnit
     */
    public LoadUnit(String tu, String physicalPosition, Product product) {
        this(tu, physicalPosition);
        this.product = product;
    }

    /**
     * Set the creation date. Check that a TransportUnit is set. Notice, that we throw a RuntimeException instead of a checked exception
     * because these check shall be done in the outer service layer already.
     */
    @PrePersist
    protected void prePersist() {
        if (null == this.transportUnit) {
            throw new DomainModelRuntimeException("Not allowed to create a new LoadUnit without a TransportUnit");
        }
    }

    /**
     * Get the transportUnit.
     *
     * @return the transportUnit.
     */
    public String getTransportUnit() {
        return transportUnit;
    }

    /**
     * Get the physicalPosition.
     *
     * @return the physicalPosition.
     */
    public String getPhysicalPosition() {
        return physicalPosition;
    }

    /**
     * Get the locked.
     *
     * @return the locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Set the locked.
     *
     * @param locked The locked to set.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
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
    public void setProduct(Product product) {
        if (this.product != null) {
            throw new DomainModelRuntimeException("Not allowed to change the Product of an LoadUnit");
        }
        this.product = product;
    }

    /**
     * Unassign the product from this LoadUnit - set it to {@literal null}.
     */
    public void unassignProduct() {
        product = null;
    }

    /**
     * Get the packagingUnits.
     *
     * @return the packagingUnits.
     */
    public Set<PackagingUnit> getPackagingUnits() {
        return packagingUnits;
    }

    /**
     * Add one or more {@link PackagingUnit}s to this LoadUnit.
     *
     * @param pUnits {@link PackagingUnit}s to add
     * @return {@literal true} if this set changed as a result of the call
     */
    public boolean addPackagingUnits(PackagingUnit... pUnits) {
        return packagingUnits.addAll(Arrays.asList(pUnits));
    }

    /**
     * Remove one or more {@link PackagingUnit}s from this LoadUnit.
     *
     * @param pUnits {@link PackagingUnit}s to remove
     */
    public void removePackagingUnits(PackagingUnit... pUnits) {
        packagingUnits.removeAll(Arrays.asList(pUnits));
    }

    /**
     * {@inheritDoc}
     * <p>
     * Return a combination of the barcode and the physicalPosition.
     */
    @Override
    public String toString() {
        return transportUnit + " / " + physicalPosition;
    }
}