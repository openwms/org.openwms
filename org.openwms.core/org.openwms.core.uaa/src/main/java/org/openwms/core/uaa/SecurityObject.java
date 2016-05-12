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
import javax.persistence.Inheritance;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.io.Serializable;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.core.values.CoreTypeDefinitions;
import org.springframework.util.Assert;

/**
 * A SecurityObject is the generalization of {@code Role}s and {@code Grant}s and combines common used properties of both.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @GlossaryTerm
 * @see org.openwms.core.uaa.Role
 * @see org.openwms.core.uaa.Grant
 * @since 0.1
 */
@Entity
@Table(name = "COR_ROLE")
@Inheritance
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING, length = 20)
public class SecurityObject extends BaseEntity implements Serializable {

    /** Unique name of the {@code SecurityObject}. */
    @Column(name = "C_NAME", unique = true)
    @OrderBy
    private String name;

    /** Description of the {@code SecurityObject}. */
    @Column(name = "C_DESCRIPTION", length = CoreTypeDefinitions.DESCRIPTION_LENGTH)
    private String description;

    /* ----------------------------- methods ------------------- */

    /**
     * Dear JPA...
     */
    protected SecurityObject() {
    }

    /**
     * Create a new {@code SecurityObject} with a name.
     *
     * @param name The name of the {@code SecurityObject}
     * @throws IllegalArgumentException when name is {@literal null} or an empty String
     */
    public SecurityObject(String name) {
        Assert.hasText(name, "A name of a SecurityObject must not be null");
        this.name = name;
    }

    /**
     * Create a new {@code SecurityObject} with name and description.
     *
     * @param name The name of the {@code SecurityObject}
     * @param description The description text of the {@code SecurityObject}
     * @throws IllegalArgumentException when name is {@literal null} or an empty String
     */
    public SecurityObject(String name, String description) {
        Assert.hasText(name, "A name of a SecurityObject must not be null");
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name.
     *
     * @return The name of the {@code SecurityObject}
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description text.
     *
     * @return The description of the {@code SecurityObject} as text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description for the {@code SecurityObject}.
     *
     * @param description The description of the {@code SecurityObject} as text
     */
    public void setDescription(String description) {
        this.description = description;
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