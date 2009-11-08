/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

/**
 * A LocationGroup.
 * <p>
 * Used to group <code>Location</code>s with same characteristics.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "LOCATION_GROUP")
@NamedQueries( { @NamedQuery(name = "LocationGroup.findAll", query = "select lg from LocationGroup lg"),
		@NamedQuery(name = "LocationGroup.findByName", query = "select lg from LocationGroup lg where lg.name = ?1") })
public class LocationGroup implements Serializable {

	private static final long serialVersionUID = 1L;

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
	 * Unique identifier of a <code>LocationGroup</code>.
	 */
	@Column(name = "NAME", unique = true)
	private String name;

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
	 * Is this <code>LocationGroup</code> be integrated in the calculation of <code>TransportUnit</code>s.
	 * <p>
	 * true : Location is been included in calculation of <code>TransportUnit</code>s.<br>
	 * false: Location is not been included in calculation of <code>TransportUnit</code>s.
	 */
	@Column(name = "LOCATION_GROUP_COUNTING_ACTIVE")
	private boolean locationGroupCountingActive = true;

	/**
	 * Number of <code>Location</code>s belonging to this <code>LocationGroup</code>.
	 */
	@Column(name = "NO_LOCATIONS")
	private int noLocations = 0;

	/**
	 * Inbound status of this <code>LocationGroup</code>.
	 */
	@Column(name = "GROUP_STATE_IN")
	private STATE groupStateIn = STATE.AVAILABLE;

	/**
	 * Outbound status of this <code>LocationGroup</code>.
	 */
	@Column(name = "GROUP_STATE_OUT")
	private STATE groupStateOut = STATE.AVAILABLE;

	/**
	 * Maximum fill level for this <code>LocationGroup</code>.
	 */
	@Column(name = "MAX_FILL_LEVEL")
	private float maxFillLevel = 0;

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
	@ManyToOne
	@JoinColumn(name = "PARENT")
	private LocationGroup parent;

	/**
	 * Child <code>LocationGroup</code>s.
	 */
	@OneToMany(mappedBy = "parent", cascade = { CascadeType.ALL })
	private Set<LocationGroup> locationGroups = new HashSet<LocationGroup>();

	/**
	 * Child <code>Location</code>s.
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
	 * Create a new <code>LocationGroup</code> with an unique name.
	 */
	public LocationGroup(String name) {
		this.name = name;
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

	/**
	 * Get the name of this <code>LocationGroup</code>.
	 * 
	 * @return the name.
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
	 * Returns the inbound state of this <code>LocationGroup</code>.
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
	 * Get the outbound state of this <code>LocationGroup</code>.
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
	 * Returns the number of all sub <code>Location</code>s.
	 * 
	 * @return
	 */
	public int getNoLocations() {
		return this.noLocations;
	}

	/**
	 * Returns the maximum fill level of this <code>LocationGroup</code>.<br>
	 * The maximum fill level defines how many <code>Location</code>s of this <code>LocationGroup</code> can be
	 * occupied with <code>TransportUnit</code>s.
	 * <p>
	 * The maximum fill level must be value between 0 and 1 and reflects a percentage value.
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
	 * For example maxFillLevel = 0.85 means 85% of all sub <code>Location</code>s can be occupied.
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
	 * Set the date of the last modification of this <code>LocationGroup</code>.
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
	 * Add a <code>LocationGroup</code> as child.
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
	 * Remove a <code>LocationGroup</code> as child.
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
			throw new IllegalArgumentException("Location to add is null");
		}
		if (location.getLocationGroup() != null) {
			location.getLocationGroup().removeLocation(location);
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
	 * JPA optimistic locking: Returns version field.
	 * 
	 * @return
	 */
	public long getVersion() {
		return this.version;
	}

	@Override
	public String toString() {
		return getName();
	}

}
