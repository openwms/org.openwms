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
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A TransportUnitType.
 * <p>
 * Holds typical static attributes of a {@link TransportUnit} such as length,
 * height, aso. So it is possible to group different {@link TransportUnit}s.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see TransportUnit
 */
@Entity
@Table(name = "TRANSPORT_UNIT_TYPE")
public class TransportUnitType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Default description of the {@link TransportUnitType}.
     */
    public static final String DEF_TYPE_DESCRIPTION = "--";

    /**
     * Unique natural key. Also used as primary key.
     */
    @Id
    @Column(name = "TYPE")
    private String type;

    /**
     * Description of this type.
     */
    @Column(name = "DESCRIPTION")
    private String description = DEF_TYPE_DESCRIPTION;

    /**
     * Length of the {@link TransportUnit}.
     */
    @Column(name = "LENGTH")
    private int length;

    /**
     * Width of the {@link TransportUnit}.
     */
    @Column(name = "WIDTH")
    private int width;

    /**
     * Height of the {@link TransportUnit}.
     */
    @Column(name = "HEIGHT")
    private int height;

    /**
     * Tare weight of the {@link TransportUnit}.
     */
    @Column(name = "WEIGHT_TARE")
    private BigDecimal weightTare;

    /**
     * Maximum weight of this {@link TransportUnit}.
     */
    @Column(name = "WEIGHT_MAX")
    private BigDecimal weightMax;

    /**
     * Effective payload of the {@link TransportUnit}.
     */
    @Column(name = "PAYLOAD")
    private BigDecimal payload;

    /**
     * Characteristic used to show specific compatibility attributes like:
     * 'isn't compatible with...' or 'is compatible with ...' or 'type owns the
     * type ...'
     */
    @Column(name = "COMPATIBILITY")
    private String compatibility;

    /**
     * Version field
     */
    @Version
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * A collection of all {@link TransportUnit}s belonging to this type.
     */
    @OneToMany(mappedBy = "transportUnitType")
    private Set<TransportUnit> transportUnits = new HashSet<TransportUnit>();

    /**
     * Describes which other {@link TransportUnitType}s and how many of that
     * type may be stacked on this {@link TransportUnitType}.
     */
    @OneToMany(mappedBy = "transportUnitType", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<TypeStackingRule> typeStackingRules = new HashSet<TypeStackingRule>();

    /**
     * A Set of {@link TypePlacingRule}s to describe all possible
     * {@link LocationType}s for this {@link TransportUnitType}.
     */
    @OneToMany(mappedBy = "transportUnitType", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<TypePlacingRule> typePlacingRules = new HashSet<TypePlacingRule>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private TransportUnitType() {}

    /**
     * Create a new {@link TransportUnitType}.
     * 
     * @param type
     *            - Unique name
     */
    public TransportUnitType(String type) {
        this.type = type;
    }

    /**
     * Returns the type of the {@link TransportUnit}.
     * 
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Set the type of the {@link TransportUnit}.
     * 
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the width of the {@link TransportUnit}.
     * 
     * @return
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the width of the {@link TransportUnit}.
     * 
     * @param width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the description of the {@link TransportUnit}.
     * 
     * @return
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description for the {@link TransportUnit}.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the height of this {@link TransportUnit}.
     * 
     * @return
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the height of this {@link TransportUnit}.
     * 
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the payload of the {@link TransportUnit}.
     * 
     * @return
     */
    public BigDecimal getPayload() {
        return this.payload;
    }

    /**
     * Set the payload of the {@link TransportUnit}.
     * 
     * @param payload
     */
    public void setPayload(BigDecimal payload) {
        this.payload = payload;
    }

    /**
     * Get the compatibility of the {@link TransportUnit}.
     * 
     * @return
     */
    public String getCompatibility() {
        return this.compatibility;
    }

    /**
     * Set the compatibility of the {@link TransportUnit}.
     * 
     * @param compatibility
     */
    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    /**
     * Get the length of the {@link TransportUnit}.
     * 
     * @return
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set the length of the {@link TransportUnit}.
     * 
     * @param length
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns a Set of all {@link TransportUnit}s belonging to this type.
     * 
     * @return
     */
    public Set<TransportUnit> getTransportUnits() {
        return this.transportUnits;
    }

    /**
     * Assign a Set of {@link TransportUnit}s to this type.
     * 
     * @param transportUnits
     */
    public void setTransportUnits(Set<TransportUnit> transportUnits) {
        this.transportUnits = transportUnits;
    }

    public void addTypePlacingRule(TypePlacingRule typePlacingRule) {
        if (typePlacingRule == null) {
            return;
        }
        this.typePlacingRules.add(typePlacingRule);
    }

    /**
     * Returns all {@link TypePlacingRule}s belonging to this
     * {@link TransportUnitType}.
     * 
     * @return
     */
    public Set<TypePlacingRule> getTypePlacingRules() {
        return this.typePlacingRules;
    }

    /**
     * Set all {@link TypePlacingRule}s belonging to this
     * {@link TransportUnitType}.
     * 
     * @param typePlacingRules
     */
    public void setTypePlacingRules(Set<TypePlacingRule> typePlacingRules) {
        this.typePlacingRules = typePlacingRules;
    }

    /**
     * Returns a Set of all {@link TypeStackingRule}s.<br>
     * A {@link TypeStackingRule} describes which {@link TransportUnitType}
     * could be placed on this {@link TransportUnitType}.
     * 
     * @return
     */
    public Set<TypeStackingRule> getTypeStackingRules() {
        return this.typeStackingRules;
    }

    /**
     * Sets a Set of all {@link TypeStackingRule}s.<br>
     * A {@link TypeStackingRule} describes which {@link TransportUnitType}
     * could be placed on this {@link TransportUnitType}.
     * 
     * @param typeStackingRules
     */
    public void setTypeStackingRules(Set<TypeStackingRule> typeStackingRules) {
        this.typeStackingRules = typeStackingRules;
    }

    /**
     * Get the weightTare.
     * 
     * @return the weightTare.
     */
    public BigDecimal getWeightTare() {
        return weightTare;
    }

    /**
     * Set the weightTare.
     * 
     * @param weightTare
     *            The weightTare to set.
     */
    public void setWeightTare(BigDecimal weightTare) {
        this.weightTare = weightTare;
    }

    /**
     * Get the weightMax.
     * 
     * @return the weightMax.
     */
    public BigDecimal getWeightMax() {
        return weightMax;
    }

    /**
     * Set the weightMax.
     * 
     * @param weightMax
     *            The weightMax to set.
     */
    public void setWeightMax(BigDecimal weightMax) {
        this.weightMax = weightMax;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return - Version field.
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Returns the type.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.type;
    }
}
