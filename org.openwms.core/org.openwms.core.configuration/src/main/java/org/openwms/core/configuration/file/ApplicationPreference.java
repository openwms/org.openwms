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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.openwms.core.configuration.PropertyScope;
import org.springframework.util.Assert;

/**
 * An ApplicationPreference is used to store a configuration setting in application scope. <p> The table model of an ApplicationPreference
 * spans an unique key over the columns C_TYPE and C_KEY. </p> <p> It's counterpart in the context of JAXB is the applicationPreference
 * element. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @GlossaryTerm
 * @since 0.1
 */
@XmlType(name = "applicationPreference", namespace = "http://www.openwms.org/schema/preferences")
@Entity
@Table(name = "COR_APP_PREFERENCE", uniqueConstraints = @UniqueConstraint(columnNames = {"C_TYPE", "C_KEY"}))
@NamedQueries({@NamedQuery(name = ApplicationPreference.NQ_FIND_BY_OWNER, query = "select ap from ApplicationPreference ap")})
public class ApplicationPreference extends AbstractPreference implements Serializable {

    /** Query to find all {@code ApplicationPreference}s. Name is {@value}. */
    public static final String NQ_FIND_BY_OWNER = "ApplicationPreference" + FIND_BY_OWNER;

    /** Type of this preference. */
    @XmlTransient
    @Enumerated(EnumType.STRING)
    @Column(name = "C_TYPE")
    private PropertyScope type = PropertyScope.APPLICATION;

    /** Key of the preference (not nullable). */
    @XmlAttribute(name = "key", required = true)
    @Column(name = "C_KEY", nullable = false)
    private String key;

    /** Create a new {@code ApplicationPreference}. Only defined by the JAXB implementation. */
    public ApplicationPreference() {
        super();
    }

    /**
     * Create a new {@code ApplicationPreference}.
     *
     * @param key the key
     * @throws IllegalArgumentException when key is {@literal null} or empty
     */
    public ApplicationPreference(String key) {
        // Called from the client-side only.
        super();
        Assert.hasText(key, "Not allowed to create an ApplicationPreference with an empty key");
        this.key = key;
    }

    private ApplicationPreference(Builder builder) {
        setValue(builder.value);
        binValue = builder.binValue;
        floatValue = builder.floatValue;
        setDescription(builder.description);
        minimum = builder.minimum;
        maximum = builder.maximum;
        type = builder.type;
        key = builder.key;
        type = PropertyScope.APPLICATION;
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
     * {@inheritDoc}
     */
    @Override
    public PropertyScope getType() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Object[] getFields() {
        return new Object[]{getType(), getKey()};
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses the type and key to create a {@link PreferenceKey} instance.
     *
     * @see AbstractPreference#getPrefKey()
     */
    @Override
    @JsonIgnore
    public PreferenceKey getPrefKey() {
        return new PreferenceKey(getType(), getKey());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses the type and the key for the hashCode calculation.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Comparison done with the type and the key fields. Not delegated to super class.
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
        ApplicationPreference other = (ApplicationPreference) obj;
        if (key == null) {
            if (other.key != null) {
                return false;
            }
        } else if (!key.equals(other.key)) {
            return false;
        }
        return type == other.type;
    }

    @Override
    public String toString() {
        return "ApplicationPreference{" +
                "type=" + type +
                ", key='" + key + '\'' +
                "} " + super.toString();
    }

    /**
     * {@code ApplicationPreference} builder static inner class.
     */
    public static final class Builder {

        private PropertyScope type;
        private String key;
        private String value;
        private Serializable binValue;
        private Float floatValue;
        private String description;
        private int minimum;
        private int maximum;

        public Builder() {
        }

        /**
         * Sets the {@code key} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code key} to set
         * @return a reference to this Builder
         */
        public Builder withKey(String val) {
            key = val;
            return this;
        }

        /**
         * Returns a {@code ApplicationPreference} built from the parameters previously set.
         *
         * @return a {@code ApplicationPreference} built with parameters of this {@code ApplicationPreference.Builder}
         */
        public ApplicationPreference build() {
            return new ApplicationPreference(this);
        }

        /**
         * Sets the {@code value} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code value} to set
         * @return a reference to this Builder
         */
        public Builder withValue(String val) {
            value = val;
            return this;
        }

        /**
         * Sets the {@code binValue} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code binValue} to set
         * @return a reference to this Builder
         */
        public Builder withBinValue(Serializable val) {
            binValue = val;
            return this;
        }

        /**
         * Sets the {@code floatValue} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code floatValue} to set
         * @return a reference to this Builder
         */
        public Builder withFloatValue(Float val) {
            floatValue = val;
            return this;
        }

        /**
         * Sets the {@code description} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code description} to set
         * @return a reference to this Builder
         */
        public Builder withDescription(String val) {
            description = val;
            return this;
        }

        /**
         * Sets the {@code minimum} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code minimum} to set
         * @return a reference to this Builder
         */
        public Builder withMinimum(int val) {
            minimum = val;
            return this;
        }

        /**
         * Sets the {@code maximum} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code maximum} to set
         * @return a reference to this Builder
         */
        public Builder withMaximum(int val) {
            maximum = val;
            return this;
        }
    }
}