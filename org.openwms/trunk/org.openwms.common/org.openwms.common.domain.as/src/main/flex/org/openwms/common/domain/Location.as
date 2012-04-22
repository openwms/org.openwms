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
package org.openwms.common.domain {

    import mx.collections.ListCollectionView;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.Location")]
    /**
     * A Location, defines a place within a warehouse.
     * <p>
     * Could be something like a storage location in the stock as well as a location
     * on a conveyer. Also virtual or error locations can be represented with the
     * <code>Location</code> entity.
     * </p>
     * Multiple <code>Location</code>s can be grouped together to a <code>LocationGroup</code>.
     *
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.LocationGroup
     */
    public class Location extends LocationBase {

        /**
         * Creates a new Location with a unique LocationPK.
         *
         * @param pk The locationPk to set
         */
        public function Location(pk : LocationPK=null) {
            super();
            if (pk != null) {
                _locationId = pk;
            }
        }

        /**
         * For compliance with Flex only.
         *
         * @param value The locationPk to set
         */
        public function set locationId(value : LocationPK) : void {
            // Not allowed
        }


        /**
         * Returns the locationId in the format area/aisle/x/y/z.
         *
         * @return area/aisle/x/y/z
         */
        public function toString() : String {
            return _locationId.area + "/" + _locationId.aisle + "/" + _locationId.x + "/" + _locationId.y + "/" + _locationId.z;
        }

        /**
         * Set the collection of messages.
         *
         * @param value The list of messages
         */
        public function set messages(value : ListCollectionView) : void {
            _messages = value;
        }
    }
}