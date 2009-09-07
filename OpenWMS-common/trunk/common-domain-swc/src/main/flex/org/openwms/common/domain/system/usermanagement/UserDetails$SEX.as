/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.system.usermanagement.UserDetails$SEX")]
    public class UserDetails$SEX extends Enum {

        public static const MAN:UserDetails$SEX = new UserDetails$SEX("MAN", _);
        public static const WOMAN:UserDetails$SEX = new UserDetails$SEX("WOMAN", _);

        function UserDetails$SEX(value:String = null, restrictor:* = null) {
            super((value || MAN.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [MAN, WOMAN];
        }

        public static function valueOf(name:String):UserDetails$SEX {
            return UserDetails$SEX(MAN.constantOf(name));
        }
    }
}