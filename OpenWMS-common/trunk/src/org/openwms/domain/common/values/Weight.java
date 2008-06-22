/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.values;

import java.math.BigDecimal;

public class Weight {

	public enum WEIGHT_UNIT {
		T, KG, G, MG
	}

	private WEIGHT_UNIT unit;
	private BigDecimal value;
	
	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private Weight(){
	}

	/**
	 * 
	 * Create a new Weight.
	 * 
	 * @param value
	 * @param unit
	 */
	public Weight(BigDecimal value, WEIGHT_UNIT unit){
		this.value = value;
		this.unit = unit;
	}
	/**
	 * Get the unit.
	 * 
	 * @return the unit.
	 */
	public WEIGHT_UNIT getUnit() {
		return unit;
	}

	/**
	 * Set the unit.
	 * 
	 * @param unit The unit to set.
	 */
	public void setUnit(WEIGHT_UNIT unit) {
		this.unit = unit;
	}

	/**
	 * Get the value.
	 * 
	 * @return the value.
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Set the value.
	 * 
	 * @param value The value to set.
	 */
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	

}
