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
package org.openwms.common.domain.system.usermanagement {

    import org.granite.util.Enum;

    /**
     * The <code>User</code>s sex.
     * 
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.common.domain.system.usermanagement.User
     */
    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.system.usermanagement.UserDetails$SEX")]
    public class UserDetails$SEX extends Enum {

        /**
         * Male sex.
         */
        public static const MALE:UserDetails$SEX = new UserDetails$SEX("MALE", _);
        /**
         * Female sex.
         */
        public static const FEMALE:UserDetails$SEX = new UserDetails$SEX("FEMALE", _);

        /**
         * Constructor.
         *
         * @param value The value
         * @param restrictor The enum restrictor
         */
        function UserDetails$SEX(value:String = null, restrictor:* = null) {
            super((value || MALE.name), restrictor);
        }

        /**
         * Returns an array with all defined values.
         *
         * @return The array of all values of type <code>UserDetails$SEX</code>
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return an array with all defined values.
         *
         * @return The array of all values of type <code>UserDetails$SEX</code>
         */
        public static function get constants():Array {
            return [MALE, FEMALE];
        }

        /**
         * Returns the <code>UserDetails$SEX</code> instance by name.
         *
         * @param name The name to be resolved
         * @return The scope
         */
        public static function valueOf(name:String):UserDetails$SEX {
            return UserDetails$SEX(MALE.constantOf(name));
        }
    }
}