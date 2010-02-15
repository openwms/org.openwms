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

/**
 * A LocationGroup.
 * <p>
 * Used to group {@link Location}s with same characteristics.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see {@link org.openwms.common.domain.Location}
 */
@Entity
@Table(name = "LOCATION_GROUP")
@NamedQueries( { @NamedQuery(name = "LocationGroup.findAll", query = "select lg from LocationGroup lg"),
        @NamedQuery(name = "LocationGroup.findByName", query = "select lg from LocationGroup lg where lg.name = ?1") })
public class LocationGroup implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A STATE.
     * <p>
     * Possible states used for {@link LocationGroup}s.
     * </p>
     * 
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     * @see {@link org.openwms.common.domain.LocationGroup}
     */
    public static enum STATE {
        AVAILABLE, NOT_AVAILABLE;
    };

    /**
     * Primary Key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique identifier of a {@link LocationGroup}.
     */
    @Column(name = "NAME", unique = true)
    private String name;

    /**
     * Description of this {@link LocationGroup}.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Type of this {@link LocationGroup}.
     */
    @Column(name = "GROUP_TYPE")
    private String groupType;

    /**
     * Is this {@link LocationGroup} be included in the calculation of
     * {@link org.openwms.common.domain.TransportUnit}s.
     * <p>
     * true : Location is been included in calculation of
     * {@link org.openwms.common.domain.TransportUnit}s.<br>
     * false: Location is not been included in calculation of
     * {@link org.openwms.common.domain.TransportUnit}s.
     * </p>
     */
    @Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
    private boolean locationGroupCountingActive = true;

    /**
     * Number of {@link Location}s belonging to this {@link LocationGroup}.
     */
    @Column(name = "NO_LOCATIONS")
    private int noLocations = 0;

    /**
     * Inbound status of this {@link LocationGroup}.
     */
    @Column(name = "GROUP_STATE_IN")
    private STATE groupStateIn = STATE.AVAILABLE;

    /**
     * Outbound status of this {@link LocationGroup}.
     */
    @Column(name = "GROUP_STATE_OUT")
    private STATE groupStateOut = STATE.AVAILABLE;

    /**
     * Maximum fill level of this {@link LocationGroup}.
     */
    @Column(name = "MAX_FILL_LEVEL")
    private float maxFillLevel = 0;

    /**
     * Last update timestamp.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;

    /**
     * Name of the PLC system, coupled with this {@link LocationGroup}.
     */
    @Column(name = "SYSTEM_CODE")
    private String systemCode;

    /**
     * Version field.s
     */
    @Version
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * Parent {@link LocationGroup}.
     */
    @ManyToOne
    @JoinColumn(name = "PARENT")
    private LocationGroup parent;

    /**
     * Child {@link LocationGroup}s.
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
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private LocationGroup() {}

    /**
     * Create a new {@link LocationGroup} with an unique name.
     */
    public LocationGroup(String name) {
        this.name = name;
    }

    /**
     * Return the technical key.
     * 
     * @return - Technical, unique key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Checks if the instance is transient.
     * 
     * @return - true: Entity is not present on the persistent storage.<br>
     *         - false : Entity already exists on the persistence storage
     */
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Get the name of this {@link LocationGroup}.
     * 
     * @return - The name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the inbound state of this {@link LocationGroup}.
     * 
     * @return
     */
    public STATE getGroupStateIn() {
        return this.groupStateIn;
    }

    public void setGroupStateIn(STATE groupStateIn) {
        this.groupStateIn = groupStateIn;
    }

    /**
     * Get the outbound state of this {@link LocationGroup}.
     * 
     * @return the groupStateOut.
     */
    public STATE getGroupStateOut() {
        return groupStateOut;
    }

    public void setGroupStateOut(STATE groupStateOut) {
        this.groupStateOut = groupStateOut;
    }

    /**
     * Returns the number of all sub {@link Location}s.
     * 
     * @return
     */
    public int getNoLocations() {
        return this.noLocations;
    }

    /**
     * Returns the maximum fill level of this {@link LocationGroup}.<br>
     * The maximum fill level defines how many {@link Location}s of this
     * {@link LocationGroup} can be occupied with
     * {@link org.openwms.common.domain.TransportUnit}s.
     * <p>
     * The maximum fill level must be value between 0 and 1 and reflects a
     * percentage value.
     * </p>
     * 
     * @return - The maximum fill level
     */
    public float getMaxFillLevel() {
        return this.maxFillLevel;
    }

    /**
     * Set the maximum fill level for this {@link LocationGroup}.
     * <p>
     * Pass a value between 0 and 1.<br>
     * For example maxFillLevel = 0.85 means 85% of all sub {@link Location}s
     * can be occupied.
     * </p>
     * 
     * @param - The maximum fill level
     */
    public void setMaxFillLevel(float maxFillLevel) {
        this.maxFillLevel = maxFillLevel;
    }

    /**
     * Returns the type of this {@link LocationGroup}.
     * 
     * @return
     */
    public String getGroupType() {
        return this.groupType;
    }

    /**
     * Set the type of this {@link LocationGroup}.
     * 
     * @param groupType
     */
    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    /**
     * Returns the last modification date of this {@link LocationGroup}.
     * 
     * @return lastUpdated.
     */
    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    /**
     * Set the date of the last modification of this {@link LocationGroup}.
     * 
     * @param lastUpdated
     */
    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * Get the description text.
     * 
     * @return description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set the description text.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get parent LocationGroup.
     * 
     * @return parent LocationGroup.
     */
    public LocationGroup getParent() {
        return this.parent;
    }

    /**
     * Set parent LocationGroup.
     * 
     * @param parent
     */
    public void setParent(LocationGroup parent) {
        this.parent = parent;
    }

    /**
     * Get all child LocationGroups.
     * 
     * @return child LocationGroups.
     */
    public Set<LocationGroup> getLocationGroups() {
        return locationGroups;
    }

    /**
     * Add a {@link LocationGroup} as child.
     * 
     * @param locationGroups
     */
    public boolean addLocationGroup(LocationGroup locationGroup) {
        if (locationGroup == null) {
            throw new IllegalArgumentException("LocationGroup to add is null");
        }
        if (locationGroup.getParent() != null) {
            locationGroup.getParent().removeLocationGroup(locationGroup);
        }
        locationGroup.setParent(this);
        return locationGroups.add(locationGroup);
    }

    /**
     * Remove a {@link LocationGroup} as child.
     * 
     * @param locationGroups
     */
    public boolean removeLocationGroup(LocationGroup locationGroup) {
        if (locationGroup == null) {
            throw new IllegalArgumentException("LocationGroup to remove is null!");
        }
        locationGroup.setParent(null);
        return locationGroups.remove(locationGroup);
    }

    /**
     * Get all locations.
     * <p>
     * <strong><i>Note:</i> The returned collection is unmodifiable</strong>
     * </p>
     * 
     * @return all Locations.
     */
    public Set<Location> getLocations() {
        return Collections.unmodifiableSet(locations);
    }

    /**
     * Add a {@link Location} as child.
     * 
     * @param location
     */
    public boolean addLocation(Location location) {

        if (location == null) {
            throw new IllegalArgumentException("Location to add is null");
        }
        if (location.getLocationGroup() != null) {
            location.getLocationGroup().removeLocation(location);
        }
        location.setLocationGroup(this);
        return locations.add(location);
    }

    /**
     * Remove a {@link Location} from children.
     * 
     * @param location
     */
    public boolean removeLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Child location is null!");
        }
        location.setLocationGroup(null);
        return locations.remove(location);
    }

    /**
     * Get the systemCode.
     * 
     * @return the systemCode.
     */
    public String getSystemCode() {
        return systemCode;
    }

    /**
     * Set the systemCode.
     * 
     * @param systemCode
     *            The systemCode to set.
     */
    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }

    /**
     * Get the locationGroupCountingActive.
     * 
     * @return the locationGroupCountingActive.
     */
    public boolean isLocationGroupCountingActive() {
        return locationGroupCountingActive;
    }

    /**
     * Set the locationGroupCountingActive.
     * 
     * @param locationGroupCountingActive
     *            The locationGroupCountingActive to set.
     */
    public void setLocationGroupCountingActive(boolean locationGroupCountingActive) {
        this.locationGroupCountingActive = locationGroupCountingActive;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return - Version field
     */
    public long getVersion() {
        return this.version;
    }

    /**
     * Return the name of the {@link LocationGroup} as String.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getName();
    }

}
