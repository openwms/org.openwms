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

    import org.openwms.common.domain.values.LocationGroupState;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.LocationGroup")]
    /**
     * A LocationGroup, used to group Locations logically.
     * <p>
     * Used to group Locations with same characteristics.
     * </p>
     *
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.Location
     */
    public class LocationGroup extends LocationGroupBase {

        /**
         * Returns the name.
         *
         * @return The name
         */
        public function toString() : String {
            return _name;
        }

        /**
         * Set the groupStateIn.
         *
         * @param the new state
         */
        public function set groupStateIn(value : LocationGroupState) : void {
            _groupStateIn = value;
        }

        /**
         * Set the groupStateOut.
         *
         * @param the new state
         */
        public function set groupStateOut(value : LocationGroupState) : void {
            _groupStateOut = value;
        }
    }
}