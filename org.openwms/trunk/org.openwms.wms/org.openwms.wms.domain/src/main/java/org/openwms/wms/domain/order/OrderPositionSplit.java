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
package org.openwms.wms.domain.order;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.wms.domain.inventory.Product;

/**
 * A OrderPositionSplit.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "WMS_ORDER_POS_SPLIT")
public class OrderPositionSplit extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -1444350691553190L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "C_ID", referencedColumnName = "C_ID", insertable = false, updatable = false)
    private OrderPosition position;

    /** Business key. */
    @Embedded
    private OrderPositionSplitKey splitId;

    /** Quantity of this split. */
    @Column(name = "C_QTY")
    private BigDecimal qty;

    /**
     * UOM.
     * 
     * @Column(name = "C_UNIT") private Unit<UnitType> unit;
     */

    /** Current priority of the split. */
    @Column(name = "C_PRIORITY")
    private int priority;

    /** The ordered <code>Product</code>. */
    @ManyToOne
    @JoinColumn(name = "C_PRODUCT_ID")
    private Product product;

    /** Version field. */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /**
     * After data was loaded, initialize some fields with transient values.
     */
    @PostLoad
    protected void postLoad() {
        this.position = this.getPosition();
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
     * Get the position.
     * 
     * @return the position.
     */
    public OrderPosition getPosition() {
        return position;
    }

    /**
     * Get the splitId.
     * 
     * @return the splitId.
     */
    public OrderPositionSplitKey getSplitId() {
        return splitId;
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
     * Get the priority.
     * 
     * @return the priority.
     */
    public int getPriority() {
        return priority;
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