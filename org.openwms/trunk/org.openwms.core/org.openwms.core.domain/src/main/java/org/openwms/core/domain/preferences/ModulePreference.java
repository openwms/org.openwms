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
package org.openwms.core.domain.preferences;

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
 * A ModulePreference is used to store configuration settings in Module scope.
 * <p>
 * The table model of an ModulePreference spans an unique key over the columns
 * C_TYPE, C_OWNER and C_KEY.
 * </p>
 * <p>
 * It's counterpart in the context of JAXB is the modulePreference element.
 * </p>
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@XmlType(name = "modulePreference", namespace = "http://www.openwms.org/schema/preferences")
@Entity
@Table(name = "COR_MODULE_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = { "C_TYPE", "C_OWNER",
        "C_KEY" }))
@NamedQueries({
        @NamedQuery(name = ModulePreference.NQ_FIND_ALL, query = "select mp from ModulePreference mp"),
        @NamedQuery(name = ModulePreference.NQ_FIND_BY_OWNER, query = "select mp from ModulePreference mp where mp.owner = :owner") })
public class ModulePreference extends AbstractPreference {

    private static final long serialVersionUID = 7318848112643933488L;
    /**
     * Query to find all <code>ModulePreference</code>s. Name is {@value} .
     */
    public static final String NQ_FIND_ALL = "ModulePreference" + FIND_ALL;
    /**
     * Query to find <strong>all</strong> <code>ModulePreference</code>s of a
     * <code>Module</code>. <li>Query parameter name <strong>owner</strong> :
     * The modulename of the <code>Module</code> to search for.</li><br />
     * Name is {@value} .
     */
    public static final String NQ_FIND_BY_OWNER = "ModulePreference" + FIND_BY_OWNER;

    /**
     * Type of this preference.
     */
    @XmlTransient
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TYPE")
    private PropertyScope type = PropertyScope.MODULE;

    /**
     * Owner of the <code>ModulePreference</code> (not nullable).
     */
    @XmlAttribute(name = "owner", required = true)
    @Column(name = "C_OWNER", nullable = false)
    private String owner;

    /**
     * Key of the <code>ModulePreference</code> (not nullable).
     */
    @XmlAttribute(name = "key", required = true)
    @Column(name = "C_KEY", nullable = false)
    private String key;

    /**
     * Create a new <code>ModulePreference</code>. Only defined by the JAXB
     * implementation.
     */
    public ModulePreference() {
        super();
    }

    /**
     * Create a new <code>ModulePreference</code>.
     * 
     * @param owner
     *            The name of the owning module
     * @param key
     *            the key
     * @throws IllegalArgumentException
     *             when key or owner is <code>null</code> or empty
     */
    public ModulePreference(String owner, String key) {
        // Called from the client.
        super();
        AssertUtils.isNotEmpty(owner, "Not allowed to create an ModulePreference with an empty owner");
        AssertUtils.isNotEmpty(key, "Not allowed to create an ModulePreference with an empty key");
        this.owner = owner;
        this.key = key;
    }

    /**
     * Get the key.
     * 
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Get the owner.
     * 
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.system.AbstractPreference#getType()
     */
    @Override
    public PropertyScope getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.core.domain.system.AbstractPreference#getFields()
     */
    @Override
    protected Object[] getFields() {
        return new Object[] { this.getType(), this.getOwner(), this.getKey() };
    }

    /**
     * {@inheritDoc}
     * 
     * Uses the type, owner and the key to create a {@link PreferenceKey}
     * instance.
     * 
     * @see org.openwms.core.domain.system.AbstractPreference#getPrefKey()
     */
    @Override
    public PreferenceKey getPrefKey() {
        return new PreferenceKey(this.getType(), this.getOwner(), this.getKey());
    }

    /**
     * {@inheritDoc}
     * 
     * Uses the type, owner and the key for the hashCode calculation.
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
     * Comparison done with the type, owner and the key fields. Not delegated to
     * super class.
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
        ModulePreference other = (ModulePreference) obj;
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
