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
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.openwms.common.domain.values.LocationGroupState;
import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;

/**
 * A LocationGroup , used to group {@link Location}s logically.
 * <p>
 * Used to group {@link Location}s with same characteristics.
 * </p>
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.Location
 */
@Entity
@Table(name = "COM_LOCATION_GROUP")
@NamedQueries({ @NamedQuery(name = "LocationGroup.findAll", query = "select lg from LocationGroup lg"),
        @NamedQuery(name = "LocationGroup.findByName", query = "select lg from LocationGroup lg where lg.name = ?1") })
public class LocationGroup extends AbstractEntity implements DomainObject<Long>, Serializable {

    private static final long serialVersionUID = -885742169116552293L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

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
     * Is the <code>LocationGroup</code> included in the calculation of
     * {@link org.openwms.common.domain.TransportUnit}s? Default: {@value}
     * <p>
     * <code>true</code> : Location is included in the calculation of
     * {@link org.openwms.common.domain.TransportUnit}s.<br>
     * <code>false</code>: Location is not included in the calculation of
     * {@link org.openwms.common.domain.TransportUnit}s.
     * </p>
     */
    @Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
    private boolean locationGroupCountingActive = true;

    /**
     * Number of {@link Location}s belonging to the <code>LocationGroup</code>.
     * Default: {@value} .
     */
    @Column(name = "NO_LOCATIONS")
    private int noLocations = 0;

    /**
     * State of infeed. Default: {@value} .
     */
    @Column(name = "GROUP_STATE_IN")
    private LocationGroupState groupStateIn = LocationGroupState.AVAILABLE;

    /**
     * State of outfeed. Default: {@value} .
     */
    @Column(name = "GROUP_STATE_OUT")
    private LocationGroupState groupStateOut = LocationGroupState.AVAILABLE;

    /**
     * Maximum fill level of the <code>LocationGroup</code>. Default: * {@value}
     * .
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

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

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
    private LocationGroup() {}

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
     * {@inheritDoc}
     */
    @Override
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
     * Change the infeed state of the <code>LocationGroup</code>.
     * 
     * @param groupStateIn
     *            The state to set
     */
    public void setGroupStateIn(LocationGroupState groupStateIn) {
        this.groupStateIn = groupStateIn;
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
     * @param groupStateOut
     *            The state to set
     */
    public void setGroupStateOut(LocationGroupState groupStateOut) {
        this.groupStateOut = groupStateOut;
    }

    /**
     * Returns the count of all sub {@link Location}s.
     * 
     * @return The count of {@link Location}s belonging to this
     *         <code>LocationGroup</code>
     */
    public int getNoLocations() {
        return this.noLocations;
    }

    /**
     * Returns the maximum fill level of the <code>LocationGroup</code>.<br>
     * The maximum fill level defines how many {@link Location}s of the
     * <code>LocationGroup</code> can be occupied by
     * {@link org.openwms.common.domain.TransportUnit}s.
     * <p>
     * The maximum fill level is a value between 0 and 1 and represents a
     * percentage value.
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
     * For example maxFillLevel = 0.85 means: 85% of all {@link Location}s can
     * be occupied.
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
     * @return <code>true</code> if the <code>LocationGroup</code> was new in
     *         the collection of <code>LocationGroup</code>s, otherwise
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
     *            The <code>LocationGroup</code> to be removed from the list of
     *            children
     * @return <code>true</code> if the <code>LocationGroup</code> was found and
     *         could be removed, otherwise <code>false</code>
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
     * @return A unmodifiable set of all {@link Location}s that belong to this
     *         <code>LocationGroup</code>
     */
    public Set<Location> getLocations() {
        return Collections.unmodifiableSet(locations);
    }

    /**
     * Add a {@link Location} to the list of children.
     * 
     * @param location
     *            The {@link Location} to be added as child
     * @return <code>true</code> if the {@link Location} was new in the
     *         collection of {@link Location}s, otherwise <code>false</code>
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
     * @return <code>true</code> if the {@link Location} was found and could be
     *         removed, otherwise <code>false</code>
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
     */
    @Override
    public long getVersion() {
        return this.version;
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
