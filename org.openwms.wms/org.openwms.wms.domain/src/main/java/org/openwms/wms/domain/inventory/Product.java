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
package org.openwms.wms.domain.inventory;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.values.CoreTypeDefinitions;
import org.openwms.wms.domain.types.WMSTypes;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A Product.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "WMS_PRODUCT")
@NamedQueries({
        @NamedQuery(name = Product.NQ_FIND_ALL, query = "select p from Product p"),
        @NamedQuery(name = Product.NQ_FIND_SKU, query = "select p from Product p where p.sku = :"
                + Product.QP_FIND_PRODUCT_SKU + " order by p.sku") })
public class Product extends AbstractEntity<Long> implements Comparable<Product>, Serializable {

    private static final long serialVersionUID = -7714815919817459002L;

    /** Unique technical key. */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue
    private Long id;

    /** The product id is an unique business key. Limited to 40 characters */
    @Column(name = "C_SKU", unique = true, length = WMSTypes.SKU_LENGTH, nullable = false)
    private String sku;

    /** Textual descriptive text. Limited to 1024 characters */
    @Column(name = "C_DESCRIPTION", length = CoreTypeDefinitions.DESCRIPTION_LENGTH)
    private String description;

    /** Where this product has to be put in stock. */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_STOCK_ZONE")
    private StockZone stockZone;

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

    /**
     * Query to find all <code>Product</code>s.<br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_ALL = "Product.findAll";

    /**
     * Query to find <strong>one</strong> <code>Product</code> by its SKU. <li>
     * Query parameter name <strong>{@value #QP_FIND_PRODUCT_SKU} </strong> :
     * The unique id of the <code>Product</code> to search for.</li><br />
     * Query name is {@value} .
     */
    public static final String NQ_FIND_SKU = "Product.findBySKU";
    public static final String QP_FIND_PRODUCT_SKU = "sku";

    /**
     * Accessed by persistence provider.
     */
    Product() {
        super();
    }

    /**
     * Create a new Product with an unique Id.
     * 
     * @param prodId
     *            The SKU of this Product
     */
    public Product(String prodId) {
        this.sku = prodId;
    }

    /**
     * Set the creation date.
     */
    @PrePersist
    void prePersist() {
        this.createdDate = new Date();
    }

    /**
     * Set the changed date.
     */
    @PreUpdate
    void preUpdate() {
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
     * Get the SKU.
     * 
     * @return the SKU.
     */
    public String getSku() {
        return sku;
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
     * Set the description.
     * 
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the stockZone.
     * 
     * @return the stockZone.
     */
    public StockZone getStockZone() {
        return stockZone;
    }

    /**
     * Set the stockZone.
     * 
     * @param stockZone
     *            The stockZone to set.
     */
    public void setStockZone(StockZone stockZone) {
        this.stockZone = stockZone;
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
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Product o) {
        return null == o ? -1 : this.sku.compareTo(o.sku);
    }

    /**
     * {@inheritDoc}
     * 
     * Return the SKU;
     */
    @Override
    public String toString() {
        return sku;
    }
}