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

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.values.TransportUnitState")]
    /**
     * A TransportUnitState defines possible states used for <code>TransportUnit<code>s.
     * 
     * @version $Revision$
     * @since 0.1
     */
    public class TransportUnitState extends Enum {

	    /**
	     * The <code>TransportUnit</code> is available.
	     */
        public static const AVAILABLE:TransportUnitState = new TransportUnitState("AVAILABLE", _);

	    /**
	     * The <code>TransportUnit</code> is okay.
	     */
        public static const OK:TransportUnitState = new TransportUnitState("OK", _);

	    /**
	     * The <code>TransportUnit</code> is not okay.
	     */
        public static const NOT_OK:TransportUnitState = new TransportUnitState("NOT_OK", _);

        /**
         * Constructor.
         *
         * @param value The value
         * @param restrictor The enum restrictor
         */
        function TransportUnitState(value:String = null, restrictor:* = null) {
            super((value || AVAILABLE.name), restrictor);
        }

        /**
         * Returns an array with all defined values.
         *
         * @return The array of all values of type <code>TransportUnitState</code>
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return an array with all defined values.
         *
         * @return The array of all values of type <code>TransportUnitState</code>
         */
        public static function get constants():Array {
            return [AVAILABLE, OK, NOT_OK];
        }

        /**
         * Returns the <code>TransportUnitState</code> instance by name.
         *
         * @param name The name to be resolved
         * @return The scope
         */
        public static function valueOf(name:String):TransportUnitState {
            return TransportUnitState(AVAILABLE.constantOf(name));
        }
    }
}