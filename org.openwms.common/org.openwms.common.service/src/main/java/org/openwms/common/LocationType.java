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
package org.openwms.common;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.core.AbstractEntity;
import org.openwms.core.DomainObject;

/**
 * A LocationType is the type of <code>Location</code>s with same characteristics.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.Location
 */
@Entity
@Table(name = "COM_LOCATION_TYPE")
@NamedQueries({
        @NamedQuery(name = LocationType.NQ_FIND_ALL, query = "select l from LocationType l"),
        @NamedQuery(name = LocationType.NQ_FIND_BY_UNIQUE_QUERY, query = "select l from LocationType l where l.type = ?1") })
public class LocationType extends AbstractEntity<Long> implements DomainObject<Long> {

    private static final long serialVersionUID = 7694581168374440182L;

    /**
     * Query to find all <code>LocationType</code>s.
     */
    public static final String NQ_FIND_ALL = "LocationType.findAll";

    /**
     * Query to find <strong>one</strong> <code>LocationType</code> by its natural key.
     * <ul>
     * <li>Query parameter index <strong>1</strong> : The name of the <code>LocationType</code> to search for.</li>
     * </ul>
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "LocationType.findByUniqueId";

    /**
     * Default value of the description, by default * {@value} .
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
     * Type of the <code>LocationType</code> (unique).
     */
    @Column(name = "C_TYPE", unique = true)
    @OrderBy
    private String type;

    /**
     * Description of the <code>LocationType</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description = DEF_TYPE_DESCRIPTION;

    /**
     * Length of the <code>LocationType</code>.
     */
    @Column(name = "LENGTH")
    private int length = 0;

    /**
     * Width of the <code>LocationType</code>.
     */
    @Column(name = "WIDTH")
    private int width = 0;

    /**
     * Height of the <code>LocationType</code>.
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
     * Accessed by the persistence provider.
     */
    @SuppressWarnings("unused")
    private LocationType() {
        super();
    }

    /**
     * Create a new <code>LocationType</code> with an unique natural key.
     * 
     * @param type
     *            Unique type
     */
    public LocationType(String type) {
        super();
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
     * Returns the unique identifier of the <code>LocationType</code>.
     * 
     * @return type The Type
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the length of the <code>LocationType</code>.
     * 
     * @return length The Length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set the length of this <code>LocationType</code>.
     * 
     * @param length
     *            The length of this type
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns the width of this <code>LocationType</code>.
     * 
     * @return width The Width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the width of this <code>LocationType</code>.
     * 
     * @param width
     *            The width of this type
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the description of this <code>LocationType</code>.
     * 
     * @return description The description text
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description of the <code>LocationType</code>.
     * 
     * @param description
     *            The description text of this type
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the height of the <code>LocationType</code>.
     * 
     * @return height The Height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the height of this <code>LocationType</code>.
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