/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

/**
 * A LocationGroup.
 * <p>
 * Used to group <code>Location</code>s with same characteristics.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "LOCATION_GROUP")
public class LocationGroup implements Serializable, ILocationGroup {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary Key.
	 */
	@Id
	@Column(name = "LOCATION_GROUP_ID")
	@GeneratedValue
	private long id;

	/**
	 * Unique identifier of a <code>LocationGroup</code>.
	 */
	@Column(name = "GROUP_ID", unique = true)
	private String groupId;

	/**
	 * Description for this <code>LocationGroup</code>.
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * Type of this <code>LocationGroup</code>.
	 */
	@Column(name = "GROUP_TYPE")
	private String groupType;

	/**
	 * Is this <code>LocationGroup</code> be integrated in the calculation of
	 * <code>TransportUnit</code>s.
	 * <p>
	 * true : Location is been included in calculation of
	 * <code>TransportUnit</code>s.<br>
	 * false: Location is not been included in calculation of
	 * <code>TransportUnit</code>s.
	 */
	@Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
	private boolean locationGroupCountingActive = true;

	/**
	 * Number of <code>Location</code>s belonging to this
	 * <code>LocationGroup</code>.
	 */
	@Column(name = "NO_LOCATIONS")
	private int noLocations;

	/**
	 * Number of free <code>Location</code>s belonging to this
	 * <code>LocationGroup</code>.
	 */
	@Column(name = "NO_FREE_LOCATIONS")
	private int noFreeLocations;

	/**
	 * Inbound status of this <code>LocationGroup</code>.
	 */
	@Column(name = "GROUP_STATE_IN")
	private short groupStateIn;

	/**
	 * Outbound status of this <code>LocationGroup</code>.
	 */
	@Column(name = "GROUP_STATE_OUT")
	private short groupStateOut;

	/**
	 * Maximum fill level for this <code>LocationGroup</code>.
	 */
	@Column(name = "MAX_FILL_LEVEL")
	private float maxFillLevel;

	/**
	 * Last update timestamp.
	 */
	@Column(name = "LAST_UPDATED")
	private Date lastUpdated;

	/**
	 * Name of the plc system, coupled with this <code>LocationGroup</code>.
	 */
	@Column(name = "SYSTEM_CODE")
	private String systemCode;

	/**
	 * Version field
	 */
	@Version
	private long version;

	/* ------------------- collection mapping ------------------- */
	/**
	 * Parent <code>LocationGroup</code>.
	 */
	@Column(name = "PARENT")
	private LocationGroup parent;

	/**
	 * Child <code>LocationGroup</code>s.
	 */
	@OneToMany(mappedBy = "parent", cascade={CascadeType.MERGE, CascadeType.PERSIST})
	private Set<LocationGroup> locationGroups = new HashSet<LocationGroup>();

	/**
	 * Child <code>Location</code>s.
	 */
	@OneToMany(mappedBy = "locationGroup")
	private Set<Location> locations = new HashSet<Location>();

	/* ----------------------------- methods ------------------- */
	/**
	 * Create a new <code>LocationGroup</code> with unique groupId as name.
	 */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private LocationGroup() {
	}

	public LocationGroup(String groupId) {
		this.groupId = groupId;
	}

	public long getId() {
		return this.id;
	}

	/**
	 * Get the groupId.
	 * 
	 * @return the groupId.
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * Returns the sum of sub <code>Location</code>s having no
	 * <code>TransportUnit</code>s.
	 * 
	 * @return
	 */
	public int getNoFreeLocations() {
		return this.noFreeLocations;
	}
	
	/**
	 * Increase the number of <tt>Location</tt>s about one.
	 *
	 */
	public void increaseNoFreeLocations(){
		this.noFreeLocations++;
	}
	
	/**
	 * Decrease the number of <tt>Location</tt>s about one.
	 *
	 */
	public void decreaseNoFreeLocations(){
		this.noFreeLocations--;
	}

	/**
	 * Returns the inbound status of this <code>Location</code>.
	 * 
	 * @return
	 */
	public short getGroupStateIn() {
		return this.groupStateIn;
	}

	/**
	 * Returns the number of all sub <code>Location</code>s.
	 * 
	 * @return
	 */
	public int getNoLocations() {
		return this.noLocations;
	}

	/**
	 * Returns the maximum fill level of this <code>LocationGroup</code>.<br>
	 * The maximum fill level defines how many <code>Location</code>s of this
	 * <code>LocationGroup</code> can be occupied with
	 * <code>TransportUnit</code>s.
	 * <p>
	 * The maximum fill level must be value between 0 and 1 and reflects a
	 * percentage value.
	 * 
	 * @return maxFillLevel.
	 */
	public float getMaxFillLevel() {
		return this.maxFillLevel;
	}

	/**
	 * Set the maximum fill level for this <code>LocationGroup</code>.
	 * <p>
	 * Pass a value between 0 and 1.<br>
	 * For example maxFillLevel = 0.85 means 85% of all sub
	 * <code>Location</code>s can be occupied.
	 * 
	 * @param maxFillLevel
	 */
	public void setMaxFillLevel(float maxFillLevel) {
		this.maxFillLevel = maxFillLevel;
	}

	/**
	 * Returns the type of this <code>LocationGroup</code>.
	 * 
	 * @return
	 */
	public String getGroupType() {
		return this.groupType;
	}

	/**
	 * Set the type of this <code>LocationGroup</code>.
	 * 
	 * @param groupType
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * Returns the last modification date of this <code>LocationGroup</code>.
	 * 
	 * @return lastUpdated.
	 */
	public Date getLastUpdated() {
		return this.lastUpdated;
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
		return Collections.unmodifiableSet(locationGroups);
	}

	/**
	 * Add a <code>LocationGroup</code> as child.
	 * 
	 * @param locationGroups
	 */
	public boolean addLocationGroup(LocationGroup locationGroup) {
		if (locationGroup == null) {
			return false;
		}
		locationGroup.setParent(this);
		return this.locationGroups.add(locationGroup);
	}

	/**
	 * Remove a <code>LocationGroup</code> as child.
	 * 
	 * @param locationGroups
	 */
	public boolean removeLocationGroup(LocationGroup locationGroup) {
		if (locationGroup == null) {
			throw new IllegalArgumentException("Child locationGroup is null!");
		}
		return this.locationGroups.remove(locationGroup);
	}

	/**
	 * Get all locations.
	 * 
	 * @return all Locations.
	 */
	public Set<Location> getLocations() {
		return Collections.unmodifiableSet(locations);
	}

	/**
	 * Add a <code>Location</code> as child.
	 * 
	 * @param location
	 */
	public boolean addLocation(Location location) {
		if (location == null) {
			return false;
		}
		location.setLocationGroup(this);
		return locations.add(location);
	}

	/**
	 * Remove a <code>Location</code> from children.
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
	 * Get the outbound status of this <code>LocationGroup</code>.
	 * 
	 * @return the groupStateOut.
	 */
	public short getGroupStateOut() {
		return groupStateOut;
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
	 * @param locationGroupCountingActive The locationGroupCountingActive to set.
	 */
	public void setLocationGroupCountingActive(boolean locationGroupCountingActive) {
		this.locationGroupCountingActive = locationGroupCountingActive;
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
