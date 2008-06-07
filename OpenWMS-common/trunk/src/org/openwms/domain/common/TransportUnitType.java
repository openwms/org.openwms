/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

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
 * 
 * A TransportUnitType. Describes typical static attributes of a
 * <code>TransportUnit</code> such as length, height, aso. So it is possible
 * to group different <code>TransportUnit</code>s.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "TRANSPORT_UNIT_TYPE")
public class TransportUnitType implements Serializable, ITransportUnitType {

	private static final long serialVersionUID = 1L;

	/**
	 * Unique type identification of a <code>TransportUnit</code>.
	 */
	@Id
	@Column(name = "TYPE")
	private String type;

	/**
	 * Description of this Type.
	 */
	@Column(name = "DESCRIPTION")
	private String description = ITransportUnitType.DEF_TYPE_DESCRIPTION;

	/**
	 * Length of the <code>TransportUnit</code>.
	 */
	@Column(name = "LENGTH")
	private int length;

	/**
	 * Width of the <code>TransportUnit</code>.
	 */
	@Column(name = "WIDTH")
	private int width;

	/**
	 * Height of the <code>TransportUnit</code>.
	 */
	@Column(name = "HEIGHT")
	private int height;

	/**
	 * Tare weight of the <code>TransportUnit</code>.
	 */
	@Column(name = "WEIGHT_TARE")
	private BigDecimal weightTare;

	/**
	 * Maximum weight of this <code>TransportUnit</code>.
	 */
	@Column(name = "WEIGHT_MAX")
	private BigDecimal weightMax;

	/**
	 * Effective payload of the <code>TransportUnit</code>.
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
	 * All <code>TransportUnit</code>s belonging to this type.
	 */
	@OneToMany(mappedBy = "transportUnitType")
	private Set<TransportUnit> transportUnits = new HashSet<TransportUnit>();

	/**
	 * Describes which other <code>TransportUnitType</code>s and how many of
	 * that type may be stacked on this <code>TransportUnitType</code>.
	 */
	@OneToMany(mappedBy = "transportUnitType", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	private Set<TypeStackingRule> typeStackingRules = new HashSet<TypeStackingRule>();

	/**
	 * A Set of <code>TypePlacingRule</code>s to describe all possible
	 * <code>LocationType</code>s for this <code>TransportUnitType</code>.
	 */
	@OneToMany(mappedBy = "transportUnitType", cascade = { CascadeType.PERSIST, CascadeType.MERGE})
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
	 * Create a new TransportUnitType.
	 * 
	 * @param type
	 */
	public TransportUnitType(String type) {
		super();
		this.type = type;
	}

	/**
	 * Returns the type of the <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Set the type of the <code>TransportUnit</code>.
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the width of the <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Set the width of the <code>TransportUnit</code>.
	 * 
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Returns the description of the <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Set the description for the <code>TransportUnit</code>.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Returns the height of this <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Set the height of this <code>TransportUnit</code>.
	 * 
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Returns the payload of the <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public BigDecimal getPayload() {
		return this.payload;
	}

	/**
	 * Set the payload of the <code>TransportUnit</code>.
	 * 
	 * @param payload
	 */
	public void setPayload(BigDecimal payload) {
		this.payload = payload;
	}

	/**
	 * Get the compatibility of the <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public String getCompatibility() {
		return this.compatibility;
	}

	/**
	 * Set the compatibility of the <code>TransportUnit</code>.
	 * 
	 * @param compatibility
	 */
	public void setCompatibility(String compatibility) {
		this.compatibility = compatibility;
	}

	/**
	 * Get the length of the <code>TransportUnit</code>.
	 * 
	 * @return
	 */
	public int getLength() {
		return this.length;
	}

	/**
	 * Set the length of the <code>TransportUnit</code>.
	 * 
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Returns a Set of <code>TransportUnit</code>s belonging to this type.
	 * 
	 * @return
	 */
	public Set<TransportUnit> getTransportUnits() {
		return this.transportUnits;
	}

	/**
	 * Assign a Set of <code>TransportUnit</code>s to this type.
	 * 
	 * @param transportUnits
	 */
	public void setTransportUnits(Set<TransportUnit> transportUnits) {
		this.transportUnits = transportUnits;
	}
	
	public void addTypePlacingRule(TypePlacingRule typePlacingRule){
		if (typePlacingRule == null) {
			return;
		}
		this.typePlacingRules.add(typePlacingRule);
	}

	/**
	 * Returns all <code>TypePlacingRule</code>s belonging to this
	 * <code>TransportUnitType</code>.
	 * 
	 * @return
	 */
	public Set<TypePlacingRule> getTypePlacingRules() {
		return this.typePlacingRules;
	}

	/**
	 * Set all <code>TypePlacingRule</code>s belonging to this
	 * <code>TransportUnitType</code>.
	 * 
	 * @param typePlacingRules
	 */
	public void setTypePlacingRules(Set<TypePlacingRule> typePlacingRules) {
		this.typePlacingRules = typePlacingRules;
	}

	/**
	 * Returns a Set of all <code>TypeStackingRule</code>s.<br>
	 * A <code>TypeStackingRule</code> describes which
	 * <code>TransportUnitType</code> could be placed on this
	 * <code>TransportUnitType</code>.
	 * 
	 * @return
	 */
	public Set<TypeStackingRule> getTypeStackingRules() {
		return this.typeStackingRules;
	}

	/**
	 * Sets a Set of all <code>TypeStackingRule</code>s.<br>
	 * A <code>TypeStackingRule</code> describes which
	 * <code>TransportUnitType</code> could be placed on this
	 * <code>TransportUnitType</code>.
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
	 * JPA optimistic locking: Returns version field.
	 * 
	 * @return
	 */
	public long getVersion() {
		return this.version;
	}
}
