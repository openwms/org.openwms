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

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

import org.openwms.common.domain.TransportUnit;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.domain.values.CoreTypeDefinitions;
import org.openwms.core.domain.values.Piece;
import org.openwms.core.exception.DomainModelRuntimeException;
import org.openwms.core.util.validation.AssertUtils;
import org.openwms.wms.domain.inventory.Product;

/**
 * A PackagingUnit is the actual assignment of a {@link Product} and a quantity.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "WMS_PACKAGING_UNIT")
public class PackagingUnit extends AbstractEntity implements DomainObject<Long> {

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

    /** Unique identifier within the {@link LoadUnit}. */
    @Column(name = "C_LABEL", unique = true)
    private String label;

    /** The packaged {@link Product}. */
    @ManyToOne
    @JoinColumn(name = "C_PRODUCT", referencedColumnName = "C_PRODUCT_ID")
    private Product product;

    @Column(name = "C_AV_STATE")
    @Enumerated(EnumType.STRING)
    private AvailabilityState availabilityState;

    /** Current quantity. */
    @Embedded
    @AttributeOverride(name = "quantity", column = @Column(name = "C_QUANTITY", length = CoreTypeDefinitions.QUANTITY_LENGTH))
    private Piece qty;

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
     * @param label
     *            The unique label of this PackagingUnit
     */
    public PackagingUnit(LoadUnit lu, String label) {
        AssertUtils.notNull(lu);
        AssertUtils.isNotEmpty(label);
        assignInitialValues(lu, label);
        this.product = lu.getProduct();
    }

    /**
     * Create a new PackagingUnit.
     * 
     * @param lu
     *            The {@link LoadUnit} where this PackingUnit is carried in
     * @param label
     *            The unique label of this PackagingUnit
     * @param product
     *            A Product to assign
     */
    public PackagingUnit(LoadUnit lu, String label, Product product) {
        AssertUtils.notNull(lu);
        AssertUtils.isNotEmpty(label);
        AssertUtils.notNull(product);
        assignInitialValues(lu, label);
        if (lu.getProduct() == null) {
            this.product = product;
            this.loadUnit.setProduct(product);
        } else {
            // check afterwards if both Products matches
            this.product = lu.getProduct();
        }
    }

    private void assignInitialValues(LoadUnit lu, String label) {
        this.loadUnit = lu;
        this.label = label;
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
        verifySameProduct();
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
        verifySameProduct();
        this.changedDate = new Date();
    }

    private void verifySameProduct() {
        if (!this.product.equals(this.loadUnit.getProduct())) {
            throw new DomainModelRuntimeException("It is not allowed to have different Products on the LoadUnit ["
                    + this.loadUnit.getProduct() + "] and the PackagingUnit [" + this.getProduct() + "]");
        }
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
    protected void setQty(Piece qty) {
        if (qty.compareTo(Piece.ZERO) < 0) {
            throw new IllegalArgumentException("Not allowed to set the quantity of a PackagingUnit less than 0");
        }
        this.qty = qty;
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
        return createdDate;
    }

    /**
     * Get the changedDate.
     * 
     * @return the changedDate.
     */
    public Date getChangedDate() {
        return changedDate;
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