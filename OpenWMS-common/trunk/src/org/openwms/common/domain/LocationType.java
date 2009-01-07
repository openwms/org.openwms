/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Type of <code>Location</code>.<br>
 * Used to describe <code>Location</code>s with same characteristics.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "LOCATION_TYPE")
public class LocationType implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String DEF_TYPE_DESCRIPTION = "--";

    /**
     * Type of this <code>LocationType</code>.
     */
    @Id
    @Column(name = "TYPE")
    private String type;

    /**
     * Description of this <code>LocationType</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description = DEF_TYPE_DESCRIPTION;

    /**
     * Length of this <code>LocationType</code>.
     */
    @Column(name = "LENGTH")
    private int length;

    /**
     * Width of this <code>LocationType</code>.
     */
    @Column(name = "WIDTH")
    private int width;

    /**
     * Height of this <code>LocationType</code>.
     */
    @Column(name = "HEIGHT")
    private int height;

    /**
     * Version field
     */
    @Version
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * All <code>Location</code>s belonging to this type.
     */
    @OneToMany(mappedBy = "locationType")
    private Set<Location> locations;

    /* ----------------------------- methods ------------------- */
    public LocationType(String type) {
	super();
	this.type = type;
    }

    /**
     * Get the unique identifier of this <code>LocationType</code>.
     * 
     * @return - type
     */
    public String getType() {
	return this.type;
    }

    /**
     * Get the length of this <code>LocationType</code>.
     * 
     * @return - length
     */
    public int getLength() {
	return this.length;
    }

    /**
     * Set the length of this <code>LocationType</code>.
     * 
     * @param length
     */
    public void setLength(int length) {
	this.length = length;
    }

    /**
     * Get the width of this <code>LocationType</code>.
     * 
     * @return - width
     */
    public int getWidth() {
	return this.width;
    }

    /**
     * Set the width of this <code>LocationType</code>.
     * 
     * @param width
     */
    public void setWidth(int width) {
	this.width = width;
    }

    /**
     * Get the description of this <code>LocationType</code>.
     * 
     * @return - description
     */
    public String getDescription() {
	return this.description;
    }

    /**
     * Set the description of this <code>LocationType</code>.
     * 
     * @param description
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * Get the height of this <code>LocationType</code>.
     * 
     * @return - height
     */
    public int getHeight() {
	return this.height;
    }

    /**
     * Set the height of this <code>LocationType</code>.
     * 
     * @param height
     */
    public void setHeight(int height) {
	this.height = height;
    }

    /**
     * Get all <code>Location</code>s belonging to this <code>LocationType</code>.
     * 
     * @return - All <code>Location</code>s belonging to this <code>LocationType</code>
     */
    public Set<Location> getLocations() {
	return this.locations;
    }

    /**
     * Set a <code>Set</code> of <code>Location</code>s belonging to this <code>LocationType</code>.
     * 
     * @param locations
     */
    public void setLocations(Set<Location> locations) {
	this.locations = locations;
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
