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
package org.openwms.core.domain.values {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias = "org.openwms.core.domain.values.PieceUnit")]
    public class PieceUnit extends Enum {

        public static const PC : PieceUnit = new PieceUnit("PC", _);

        public static const DOZ : PieceUnit = new PieceUnit("DOZ", _);

        function PieceUnit(value : String=null, restrictor : *=null) {
            super((value || PC.name), restrictor);
        }

        override protected function getConstants() : Array {
            return constants;
        }

        public static function get constants() : Array {
            return [PC, DOZ];
        }

        public static function valueOf(name : String) : PieceUnit {
            return PieceUnit(PC.constantOf(name));
        }
    }
}
