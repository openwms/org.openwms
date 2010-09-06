/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
import javax.persistence.Version;

import org.openwms.common.domain.system.Message;

/**
 * A Location - Any kind of place within a warehouse.
 * <p>
 * Could be a storage location in the stock as well as a location on a conveyer.
 * Also virtual and error locations can be described with a {@link Location}
 * Entity.
 * </p>
 * Multiple {@link Location}s can be grouped together to a
 * {@link LocationGroup}.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.LocationGroup
 */
@Entity
@Table(name = "COR_LOCATION", uniqueConstraints = @UniqueConstraint(columnNames = { "AREA", "AISLE", "X", "Y", "Z" }))
@NamedQueries( {
        @NamedQuery(name = Location.NQ_FIND_ALL, query = "select l from Location l"),
        @NamedQuery(name = Location.NQ_FIND_BY_UNIQUE_QUERY, query = "select l from Location l where l.locationId = ?1"),
        @NamedQuery(name = Location.NQ_FIND_ALL_EAGER, query = "select l from Location l left join fetch l.messages left join fetch l.locationType") })
public class Location implements DomainObject, Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 6958794248591576907L;

    /**
     * Query to find all {@link Location}s.
     */
    public static final String NQ_FIND_ALL = "Location.findAll";

    /**
     * Query to find all {@link Location}s and all {@link Message}s, eager
     * loaded.
     */
    public static final String NQ_FIND_ALL_EAGER = "Location.findAllEager";

    /**
     * Query to find <strong>one</strong> {@link Location} by its natural key.
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "Location.findByLocationPK";

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique natural key.
     */
    @Embedded
    private LocationPK locationId;

    /**
     * Description for the {@link Location}.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Maximum number of {@link org.openwms.common.domain.TransportUnit}s
     * placed on this {@link Location}.
     */
    @Column(name = "NO_MAX_TRANSPORT_UNITS")
    private short noMaxTransportUnits = 1;

    /**
     * Maximum weight on this {@link Location}.
     */
    @Column(name = "MAXIMUM_WEIGHT", scale = 3)
    private BigDecimal maximumWeight;

    /**
     * Date of last change. When a
     * {@link org.openwms.common.domain.TransportUnit} is moving on or from this
     * location, lastAccess will be updated. This is necessary to find old
     * {@link org.openwms.common.domain.TransportUnit}s and also for inventory
     * calculation.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_ACCESS")
    private Date lastAccess;

    /**
     * Flag to indicate whether {@link org.openwms.common.domain.TransportUnit}s
     * should be counted on this {@link Location} or not.
     */
    @Column(name = "COUNTING_ACTIVE")
    private boolean countingActive = false;

    /**
     * Reserved for stock check procedure and inventory calculation.
     */
    @Column(name = "CHECK_STATE")
    private String checkState = "--";

    /**
     * Shall this {@link Location} be integrated in the calculation of
     * {@link org.openwms.common.domain.TransportUnit}s on the parent
     * {@link org.openwms.common.domain.LocationGroup}.
     * <p>
     * <code>true</code> : Location is included in calculation of
     * {@link org.openwms.common.domain.TransportUnit}s.<br>
     * <code>false</code>: Location is not included in calculation of
     * {@link org.openwms.common.domain.TransportUnit}s (Default).
     * </p>
     */
    @Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
    private boolean locationGroupCountingActive = false;

    /**
     * Signals the incoming state of this <code>Location</code>.
     * <p>
     * <code>true</code> : {@link Location} is available to carry
     * {@link org.openwms.common.domain.TransportUnit}s. (Default)<br>
     * <code>false</code>: {@link Location} is locked, and cannot carry
     * {@link org.openwms.common.domain.TransportUnit}s.
     * </p>
     */
    @Column(name = "INCOMING_ACTIVE")
    private boolean incomingActive = true;

    /**
     * Signals the outgoing state of this {@link Location}.
     * <p>
     * <code>true</code> : {@link Location} is enabled for outgoing
     * <code>Transport</code>s (Default)<br>
     * <code>false</code>: {@link Location} is locked,
     * {@link org.openwms.common.domain.TransportUnit}s can't leave from this
     * location.
     * </p>
     */
    @Column(name = "OUTGOING_ACTIVE")
    private boolean outgoingActive = true;

    /**
     * The PLC is able to change the state of a {@link Location}. This property
     * stores the last state, received from the PLC.
     * <p>
     * -1: Not defined.<br>
     * 0 : No PLC error, everything okay (Default).
     * </p>
     */
    @Column(name = "PLC_STATE")
    private short plcState = 0;

    /**
     * State to include the {@link Location} in allocation procedure.
     * <p>
     * <code>true</code> : This {@link Location} will be considered in storage
     * calculation by an allocation procedure. (Default)<br>
     * <code>false</code> : This {@link Location} will not be considered in
     * allocation process.
     * </p>
     */
    @Column(name = "CONSIDERED_IN_ALLOCATION")
    private boolean consideredInAllocation = true;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * The {@link LocationType} the {@link Location} belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "LOCATION_TYPE")
    private LocationType locationType;

    /**
     * The {@link LocationGroup} the {@link Location} belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "LOCATION_GROUP", nullable = true)
    private LocationGroup locationGroup;

    /**
     * Stores {@link Message}s for this {@link Location}.
     */
    @OneToMany(cascade = { CascadeType.ALL })
    @JoinTable(name = "COR_LOCATION_MESSAGE", joinColumns = @JoinColumn(name = "LOCATION_ID"), inverseJoinColumns = @JoinColumn(name = "MESSAGE_ID"))
    private Set<Message> messages = new HashSet<Message>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Location() {}

    /**
     * Create a new {@link Location} with the business key.
     * 
     * @param locationId
     *            The unique natural key of the {@link Location}
     */
    public Location(LocationPK locationId) {
        this.locationId = locationId;
    }

    /**
     * Return the technical key.
     * 
     * @return The technical, unique key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    @PrePersist
    @PreUpdate
    protected void preUpdate() {
        lastAccess = new Date();
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

    public boolean getConsideredInAllocation() {
        return this.consideredInAllocation;
    }

    public void setConsideredInAllocation(boolean consideredInAllocation) {
        this.consideredInAllocation = consideredInAllocation;
    }

    public boolean getCountingActive() {
        return this.countingActive;
    }

    public void setCountingActive(boolean countingActive) {
        this.countingActive = countingActive;
    }

    public Set<Message> getMessages() {
        return Collections.unmodifiableSet(messages);
    }

    /**
     * Remove one {@link Message} from this {@link Location}.
     * 
     * @param message
     *            The {@link Message} to be removed
     * @return true if the {@link Message} was found and removed, otherwise
     *         false
     */
    public boolean removeMessage(Message message) {
        if (message == null) {
            throw new IllegalArgumentException("Message may not be null!");
        }
        return this.messages.remove(message);
    }

    /**
     * Add a new {@link Message} to this {@link Location}.
     * 
     * @param message
     *            The {@link Message} to be added
     * @return true if the {@link Message} was new in the collection of
     *         messages, otherwise false
     */
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

    public boolean getLocationGroupCountingActive() {
        return this.locationGroupCountingActive;
    }

    public void setLocationGroupCountingActive(boolean locationGroupCountingActive) {
        this.locationGroupCountingActive = locationGroupCountingActive;
    }

    public boolean getIncomingActive() {
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

    /**
     * Add this {@link Location} to the <code>locationGroup</code>.
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
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * Return the {@link LocationPK} as String.
     * 
     * @see java.lang.Object#toString()
     * @return String
     */
    @Override
    public String toString() {
        return this.locationId.toString();
    }
}
