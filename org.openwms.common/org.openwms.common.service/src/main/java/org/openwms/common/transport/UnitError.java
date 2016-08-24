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
package org.openwms.common.transport;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

import org.ameba.integration.jpa.BaseEntity;

/**
 * An UnitError represents an error occurring on {@code TransportUnit}s, on {@code LoadUnit}s or others.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
@Entity
@Table(name = "COM_UNIT_ERROR")
class UnitError extends BaseEntity implements Serializable {

    /** Separator to use in toString method. */
    static final String SEPARATOR = "::";

    /** Error number. */
    @Column(name = "C_ERROR_NO")
    private String errorNo;

    /** Error message text. */
    @Column(name = "C_ERROR_TEXT")
    private String errorText;

    /*~ ----------------------------- constructors ------------------- */

    /**
     * Dear JPA...
     */
    UnitError() {
    }

    private UnitError(Builder builder) {
        setErrorNo(builder.errorNo);
        setErrorText(builder.errorText);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /*~ ----------------------------- methods ------------------- */

    /**
     * Return the error number.
     *
     * @return The error number
     */
    public String getErrorNo() {
        return errorNo;
    }

    /**
     * Set the error number.
     *
     * @param errorNo The errorNo to set.
     */
    public void setErrorNo(String errorNo) {
        this.errorNo = errorNo;
    }

    /**
     * Return the error text.
     *
     * @return The error text
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * Set the error text.
     *
     * @param errorText The errorText to set.
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnitError unitError = (UnitError) o;
        return Objects.equals(errorNo, unitError.errorNo) &&
                Objects.equals(errorText, unitError.errorText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(errorNo, errorText);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return errorNo + SEPARATOR + errorText;
    }


    /**
     * {@code UnitError} builder static inner class.
     */
    public static final class Builder {

        private String errorNo;
        private String errorText;

        private Builder() {
        }

        /**
         * Sets the {@code errorNo} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code errorNo} to set
         * @return a reference to this Builder
         */
        public Builder errorNo(String val) {
            errorNo = val;
            return this;
        }

        /**
         * Sets the {@code errorText} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code errorText} to set
         * @return a reference to this Builder
         */
        public Builder errorText(String val) {
            errorText = val;
            return this;
        }

        /**
         * Returns a {@code UnitError} built from the parameters previously set.
         *
         * @return a {@code UnitError} built with parameters of this {@code UnitError.Builder}
         */
        public UnitError build() {
            return new UnitError(this);
        }
    }
}