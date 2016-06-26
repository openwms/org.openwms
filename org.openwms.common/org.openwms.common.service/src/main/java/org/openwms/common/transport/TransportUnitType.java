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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.openwms.common.location.LocationType;
import org.openwms.core.AbstractEntity;
import org.openwms.core.DomainObject;

/**
 * A TransportUnitType is a type of a certain <code>TransportUnit</code>s.
 * <p>
 * Typically to store some static attributes of <code>TransportUnit</code>s, such as the length, the height, or the weight of
 * <code>TransportUnit</code>s. It is possible to group and characterize <code>TransportUnit</code>s.
 * </p>
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see TransportUnit
 */
@Entity
@Table(name = "COM_TRANSPORT_UNIT_TYPE")
@NamedQueries({
        @NamedQuery(name = TransportUnitType.NQ_FIND_ALL, query = "select tut from TransportUnitType tut order by tut.type"),
        @NamedQuery(name = TransportUnitType.NQ_FIND_BY_NAME, query = "select tut from TransportUnitType tut where tut.type = ?1") })
public class TransportUnitType extends AbstractEntity<Long> implements DomainObject<Long> {

    private static final long serialVersionUID = -8223409025971215884L;

    /**
     * Query to find all <code>TransportUnitType</code>s.
     */
    public static final String NQ_FIND_ALL = "TransportUnitType.findAll";

    /**
     * Query to find <strong>one</strong> <code>TransportUnitType</code> by its natural key.
     * <ul>
     * <li>Query parameter index <strong>1</strong> : The name of the <code>TransportUnitType</code> to search for.</li>
     * </ul>
     */
    public static final String NQ_FIND_BY_NAME = "TransportUnitType.findByID";

    /**
     * Default description of the <code>TransportUnitType</code>. Default value}.
     */
    public static final String DEF_TYPE_DESCRIPTION = "--";

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique natural key.
     */
    @Column(name = "TYPE", unique = true)
    @OrderBy
    private String type;

    /**
     * Description for this type.
     */
    @Column(name = "DESCRIPTION")
    private String description = DEF_TYPE_DESCRIPTION;

    /**
     * Length of the <code>TransportUnitType</code>.
     */
    @Column(name = "LENGTH")
    private int length = 0;

    /**
     * Width of the <code>TransportUnitType</code>.
     */
    @Column(name = "WIDTH")
    private int width = 0;

    /**
     * Height of the <code>TransportUnitType</code>.
     */
    @Column(name = "HEIGHT")
    private int height = 0;

    /**
     * Tare weight of the <code>TransportUnitType</code>.
     */
    @Column(name = "WEIGHT_TARE", scale = 3)
    private BigDecimal weightTare;

    /**
     * Maximum weight of the <code>TransportUnitType</code>.
     */
    @Column(name = "WEIGHT_MAX", scale = 3)
    private BigDecimal weightMax;

    /**
     * Effective payload of the <code>TransportUnitType</code>.
     */
    @Column(name = "PAYLOAD", scale = 3)
    private BigDecimal payload;

    /**
     * Characteristic used to hold specific compatibility attributes.<br>
     * Example:<br>
     * 'isn't compatible with...' or 'is compatible with ...' or 'type owns another type ...'
     */
    @Column(name = "COMPATIBILITY")
    private String compatibility;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * A collection of all {@link TransportUnit}s belonging to this type.
     */
    @OneToMany
    @JoinTable(name = "COM_TU_UNIT_TYPE", joinColumns = @JoinColumn(name = "TRANSPORT_UNIT_TYPE_ID"), inverseJoinColumns = @JoinColumn(name = "TRANSPORT_UNIT_ID"))
    private Set<TransportUnit> transportUnits = new HashSet<TransportUnit>();

    /**
     * Describes other <code>TransportUnitType</code>s and how many of these may be stacked on the <code>TransportUnitType</code>.
     */
    @OneToMany(mappedBy = "transportUnitType", cascade = { CascadeType.ALL })
    private Set<TypeStackingRule> typeStackingRules = new HashSet<TypeStackingRule>();

    /**
     * A Set of {@link TypePlacingRule}s store all possible {@link LocationType} s of the <code>TransportUnitType</code>.
     */
    @OneToMany(mappedBy = "transportUnitType", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Set<TypePlacingRule> typePlacingRules = new HashSet<TypePlacingRule>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private TransportUnitType() {
        super();
    }

    /**
     * Create a new <code>TransportUnitType</code>.
     * 
     * @param type
     *            Unique name
     */
    public TransportUnitType(String type) {
        this.type = type;
    }

    /**
     * Returns the type of the <code>TransportUnitType</code>.
     * 
     * @return The type
     */
    public String getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return getType() == null || getType().isEmpty() ? true : false;
    }

