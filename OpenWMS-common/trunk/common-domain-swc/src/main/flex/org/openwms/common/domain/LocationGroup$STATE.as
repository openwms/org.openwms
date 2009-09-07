/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.LocationGroup$STATE")]
    public class LocationGroup$STATE extends Enum {

        public static const AVAILABLE:LocationGroup$STATE = new LocationGroup$STATE("AVAILABLE", _);
        public static const NOT_AVAILABLE:LocationGroup$STATE = new LocationGroup$STATE("NOT_AVAILABLE", _);

        function LocationGroup$STATE(value:String = null, restrictor:* = null) {
            super((value || AVAILABLE.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [AVAILABLE, NOT_AVAILABLE];
        }

        public static function valueOf(name:String):LocationGroup$STATE {
            return LocationGroup$STATE(AVAILABLE.constantOf(name));
        }
    }
}