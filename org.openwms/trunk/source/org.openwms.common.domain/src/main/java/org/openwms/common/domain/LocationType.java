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
package org.openwms.common.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A LocationType.
 * <p>
 * Type of a {@link org.openwms.common.domain.Location}.<br>
 * Used to describe {@link org.openwms.common.domain.Location}s that have same
 * characteristics.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see {@link org.openwms.common.domain.Location}
 */
@Entity
@Table(name = "LOCATION_TYPE")
public class LocationType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default value of the description.
     */
    public static final String DEF_TYPE_DESCRIPTION = "--";

    /**
     * Type of this {@link LocationType}.
     */
    @Id
    @Column(name = "TYPE")
    private String type;

    /**
     * Description of this {@link LocationType}.
     */
    @Column(name = "DESCRIPTION")
    private String description = DEF_TYPE_DESCRIPTION;

    /**
     * Length of this {@link LocationType}.
     */
    @Column(name = "LENGTH")
    private int length = 0;

    /**
     * Width of this {@link LocationType}.
     */
    @Column(name = "WIDTH")
    private int width = 0;

    /**
     * Height of this {@link LocationType}.
     */
    @Column(name = "HEIGHT")
    private int height = 0;

    /**
     * Version field.
     */
    @Version
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * All {@link LocationType}s belonging to this type.
     */
    @OneToMany(mappedBy = "locationType")
    private Set<Location> locations;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private LocationType() {}

    /**
     * Create a new {@link LocationType} with a unique natural key.
     * 
     * @param type
     *            - Unique type
     */
    public LocationType(String type) {
        super();
        this.type = type;
    }

    /**
     * Get the unique identifier of this {@link LocationType}.
     * 
     * @return - type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the length of this {@link LocationType}.
     * 
     * @return - length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set the length of this {@link LocationType}.
     * 
     * @param length
     *            The length of this type
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Get the width of this {@link LocationType}.
     * 
     * @return - width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the width of this {@link LocationType}.
     * 
     * @param width
     *            The width of this type
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Get the description of this {@link LocationType}.
     * 
     * @return - description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description of this {@link LocationType}.
     * 
     * @param description
     *            The description text of this type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the height of this {@link LocationType}.
     * 
     * @return - height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the height of this {@link LocationType}.
     * 
     * @param height
     *            The height of this type
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Get all {@link LocationType}s belonging to this {@link LocationType}.
     * 
     * @return - All {@link LocationType}s belonging to this
     *         {@link LocationType}
     */
    public Set<Location> getLocations() {
        return this.locations;
    }

    /**
     * Set a {@java.util.Set} of {@link LocationType}s belonging
     * to this {@link LocationType}.
     * 
     * @param locations
     *            a collection of {@link Location} to assign to this type
     */
    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return - Version field
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Return the type as String.
     * 
     * @see java.lang.Object#toString()
     * @return as String
     */
    @Override
    public String toString() {
        return getType();
    }
}
