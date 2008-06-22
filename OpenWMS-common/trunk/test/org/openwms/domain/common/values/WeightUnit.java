/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.values;

/**
 * 
 * A WeightUnit.
 * <p>
 * In SI format.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public enum WeightUnit {
	MG(1000000000), G(1000000), KG(1000), T(1);

	private Long baseUnit;

	WeightUnit(long baseUnit) {
		this.baseUnit = baseUnit;
	}

	public long getBaseUnit() {
		return this.baseUnit;
	}
}
