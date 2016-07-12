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
import org.openwms.common.location.LocationType;
import org.springframework.util.Assert;

/**
 * A TypePlacingRule is a {@code Rule} that defines which types of {@code TransportUnit}s ({@code TransportUnitType}s) can be put on which
 * type of {@code Location} ({@code LocationType}). <p> A privilegeLevel is defined to order a list of allowed {@code LocationType}s. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @GlossaryTerm
 * @see TransportUnitType
 * @since 0.1
 */
@Entity
@Table(name = "COM_TYPE_PLACING_RULE", uniqueConstraints = @UniqueConstraint(columnNames = {"C_TUT_ID",
        "C_PRIVILEGE_LEVEL", "C_ALLOWED_LOCATION_TYPE"}))
public class TypePlacingRule extends BaseEntity implements Serializable, Rule {

    /** To separate fields in toString method. */
    public static final String SEPARATOR = "::";
    /** Parent {@link TransportUnitType} (not-null). */
    @ManyToOne
    @JoinColumn(name = "C_TUT_ID", nullable = false)
    private TransportUnitType transportUnitType;

    /**
     * The privilegeLevel defines a priority to describe which {@link TransportUnitType} can be placed on which {@link LocationType}. <p> A
     * value of 0 means the lowest priority. Increasing the privilegeLevel implies a higher priority and means the {@link TransportUnitType}
     * can be placed to the {@link LocationType} with the highest privilegeLevel. </p> <p> To forbid a {@link TransportUnitType} on a
     * certain {@link LocationType} the privilegeLevel must be set to -1. </p> (not-null)
     */
    @Column(name = "C_PRIVILEGE_LEVEL", nullable = false)
    private int privilegeLevel = DEF_PRIVILEGE_LEVEL;
    /** Default value of {@link #privilegeLevel}. */
    public static final int DEF_PRIVILEGE_LEVEL = 0;

    /** An allowed {@link LocationType} on which the owning {@link TransportUnitType} may be placed (not-null). */
    @ManyToOne
    @JoinColumn(name = "C_ALLOWED_LOCATION_TYPE", nullable = false)
    private LocationType allowedLocationType;

    /*~ ----------------------------- constructors ------------------- */

    /** Dear JPA... */
    protected TypePlacingRule() {
    }

    /**
     * Create a new {@code TypePlacingRule} with privilegeLevel and allowedLocationType.
     *
     * @param transportUnitType The {@link TransportUnitType} for this rule
     * @param allowedLocationType The allowed {@link LocationType}
     * @param privilegeLevel The privilege level
     */
    public TypePlacingRule(TransportUnitType transportUnitType, LocationType allowedLocationType, int privilegeLevel) {
        Assert.notNull(transportUnitType);
        Assert.notNull(allowedLocationType);
        this.transportUnitType = transportUnitType;
        this.allowedLocationType = allowedLocationType;
        this.privilegeLevel = privilegeLevel;
    }

    /**
     * Create a new {@code TypePlacingRule} with allowedLocationType.
     *
     * @param transportUnitType The {@link TransportUnitType} for this rule
     * @param allowedLocationType The allowed {@link LocationType}
     */
    public TypePlacingRule(TransportUnitType transportUnitType, LocationType allowedLocationType) {
        Assert.notNull(transportUnitType);
        Assert.notNull(allowedLocationType);
        this.transportUnitType = transportUnitType;
        this.allowedLocationType = allowedLocationType;
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
     * Get the privilegeLevel.
     *
     * @return The privilegeLevel.
     */
    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    /**
     * Set the privilegeLevel.
     *
     * @param privilegeLevel The level to set
     */
    public void setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    /**
     * Get the allowedLocationType.
     *
     * @return The allowedLocationType.
     */
    public LocationType getAllowedLocationType() {
        return allowedLocationType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePlacingRule that = (TypePlacingRule) o;
        return privilegeLevel == that.privilegeLevel &&
                Objects.equals(transportUnitType, that.transportUnitType) &&
                Objects.equals(allowedLocationType, that.allowedLocationType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(transportUnitType, privilegeLevel, allowedLocationType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return privilegeLevel + SEPARATOR + transportUnitType + SEPARATOR + allowedLocationType;
    }
}