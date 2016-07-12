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
package org.openwms.common.location;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import org.ameba.integration.jpa.BaseEntity;
import org.springframework.util.Assert;

/**
 * A LocationType is the type of {@code Location}s with same characteristics.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @GlossaryTerm
 * @see org.openwms.common.location.Location
 * @since 0.1
 */
@Entity
@Table(name = "COM_LOCATION_TYPE")
public class LocationType extends BaseEntity implements Serializable {
    
    /** Type of the {@code LocationType} (unique). */
    @Column(name = "C_TYPE", unique = true)
    @OrderBy
    private String type;

    /** Description of the {@code LocationType}. */
    @Column(name = "C_DESCRIPTION")
    private String description = DEF_TYPE_DESCRIPTION;
    /** Default value of the description, by default * {@value} . */
    public static final String DEF_TYPE_DESCRIPTION = "--";

    /** Length of the {@code LocationType}. */
    @Column(name = "C_LENGTH")
    private int length = DEF_LENGTH;
    /** Default value of {@link #length}. */
    public static final int DEF_LENGTH = 0;

    /** Width of the {@code LocationType}. */
    @Column(name = "C_WIDTH")
    private int width = DEF_WIDTH;
    /** Default value of {@link #width}. */
    public static final int DEF_WIDTH = 0;

    /** Height of the {@code LocationType}. */
    @Column(name = "C_HEIGHT")
    private int height = DEF_HEIGHT;
    /** Default value of {@link #height}. */
    public static final int DEF_HEIGHT = 0;

    /*~ ----------------------------- constructors ------------------- */
    /**
     * Dear JPA...
     */
    protected LocationType() {
    }

    /**
     * Create a new {@code LocationType} with an unique natural key.
     *
     * @param type Unique type
     */
    public LocationType(String type) {
        Assert.hasText(type, "type must exist when creating a new LocationType");
        this.type = type;
    }

    /* ----------------------------- methods ------------------- */
    /**
     * Returns the unique identifier of the {@code LocationType}.
     *
     * @return type The Type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the length of the {@code LocationType}.
     *
     * @return length The Length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set the length of this {@code LocationType}.
     *
     * @param length The length of this type
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the width of this {@code LocationType}.
     *
     * @return width The Width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the width of this {@code LocationType}.
     *
     * @param width The width of this type
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the {@code LocationType}.
     *
     * @return height The Height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the height of this {@code LocationType}.
     *
     * @param height The height of this type
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the description of this {@code LocationType}.
     *
     * @return description The description text
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description of the {@code LocationType}.
     *
     * @param description The description text of this type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the type as String.
     *
     * @return String
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationType that = (LocationType) o;
        return Objects.equals(type, that.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}