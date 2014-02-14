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
package org.openwms.core.domain.system.usermanagement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.PreferenceKey;
import org.openwms.core.domain.system.PropertyScope;
import org.openwms.core.util.validation.AssertUtils;

/**
 * An UserPreference is used to store settings specific to an <code>User</code>. It is always assigned to a particular <code>User</code> and
 * not accessible from, nor valid for, other <code>User</code>s. UserPreferences cannot be overruled by any other type of
 * <code>Preferences</code>.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@XmlType(name = "userPreference", namespace = "http://www.openwms.org/schema/usermanagement")
@Entity
@Table(name = "COR_USER_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = { "C_TYPE", "C_OWNER", "C_KEY" }))
@NamedQueries({
        @NamedQuery(name = UserPreference.NQ_FIND_ALL, query = "select up from UserPreference up"),
        @NamedQuery(name = UserPreference.NQ_FIND_BY_OWNER, query = "select up from UserPreference up where up.owner = :owner") })
public class UserPreference extends AbstractPreference {

    private static final long serialVersionUID = -6569559231034802554L;
    /**
     * Type of this preference.
     */
    @XmlTransient
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TYPE")
    private PropertyScope type = PropertyScope.USER;

    /**
     * Owner of the <code>AbstractPreference</code>.
     */
    @XmlAttribute(name = "owner", required = true)
    @Column(name = "C_OWNER")
    private String owner;

    /**
     * Key value of the <code>AbstractPreference</code>.
     */
    @XmlAttribute(name = "key", required = true)
    @Column(name = "C_KEY")
    private String key;
    /**
     * Query to find all <code>UserPreference</code>s.
     */
    public static final String NQ_FIND_ALL = "UserPreference" + FIND_ALL;
    /**
     * Query to find <strong>all</strong> <code>UserPreference</code>s of an <code>User</code>. <li>Query parameter name
     * <strong>owner</strong> : The userName of the <code>User</code> to search for.</li><br />
     * Name is {@value} .
     */
    public static final String NQ_FIND_BY_OWNER = "UserPreference" + FIND_BY_OWNER;

    /**
     * Create a new UserPreference. Defined for the JAXB implementation.
     */
    UserPreference() {
        super();
    }

    /**
     * Create a new UserPreference.
     * 
     * @param owner
     *            The User's username is set as owner of this preference
     * @param key
     *            The key of this preference
     * @throws IllegalArgumentException
     *             when owner or key is <code>null</code> or empty
     */
    public UserPreference(String owner, String key) {
        // Called from the client-side only.
        super();
        AssertUtils.isNotEmpty(owner, "Not allowed to create an UserPreference with an empty owner");
        AssertUtils.isNotEmpty(key, "Not allowed to create an UserPreference with an empty key");
        this.owner = owner;
        this.key = key;
    }

    /**
     * Get the owner.
     * 
     * @return the owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Get the key.
     * 
     * @return the key.
     */
    public String getKey() {
        return key;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.system.AbstractPreference#getType()
     */
    @Override
    public PropertyScope getType() {
        return PropertyScope.USER;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.system.AbstractPreference#getFields()
     */
    @Override
    protected Object[] getFields() {
        return new Object[] { getType(), getOwner(), getKey() };
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.system.AbstractPreference#getPrefKey()
     */
    @Override
    public PreferenceKey getPrefKey() {
        return new PreferenceKey(getType(), getOwner(), getKey());
    }

    /**
     * {@inheritDoc}
     * 
     * Uses key, owner and type for hashCode calculation.
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Comparison done with key, owner and type fields. Not delegated to super class.
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
        UserPreference other = (UserPreference) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        if (owner == null) {
            if (other.owner != null) {
                return false;
            }
        } else if (!owner.equals(other.owner)) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }
}