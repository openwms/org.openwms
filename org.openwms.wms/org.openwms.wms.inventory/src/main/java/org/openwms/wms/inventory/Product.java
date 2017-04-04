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
package org.openwms.wms.inventory;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.core.values.CoreTypeDefinitions;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A Product.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Entity
@Table(name = "WMS_PRODUCT")
public class Product extends BaseEntity implements Comparable<Product>, Serializable {

    /** The product id is the unique business key. */
    @Column(name = "C_SKU", unique = true, length = 20, nullable = false)
    private String sku;

    /** Textual descriptive text. */
    @Column(name = "C_DESCRIPTION", length = CoreTypeDefinitions.DESCRIPTION_LENGTH)
    private String description;

    /** Where this product has to be placed in stock. */
    @Enumerated(EnumType.STRING)
    @Column(name = "C_STOCK_ZONE")
    private StockZone stockZone;

    /**
     * Dear JPA ...
     */
    protected Product() {

    }

    /**
     * Create a Product with a sku.
     *
     * @param sku The sku
     */
    public Product(String sku) {
        this.sku = sku;
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
     * {@inheritDoc}
     *
     * Uses the sku for comparison
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