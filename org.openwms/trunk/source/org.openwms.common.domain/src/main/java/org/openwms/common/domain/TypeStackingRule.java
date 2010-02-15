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
package org.openwms.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * A TypeStackingRule.
 * <p>
 * Defines which {@link TransportUnitType} may be placed on the which other
 * {@link TransportUnitType}.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see TransportUnitType
 */
@Entity
@Table(name = "TYPE_STACKING_RULE", uniqueConstraints = @UniqueConstraint(columnNames = { "TRANSPORT_UNIT_TYPE",
        "NO_TRANSPORT_UNITS", "ALLOWED_TRANSPORT_UNIT_TYPE" }))
public class TypeStackingRule implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Parent {@link TransportUnitType}.
     */
    @ManyToOne
    @JoinColumn(name = "TRANSPORT_UNIT_TYPE")
    private TransportUnitType transportUnitType;

    /**
     * Number of {@link TransportUnitType}s that may be placed on the owning
     * {@link TransportUnitType}.
     */
    @Column(name = "NO_TRANSPORT_UNITS", nullable = false)
    private short noTransportUnits;

    /**
     * The allowed {@link TransportUnitType} that may be placed on the owning
     * {@link TransportUnitType}.
     */
    @ManyToOne
    @JoinColumn(name = "ALLOWED_TRANSPORT_UNIT_TYPE", nullable = false)
    private TransportUnitType allowedTransportUnitType;

    /* ----------------------------- methods ------------------- */
    /**
     * Create a new {@link TypeStackingRule}.
     */
    @SuppressWarnings("unused")
    private TypeStackingRule() {}

    /**
     * Create a new {@link TypeStackingRule}. Define how many
     * {@link org.openwms.common.domain.TransportUnit}s of the
     * allowedTransportUnitType may stacked on this {@link TransportUnitType}.
     * 
     * @param noTransportUnits
     * @param allowedTransportUnitType
     */
    public TypeStackingRule(short noTransportUnits, TransportUnitType allowedTransportUnitType) {
        this.noTransportUnits = noTransportUnits;
        this.allowedTransportUnitType = allowedTransportUnitType;
    }

    /**
     * Return the unique technical key.
     * 
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Get the transportUnitType.
     * 
     * @return the transportUnitType.
     */
    public TransportUnitType getTransportUnitType() {
        return transportUnitType;
    }

    /**
     * Returns the number of {@link TransportUnitType}s that may be placed on
     * the owning {@link TransportUnitType}.
     * 
     * @return
     */
    public short getNoTransportUnits() {
        return this.noTransportUnits;
    }

    /**
     * Returns the allowed {@link TransportUnitType} that may be placed on the
     * owning {@link TransportUnitType}.
     * 
     * @return
     */
    public TransportUnitType getAllowedTransportUnitType() {
        return this.allowedTransportUnitType;
    }
}