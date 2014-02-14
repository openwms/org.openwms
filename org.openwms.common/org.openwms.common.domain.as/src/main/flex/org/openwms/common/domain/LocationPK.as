/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.domain {

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.LocationPK")]
    /**
     * A LocationPK, is a value type and is used as an unique natural key of
     * <code>Location</code> entities.
     * 
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.Location
     */
    public class LocationPK extends LocationPKBase {
    	
        /**
         * Creates a new LocationPK.
         *
         * @param area The area
         * @param aisle The aisle
         * @param x The x dimension
         * @param y The y dimension
         * @param z The z dimension
         */
        public function LocationPK(area:String = null, aisle:String = null, x:String = null, y:String = null, z:String = null) {
            super();
            if (area != null && aisle != null && x != null && y != null && z != null) {
	            _area = area;
	            _aisle = aisle;
	            _x = x;
	            _y = y;
	            _z = z;
            }
        }
        
        /**
         * Returns area/aisle/x/y/z as String.
         *
         * @return 'area/aisle/x/y/z'
         */
        public function toString():String {
        	return _area+"/"+_aisle+"/"+_x+"/"+_y+"/"+_z;
        }
    }
}