/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.TransportUnit$TU_STATE")]
    public class TransportUnit$TU_STATE extends Enum {

        public static const AVAILABLE:TransportUnit$TU_STATE = new TransportUnit$TU_STATE("AVAILABLE", _);
        public static const OK:TransportUnit$TU_STATE = new TransportUnit$TU_STATE("OK", _);
        public static const NOT_OK:TransportUnit$TU_STATE = new TransportUnit$TU_STATE("NOT_OK", _);

        function TransportUnit$TU_STATE(value:String = null, restrictor:* = null) {
            super((value || AVAILABLE.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [AVAILABLE, OK, NOT_OK];
        }

        public static function valueOf(name:String):TransportUnit$TU_STATE {
            return TransportUnit$TU_STATE(AVAILABLE.constantOf(name));
        }
    }
}