    /**
     * Set the type of the <code>TransportUnitType</code>.
     * 
     * @param type
     *            The type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Returns the width of the <code>TransportUnitType</code>.
     * 
     * @return The width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Set the width of the <code>TransportUnitType</code>.
     * 
     * @param width
     *            The width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the description of the <code>TransportUnitType</code>.
     * 
     * @return The description text
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description for the <code>TransportUnitType</code>.
     * 
     * @param description
     *            The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the height of the <code>TransportUnitType</code>.
     * 
     * @return The height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Set the height of the <code>TransportUnitType</code>.
     * 
     * @param height
     *            The height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the payload of the <code>TransportUnitType</code>.
     * 
     * @return The payload
     */
    public BigDecimal getPayload() {
        return this.payload;
    }

    /**
     * Set the payload of the <code>TransportUnitType</code>.
     * 
     * @param payload
     *            The payload to set
     */
    public void setPayload(BigDecimal payload) {
        this.payload = payload;
    }

    /**
     * Returns the compatibility of the <code>TransportUnitType</code>.
     * 
     * @return The compatibility
     */
    public String getCompatibility() {
        return this.compatibility;
    }

    /**
     * Set the compatibility of the <code>TransportUnitType</code>.
     * 
     * @param compatibility
     *            The compatibility to set
     */
    public void setCompatibility(String compatibility) {
        this.compatibility = compatibility;
    }

    /**
     * Get the length of the <code>TransportUnitType</code>.
     * 
     * @return The length
     */
    public int getLength() {
        return this.length;
    }

    /**
     * Set the length of the <code>TransportUnitType</code>.
     * 
     * @param length
     *            The length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Returns a Set of all {@link TransportUnit}s belonging to the <code>TransportUnitType</code>.
     * 
     * @return A Set of all {@link TransportUnit}s belonging to the <code>TransportUnitType</code>
     */
    public Set<TransportUnit> getTransportUnits() {
        return this.transportUnits;
    }

    /**
     * Assign a Set of {@link TransportUnit}s to the <code>TransportUnitType</code>. Already existing {@link TransportUnit}s will be
     * removed.
     * 
     * @param transportUnits
     *            A Set of {@link TransportUnit}s.
     */
    public void setTransportUnits(Set<TransportUnit> transportUnits) {
        this.transportUnits = transportUnits;
    }

    /**
     * Add a rule to the <code>TransportUnitType</code>. A {@link TypePlacingRule} determines what <code>TransportUnitType</code>s can be
     * placed on which locations.
     * 
     * @param typePlacingRule
     *            The rule to set
     * @return <code>true</code> when the rule was added gracefully, otherwise <code>false</code>
     */
    public boolean addTypePlacingRule(TypePlacingRule typePlacingRule) {
        if (typePlacingRule == null) {
            return false;
        }
        return this.typePlacingRules.add(typePlacingRule);
    }

    /**
     * Remove a {@link TypePlacingRule} from the collection or rules.
     * 
     * @param typePlacingRule
     *            The rule to be removed
     * @return <code>true</code> when the rule was removed gracefully, otherwise <code>false</code>
     */
    public boolean removeTypePlacingRule(TypePlacingRule typePlacingRule) {
        if (typePlacingRule == null) {
            return false;
        }
        return this.typePlacingRules.remove(typePlacingRule);
    }

    /**
     * Returns all {@link TypePlacingRule}s belonging to the <code>TransportUnitType</code>.
     * 
     * @return A Set of all placing rules
     */
    public Set<TypePlacingRule> getTypePlacingRules() {
        return this.typePlacingRules;
    }

    /**
     * Assign a Set of {@link TypePlacingRule}s to the <code>TransportUnitType</code>. Already existing {@link TypePlacingRule}s will be
     * removed.
     * 
     * @param typePlacingRules
     *            The rules to set
     */
    public void setTypePlacingRules(Set<TypePlacingRule> typePlacingRules) {
        this.typePlacingRules = typePlacingRules;
    }

    /**
     * Returns a Set of all {@link TypeStackingRule}s. A {@link TypeStackingRule} determines which other <code>TransportUnitType</code>s can
     * be placed on the <code>TransportUnitType</code>.
     * 
     * @return A Set of all stacking rules
     */
    public Set<TypeStackingRule> getTypeStackingRules() {
        return this.typeStackingRules;
    }

    /**
     * Assign a Set of {@link TypeStackingRule}s. A {@link TypeStackingRule} determines which <code>TransportUnitType</code>s can be placed
     * on the <code>TransportUnitType</code>. Already existing {@link TypeStackingRule} s will be removed.
     * 
     * @param typeStackingRules
     *            The rules to set
     */
    public void setTypeStackingRules(Set<TypeStackingRule> typeStackingRules) {
        this.typeStackingRules = typeStackingRules;
    }

    /**
     * Get the weightTare.
     * 
     * @return The weightTare.
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
     * @return The weightMax.
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
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * Returns the type.
     * 
     * @see java.lang.Object#toString()
     * @return as String
     */
    @Override
    public String toString() {
        return this.type;
    }
}
