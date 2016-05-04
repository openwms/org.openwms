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
package org.openwms.core.configuration;

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
import java.io.Serializable;

import org.openwms.core.system.AbstractPreference;
import org.openwms.core.validation.AssertUtils;

/**
 * A RolePreference is used to provide settings specific to an <code>Role</code> . These kind of <code>Preferences</code> is valid for the
 * assigned Role only. <code>User</code>s assigned to a <code>Role</code> inherit these RolePreferences but a RolePreference can be
 * overruled by an <code>UserPreference</code>. RolePreferences can be defined within a preferences file but also be created with the UI.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@XmlType(name = "rolePreference", namespace = "http://www.openwms.org/schema/usermanagement")
@Entity
@Table(name = "COR_ROLE_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = { "C_TYPE", "C_OWNER", "C_KEY" }))
@NamedQueries({
        @NamedQuery(name = RolePreference.NQ_FIND_ALL, query = "select rp from RolePreference rp"),
        @NamedQuery(name = RolePreference.NQ_FIND_BY_OWNER, query = "select rp from RolePreference rp where rp.owner = :owner") })
public class RolePreference extends AbstractPreference implements Serializable {

    private static final long serialVersionUID = 8267024349554036680L;
    /**
     * Type of this preference.
     */
    @XmlTransient
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TYPE")
    private PropertyScope type = PropertyScope.ROLE;
    /**
     * Owner of the <code>RolePreference</code>.
     */
    @XmlAttribute(name = "owner", required = true)
    @Column(name = "C_OWNER")
    private String owner;
    /**
     * Key value of the <code>RolePreference</code>.
     */
    @XmlAttribute(name = "key", required = true)
    @Column(name = "C_KEY")
    private String key;
    /**
     * Query to find all <code>RolePreference</code>s. Name is {@value} .
     */
    public static final String NQ_FIND_ALL = "RolePreference" + FIND_ALL;
    /**
     * Query to find <strong>all</strong> <code>RolePreference</code>s of a <code>Role</code>. <li>Query parameter name
     * <strong>owner</strong> : The rolename of the <code>Role</code> to search for.</li><br />
     * Name is {@value} .
     */
    public static final String NQ_FIND_BY_OWNER = "RolePreference" + FIND_BY_OWNER;

    /**
     * Create a new RolePreference. Defined for the JAXB implementation.
     */
    RolePreference() {
        super();
    }

    /**
     * Create a new RolePreference.
     * 
     * @param rolename
     *            The name of the Role that owns this preference
     * @param key
     *            the key
     * @throws IllegalArgumentException
     *             when rolename or key is <code>null</code> or empty
     */
    public RolePreference(String rolename, String key) {
        // Called from the client.
        super();
        AssertUtils.isNotEmpty(owner, "Not allowed to create a RolePreference with an empty rolename");
        AssertUtils.isNotEmpty(key, "Not allowed to create a RolePreference with an empty key");
        owner = rolename;
        this.key = key;
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
     * Get the name of the <code>Role</code> as String.
     * 
     * @return the rolename.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.system.AbstractPreference#getType()
     */
    @Override
    public PropertyScope getType() {
        return PropertyScope.ROLE;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.system.AbstractPreference#getFields()
     */
    @Override
    protected Object[] getFields() {
        return new Object[] { getType(), getOwner(), getKey() };
    }

    /**
     * {@inheritDoc}
     * 
     * Uses the type, owner and the key to create a {@link PreferenceKey} instance.
     * 
     * @see org.openwms.core.system.AbstractPreference#getPrefKey()
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
        RolePreference other = (RolePreference) obj;
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
