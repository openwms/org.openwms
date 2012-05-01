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
    [RemoteClass(alias="org.openwms.core.domain.system.PropertyScope")]
    /**
     * A PropertyScope defines all possible scopes of preferences.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class PropertyScope extends Enum {

        /** APPLICATION type. */
        public static const APPLICATION : PropertyScope = new PropertyScope("APPLICATION", _);
        /** MODULE type. */
        public static const MODULE : PropertyScope = new PropertyScope("MODULE", _);
        /** ROLE type. */
        public static const ROLE : PropertyScope = new PropertyScope("ROLE", _);
        /** USER type. */
        public static const USER : PropertyScope = new PropertyScope("USER", _);

        /**
         * Constructor.
         *
         * @param value
         * @param restrictor
         */
        function PropertyScope(value : String=null, restrictor : *=null) {
            super((value || APPLICATION.name), restrictor);
        }

        /**
         * Get all defined PropertyScope.
         *
         * @return an Array of PropertyScope
         */
        override protected function getConstants() : Array {
            return constants;
        }

        /**
         * Get all defined PropertyScope.
         *
         * @return an Array of PropertyScope
         */
        public static function get constants() : Array {
            return [APPLICATION, MODULE, ROLE, USER];
        }

        /**
         * Find by String.
         *
         * @param name
         * @return the PropertyScope
         */
        public static function valueOf(name : String) : PropertyScope {
            return PropertyScope(APPLICATION.constantOf(name));
        }
    }
}