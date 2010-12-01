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
package org.openwms.common.domain.values {

    import org.granite.util.Enum;

	/**
	 * A LocationGroupState defines possible states used for <code>LocationGroup<code>s.
	 * 
	 * @version $Revision$
	 * @since 0.1
	 * @see org.openwms.common.domain.LocationGroup
	 */
    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.values.LocationGroupState")]
    public class LocationGroupState extends Enum {

	    /**
	     * Available.
	     */
        public static const AVAILABLE:LocationGroupState = new LocationGroupState("AVAILABLE", _);
        
        /**
         * Not available.
         */
        public static const NOT_AVAILABLE:LocationGroupState = new LocationGroupState("NOT_AVAILABLE", _);

        /**
         * Constructor.
         *
         * @param value The value
         * @param restrictor The enum restrictor
         */
        function LocationGroupState(value:String = null, restrictor:* = null) {
            super((value || AVAILABLE.name), restrictor);
        }

        /**
         * Returns an array with all defined values.
         *
         * @return The array of all values of type <code>LocationGroupState</code>
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return an array with all defined values.
         *
         * @return The array of all values of type <code>LocationGroupState</code>
         */
        public static function get constants():Array {
            return [AVAILABLE, NOT_AVAILABLE];
        }

        /**
         * Returns the <code>LocationGroupState</code> instance by name.
         *
         * @param name The name to be resolved
         * @return The scope
         */
        public static function valueOf(name:String):LocationGroupState {
            return LocationGroupState(AVAILABLE.constantOf(name));
        }
    }
}