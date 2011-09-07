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
package org.openwms.core.domain.system;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;

/**
 * A AbstractPreference, could be an user-, role- or system preference.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "COR_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = { "C_OWNER", "C_KEY" }))
@DiscriminatorColumn(name = "C_TYPE")
@NamedQueries({
        @NamedQuery(name = AbstractPreference.NQ_FIND_ALL, query = "SELECT p FROM AbstractPreference p"),
        @NamedQuery(name = AbstractPreference.NQ_FIND_BY_UNIQUE_ID, query = "SELECT p FROM AbstractPreference p WHERE p.key = :key and p.owner = :owner") })
public abstract class AbstractPreference extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = 4396571221433949201L;

    /**
     * Query to find all <code>AbstractPreference</code>s.
     */
    public static final String NQ_FIND_ALL = "AbstractPreference" + FIND_ALL;
    /**
     * Query to find <strong>one</strong> <code>AbstractPreference</code> by its
     * natural key.
     * <p>
     * <li>Query parameter name <strong>key</strong> : The key of the
     * <code>AbstractPreference</code> to search for.</li>
     * <li>Query parameter name <strong>owner</strong> : The owner of the
     * <code>AbstractPreference</code> to search for.</li>
     * </p>
     */
    public static final String NQ_FIND_BY_UNIQUE_ID = "AbstractPreference" + FIND_BY_ID;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Owner of the <code>AbstractPreference</code>.
     */
    @Column(name = "C_OWNER")
    private String owner;

    /**
     * Key value of the <code>AbstractPreference</code>.
     */
    @Column(name = "C_KEY")
    private String key;

    /**
     * The value of the <code>AbstractPreference</code>.
     */
    @Column(name = "C_VALUE")
    private String value;

    /**
     * Float representation of the value.
     */
    @Column(name = "FLOAT_VALUE")
    private Float floatValue;

    /**
     * Description text of the <code>AbstractPreference</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Minimum value.
     */
    @Column(name = "C_MINIMUM")
    private int minimum;

    /**
     * Maximum value.
     */
    @Column(name = "C_MAXIMUM")
    private int maximum;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- constructors ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected AbstractPreference() {}

    /**
     * Create a <code>AbstractPreference</code> with a key.
     * 
     * @param key
     *            The key of this preference.
     */
    public AbstractPreference(String key) {
        this.key = key;
    }

    /* ----------------------------- inherited ------------------- */
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
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return version;
    }

    /**
     * Return the key concatenated with value.
     * 
     * @see java.lang.Object#toString()
     * @return As String
     */
    @Override
    public String toString() {
        return key + value;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((owner == null) ? 0 : owner.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AbstractPreference)) {
            return false;
        }
        AbstractPreference other = (AbstractPreference) obj;
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
        return true;
    }

    /* ----------------------------- methods ------------------- */
    /**
     * Get the owner.
     * 
     * @return the owner.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Set the owner.
     * 
     * @param owner
     *            The owner to set.
     */
    protected void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Return the value of the <code>AbstractPreference</code>.
     * 
     * @return The value of the <code>AbstractPreference</code>
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of the <code>AbstractPreference</code>.
     * 
     * @param value
     *            The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Return the key of the <code>AbstractPreference</code>.
     * 
     * @return The key of the <code>AbstractPreference</code>
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Set the key of the <code>AbstractPreference</code>.
     * 
     * @param key
     *            The key to set
     */
    public void setKey(String key) {
        this.value = key;
    }

    /**
     * Get the <code>floatValue</code> of the <code>AbstractPreference</code>.
     * 
     * @return The <code>floatValue</code> of the
     *         <code>AbstractPreference</code>
     */
    public Float getFloatValue() {
        return this.floatValue;
    }

    /**
     * Set the <code>floatValue</code> of the <code>AbstractPreference</code>.
     * 
     * @param floatValue
     *            The <code>floatValue</code> to set
     */
    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    /**
     * Return the description of the <code>AbstractPreference</code>.
     * 
     * @return The description as String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set a description for the <code>AbstractPreference</code>.
     * 
     * @param description
     *            The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the possible minimum value of the <code>AbstractPreference</code>.
     * 
     * @return The possible minimum value
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Set a possible minimum value for the <code>AbstractPreference</code>.
     * 
     * @param minimum
     *            The possible minimum value to set
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Return the possible maximum value of the <code>AbstractPreference</code>.
     * 
     * @return The possible maximum value
     */
    public int getMaximum() {
        return this.maximum;
    }

    /**
     * Set a possible maximum integer value for the
     * <code>AbstractPreference</code>.
     * 
     * @param maximum
     *            The possible maximum value to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }
}
