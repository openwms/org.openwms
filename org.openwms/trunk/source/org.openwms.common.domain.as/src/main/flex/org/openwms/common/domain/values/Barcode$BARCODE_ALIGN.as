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

    /**
     * A Barcode$BARCODE_ALIGN defines whether the <code>Barcode</code> is applied
     * <code>LEFT</code> or <code>RIGHT</code>.
     * <p>
     * Only be used when padding is activated.
     * </p>
     * 
     * @version $Revision$
     * @since 0.1
     */
    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.values.Barcode$BARCODE_ALIGN")]
    public class Barcode$BARCODE_ALIGN extends Enum {

        /**
         * Barcode is left aligned.
         */
        public static const LEFT:Barcode$BARCODE_ALIGN = new Barcode$BARCODE_ALIGN("LEFT", _);
        /**
         * Barcode is right aligned.
         */
        public static const RIGHT:Barcode$BARCODE_ALIGN = new Barcode$BARCODE_ALIGN("RIGHT", _);

        /**
         * Constructor.
         *
         * @param value The value
         * @param restrictor The enum restrictor
         */
        function Barcode$BARCODE_ALIGN(value:String = null, restrictor:* = null) {
            super((value || LEFT.name), restrictor);
        }

        /**
         * Returns an array with all defined values.
         *
         * @return The array of all values of type <code>Barcode$BARCODE_ALIGN</code>
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return an array with all defined values.
         *
         * @return The array of all values of type <code>Barcode$BARCODE_ALIGN</code>
         */
        public static function get constants():Array {
            return [LEFT, RIGHT];
        }

        /**
         * Returns the <code>Barcode$BARCODE_ALIGN</code> instance by name.
         *
         * @param name The name to be resolved
         * @return The scope
         */
        public static function valueOf(name:String):Barcode$BARCODE_ALIGN {
            return Barcode$BARCODE_ALIGN(LEFT.constantOf(name));
        }
    }
}