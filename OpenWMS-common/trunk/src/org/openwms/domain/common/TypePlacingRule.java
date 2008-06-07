/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

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
 * A TypePlacingRule.
 * <p>
 * Describes which <code>TransportUnitType</code> may be placed on which
 * <code>LocationType</code>.<br>
 * A privilegeLevel can be set to order all allowed <code>LocationType</code>s.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "TYPE_PLACING_RULE", uniqueConstraints = @UniqueConstraint(columnNames = { "TRANSPORT_UNIT_TYPE",
		"PRIVILEGE_LEVEL", "ALLOWED_LOCATION_TYPE" }))
public class TypePlacingRule implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;

	/**
	 * Parent <code>TransportUnitType</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "TRANSPORT_UNIT_TYPE")
	private TransportUnitType transportUnitType;

	/**
	 * The privilegeLevel defines a priority to describe which
	 * <code>TransportUnitType</code> shall be placed on which
	 * <code>LocationType</code>.<br>
	 * <p>
	 * A value of 0 is the lowest priority. Increasing the privilegeLevel
	 * implies a higher priority, that means the <code>TransportUnitType</code>
	 * shall be placed to the <code>LocationType</code> with the highest
	 * privilegeLevel.
	 * <p>
	 * To forbid a <code>TransportUnitType</code> on a
	 * <code>LocationType</code> the privilegeLevel must be set to -1.
	 */
	@Column(name = "PRIVILEGE_LEVEL", nullable = false)
	private int privilegeLevel = 0;

	/**
	 * The allowed <code>LocationType</code> on which the owning
	 * <code>TransportUnitType</code> may be placed.
	 */
	@Column(name = "ALLOWED_LOCATION_TYPE", nullable = false)
	private LocationType allowedLocationType;

	/* ----------------------------- methods ------------------- */
	public TypePlacingRule(int privilegeLevel, LocationType allowedLocationType) {
		super();
		this.privilegeLevel = privilegeLevel;
		this.allowedLocationType = allowedLocationType;
	}

	public TypePlacingRule(LocationType allowedLocationType) {
		super();
		this.allowedLocationType = allowedLocationType;
	}

	/**
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public long getId() {
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
	 * Get the privilegeLevel.
	 * 
	 * @return the privilegeLevel.
	 */
	public int getPrivilegeLevel() {
		return privilegeLevel;
	}

	/**
	 * Get the allowedLocationType.
	 * 
	 * @return the allowedLocationType.
	 */
	public LocationType getAllowedLocationType() {
		return allowedLocationType;
	}
}
