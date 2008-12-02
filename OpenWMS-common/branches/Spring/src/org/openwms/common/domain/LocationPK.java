/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This value type is the primary key of the <code>Location</code> entity.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Embeddable
public class LocationPK implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Expresses an area.
     */
    @Column(name = "AREA")
    private String area;

    /**
     * Expresses an aisle.
     */
    @Column(name = "AISLE")
    private String aisle;

    /**
     * Expresses an dimension x.
     */
    @Column(name = "X")
    private String x;

    /**
     * Expresses an dimension y.
     */
    @Column(name = "Y")
    private String y;

    /**
     * Expresses an dimension z.
     */
    @Column(name = "Z")
    private String z;

    /* ----------------------------- methods ------------------- */
    @SuppressWarnings("unused")
    private LocationPK() {}

    /**
     * Create a new LocationPK.
     * 
     * @param area
     * @param aisle
     * @param x
     * @param y
     * @param z
     */
    public LocationPK(String area, String aisle, String x, String y, String z) {
	this.area = area;
	this.aisle = aisle;
	this.x = x;
	this.y = y;
	this.z = z;
    }

    /**
     * Get the area region.
     * 
     * @return - area
     */
    public String getArea() {
	return this.area;
    }

    /**
     * Get the aisle region.
     * 
     * @return - aisle
     */
    public String getAisle() {
	return this.aisle;
    }

    /**
     * Get the x dimension.
     * 
     * @return - x
     */
    public String getX() {
	return this.x;
    }

    /**
     * Get the y dimension.
     * 
     * @return - y
     */
    public String getY() {
	return this.y;
    }

    /**
     * Get the z dimension.
     * 
     * @return - z
     */
    public String getZ() {
	return this.z;
    }

    @Override
    public boolean equals(Object o) {
	if (o == this) {
	    return true;
	}
	if (!(o instanceof LocationPK)) {
	    return false;
	}
	LocationPK other = (LocationPK) o;
	return this.y.equals(other.y) && this.x.equals(other.x) && this.area.equals(other.area)
		&& this.z.equals(other.z) && this.aisle.equals(other.aisle);
    }

    @Override
    public int hashCode() {
	return this.y.hashCode() ^ this.x.hashCode() ^ this.area.hashCode() ^ this.z.hashCode() ^ this.aisle.hashCode();
    }

    @Override
    public String toString() {
	return "{" + this.area + "/" + this.aisle + "/" + this.x + "/" + this.y + "/" + this.z + "}";
    }

}
