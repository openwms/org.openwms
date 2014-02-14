/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.domain.system.usermanagement {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.usermanagement.UserDetails$SEX")]
    public class UserDetails$SEX extends Enum {

        public static const MALE:UserDetails$SEX = new UserDetails$SEX("MALE", _);
        public static const FEMALE:UserDetails$SEX = new UserDetails$SEX("FEMALE", _);

        function UserDetails$SEX(value:String = null, restrictor:* = null) {
            super((value || MALE.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [MALE, FEMALE];
        }

        public static function valueOf(name:String):UserDetails$SEX {
            return UserDetails$SEX(MALE.constantOf(name));
        }
    }
}