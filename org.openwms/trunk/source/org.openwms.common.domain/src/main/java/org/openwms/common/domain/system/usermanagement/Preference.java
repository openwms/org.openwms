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
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A Preference.
 * <p>
 * Could be an user-, role- or system preference.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
// TODO [scherrer] : + Assign key, value as complementary key instead of using
// ID + Replace BigDecimal with float
@Entity
@Table(name = "T_PREFERENCE")
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
    private BigDecimal floatValue;

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
     * Version field
     */
    @Version
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a {@link Preference}
     */
    public Preference() {}

    /**
     * Return the unique technical key.
     * 
     * @return
     */
    public Long getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.value = key;
    }

    public BigDecimal getFloatValue() {
        return this.floatValue;
    }

    public void setFloatValue(BigDecimal floatValue) {
        this.floatValue = floatValue;
    }

    /**
     * Return the description of this {@link Preference}.
     * 
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set a description for this {@link Preference}.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the possible minimum value of this {@link Preference}.
     * 
     * @return
     */
    public int getMinimum() {
        return this.minimum;
    }

    /**
     * Set a possible minimum value for this {@link Preference}.
     * 
     * @param minimum
     */
    public void setMinimum(int minimum) {
        this.minimum = minimum;
    }

    /**
     * Return the possible maximum value of this {@link Preference}.
     * 
     * @return
     */
    public int getMaximum() {
        return this.maximum;
    }

    /**
     * Set a possible maximum integer value for this {@link Preference}.
     * 
     * @param maximum
     */
    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return - Version field
     */
    public long getVersion() {
        return version;
    }

    /**
     * Return the key concatenated with value.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return key + value;
    }
}
