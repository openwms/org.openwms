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
package org.openwms.core.uaa;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;

import org.openwms.core.AbstractEntity;
import org.openwms.core.validation.AssertUtils;
import org.openwms.core.values.CoreTypeDefinitions;

/**
 * A SecurityObject is the generalization of <code>Role</code>s and <code>Grant</code>s and combines common used properties of both.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.uaa.Role
 * @see org.openwms.core.uaa.Grant
 */
@Entity
@Table(name = "COR_ROLE")
@Inheritance
@DiscriminatorColumn(name="TYPE", discriminatorType=DiscriminatorType.STRING,length=20)
@NamedQueries({
        @NamedQuery(name = SecurityObject.NQ_FIND_ALL, query = "select g from SecurityObject g"),
        @NamedQuery(name = SecurityObject.NQ_FIND_BY_UNIQUE_QUERY, query = "select g from SecurityObject g where g.name = ?1") })
public class SecurityObject extends AbstractEntity<Long> implements Serializable {

    private static final long serialVersionUID = 7585736035228078754L;
    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "C_ID")
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    /**
     * Unique name of the <code>SecurityObject</code>.
     */
    @Column(name = "C_NAME", unique = true)
    @OrderBy
    private String name;
    /**
     * Description of the <code>SecurityObject</code>.
     */
    @Column(name = "C_DESCRIPTION", length = CoreTypeDefinitions.DESCRIPTION_LENGTH)
    private String description;
    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;
    /**
     * Query to find all {@link SecurityObject}s. Name is {@value} .
     */
    public static final String NQ_FIND_ALL = "SecurityObject.findAll";
    /**
     * Query to find <strong>one</strong> {@link SecurityObject} by its natural key. <li>Query parameter index <strong>1</strong> : The name
     * of the <code>SecurityObject</code> to search for.</li><br />
     * Name is {@value} .
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "SecurityObject.findByName";

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected SecurityObject() {
        super();
    }

    /**
     * Create a new <code>SecurityObject</code> with a name.
     * 
     * @param name
     *            The name of the <code>SecurityObject</code>
     * @throws IllegalArgumentException
     *             when name is <code>null</code> or an empty String
     */
    public SecurityObject(String name) {
        AssertUtils.isNotEmpty(name, "A name of a SecurityObject must not be null");
        this.name = name;
    }

    /**
     * Create a new <code>SecurityObject</code> with name and description.
     * 
     * @param name
     *            The name of the <code>SecurityObject</code>
     * @param description
     *            The description text of the <code>SecurityObject</code>
     * @throws IllegalArgumentException
     *             when name is <code>null</code> or an empty String
     */
    public SecurityObject(String name, String description) {
        AssertUtils.isNotEmpty(name, "A name of a SecurityObject must not be null");
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return id == null;
    }

    /**
     * Returns the name.
     * 
     * @return The name of the <code>SecurityObject</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description text.
     * 
     * @return The description of the <code>SecurityObject</code> as text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description for the <code>SecurityObject</code>.
     * 
     * @param description
     *            The description of the <code>SecurityObject</code> as text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return version;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * {@inheritDoc} Compare the name.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityObject other = (SecurityObject) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Return the name.
     * 
     * @return the name
     */
    @Override
    public String toString() {
        return name;
    }
}