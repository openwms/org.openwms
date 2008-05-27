/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.domain.common.system.Message;

/**
 * This class is used to specify a location. A location could be a storage
 * location in stock as well as a location on the conveyor. Also virtual and
 * error locations should be described by an <code>Location</code> entity.
 * 
 * <code>Location</code>s could be grouped together to a
 * <code>LocationGroup</code>.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "LOCATION")
public class Location implements Serializable, ILocation {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;

	@Embedded
	private LocationPK locationId;

	/**
	 * Describes the <code>Location</code>.
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * Number of <code>TransportUnit</code>s actually placed on this
	 * <code>Location</code>.
	 */
	@Column(name = "NO_TRANSPORT_UNITS")
	private short noTransportUnits;

	/**
	 * Number of <code>TransportUnit</code>s maximum placed on this
	 * <code>Location</code>.
	 */
	@Column(name = "NO_MAX_TRANSPORT_UNITS")
	private short noMaxTransportUnits;

	/**
	 * Number of empty <code>TransportUnit</code>s placed on this
	 * <code>Location</code>.
	 */
	@Column(name = "NO_EMPTY_TRANSPORT_UNITS")
	private short noEmptyTransportUnits;

	/**
	 * Number of <code>TransportUnit</code>s placed on this
	 * <code>Location</code> having a target <code>Location</code> other
	 * then this.
	 */
	@Column(name = "NO_LEAVE_TRANSPORT_UNITS")
	private short noLeaveTransportUnits;

	/**
	 * Number of <code>TransportUnit</code>s not placed on this
	 * <code>Location</code> but having as target this <code>Location</code>.
	 */
	@Column(name = "NO_INCOMING_TRANSPORT_UNITS")
	private short noIncomingTransportUnits;

	/**
	 * Maximum of weight for this <code>Location</code>.
	 */
	@Column(name = "MAXIMUM_WEIGHT")
	private BigDecimal maximumWeight;

	/**
	 * Timestamp of last change of the <code>TransportUnit</code>. When a
	 * <code>TransportUnit</code> is entering or leaving this place, the
	 * timestamp will be updated. This is necessary to locate old
	 * <code>TransportUnit</code>s in the stock as well as for inventory
	 * calculation.
	 */
	@Column(name = "LAST_ACCESS")
	private Date lastAccess;

	/**
	 * Flag to indicate whether <code>TransportUnit</code>s should be counted
	 * on this <code>Location</code>.
	 */
	@Column(name = "COUNTING_ACTIVE")
	private boolean countingActive;

	/**
	 * Reserved for stock check procedure and for inventory control.
	 */
	@Column(name = "CHECK_STATE")
	private String checkState;

	/**
	 * Shall this <code>Location</code> be integrated in the calculation of
	 * <code>TransportUnit</code>s on the parent <code>LocationGroup</code>.
	 * <p>
	 * true : Location is been included in calculation of
	 * <code>TransportUnit</code>s.<br>
	 * false: Location is not been included in calculation of
	 * <code>TransportUnit</code>s.
	 */
	@Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
	private boolean locationGroupCountingActive;

	/**
	 * Signals the state of incoming for this <code>Location</code>.
	 * <p>
	 * true : <code>Location</code> is available to gather
	 * <code>TransportUnit</code>s.<br>
	 * false: <code>Location</code> is locked, and cannot gather
	 * <code>TransportUnit</code>s.
	 */
	@Column(name = "INCOMING_ACTIVE")
	private boolean incomingActive;

	/**
	 * Signals the state of outgoing of this <code>Location</code>.
	 * <p>
	 * true : <code>Location</code> is enabled for outgoing transports<br>
	 * false: <code>Location</code> is locked, <code>TransportUnit</code>s
	 * can't leave from here.
	 */
	@Column(name = "OUTGOING_STATE")
	private boolean outgoingActive;

	/**
	 * The PLC could change the state of an <code>Location</code>. This
	 * property stores the last state, received from the PLC.
	 * <p>
	 * -1: Not defined.<br>
	 * 0 : No plc error state, everything okay.
	 */
	@Column(name = "PLC_STATE")
	private short plcState;

	/**
	 * State to lock the <code>Location</code>.
	 * <p>
	 * true : This <code>Location</code> will been considered in storage
	 * calculation with by an allocator.<br>
	 * false: This <code>Location</code> will not been considered.
	 */
	@Column(name = "CONSIDERED_IN_ALLOCATION")
	private boolean consideredInAllocation;

	/**
	 * Version field
	 */
	@Version
	private long version;

	/* ------------------- collection mapping ------------------- */
	/**
	 * The <code>LocationType</code> of this <code>Location</code>.
	 */
	@ManyToOne
	@JoinColumn(name = "LOCATION_TYPE")
	private LocationType locationType;

	/**
	 * The <code>LocationGroup</code> to which this <code>Location</code>
	 * belongs.
	 */
	@ManyToOne
	@JoinColumn(name = "LOCATION_GROUP_ID")
	private LocationGroup locationGroup;

	/**
	 * Stores a <code>Message</code> for this <code>Location</code>.
	 */
	@OneToMany(mappedBy = "id")
	private List<Message> messages;

	/* ----------------------------- methods ------------------- */
	public Location(LocationPK locationId) {
		super();
		this.locationId = locationId;
	}

	public long getId() {
		return this.id;
	}

	public LocationPK getLocationId() {
		return this.locationId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getNoLeaveTransportUnits() {
		return this.noLeaveTransportUnits;
	}

	public void setNoLeaveTransportUnits(short noLeaveTransportUnits) {
		this.noLeaveTransportUnits = noLeaveTransportUnits;
	}

	public short getNoIncomingTransportUnits() {
		return this.noIncomingTransportUnits;
	}

	public void setNoIncomingTransportUnits(short noIncomingTransportUnits) {
		this.noIncomingTransportUnits = noIncomingTransportUnits;
	}

	public Date getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public boolean isConsideredInAllocation() {
		return this.consideredInAllocation;
	}

	public void setConsideredInAllocation(boolean consideredInAllocation) {
		this.consideredInAllocation = consideredInAllocation;
	}

	public boolean isCountingActive() {
		return this.countingActive;
	}

	public void setCountingActive(boolean countingActive) {
		this.countingActive = countingActive;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public void addMessage(Message message) {
		if (this.messages == null) {
			this.messages = new ArrayList<Message>();
		}
		messages.add(message);
	}

	public boolean isOutgoingActive() {
		return this.outgoingActive;
	}

	public void setOutgoingActive(boolean outgoingActive) {
		this.outgoingActive = outgoingActive;
	}

	public BigDecimal getMaximumWeight() {
		return this.maximumWeight;
	}

	public void setMaximumWeight(BigDecimal maximumWeight) {
		this.maximumWeight = maximumWeight;
	}

	public short getNoMaxTransportUnits() {
		return this.noMaxTransportUnits;
	}

	public void setNoMaxTransportUnits(short noMaxTransportUnits) {
		this.noMaxTransportUnits = noMaxTransportUnits;
	}

	public short getNoEmptyTransportUnits() {
		return this.noEmptyTransportUnits;
	}

	public void setNoEmptyTransportUnits(short noEmptyTransportUnits) {
		this.noEmptyTransportUnits = noEmptyTransportUnits;
	}

	public short getNoTransportUnits() {
		return this.noTransportUnits;
	}

	public void setNoTransportUnits(short noTransportUnits) {
		this.noTransportUnits = noTransportUnits;
	}

	public short getPlcState() {
		return this.plcState;
	}

	public void setPlcState(short plcState) {
		this.plcState = plcState;
	}

	public String getCheckState() {
		return this.checkState;
	}

	public void setCheckState(String checkState) {
		this.checkState = checkState;
	}

	public boolean isLocationGroupCountingActive() {
		return this.locationGroupCountingActive;
	}

	public void setLocationGroupCountingActive(boolean locationGroupCountingActive) {
		this.locationGroupCountingActive = locationGroupCountingActive;
	}

	public boolean isIncomingActive() {
		return this.incomingActive;
	}

	public void setIncomingActive(boolean incomingActive) {
		this.incomingActive = incomingActive;
	}

	public LocationType getLocationType() {
		return this.locationType;
	}

	public void setLocationType(LocationType locationType) {
		this.locationType = locationType;
	}

	public LocationGroup getLocationGroup() {
		return this.locationGroup;
	}

	public void setLocationGroup(LocationGroup locationGroup) {
		this.locationGroup = locationGroup;
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
