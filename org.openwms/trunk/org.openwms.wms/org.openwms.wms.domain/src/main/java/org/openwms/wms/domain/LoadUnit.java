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

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.common.domain.TransportUnit;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
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
@Table(name = "WMS_LOAD_UNIT")
public class LoadUnit extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -5524006837325285793L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /** The {@link TransportUnit} where this {@link LoadUnit} belongs to. */
    @ManyToOne
    @JoinColumn(name = "C_TRANSPORT_UNIT")
    private TransportUnit transportUnit;

    /** Where this {@link LoadUnit} is located on the {@link TransportUnit}. */
    @Column(name = "C_PHYSICAL_POS")
    private String physicalPosition;

    /** Locked for allocation. */
    @Column(name = "C_LOCKED")
    private boolean locked = false;

    @ManyToOne
    @JoinColumn(name = "C_PRODUCT_ID", referencedColumnName = "C_PRODUCT_ID", insertable = false, updatable = false)
    private Product product;

    /** Sum of quantities of all PackagingUnits. */
    @Column(name = "C_QTY")
    private BigDecimal qty;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    @OneToMany(mappedBy = "loadUnit")
    private Set<PackagingUnit> packagingUnits = new HashSet<PackagingUnit>();

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
     * Get the product.
     * 
     * @return the product.
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Get the qty.
     * 
     * @return the qty.
     */
    public BigDecimal getQty() {
        return qty;
    }

    /**
     * Get the packagingUnits.
     * 
     * @return the packagingUnits.
     */
    public Set<PackagingUnit> getPackagingUnits() {
        return packagingUnits;
    }
}