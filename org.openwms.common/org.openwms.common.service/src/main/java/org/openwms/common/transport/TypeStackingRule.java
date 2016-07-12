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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Objects;

import org.ameba.integration.jpa.BaseEntity;

/**
 * A TypeStackingRule is a {@link Rule} that defines which {@link TransportUnitType} can be stacked on other types. Additionally a maximum
 * number of {@link TransportUnit}s can be defined.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @GlossaryTerm
 * @see TransportUnitType
 * @since 0.1
 */
@Entity
@Table(name = "COM_TYPE_STACKING_RULE", uniqueConstraints = @UniqueConstraint(columnNames = {"C_TRANSPORT_UNIT_TYPE",
        "C_NO_TRANSPORT_UNITS", "C_ALLOWED_TRANSPORT_UNIT_TYPE"}))
class TypeStackingRule extends BaseEntity implements Serializable, Rule {

    /** To separate fields in toString method. */
    static final String SEPARATOR = "::";
    /** Parent {@link TransportUnitType}. */
    @ManyToOne
    @JoinColumn(name = "C_TRANSPORT_UNIT_TYPE")
    private TransportUnitType transportUnitType;

    /** Number of {@link TransportUnitType}s that may be placed on the owning {@link TransportUnitType} (not-null). */
    @Column(name = "C_NO_TRANSPORT_UNITS", nullable = false)
    private short noTransportUnits;

    /** The allowed {@link TransportUnitType} that may be placed on the owning {@link TransportUnitType} (not-null). */
    @ManyToOne
    @JoinColumn(name = "C_ALLOWED_TRANSPORT_UNIT_TYPE", nullable = false)
    private TransportUnitType allowedTransportUnitType;

    /*~ ----------------------------- constructors ------------------- */

    /**
     * Dear JPA...
     */
    TypeStackingRule() {
    }

    /**
     * Create a new {@code TypeStackingRule}. Define how many {@link TransportUnit}s of the allowedTransportUnitType may stacked on this
     * {@link TransportUnitType}.
     *
     * @param noTransportUnits The number of allowed {@link TransportUnit}s
     * @param allowedTransportUnitType The allowed {@link TransportUnitType}
     */
    TypeStackingRule(short noTransportUnits, TransportUnitType allowedTransportUnitType) {
        this.noTransportUnits = noTransportUnits;
        this.allowedTransportUnitType = allowedTransportUnitType;
    }

    /*~ ----------------------------- methods ------------------- */

    /**
     * Get the transportUnitType.
     *
     * @return The transportUnitType.
     */
    public TransportUnitType getTransportUnitType() {
        return transportUnitType;
    }

    /**
     * Returns the number of {@link TransportUnitType}s that may be placed on the owning {@link TransportUnitType}.
     *
     * @return The number of TransportUnits allowed
     */
    public short getNoTransportUnits() {
        return this.noTransportUnits;
    }

    /**
     * Returns the allowed {@link TransportUnitType} that may be placed on the owning {@link TransportUnitType}.
     *
     * @return The allowed TransportUnitType
     */
    public TransportUnitType getAllowedTransportUnitType() {
        return this.allowedTransportUnitType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeStackingRule that = (TypeStackingRule) o;
        return noTransportUnits == that.noTransportUnits &&
                Objects.equals(transportUnitType, that.transportUnitType) &&
                Objects.equals(allowedTransportUnitType, that.allowedTransportUnitType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(transportUnitType, noTransportUnits, allowedTransportUnitType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return noTransportUnits + SEPARATOR + transportUnitType + SEPARATOR + allowedTransportUnitType;
    }
}