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
package org.openwms.common.domain.system {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.system.PropertyScope")]
    /**
     * A PropertyScope defines the used scope of preference settings.
     * 
     * @version $Revision$
     * @since 0.1
     */
    public class PropertyScope extends Enum {

        /**
         * Application wide.
         */
        public static const APPLICATION:PropertyScope = new PropertyScope("APPLICATION", _);
        /**
         * <code>Module</code> wide.
         */
        public static const MODULE:PropertyScope = new PropertyScope("MODULE", _);
        /**
         * Valid for a <code>Role</code>.
         */
        public static const ROLE:PropertyScope = new PropertyScope("ROLE", _);
        /**
         * Valid for a <code>User</code>.
         */
        public static const USER:PropertyScope = new PropertyScope("USER", _);

        /**
         * Constructor.
         *
         * @param value The value
         * @param restrictor The enum restrictor
         */
        function PropertyScope(value:String = null, restrictor:* = null) {
            super((value || APPLICATION.name), restrictor);
        }

        /**
         * Returns an array with all defined values.
         *
         * @return The array of all values of type <code>PropertyScope</code>
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return an array with all defined values.
         *
         * @return The array of all values of type <code>PropertyScope</code>
         */
        public static function get constants():Array {
            return [APPLICATION, MODULE, ROLE, USER];
        }

        /**
         * Returns the <code>PropertyScope</code> instance by name.
         *
         * @param name The name to be resolved
         * @return The scope
         */
        public static function valueOf(name:String):PropertyScope {
            return PropertyScope(APPLICATION.constantOf(name));
        }
    }
}