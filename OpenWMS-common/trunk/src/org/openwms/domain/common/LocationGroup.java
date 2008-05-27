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
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

@Entity
@Table(name = "LOCATION_GROUP", uniqueConstraints = @UniqueConstraint(columnNames = ("GROUP_ID")))
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
	private int maxFillLevel;

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
	private long parent;

	/**
	 * Child <code>LocationGroup</code>s.
	 */
	@OneToMany(mappedBy = "parent")
	private Set<LocationGroup> locationGroups;

	/**
	 * Child <code>Location</code>s.
	 */
	@OneToMany(mappedBy = "locationGroup")
	private Set<Location> locations;

	/* ----------------------------- methods ------------------- */
	public LocationGroup(String groupId) {
		super();
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

	public int getNoFreeLocations() {
		return this.noFreeLocations;
	}

	public void setNoFreeLocations(int noFreeLocations) {
		this.noFreeLocations = noFreeLocations;
	}

	public short getGroupStateIn() {
		return this.groupStateIn;
	}

	public void setGroupStateIn(short groupStateIn) {
		this.groupStateIn = groupStateIn;
	}

	public int getNoLocations() {
		return this.noLocations;
	}

	public void setNoLocations(int noLocations) {
		this.noLocations = noLocations;
	}

	public int getMaxFillLevel() {
		return this.maxFillLevel;
	}

	public void setMaxFillLevel(int maxFillLevel) {
		this.maxFillLevel = maxFillLevel;
	}

	public String getGroupType() {
		return this.groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public Date getLastUpdated() {
		return this.lastUpdated;
	}

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
	public long getParent() {
		return this.parent;
	}

	/**
	 * Set parent LocationGroup.
	 * 
	 * @param parent
	 */
	public void setParent(long parent) {
		this.parent = parent;
	}

	/**
	 * Get all client LocationGroups.
	 * 
	 * @return client LocationGroups.
	 */
	public Set<LocationGroup> getLocationGroups() {
		return this.locationGroups;
	}

	/**
	 * Set all child LocationGroups.
	 * 
	 * @param locationGroups
	 */
	public void setLocationGroups(Set<LocationGroup> locationGroups) {
		this.locationGroups = locationGroups;
	}

	/**
	 * Get all locations.
	 * 
	 * @return all Locations.
	 */
	public Set<Location> getLocations() {
		return this.locations;
	}

	/**
	 * Set all locations.
	 * 
	 * @param locations
	 */
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}

	/**
	 * Get the groupStateOut.
	 * 
	 * @return the groupStateOut.
	 */
	public short getGroupStateOut() {
		return groupStateOut;
	}

	/**
	 * Set the groupStateOut.
	 * 
	 * @param groupStateOut
	 *            The groupStateOut to set.
	 */
	public void setGroupStateOut(short groupStateOut) {
		this.groupStateOut = groupStateOut;
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
	 * JPA optimistic locking: Returns version field.
	 * 
	 * @return
	 */
	public long getVersion() {
		return this.version;
	}
}
