/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.openwms.common.domain.system.Message;

public interface ILocation {

	public abstract LocationPK getLocationId();

	public abstract String getDescription();

	public abstract short getNoLeaveTransportUnits();

	public abstract short getNoIncomingTransportUnits();

	public abstract Date getLastAccess();

	public abstract boolean isConsideredInAllocation();

	public abstract boolean isCountingActive();

	public abstract long getVersion();

	public abstract Set<Message> getMessages();

	public abstract boolean isOutgoingActive();

	public abstract BigDecimal getMaximumWeight();

	public abstract short getNoMaxTransportUnits();

	public abstract short getNoEmptyTransportUnits();

	public abstract short getNoTransportUnits();

	public abstract short getPlcState();

	public abstract String getCheckState();

	public abstract boolean isLocationGroupCountingActive();

	public abstract boolean isIncomingActive();

	public abstract LocationType getLocationType();

	public abstract LocationGroup getLocationGroup();

}