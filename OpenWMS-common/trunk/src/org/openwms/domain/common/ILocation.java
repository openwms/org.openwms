/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.math.BigDecimal;
import java.util.Date;

import org.openwms.domain.common.system.IMessage;

public interface ILocation {

	public abstract LocationPK getId();

	public abstract String getDescription();

	public abstract short getNoLeaveTransportUnits();

	public abstract short getNoIncomingTransportUnits();

	public abstract Date getLastAccess();

	public abstract boolean isConsideredInAllocation();

	public abstract boolean isCountingActive();

	public abstract long getVersion();

	public abstract IMessage getMessage();

	public abstract boolean isOutgoingActive();

	public abstract BigDecimal getMaximumWeight();

	public abstract short getNoMaxTransportUnits();

	public abstract short getNoEmptyTransportUnits();

	public abstract short getNoTransportUnits();

	public abstract short getPlcState();

	public abstract String getCheckState();

	public abstract boolean isLocationGroupCountingActive();

	public abstract boolean isIncomingActive();

	public abstract ILocationType getLocationType();

	public abstract ILocationGroup getLocationGroup();

}