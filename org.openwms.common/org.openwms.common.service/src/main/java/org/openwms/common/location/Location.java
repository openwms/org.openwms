/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.location;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openwms.common.transport.TransportUnit;
import org.openwms.core.values.Message;

/**
 * A Location, represents some physical as well as virtual place in a warehouse.
 * <p>
 * Could be something like a storage location in the stock or a location on a conveyer. Also virtual or error locations can be modeled with
 * a Location entity.
 * </p>
 * Multiple Locations are grouped to a LocationGroup.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see LocationGroup
 */
@Entity
@Table(name = "COM_LOCATION", uniqueConstraints = @UniqueConstraint(columnNames = { "AREA", "AISLE", "X", "Y", "Z" }))
@NamedQueries({
        @NamedQuery(name = Location.NQ_FIND_ALL, query = "select l from Location l"),
        @NamedQuery(name = Location.NQ_FIND_BY_UNIQUE_QUERY, query = "select l from Location l where l.locationId = ?1"),
        @NamedQuery(name = Location.NQ_FIND_ALL_EAGER, query = "select distinct l from Location l left join fetch l.messages left join fetch l.locationType") })
public class Location extends Target {

    private static final long serialVersionUID = 6958794248591576907L;

    /**
     * Query to find all <code>Location</code>s.
     */
    public static final String NQ_FIND_ALL = "Location.findAll";

    /**
     * Query to find all <code>Location</code>s with all {@link Message}s and {@link LocationType}s (eager loaded).
     */
    public static final String NQ_FIND_ALL_EAGER = "Location.findAllEager";

    /**
     * Query to find <strong>one</strong> <code>Location</code> by its natural key. <li>Query parameter index <strong>1</strong> : The
     * locationId of the <code>Location</code> to search for</li>
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "Location.findByLocationPK";

    /**
     * Unique natural key.
     */
    @Embedded
    private LocationPK locationId;

    /**
     * Description of the <code>Location</code>.
     */
    @Column(name = "C_DESCRIPTION")
    private String description;

    /**
     * Maximum number of {@link TransportUnit}s placed on this <code>Location</code>.
     */
    @Column(name = "NO_MAX_TRANSPORT_UNITS")
    private short noMaxTransportUnits = 1;

    /**
     * Maximum allowed weight on this <code>Location</code>.
     */
    @Column(name = "MAXIMUM_WEIGHT", scale = 3)
    private BigDecimal maximumWeight;

    /**
     * Date of last change. When a {@link TransportUnit} is moving to or away from this <code>Location</code>,
     * lastAccess will be updated. This is necessary to find old {@link TransportUnit}s as well as for inventory
     * calculation.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ACCESS")
    private Date lastAccess;

    /**
     * Flag to indicate whether {@link TransportUnit}s should be counted on this <code>Location</code> or not.
     */
    @Column(name = "COUNTING_ACTIVE")
    private boolean countingActive = false;

    /**
     * Reserved for stock check procedure and inventory calculation.
     */
    @Column(name = "CHECK_STATE")
    private String checkState = "--";

    /**
     * Shall this <code>Location</code> be integrated in the calculation of {@link TransportUnit}s of the parent
     * {@link LocationGroup}.
     * <p>
     * <code>true</code> : <code>Location</code> is included in calculation of {@link TransportUnit}s.<br>
     * <code>false</code>: <code>Location</code> is not included in calculation of {@link TransportUnit}s.
     * </p>
     */
    @Column(name = "LG_COUNTING_ACTIVE")
    private boolean locationGroupCountingActive = false;

    /**
     * Signals the incoming state of this <code>Location</code>. <code>Location</code>s which are blocked for incoming cannot pick up
     * {@link TransportUnit}s.
     * <p>
     * <code>true</code> : <code>Location</code> is ready to pick up {@link TransportUnit}s.<br>
     * <code>false</code>: <code>Location</code> is locked, and cannot pick up {@link TransportUnit}s.
     * </p>
     */
    @Column(name = "INCOMING_ACTIVE")
    private boolean incomingActive = true;

    /**
     * Signals the outgoing state of this <code>Location</code>. <code>Location</code>s which are blocked for outgoing cannot release
     * {@link TransportUnit}s.
     * <p>
     * <code>true</code> : <code>Location</code> is enabled for outgoing <code>Transport</code>s<br>
     * <code>false</code>: <code>Location</code> is locked, {@link TransportUnit}s can't leave this
     * <code>Location</code>.
     * </p>
     */
    @Column(name = "OUTGOING_ACTIVE")
    private boolean outgoingActive = true;

    /**
     * The PLC is able to change the state of a <code>Location</code>. This property stores the last state, received from the PLC.
     * <p>
     * -1: Not defined.<br>
     * 0 : No PLC error, everything okay.
     * </p>
     */
    @Column(name = "PLC_STATE")
    private short plcState = 0;

