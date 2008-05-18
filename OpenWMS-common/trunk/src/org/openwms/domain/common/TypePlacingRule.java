/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

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
@Embeddable
public class TypePlacingRule implements Serializable {
	private static final long serialVersionUID = 1L;

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
	private short privilegeLevel = 0;

	/**
	 * The allowed <code>LocationType</code> on which the owning
	 * <code>TransportUnitType</code> may be placed.
	 */
	@Column(name = "ALLOWED_LOCATION_TYPE", nullable = false)
	private ILocationType allowedLocationType;

	/* ----------------------------- methods ------------------- */
	public TypePlacingRule(short privilegeLevel, ILocationType allowedLocationType) {
		super();
		this.privilegeLevel = privilegeLevel;
		this.allowedLocationType = allowedLocationType;
	}

	/**
	 * Get the privilegeLevel.
	 * 
	 * @return the privilegeLevel.
	 */
	public short getPrivilegeLevel() {
		return privilegeLevel;
	}

	/**
	 * Get the allowedLocationType.
	 * 
	 * @return the allowedLocationType.
	 */
	public ILocationType getAllowedLocationType() {
		return allowedLocationType;
	}
}
