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
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.openwms.common.domain.TransportUnit;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
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
    @ManyToOne
    @JoinColumn(name = "C_TRANSPORT_UNIT")
    private TransportUnit transportUnit;

    /** Carrying {@link LoadUnit}. */
    @ManyToOne
    @JoinColumn(name = "C_LOAD_UNIT")
    private LoadUnit loadUnit;

    /** Unique identifier within the {@link LoadUnit}. */
    @Column(name = "C_NO")
    private int no;

    /** The packaged {@link Product}. */
    @ManyToOne
    @JoinColumn(name = "C_PRODUCT", referencedColumnName = "C_PRODUCT_ID")
    private Product product;

    @Column(name = "C_AV_STATE")
    @Enumerated(EnumType.STRING)
    private AvailabilityState availabilityState;

    /** Current quantity. */
    @Column(name = "C_QTY")
    private BigDecimal qty;

    /**
     * UOM. private UnitType unit;
     */

    /** Used to control the putaway strategy. */
    @Column(name = "C_FIFO_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fifoDate;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

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
     * Get the no.
     * 
     * @return the no.
     */
    public int getNo() {
        return no;
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
     * Get the availabilityState.
     * 
     * @return the availabilityState.
     */
    public AvailabilityState getAvailabilityState() {
        return availabilityState;
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
     * Get the fifoDate.
     * 
     * @return the fifoDate.
     */
    public Date getFifoDate() {
        return fifoDate;
    }
}