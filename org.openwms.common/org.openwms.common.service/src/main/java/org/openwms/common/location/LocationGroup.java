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
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.openwms.common.transport.TransportUnit;

/**
 * A LocationGroup is a logical group of <code>Location</code>s, grouping together <code>Location</code>s with same characteristics.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.location.Location
 */
@Entity
@Table(name = "COM_LOCATION_GROUP")
@NamedQueries({ @NamedQuery(name = "LocationGroup.findAll", query = "select lg from LocationGroup lg"),
        @NamedQuery(name = "LocationGroup.findByName", query = "select lg from LocationGroup lg where lg.name = ?1") })
public class LocationGroup extends Target implements Serializable {

    /**
     * Unique identifier of a <code>LocationGroup</code>.
     */
    @Column(name = "NAME", unique = true)
    private String name;

    /**
     * Description for the <code>LocationGroup</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * A type can be assigned to a <code>LocationGroup</code>.
     */
    @Column(name = "GROUP_TYPE")
    private String groupType;

    /**
     * Is the <code>LocationGroup</code> included in the calculation of {@link TransportUnit}s?
     * <p>
     * <code>true</code> : Location is included in the calculation of {@link TransportUnit}s.<br>
     * <code>false</code>: Location is not included in the calculation of {@link TransportUnit}s.
     * </p>
     */
    @Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
    private boolean locationGroupCountingActive = true;

    /**
     * Number of {@link Location}s belonging to the <code>LocationGroup</code>.
     */
    @Column(name = "NO_LOCATIONS")
    private int noLocations = 0;

    /**
     * State of infeed.
     */
    @Column(name = "GROUP_STATE_IN")
    @Enumerated(EnumType.STRING)
    private LocationGroupState groupStateIn = LocationGroupState.AVAILABLE;

    /**
     * References the <code>LocationGroup</code> that locked this <code>LocationGroup</code> for infeed.
     */
    @ManyToOne
    @JoinColumn(name = "IN_LOCKER")
    private LocationGroup stateInLocker;

    /**
     * State of outfeed.
     */
    @Column(name = "GROUP_STATE_OUT")
    @Enumerated(EnumType.STRING)
    private LocationGroupState groupStateOut = LocationGroupState.AVAILABLE;

    /**
     * References the <code>LocationGroup</code> that locked this <code>LocationGroup</code> for outfeed.
     */
    @ManyToOne
    @JoinColumn(name = "OUT_LOCKER")
    private LocationGroup stateOutLocker;

    /**
     * Maximum fill level of the <code>LocationGroup</code>.
     */
    @Column(name = "MAX_FILL_LEVEL")
    private float maxFillLevel = 0;

    /**
     * Date of the last change.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    /**
     * Name of the PLC system, tied to this <code>LocationGroup</code>.
     */
    @Column(name = "SYSTEM_CODE")
    private String systemCode;

    /* ------------------- collection mapping ------------------- */
    /**
     * Parent <code>LocationGroup</code>.
     */
    @ManyToOne
    @JoinColumn(name = "PARENT")
    private LocationGroup parent;

