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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A LocationType - Defines a type for {@link Location}s.
 * <p>
 * Type of a {@link org.openwms.common.domain.Location}.<br>
 * Used to describe {@link org.openwms.common.domain.Location}s that have same
 * characteristics.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.Location
 */
@Entity
@Table(name = "COR_LOCATION_TYPE")
@NamedQueries( {
        @NamedQuery(name = LocationType.NQ_FIND_ALL, query = "select l from LocationType l"),
        @NamedQuery(name = LocationType.NQ_FIND_BY_UNIQUE_QUERY, query = "select l from LocationType l where l.type = ?1") })
public class LocationType implements DomainObject, Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7694581168374440182L;

    /**
     * Query to find all {@link LocationType}s.
     */
    public static final String NQ_FIND_ALL = "LocationType.findAll";

    /**
     * Query to find <strong>one</strong> {@link LocationType} by its natural
     * key.
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "LocationType.findByUniqueId";

    /**
     * Default value of the description, by default
     * {@value #DEF_TYPE_DESCRIPTION}.
     */
    public static final String DEF_TYPE_DESCRIPTION = "--";

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Type of this {@link LocationType}.
     */
    @Column(name = "TYPE", unique = true)
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
    @Column(name = "C_VERSION")
    private long version;

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
     *            Unique type
     */
    public LocationType(String type) {
        super();
        this.type = type;
    }

    /**
     * Return the technical key.
     * 
     * @return The technical, unique key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Get the unique identifier of this {@link LocationType}.
     * 
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Get the length of this {@link LocationType}.
     * 
     * @return length
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
     * @return width
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
     * @return description
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
     * @return height
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
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * Return the type as String.
     * 
     * @see java.lang.Object#toString()
     * @return String
     */
    @Override
    public String toString() {
        return getType();
    }
}
