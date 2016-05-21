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
package org.openwms.core.configuration.file;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openwms.core.configuration.PreferenceKey;
import org.openwms.core.configuration.PropertyScope;

/**
 * An AbstractPreference is a superclass for all other preference classes within the application. <p> It encapsulates some common behavior
 * of preference types. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
@XmlType(name = "abstractPreference", propOrder = {"description"}, namespace = "http://www.openwms.org/schema/preferences")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "COR_PREFERENCE")
public abstract class AbstractPreference implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    /**
     * Suffix for the FIND_BY_OWNER named query. Default {@value}
     */
    public static final String FIND_BY_OWNER = ".findByOwner";

    /** The String value of the {@code AbstractPreference}. */
    @Column(name = "C_VALUE")
    private String value;

    /** A binary value for this {@link AbstractPreference}. */
    @XmlTransient
    @Lob
    @Column(name = "C_BINVALUE")
    private Serializable binValue;

    /** A float value of the {@link AbstractPreference}. */
    @XmlAttribute(name = "floatValue")
    @Column(name = "C_FLOAT_VALUE")
    private Float floatValue;

    /** Description text of the {@link AbstractPreference}. */
    @Column(name = "C_DESCRIPTION")
    private String description;

    /** Minimum value. */
    @XmlAttribute(name = "minimum")
    @Column(name = "C_MINIMUM")
    private int minimum = 0;

    /** Maximum value. */
    @XmlAttribute(name = "maximum")
    @Column(name = "C_MAXIMUM")
    private int maximum = 0;

    /** Flag to remember if the preference was originally imported from a file. */
    @XmlTransient
    @Column(name = "C_FROM_FILE")
    private boolean fromFile = true;

    /* ----------------------------- methods ------------------- */

    /**
     * Return the <code>value</code> of the {@link AbstractPreference}.
     *
     * @return The value of the {@link AbstractPreference}
     */
    @XmlAttribute(name = "val")
    public String getValue() {
        return value;
    }

    /**
     * Set the <code>value</code> of the {@link AbstractPreference}.
     *
     * @param value The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Get the binValue.
     *
     * @return the binValue.
     */
    @XmlTransient
    @JsonIgnore
    public Serializable getBinValue() {
        return binValue;
    }

    /**
     * Get the <code>floatValue</code> of the {@link AbstractPreference}.
     *
     * @return The floatValue of the preference
     */
    public Float getFloatValue() {
        return floatValue;
    }

    /**
     * Return the <code>description</code> of the {@link AbstractPreference}.
     *
     * @return The description as String
     */
    @XmlValue
    public String getDescription() {
        return description;
    }

    /**
     * Set a <code>description</code> for the {@link AbstractPreference}.
     *
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Return the possible minimum value of the {@link AbstractPreference}.
     *
     * @return The possible minimum value
     */
    public int getMinimum() {
        return minimum;
    }

    /**
     * Return the possible maximum value of the {@link AbstractPreference}.
     *
     * @return The possible maximum value
     */
    public int getMaximum() {
        return maximum;
    }

    /**
     * Return all fields as concatenated String.
     *
     * @return fields as String
     */
    @JsonIgnore
    public String getPropertiesAsString() {
        // TODO [openwms]: 17/05/16
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPreference that = (AbstractPreference) o;
        return minimum == that.minimum &&
                maximum == that.maximum &&
                fromFile == that.fromFile &&
                Objects.equals(value, that.value) &&
                Objects.equals(binValue, that.binValue) &&
                Objects.equals(floatValue, that.floatValue) &&
                Objects.equals(description, that.description);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(value, binValue, floatValue, description, minimum, maximum, fromFile);
    }

    /**
     * Return all fields as an array of objects.
     *
     * @return fields as array
     */
    protected abstract Object[] getFields();

    /**
     * Return the particular type of the preference.
     *
     * @return The type of the preference
     */
    public abstract PropertyScope getType();

    /**
     * Return a {@link PreferenceKey} of this preference.
     *
     * @return A {@link PreferenceKey}
     */
    public abstract PreferenceKey getPrefKey();
}