    /**
     * Child <code>LocationGroup</code>s.
     */
    @OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL })
    private Set<LocationGroup> locationGroups = new HashSet<LocationGroup>();

    /**
     * Child {@link Location}s.
     */
    @OneToMany(mappedBy = "locationGroup")
    private Set<Location> locations = new HashSet<Location>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by the persistence provider.
     */
    @SuppressWarnings("unused")
    private LocationGroup() {
        super();
    }

    /**
     * Create a new <code>LocationGroup</code> with an unique name.
     * 
     * @param name
     *            The name of the <code>LocationGroup</code>
     */
    public LocationGroup(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the <code>LocationGroup</code>.
     * 
     * @return The name of the <code>LocationGroup</code>
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the <code>LocationGroup</code>.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the infeed state of the <code>LocationGroup</code>.
     * 
     * @return The state of infeed
     */
    public LocationGroupState getGroupStateIn() {
        return this.groupStateIn;
    }

    /**
     * Check whether infeed is allowed for the <code>LocationGroup</code>.
     * 
     * @return <code>true</code> if allowed, otherwise <code>false</code>.
     */
    public boolean isInfeedAllowed() {
        return (getGroupStateIn() == LocationGroupState.AVAILABLE);
    }

    /**
     * Check whether infeed of the <code>LocationGroup</code> is blocked.
     * 
     * @return <code>true</code> if blocked, otherwise <code>false</code>.
     */
    public boolean isInfeedBlocked() {
        return !isInfeedAllowed();
    }

    /**
     * Check whether outfeed is allowed for the <code>LocationGroup</code>.
     * 
     * @return <code>true</code> if allowed, otherwise <code>false</code>.
     */
    public boolean isOutfeedAllowed() {
        return (getGroupStateIn() == LocationGroupState.AVAILABLE);
    }

    /**
     * Check whether outfeed of the <code>LocationGroup</code> is blocked.
     * 
     * @return <code>true</code> if blocked, otherwise <code>false</code>.
     */
    public boolean isOutfeedBlocked() {
        return !isInfeedAllowed();
    }

    /**
     * Change the infeed state of the <code>LocationGroup</code>.
     * 
     * @param gStateIn
     *            The state to set
     * @param lockLg
     *            The <code>LocationGroup</code> that wants to lock/unlock this <code>LocationGroup</code>.
     */
    public void setGroupStateIn(LocationGroupState gStateIn, LocationGroup lockLg) {
        if (this.groupStateIn == LocationGroupState.NOT_AVAILABLE && gStateIn == LocationGroupState.AVAILABLE
                && (this.stateInLocker == null || this.stateInLocker.equals(lockLg))) {
            this.groupStateIn = gStateIn;
            this.stateInLocker = null;
            for (LocationGroup child : locationGroups) {
                child.setGroupStateIn(gStateIn, lockLg);
            }
        }
        if (this.groupStateIn == LocationGroupState.AVAILABLE && gStateIn == LocationGroupState.NOT_AVAILABLE
                && (this.stateInLocker == null || this.stateInLocker.equals(lockLg))) {
            this.groupStateIn = gStateIn;
            this.stateInLocker = lockLg;
            for (LocationGroup child : locationGroups) {
                child.setGroupStateIn(gStateIn, lockLg);
            }
        }
    }

    /**
     * Return the outfeed state of the <code>LocationGroup</code>.
     * 
     * @return The state of outfeed
     */
    public LocationGroupState getGroupStateOut() {
        return groupStateOut;
    }

    /**
     * Set the outfeed state of the <code>LocationGroup</code>.
     * 
     * @param gStateOut
     *            The state to set
     * @param lockLg
     *            The <code>LocationGroup</code> that wants to lock/unlock this <code>LocationGroup</code>.
     */
    public void setGroupStateOut(LocationGroupState gStateOut, LocationGroup lockLg) {
        if (this.groupStateOut == LocationGroupState.NOT_AVAILABLE && gStateOut == LocationGroupState.AVAILABLE
                && (this.stateOutLocker == null || this.stateOutLocker.equals(lockLg))) {
            this.groupStateOut = gStateOut;
            this.stateOutLocker = null;
            for (LocationGroup child : locationGroups) {
                child.setGroupStateOut(gStateOut, lockLg);
            }
        }
        if (this.groupStateOut == LocationGroupState.AVAILABLE && gStateOut == LocationGroupState.NOT_AVAILABLE
                && (this.stateOutLocker == null || this.stateOutLocker.equals(lockLg))) {
            this.groupStateOut = gStateOut;
            this.stateOutLocker = lockLg;
            for (LocationGroup child : locationGroups) {
                child.setGroupStateOut(gStateOut, lockLg);
            }
        }
    }

    /**
     * Returns the count of all sub {@link Location}s.
     * 
     * @return The count of {@link Location}s belonging to this <code>LocationGroup</code>
     */
    public int getNoLocations() {
        return this.noLocations;
    }

    /**
     * Returns the maximum fill level of the <code>LocationGroup</code>.<br>
     * The maximum fill level defines how many {@link Location}s of the <code>LocationGroup</code> can be occupied by
     * {@link TransportUnit}s.
     * <p>
     * The maximum fill level is a value between 0 and 1 and represents a percentage value.
     * </p>
     * 
     * @return The maximum fill level
     */
    public float getMaxFillLevel() {
        return this.maxFillLevel;
    }

    /**
     * Set the maximum fill level for the <code>LocationGroup</code>.
     * <p>
     * Pass a value between 0 and 1.<br>
     * For example maxFillLevel = 0.85 means: 85% of all {@link Location}s can be occupied.
     * </p>
     * 
     * @param maxFillLevel
     *            The maximum fill level
     */
    public void setMaxFillLevel(float maxFillLevel) {
        this.maxFillLevel = maxFillLevel;
    }

    /**
     * Returns the type of the <code>LocationGroup</code>.
     * 
     * @return The type of the <code>LocationGroup</code>
     */
    public String getGroupType() {
        return this.groupType;
    }

    /**
     * Set the type for the <code>LocationGroup</code>.
     * 
     * @param groupType
     *            The type of the <code>LocationGroup</code>
     */
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    /**
     * Returns the date of the last modification.
     * 
     * @return lastUpdated.
     */
    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Set the date of the last modification.
     * 
     * @param lastUpdated
     *            The date to set
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Returns the description text.
     * 
     * @return The Description as String
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description text.
     * 
     * @param description
     *            The String to set as description text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the parent <code>LocationGroup</code>.
     * 
     * @return The parent <code>LocationGroup</code>
     */
    public LocationGroup getParent() {
        return this.parent;
    }

    /**
     * Set the parent <code>LocationGroup</code>.
     * 
     * @param parent
     *            The <code>LocationGroup</code> to set as parent
     */
    public void setParent(LocationGroup parent) {
        this.parent = parent;
    }

    /**
     * Return all child <code>LocationGroup</code>.
     * 
     * @return A set of all <code>LocationGroup</code> having this one as parent
     */
    public Set<LocationGroup> getLocationGroups() {
        return locationGroups;
    }

    /**
     * Add a <code>LocationGroup</code> to the list of children.
     * 
     * @param locationGroup
     *            The <code>LocationGroup</code> to be added as a child
     * @return <code>true</code> if the <code>LocationGroup</code> was new in the collection of <code>LocationGroup</code>s, otherwise
     *         <code>false</code>
     */
    public boolean addLocationGroup(LocationGroup locationGroup) {
        if (locationGroup == null) {
            throw new IllegalArgumentException("LocationGroup to be added is null");
        }
        if (locationGroup.getParent() != null) {
            locationGroup.getParent().removeLocationGroup(locationGroup);
        }
        locationGroup.setParent(this);
        return locationGroups.add(locationGroup);
    }

    /**
     * Remove a <code>LocationGroup</code> from the list of children.
     * 
     * @param locationGroup
     *            The <code>LocationGroup</code> to be removed from the list of children
     * @return <code>true</code> if the <code>LocationGroup</code> was found and could be removed, otherwise <code>false</code>
     */
    public boolean removeLocationGroup(LocationGroup locationGroup) {
        if (locationGroup == null) {
            throw new IllegalArgumentException("LocationGroup to remove is null!");
        }
        locationGroup.setParent(null);
        return locationGroups.remove(locationGroup);
    }

    /**
     * Return all {@link Location}s in an unmodifiable Collection.
     * 
     * @return A unmodifiable set of all {@link Location}s that belong to this <code>LocationGroup</code>
     */
    public Set<Location> getLocations() {
        return Collections.unmodifiableSet(locations);
    }

    /**
     * Add a {@link Location} to the list of children.
     * 
     * @param location
     *            The {@link Location} to be added as child
     * @return <code>true</code> if the {@link Location} was new in the collection of {@link Location}s, otherwise <code>false</code>
     */
    public boolean addLocation(Location location) {

        if (location == null) {
            throw new IllegalArgumentException("Location to be added is null");
        }
        if (location.getLocationGroup() != null) {
            location.getLocationGroup().removeLocation(location);
        }
        location.setLocationGroup(this);
        return locations.add(location);
    }

    /**
     * Remove a {@link Location} from the list of children.
     * 
     * @param location
     *            The {@link Location} to be removed from the list of children
     * @return <code>true</code> if the {@link Location} was found and could be removed, otherwise <code>false</code>
     */
    public boolean removeLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Child location is null!");
        }
        location.setLocationGroup(null);
        return locations.remove(location);
    }

    /**
     * Returns the systemCode.
     * 
     * @return The systemCode
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Set the systemCode.
     * 
     * @param systemCode
     *            The systemCode to set
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * Returns the locationGroupCountingActive.
     * 
     * @return The locationGroupCountingActive
     */
    public boolean isLocationGroupCountingActive() {
        return locationGroupCountingActive;
    }

    /**
     * Set the locationGroupCountingActive.
     * 
     * @param locationGroupCountingActive
     *            The locationGroupCountingActive to set
     */
    public void setLocationGroupCountingActive(boolean locationGroupCountingActive) {
        this.locationGroupCountingActive = locationGroupCountingActive;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof LocationGroup)) {
            return false;
        }
        LocationGroup other = (LocationGroup) obj;
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Return the name of the <code>LocationGroup</code> as String.
     * 
     * @see java.lang.Object#toString()
     * @return String
     */
    @Override
    public String toString() {
        return getName();
    }
}