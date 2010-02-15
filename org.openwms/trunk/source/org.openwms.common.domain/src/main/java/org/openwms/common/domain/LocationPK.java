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

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * This value type is used as unique natural key of the
 * {@link org.openwms.common.domain.Location} Entity.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see {@link org.openwms.common.domain.Location}
 */
@Embeddable
public class LocationPK implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Expresses an area.
     */
    @Column(name = "AREA", nullable = false)
    private String area;

    /**
     * Expresses an aisle.
     */
    @Column(name = "AISLE", nullable = false)
    private String aisle;

    /**
     * Expresses an dimension x.
     */
    @Column(name = "X", nullable = false)
    private String x;

    /**
     * Expresses an dimension y.
     */
    @Column(name = "Y", nullable = false)
    private String y;

    /**
     * Expresses an dimension z.
     */
    @Column(name = "Z", nullable = false)
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

    /**
     * Return a String like {AREA/AISLE/X/Y/Z}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{" + this.area + "/" + this.aisle + "/" + this.x + "/" + this.y + "/" + this.z + "}";
    }

}