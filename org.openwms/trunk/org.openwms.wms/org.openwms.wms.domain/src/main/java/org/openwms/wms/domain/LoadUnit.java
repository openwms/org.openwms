/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.wms.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.common.domain.TransportUnit;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.domain.values.Piece;
import org.openwms.core.exception.DomainModelRuntimeException;
import org.openwms.wms.domain.inventory.Product;

/**
 * A LoadUnit is used to divide a {@link TransportUnit} into physical areas. It
 * is used for separation concerns only and cannot be transported without a
 * {@link TransportUnit}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.common.domain.TransportUnit
 */
@Entity
@Table(name = "WMS_LOAD_UNIT", uniqueConstraints = @UniqueConstraint(columnNames = { "C_TRANSPORT_UNIT",
        "C_PHYSICAL_POS" }))
public class LoadUnit extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -5524006837325285793L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /** The {@link TransportUnit} where this {@link LoadUnit} belongs to. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "C_TRANSPORT_UNIT")
    private TransportUnit transportUnit;

    /** Where this {@link LoadUnit} is located on the {@link TransportUnit}. */
    @Column(name = "C_PHYSICAL_POS")
    private String physicalPosition;

    /** Locked for allocation. */
    @Column(name = "C_LOCKED")
    private boolean locked = false;

    /** The Product that is carried in this LoadUnit. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "C_PRODUCT_ID")
    private Product product;

    /** Sum of quantities of all PackagingUnits. */
    @Embedded
    private Piece qty;

    /** The date this LoadUnit was created. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CREATED_DT")
    private Date createdDate;

    /** The date this LoadUnit has changed the last time. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_CHANGED_DT")
    private Date changedDate;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /** All {@link PackagingUnit}s that belong to this LoadUnit. */
    @OneToMany(mappedBy = "loadUnit")
    private Set<PackagingUnit> packagingUnits = new HashSet<PackagingUnit>();

    /**
     * Accessed by persistence provider.
     */
    protected LoadUnit() {}

    /**
     * Create a new LoadUnit.
     * 
     * @param tu
     *            The {@link TransportUnit} where this LoadUnit stands on.
     * @param physicalPosition
     *            The physical position within the {@link TransportUnit} where
     *            this LoadUnit stands on
     */
    public LoadUnit(TransportUnit tu, String physicalPosition) {
        this.transportUnit = tu;
        this.physicalPosition = physicalPosition;
    }

    /**
     * Create a new LoadUnit.
     * 
     * @param tu
     *            The {@link TransportUnit} where this LoadUnit stands on.
     * @param physicalPosition
     *            The physical position within the {@link TransportUnit} where
     *            this LoadUnit stands on
     * @param quantity
     *            The quantity of this LoadUnit
     * @param product
     *            The {@link Product} to set on this LoadUnit
     */
    public LoadUnit(TransportUnit tu, String physicalPosition, Piece quantity, Product product) {
        this(tu, physicalPosition);
        this.qty = quantity;
        this.product = product;
    }

    /**
     * Set the creation date. Check the quantity for values different than 0 and
     * throw a RuntimeException. Notice, that we throw a RuntimeException
     * instead of a checked exception because these check shall be done in the
     * outer service layer already.
     */
    @PrePersist
    protected void prePersist() {
        if (Piece.ZERO.compareTo(this.qty) == 0) {
            throw new DomainModelRuntimeException("Not allowed to create a new LoadUnit with a quantity of 0");
        }
        if (null == this.transportUnit) {
            throw new DomainModelRuntimeException("Not allowed to create a new LoadUnit without a TransportUnit");
        }
        this.createdDate = new Date();
    }

    /**
     * Set the changed date.
     */
    @PreUpdate
    protected void preUpdate() {
        this.changedDate = new Date();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
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
     * @param locked
     *            The locked to set.
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
     * @param product
     *            The product to set.
     */
    public void setProduct(Product product) {
        if (this.product != null) {
            throw new DomainModelRuntimeException("Not allowed to change the Product of an LoadUnit");
        }
        this.product = product;
    }

    /**
     * Unassign the product from this LoadUnit - set it to <code>null</code>.
     */
    public void unassignProduct() {
        this.packagingUnits = null;
        this.qty = Piece.ZERO;
        this.product = null;
    }

    /**
     * Get the qty.
     * 
     * @return the qty.
     */
    public Piece getQty() {
        return qty;
    }

    /**
     * Set the qty.
     * 
     * @param qty
     *            The qty to set.
     */
    void setQty(Piece qty) {
        this.qty = qty;
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
     * @param pUnits
     *            {@link PackagingUnit}s to add
     * @return <code>true</code> if this set changed as a result of the call
     */
    public boolean addPackagingUnits(PackagingUnit... pUnits) {
        return this.packagingUnits.addAll(packagingUnits);
    }

    /**
     * Remove one or more {@link PackagingUnit}s from this LoadUnit.
     * 
     * @param pUnits
     *            {@link PackagingUnit}s to remove
     */
    public void removePackagingUnits(PackagingUnit... pUnits) {
        this.packagingUnits.removeAll(Arrays.asList(pUnits));
    }

    /**
     * {@inheritDoc}
     * 
     * Return a combination of the barcode and the physicalPosition.
     */
    @Override
    public String toString() {
        return this.transportUnit.getBarcode() + "/" + this.physicalPosition;
    }
}