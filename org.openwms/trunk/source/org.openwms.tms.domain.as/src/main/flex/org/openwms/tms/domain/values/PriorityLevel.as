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
package org.openwms.tms.domain.values {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.tms.domain.values.PriorityLevel")]
    /**
     * A PriorityLevel is used to prioritize the execution of <code>TransportOrder<code>s.
     * 
     * @version $Revision$
     * @since 0.1
     */
    public class PriorityLevel extends Enum {

        /**
         * Lowest priority.
         */
        public static const LOWEST:PriorityLevel = new PriorityLevel("LOWEST", _);
        /**
         * Low priority.
         */
        public static const LOW:PriorityLevel = new PriorityLevel("LOW", _);
        /**
         * Standard priority.
         */
        public static const NORMAL:PriorityLevel = new PriorityLevel("NORMAL", _);
        /**
         * High priority.
         */
        public static const HIGH:PriorityLevel = new PriorityLevel("HIGH", _);
        /**
         * Highest priority.
         */
        public static const HIGHEST:PriorityLevel = new PriorityLevel("HIGHEST", _);

        /**
         * Internal constructor.
         * 
         * @param value Enum value
         * @param restrictor see Enum class
         */
        function PriorityLevel(value:String = null, restrictor:* = null) {
            super((value || LOWEST.name), restrictor);
        }

        /**
         * Return all available priorities in an Array.
         * 
         * @return All priorities in an Array
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return all priorities in an Array.
         * 
         * @return All priorities in an Array
         */
        public static function get constants():Array {
            return [LOWEST, LOW, NORMAL, HIGH, HIGHEST];
        }

        /**
         * Resolve a priority by name.
         * 
         * @param name The name as String
         * @return The enum constant.
         */
        public static function valueOf(name:String):PriorityLevel {
            return PriorityLevel(LOWEST.constantOf(name));
        }
    }
}