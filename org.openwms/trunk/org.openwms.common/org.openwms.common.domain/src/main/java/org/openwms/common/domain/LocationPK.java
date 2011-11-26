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
 * A LocationPK, is a value type and is used as an unique natural key of
 * {@link org.openwms.common.domain.Location} entities.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.domain.Location
 */
@Embeddable
public class LocationPK implements Serializable {

    private static final long serialVersionUID = 7370071817754524569L;

    /**
     * Expresses the area where the <code>Location</code> belongs to.
     */
    @Column(name = "AREA", nullable = false)
    private String area;

    /**
     * Expresses the aisle where the <code>Location</code> belongs to.
     */
    @Column(name = "AISLE", nullable = false)
    private String aisle;

    /**
     * Expresses the dimension x where this <code>Location</code> belongs to.
     */
    @Column(name = "X", nullable = false)
    private String x;

    /**
     * Expresses the dimension y where this <code>Location</code> belongs to.
     */
    @Column(name = "Y", nullable = false)
    private String y;

    /**
     * Expresses the dimension z where this <code>Location</code> belongs to.
     */
    @Column(name = "Z", nullable = false)
    private String z;

    /* ----------------------------- methods ------------------- */
    @SuppressWarnings("unused")
    private LocationPK() {
        super();
    }

    /**
     * Create a new LocationPK.
     * 
     * @param area
     *            Area where the <code>Location</code> belongs to
     * @param aisle
     *            Aisle where the <code>Location</code> belongs to
     * @param x
     *            Dimension x where the <code>Location</code> belongs to
     * @param y
     *            Dimension y where the <code>Location</code> belongs to
     * @param z
     *            Dimension z where the <code>Location</code> belongs to
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
     * @return The area
     */
    public String getArea() {
        return this.area;
    }

    /**
     * Get the aisle region.
     * 
     * @return The aisle
     */
    public String getAisle() {
        return this.aisle;
    }

    /**
     * Get the x dimension.
     * 
     * @return The x dimension
     */
    public String getX() {
        return this.x;
    }

    /**
     * Get the y dimension.
     * 
     * @return The y dimension
     */
    public String getY() {
        return this.y;
    }

    /**
     * Get the z dimension.
     * 
     * @return The z dimension
     */
    public String getZ() {
        return this.z;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#equals(Object)
     */
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

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.y.hashCode() ^ this.x.hashCode() ^ this.area.hashCode() ^ this.z.hashCode() ^ this.aisle.hashCode();
    }

    /**
     * Return a String like {AREA/AISLE/X/Y/Z}.
     * 
     * @see java.lang.Object#toString()
     * @return String
     */
    @Override
    public String toString() {
        return "{" + this.area + "/" + this.aisle + "/" + this.x + "/" + this.y + "/" + this.z + "}";
    }

}