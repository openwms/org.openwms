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

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.LocationType")]
    /**
     * A LocationType defines a type for <code>Location</code>s.
     * <p>
     * Type of a <code>Location</code>.<br>
     * Used to group <code>Location</code>s with same characteristics.
     * </p>
     *
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.Location
     */
    public class LocationType extends LocationTypeBase {

        /**
         * Set the type of the <code>LocationType</code>.
         *
         * @param type The type to set
         */
        public function set type(value : String) : void {
            _type = value;
        }

    }
}