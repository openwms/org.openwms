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
package org.openwms.wms.domain.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.domain.values.CoreTypeDefinitions;
import org.openwms.wms.domain.types.WMSTypes;

/**
 * A Product.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "WMS_PRODUCT")
public class Product extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -7714815919817459002L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /** The product id is an unique business key. Limited to 40 characters */
    @Column(name = "C_PRODUCT_ID", unique = true, length = WMSTypes.PRODUCT_ID_LENGTH, nullable = false)
    private String productId;

    /** Textual descriptive text. Limited to 1024 characters */
    @Column(name = "C_DESCRIPTION", length = CoreTypeDefinitions.DESCRIPTION_LENGTH)
    private String description;

    /** Where this product has to be put in stock. */
    @Enumerated(EnumType.STRING)
    private StockZone stockZone;

    /**
     * Base UOM of product.
     * 
     * @Column(name = "C_BASE_UOM") private Unit<UnitType> baseUOM;
     */
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
     * Get the productId.
     * 
     * @return the productId.
     */
    public String getProductId() {
        return productId;
    }

    /**
     * Get the description.
     * 
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the stockZone.
     * 
     * @return the stockZone.
     */
    public StockZone getStockZone() {
        return stockZone;
    }
}