    /**
     * Determines whether the <code>Location</code> is considered in the allocation procedure.
     * <p>
     * <code>true</code> : This <code>Location</code> will be considered in storage calculation by an allocation procedure.<br>
     * <code>false</code> : This <code>Location</code> will not be considered in the allocation process.
     * </p>
     */
    @Column(name = "CONSIDERED_IN_ALLOCATION")
    private boolean consideredInAllocation = true;

    /* ------------------- collection mapping ------------------- */
    /**
     * The {@link LocationType} where the <code>Location</code> belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "LOCATION_TYPE")
    private LocationType locationType;

    /**
     * The {@link LocationGroup} where the <code>Location</code> belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "LOCATION_GROUP")
    private LocationGroup locationGroup;

    /**
     * Stored {@link Message}s for this <code>Location</code>.
     */
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "COM_LOCATION_MESSAGE", joinColumns = @JoinColumn(name = "LOCATION_ID"), inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID"))
    private Set<Message> messages = new HashSet<Message>();

    /**
     * Create a new <code>Location</code> with the business key.
     * 
     * @param locationId
     *            The unique natural key of the <code>Location</code>
     */
    public Location(LocationPK locationId) {
        this.locationId = locationId;
    }

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Location() {
        super();
    }

    /**
     * Add a new {@link Message} to this <code>Location</code>.
     * 
     * @param message
     *            The {@link Message} to be added
     * @return <code>true</code> if the {@link Message} is new in the collection of messages, otherwise <code>false</code>
     */
    public boolean addMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message may not be null!");
        }
        return this.messages.add(message);
    }

    /**
     * Returns the checkState to indicate the stock check procedure.
     * 
     * @return The checkState
     */
    public String getCheckState() {
        return this.checkState;
    }

    /**
     * Determine whether the <code>Location</code> is considered during allocation.
     * 
     * @return <code>true</code> when considered in allocation, otherwise <code>false</code>
     */
    public boolean isConsideredInAllocation() {
        return this.consideredInAllocation;
    }

    /**
     * Determine whether {@link TransportUnit}s should be counted on this <code>Location</code> or not.
     * 
     * @return <code>true</code> when counting is active, otherwise <code>false</code>
     */
    public boolean isCountingActive() {
        return this.countingActive;
    }

    /**
     * Returns the description of the <code>Location</code>.
     * 
     * @return The description text
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Determine whether incoming mode is activated and {@link TransportUnit}s can be put on this
     * <code>Location</code>.
     * 
     * @return <code>true</code> when incoming mode is activated, otherwise <code>false</code>
     */
    public boolean isIncomingActive() {
        return this.incomingActive;
    }

    /**
     * Check whether infeed is blocked and moving {@link TransportUnit}s to here is forbidden.
     * 
     * @return <code>true</code> is blocked, otherwise <code>false</code>
     */
    public boolean isInfeedBlocked() {
        return !this.incomingActive;
    }

    /**
     * Return the date when the <code>Location</code> was updated the last time.
     * 
     * @return Timestamp of the last update
     */
    public Date getLastAccess() {
        return this.lastAccess;
    }

    /**
     * Return the {@link LocationGroup} where the <code>Location</code> belongs to.
     * 
     * @return The {@link LocationGroup} of the <code>Location</code>
     */
    public LocationGroup getLocationGroup() {
        return this.locationGroup;
    }

    /**
     * Determine whether the <code>Location</code> is part of the parent {@link LocationGroup}s calculation
     * procedure of {@link TransportUnit}s.
     * 
     * @return <code>true</code> if calculation is activated, otherwise <code>false</code>
     */
    public boolean isLocationGroupCountingActive() {
        return this.locationGroupCountingActive;
    }

    /**
     * Returns the locationId (natural key) of the <code>Location</code>.
     * 
     * @return The locationId
     */
    public LocationPK getLocationId() {
        return this.locationId;
    }

    /**
     * Returns the type of <code>Location</code>.
     * 
     * @return The type
     */
    public LocationType getLocationType() {
        return this.locationType;
    }

    /**
     * Return the maximum allowed weight on the <code>Location</code>.
     * 
     * @return The maximum allowed weight
     */
    public BigDecimal getMaximumWeight() {
        return this.maximumWeight;
    }

    /**
     * Returns an unmodifiable Set of {@link Message}s stored for the <code>Location</code>.
     * 
     * @return An unmodifiable Set
     */
    public Set<Message> getMessages() {
        return Collections.unmodifiableSet(messages);
    }

    /**
     * Returns the maximum number of {@link TransportUnit}s allowed on the <code>Location</code>.
     * 
     * @return The maximum number of {@link TransportUnit}s
     */
    public short getNoMaxTransportUnits() {
        return this.noMaxTransportUnits;
    }

    /**
     * Determine whether outgoing mode is activated and {@link TransportUnit}s can leave this
     * <code>Location</code>.
     * 
     * @return <code>true</code> when outgoing mode is activated, otherwise <code>false</code>
     */
    public boolean isOutgoingActive() {
        return this.outgoingActive;
    }

    /**
     * Check whether outfeed is blocked and moving {@link TransportUnit}s from here is forbidden.
     * 
     * @return <code>true</code> is blocked, otherwise <code>false</code>
     */
    public boolean isOutfeedBlocked() {
        return !this.outgoingActive;
    }

    /**
     * Return the current set plc state.
     * 
     * @return the plc state
     */
    public short getPlcState() {
        return this.plcState;
    }

    /**
     * Remove one or more {@link Message}s from this <code>Location</code>.
     * 
     * @param msgs
     *            An array of {@link Message}s to be removed
     * @return <code>true</code> if the {@link Message}s were found and removed, otherwise <code>false</code>
     * @throws IllegalArgumentException
     *             when messages is <code>null</code>
     */
    public boolean removeMessages(Message... msgs) {
        if (msgs == null) {
            throw new IllegalArgumentException("Message may not be null!");
        }
        return this.messages.removeAll(Arrays.asList(msgs));
    }

    /**
     * Change the checkState of the <code>Location</code>.
     * 
     * @param checkState
     *            The new state to set
     */
    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    /**
     * Change the behavior whether the <code>Location</code> shall be considered in the allocation procedure or not.
     * 
     * @param consideredInAllocation
     *            <code>true</code> allocation active, otherwise <code>false</code>
     */
    public void setConsideredInAllocation(boolean consideredInAllocation) {
        this.consideredInAllocation = consideredInAllocation;
    }

    /**
     * Change the behavior whether the <code>Location</code> shall be considered in the calculation of
     * {@link TransportUnit}s or not.
     * 
     * @param countingActive
     *            <code>true</code> counting active, otherwise <code>false</code>
     */
    public void setCountingActive(boolean countingActive) {
        this.countingActive = countingActive;
    }

    /**
     * Change the description of the <code>Location</code>.
     * 
     * @param description
     *            The new description text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Change the incoming state of the <code>Location</code>.
     * 
     * @param incomingActive
     *            <code>true</code> The <code>Location</code> can pick up {@link TransportUnit}s, otherwise
     *            <code>false</code>
     */
    public void setIncomingActive(boolean incomingActive) {
        this.incomingActive = incomingActive;
    }

    /**
     * Change the date when the <code>Location</code> was updated the last time.
     * 
     * @param lastAccess
     *            The date of change.
     */
    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    /**
     * Add this <code>Location</code> to the <code>locationGroup</code>. When the argument is <code>null</code> an existing
     * {@link LocationGroup} is removed from the <code>Location</code>.
     * 
     * @param locationGroup
     *            The {@link LocationGroup} to be assigned
     */
    public void setLocationGroup(LocationGroup locationGroup) {
        if (locationGroup != null) {
            this.setLocationGroupCountingActive(locationGroup.isLocationGroupCountingActive());
        }
        this.locationGroup = locationGroup;
    }

    /**
     * Define whether or not the <code>Location</code> shall be considered in counting {@link TransportUnit}s of
     * the parent {@link LocationGroup}.
     * 
     * @param locationGroupCountingActive
     *            <code>true</code> if considered, otherwise <code>false</code>
     */
    public void setLocationGroupCountingActive(boolean locationGroupCountingActive) {
        this.locationGroupCountingActive = locationGroupCountingActive;
    }

    /**
     * Change the type of the <code>Location</code>.
     * 
     * @param locationType
     *            The new type to set
     */
    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    /**
     * Change the maximum allowed weight of the <code>Location</code>.
     * 
     * @param maximumWeight
     *            The new weight to set
     */
    public void setMaximumWeight(BigDecimal maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    /**
     * Change the maximum number of {@link TransportUnit}s allowed on the <code>Location</code>.
     * 
     * @param noMaxTransportUnits
     *            The number of {@link TransportUnit}s to set
     */
    public void setNoMaxTransportUnits(short noMaxTransportUnits) {
        this.noMaxTransportUnits = noMaxTransportUnits;
    }

    /**
     * Change the outgoing state of the <code>Location</code>.
     * 
     * @param outgoingActive
     *            <code>true</code> {@link TransportUnit}s can be moved away from the <code>Location</code>,
     *            otherwise <code>false</code>
     */
    public void setOutgoingActive(boolean outgoingActive) {
        this.outgoingActive = outgoingActive;
    }

    /**
     * Change the current plc state.
     * 
     * @param plcState
     *            The new state to set
     */
    public void setPlcState(short plcState) {
        this.plcState = plcState;
    }

    /**
     * Return the {@link LocationPK} as String.
     * 
     * @see LocationPK#toString()
     * @return String locationId
     */
    @Override
    public String toString() {
        return this.locationId.toString();
    }

    /**
     * On update or insert the lastAccess is updated to the current date. (JPA lifecycle callback method).
     */
    @PrePersist
    @PreUpdate
    protected void preUpdate() {
        lastAccess = new Date();
    }
}
