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
package org.openwms.common.domain.units {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.units.PieceUnit")]
    /**
     * A PieceUnit is the type of a <code>Piece</code>.
     *
     * @version $Revision: 1722 $
     * @since 0.1
     * @see org.openwms.common.domain.units.Piece
     */
    public class PieceUnit extends Enum {

        /** PC (piece). */
        public static const PC : PieceUnit = new PieceUnit("PC", _);
        /** DOZ (dozen). */
        public static const DOZ : PieceUnit = new PieceUnit("DOZ", _);

        /**
         * Constructor.
         *
         * @param value
         * @param restrictor
         */
        function PieceUnit(value : String=null, restrictor : *=null) {
            super((value || PC.name), restrictor);
        }

        /**
         * Get all defined PieceUnit.
         *
         * @return an Array of PieceUnit
         */
        override protected function getConstants() : Array {
            return constants;
        }

        /**
         * Get all defined PieceUnit.
         *
         * @return an Array of PieceUnit
         */
        public static function get constants() : Array {
            return [PC, DOZ];
        }

        /**
         * Find by String.
         *
         * @param name
         * @return the PieceUnit
         */
        public static function valueOf(name : String) : PieceUnit {
            return PieceUnit(PC.constantOf(name));
        }
    }
}