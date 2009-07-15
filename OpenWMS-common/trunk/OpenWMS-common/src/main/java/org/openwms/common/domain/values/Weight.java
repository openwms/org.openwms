/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.values;

import java.math.BigDecimal;

/**
 * 
 * A Weight.
 * <p>
 * Describes a weight in real world, always including a unit and a value.
 * 
 * @author <a href="openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
public class Weight implements Comparable<Weight>, Unit<WeightUnit> {

	private WeightUnit unit;
	private BigDecimal value;

	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private Weight() {
	}

	/**
	 * 
	 * Create a new Weight.
	 * 
	 * @param value
	 * @param unit
	 */
	public Weight(BigDecimal value, WeightUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	/**
	 * Get the unit.
	 * 
	 * @return the unit.
	 */
	public WeightUnit getUnit() {
		return unit;
	}

	/**
	 * Get the value.
	 * 
	 * @return the value.
	 */
	public BigDecimal getValue() {
		return value;
	}

	public void convertTo(WeightUnit unit) {
		value = value.scaleByPowerOfTen((this.getUnit().ordinal() - unit
				.ordinal()) * 3);
		this.unit = unit;
	}

	/**
	 * Compares o.Value with this.value.
	 */
	public int compareTo(Weight o) {
		if (o.getUnit().ordinal() > this.getUnit().ordinal()) {
			return 1;
		} else if (o.getUnit().ordinal() < this.getUnit().ordinal()) {
			return -1;
		} else {
			return o.getValue().compareTo(this.getValue());
		}
	}

}
