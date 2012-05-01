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
package org.openwms.core.domain.system {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.PreferenceType")]
    /**
     * A PreferenceType defines all possible types of preferences.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class PreferenceType extends Enum {

        /** Float type. */
        public static const FLOAT : PreferenceType = new PreferenceType("FLOAT", _);
        /** String type. */
        public static const STRING : PreferenceType = new PreferenceType("STRING", _);
        /** Integer type. */
        public static const INT : PreferenceType = new PreferenceType("INT", _);
        /** Object type. */
        public static const OBJECT : PreferenceType = new PreferenceType("OBJECT", _);

        /**
         * Constructor.
         *
         * @param value
         * @param restrictor
         */
        function PreferenceType(value : String=null, restrictor : *=null) {
            super((value || FLOAT.name), restrictor);
        }

        /**
         * Get all defined PreferenceType.
         *
         * @return an Array of PreferenceType
         */
        override protected function getConstants() : Array {
            return constants;
        }

        /**
         * Get all defined PreferenceType.
         *
         * @return an Array of PreferenceType
         */
        public static function get constants() : Array {
            return [FLOAT, STRING, INT, OBJECT];
        }

        /**
         * Find by String.
         *
         * @param name
         * @return the PreferenceType
         */
        public static function valueOf(name : String) : PreferenceType {
            return PreferenceType(FLOAT.constantOf(name));
        }
    }
}