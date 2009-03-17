/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.openwms.common.domain.system.Message;

/**
 * This class is used to specify a location. A location could be a storage location in stock as well as a location on
 * the conveyor. Also virtual and error locations should be described by an <code>Location</code> entity.
 * 
 * <code>Location</code>s could be grouped together to a <code>LocationGroup</code>.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "LOCATION", uniqueConstraints = @UniqueConstraint(columnNames = { "AREA", "AISLE", "X", "Y", "Z" }))
@NamedQueries( { @NamedQuery(name = "Location.findAll", query = "select l from Location l"),
		@NamedQuery(name = "Location.findByLocationPK", query = "select l from Location l where l.locationId = ?1") })
public class Location implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	/**
	 * Unique key.
	 */
	@Embedded
	private LocationPK locationId;

	/**
	 * Describes the <code>Location</code>.
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * Number of <code>TransportUnit</code>s maximum placed on this <code>Location</code>.
	 */
	@Column(name = "NO_MAX_TRANSPORT_UNITS")
	private short noMaxTransportUnits = 1;

	/**
	 * Maximum of weight for this <code>Location</code>.
	 */
	@Column(name = "MAXIMUM_WEIGHT")
	private BigDecimal maximumWeight;

	/**
	 * Timestamp of last change of the <code>TransportUnit</code>. When a <code>TransportUnit</code> is entering or
	 * leaving this place, the timestamp will be updated. This is necessary to locate old <code>TransportUnit</code>s in
	 * the stock as well as for inventory calculation.
	 */
	@Column(name = "LAST_ACCESS")
	private Date lastAccess;

	/**
	 * Flag to indicate whether <code>TransportUnit</code>s should be counted on this <code>Location</code>.
	 */
	@Column(name = "COUNTING_ACTIVE")
	private Boolean countingActive;

	/**
	 * Reserved for stock check procedure and for inventory control.
	 */
	@Column(name = "CHECK_STATE")
	private String checkState = "--";

	/**
	 * Shall this <code>Location</code> be integrated in the calculation of <code>TransportUnit</code>s on the parent
	 * <code>LocationGroup</code>.
	 * <p>
	 * true : Location is been included in calculation of <code>TransportUnit</code>s.<br>
	 * false: Location is not been included in calculation of <code>TransportUnit</code>s.
	 */
	@Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
	private Boolean locationGroupCountingActive;

	/**
	 * Signals the state of incoming for this <code>Location</code>.
	 * <p>
	 * true : <code>Location</code> is available to gather <code>TransportUnit</code>s.<br>
	 * false: <code>Location</code> is locked, and cannot gather <code>TransportUnit</code>s.
	 */
	@Column(name = "INCOMING_ACTIVE")
	private Boolean incomingActive;

	/**
	 * Signals the state of outgoing of this <code>Location</code>.
	 * <p>
	 * true : <code>Location</code> is enabled for outgoing transports<br>
	 * false: <code>Location</code> is locked, <code>TransportUnit</code>s can't leave from here.
	 */
	@Column(name = "OUTGOING_ACTIVE")
	private Boolean outgoingActive;

	/**
	 * The PLC could change the state of an <code>Location</code>. This property stores the last state, received from
	 * the PLC.
	 * <p>
	 * -1: Not defined.<br>
	 * 0 : No plc error state, everything okay.
	 */
	@Column(name = "PLC_STATE")
	private short plcState = 0;

	/**
	 * State to lock the <code>Location</code>.
	 * <p>
	 * true : This <code>Location</code> will been considered in storage calculation with by an allocator.<br>
	 * false: This <code>Location</code> will not been considered.
	 */
	@Column(name = "CONSIDERED_IN_ALLOCATION")
	private Boolean consideredInAllocation;

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
	 * The <code>LocationGroup</code> to which this <code>Location</code> belongs.
	 */
	@ManyToOne
	@JoinColumn(name = "LOCATION_GROUP", nullable = true)
	private LocationGroup locationGroup;

	/**
	 * Stores a <code>Message</code> for this <code>Location</code>.
	 */
	@OneToMany(cascade = { CascadeType.ALL })
	private Set<Message> messages = new HashSet<Message>();

	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private Location() {}

	/**
	 * 
	 * Create a new <code>Location</code>.
	 * 
	 * @param locationId
	 */
	public Location(LocationPK locationId) {
		this.locationId = locationId;
	}

	public Long getId() {
		return this.id;
	}

	/**
	 * Returns true if this is a transient object.
	 * 
	 * @return
	 */
	public boolean isNew() {
		return this.id == null;
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

	public Date getLastAccess() {
		return this.lastAccess;
	}

	public void setLastAccess(Date lastAccess) {
		this.lastAccess = lastAccess;
	}

	public Boolean getConsideredInAllocation() {
		return this.consideredInAllocation;
	}

	public void setConsideredInAllocation(Boolean consideredInAllocation) {
		this.consideredInAllocation = consideredInAllocation;
	}

	public Boolean getCountingActive() {
		return this.countingActive;
	}

	public void setCountingActive(Boolean countingActive) {
		this.countingActive = countingActive;
	}

	public Set<Message> getMessages() {
		return Collections.unmodifiableSet(messages);
	}

	public boolean removeMessage(Message message) {
		if (message == null) {
			throw new IllegalArgumentException("Message may not be null!");
		}
		return this.messages.remove(message);
	}

	public boolean addMessage(Message message) {
		if (message == null) {
			throw new IllegalArgumentException("Message may not be null!");
		}
		return this.messages.add(message);
	}

	public Boolean getOutgoingActive() {
		return this.outgoingActive;
	}

	public void setOutgoingActive(Boolean outgoingActive) {
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

	public Boolean getLocationGroupCountingActive() {
		return this.locationGroupCountingActive;
	}

	public void setLocationGroupCountingActive(Boolean locationGroupCountingActive) {
		this.locationGroupCountingActive = locationGroupCountingActive;
	}

	public Boolean getIncomingActive() {
		return this.incomingActive;
	}

	public void setIncomingActive(Boolean incomingActive) {
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
		if (locationGroup != null) {
			this.setLocationGroupCountingActive(locationGroup.isLocationGroupCountingActive());
		}
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
