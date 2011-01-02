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
package org.openwms.core.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;

/**
 * A Preference, could be an user-, role- or system preference.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = { "C_KEY", "C_VALUE" }))
public class Preference extends AbstractEntity implements DomainObject<Long>, Serializable {

    private static final long serialVersionUID = 4396571221433949201L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Key value of the <code>Preference</code>.
     */
    @Column(name = "C_KEY")
    private String key;

    /**
     * The type of the value.
     */
    @Column(name = "C_TYPE")
    private String type;

    /**
     * The value of the <code>Preference</code>.
     */
    @Column(name = "C_VALUE")
    private String value;

    /**
     * Float representation of the value.
     */
    @Column(name = "FLOAT_VALUE")
    private Float floatValue;

    /**
     * Description text of the <code>Preference</code>.
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

    /* ----------------------------- methods ------------------- */
    /**
     * Create a <code>Preference</code>.
     */
    public Preference() {}

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
     * Return the type of the <code>Preference</code>.
     * 
     * @return The type as String
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the type of the <code>Preference</code>.
     * 
     * @param type
     *            The type to set as String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Return the value of the <code>Preference</code>.
     * 
     * @return The value of the <code>Preference</code>
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of the <code>Preference</code>.
     * 
     * @param value
     *            The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Return the key of the <code>Preference</code>.
     * 
     * @return The key of the <code>Preference</code>
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Set the key of the <code>Preference</code>.
     * 
     * @param key
     *            The key to set
     */
    public void setKey(String key) {
        this.value = key;
    }

    /**
     * Get the <code>floatValue</code> of the <code>Preference</code>.
     * 
     * @return The <code>floatValue</code> of the <code>Preference</code>
     */
    public Float getFloatValue() {
        return this.floatValue;
    }

    /**
     * Set the <code>floatValue</code> of the <code>Preference</code>.
     * 
     * @param floatValue
     *            The <code>floatValue</code> to set
     */
    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    /**
     * Return the description of the <code>Preference</code>.
     * 
     * @return The description as String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set a description for the <code>Preference</code>.
     * 
     * @param description
     *            The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the possible minimum value of the <code>Preference</code>.
     * 
     * @return The possible minimum value
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Set a possible minimum value for the <code>Preference</code>.
     * 
     * @param minimum
     *            The possible minimum value to set
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Return the possible maximum value of the <code>Preference</code>.
     * 
     * @return The possible maximum value
     */
    public int getMaximum() {
        return this.maximum;
    }

    /**
     * Set a possible maximum integer value for the <code>Preference</code>.
     * 
     * @param maximum
     *            The possible maximum value to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
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
}
