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
    [RemoteClass(alias = "org.openwms.common.domain.values.Barcode$BARCODE_ALIGN")]
    public class Barcode$BARCODE_ALIGN extends Enum {

        public static const LEFT : Barcode$BARCODE_ALIGN = new Barcode$BARCODE_ALIGN("LEFT", _);

        public static const RIGHT : Barcode$BARCODE_ALIGN = new Barcode$BARCODE_ALIGN("RIGHT", _);

        function Barcode$BARCODE_ALIGN(value : String=null, restrictor : *=null) {
            super((value || LEFT.name), restrictor);
        }

        override protected function getConstants() : Array {
            return constants;
        }

        public static function get constants() : Array {
            return [LEFT, RIGHT];
        }

        public static function valueOf(name : String) : Barcode$BARCODE_ALIGN {
            return Barcode$BARCODE_ALIGN(LEFT.constantOf(name));
        }
    }
}
