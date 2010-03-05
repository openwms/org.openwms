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
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

/**
 * A Preference - Could be an user-, role- or system preference.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "T_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = { "C_KEY", "VALUE" }))
public class Preference implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Key value of the {@link Preference}.
     */
    @Column(name = "C_KEY")
    private String key;

    /**
     * The type of the value.
     */
    @Column(name = "TYPE")
    private String type;

    /**
     * The value of the {@link Preference}.
     */
    @Column(name = "VALUE")
    private String value;

    /**
     * A float representation of the value.
     */
    @Column(name = "FLOAT_VALUE")
    private Float floatValue;

    /**
     * Description text of the {@link Preference}.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * A minimum for the value.
     */
    @Column(name = "MINIMUM")
    private int minimum;

    /**
     * A maximum for the value.
     */
    @Column(name = "MAXIMUM")
    private int maximum;

    /**
     * Version field.
     */
    @Version
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a {@link Preference}.
     */
    public Preference() {}

    /**
     * Return the unique technical key.
     * 
     * @return The unique technical key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Return the type of the {@link Preference}.
     * 
     * @return The type as String
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the type of the {@link Preference}.
     * 
     * @param type
     *            The type to set as String
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Return the value of the {@link Preference}.
     * 
     * @return The value of the {@link Preference}
     */
    public String getValue() {
        return this.value;
    }

    /**
     * Set the value of the {@link Preference}.
     * 
     * @param value
     *            The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the key of the {@link Preference}.
     * 
     * @return The key of the {@link Preference}
     */
    public String getKey() {
        return this.key;
    }

    /**
     * Set the key of this {@link Preference}.
     * 
     * @param key
     *            The key to set
     */
    public void setKey(String key) {
        this.value = key;
    }

    /**
     * Get the <code>floatValue</code> of the {@link Preference}.
     * 
     * @return The <code>floatValue</code> of the {@link Preference}
     */
    public Float getFloatValue() {
        return this.floatValue;
    }

    /**
     * Set the <code>floatValue</code> of the {@link Preference}.
     * 
     * @param floatValue
     *            The <code>floatValue</code> to set
     */
    public void setFloatValue(Float floatValue) {
        this.floatValue = floatValue;
    }

    /**
     * Return the description of this {@link Preference}.
     * 
     * @return The description as String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set a description for this {@link Preference}.
     * 
     * @param description
     *            The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the possible minimum value of this {@link Preference}.
     * 
     * @return The possible minimum value
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Set a possible minimum value for this {@link Preference}.
     * 
     * @param minimum
     *            The possible minimum value to set
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Return the possible maximum value of this {@link Preference}.
     * 
     * @return The possible maximum value
     */
    public int getMaximum() {
        return this.maximum;
    }

    /**
     * Set a possible maximum integer value for this {@link Preference}.
     * 
     * @param maximum
     *            The possible maximum value to set
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return The version field
     */
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
