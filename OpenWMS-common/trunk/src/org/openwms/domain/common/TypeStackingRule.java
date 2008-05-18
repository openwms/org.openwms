/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
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
 * 
 * A TypeStackingRule.<br>
 * Defines what <code>TransportUnitType</code> may be placed on the owning
 * <code>TransportUnitType</code>.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Embeddable
public class TypeStackingRule implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Number of <code>TransportUnitType</code>s that may be placed on the
	 * owning <code>TransportUnitType</code>.
	 */
	@Column(name = "NO_TRANSPORT_UNITS", nullable = false)
	private short noTransportUnits;

	/**
	 * The allowed <code>TransportUnitType</code> that may be placed on the
	 * owning <code>TransportUnitType</code>.
	 */
	@Column(name = "ALLOWED_TYPE", nullable = false)
	private ITransportUnitType allowedTransportUnitType;

	/* ----------------------------- methods ------------------- */
	public TypeStackingRule(short noTransportUnits, ITransportUnitType allowedTransportUnitType) {
		this.noTransportUnits = noTransportUnits;
		this.allowedTransportUnitType = allowedTransportUnitType;
	}

	/**
	 * Returns the number of <code>TransportUnitType</code>s that may be
	 * placed on the owning <code>TransportUnitType</code>.
	 * 
	 * @return
	 */
	public short getNoTransportUnits() {
		return this.noTransportUnits;
	}

	/**
	 * Returns the allowed <code>TransportUnitType</code> that may be placed
	 * on the owning <code>TransportUnitType</code>.
	 * 
	 * @return
	 */
	public ITransportUnitType getAllowedTransportUnitType() {
		return this.allowedTransportUnitType;
	}
}
