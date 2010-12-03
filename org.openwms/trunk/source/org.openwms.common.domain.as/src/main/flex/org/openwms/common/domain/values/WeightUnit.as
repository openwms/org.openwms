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
    [RemoteClass(alias="org.openwms.common.domain.values.WeightUnit")]
	/**
	 * A WeightUnit is a concrete set of all possible weights.
	 * <p>
	 * In SI format.
	 * </p>
	 * 
	 * @version $Revision$
	 * @since 0.1
	 */
    public class WeightUnit extends Enum {

	    /**
	     * Milligram.
	     */
        public static const MG:WeightUnit = new WeightUnit("MG", _);

        /**
         * Gram.
         */
        public static const G:WeightUnit = new WeightUnit("G", _);

        /**
         * Kilogram.
         */
        public static const KG:WeightUnit = new WeightUnit("KG", _);

        /**
         * Tons.
         */
        public static const T:WeightUnit = new WeightUnit("T", _);

        /**
         * Constructor.
         *
         * @param value The value
         * @param restrictor The enum restrictor
         */
        function WeightUnit(value:String = null, restrictor:* = null) {
            super((value || MG.name), restrictor);
        }

        /**
         * Returns an array with all defined values.
         *
         * @return The array of all values of type <code>WeightUnit</code>
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return an array with all defined values.
         *
         * @return The array of all values of type <code>WeightUnit</code>
         */
        public static function get constants():Array {
            return [MG, G, KG, T];
        }

        /**
         * Returns the <code>WeightUnit</code> instance by name.
         *
         * @param name The name to be resolved
         * @return The scope
         */
        public static function valueOf(name:String):WeightUnit {
            return WeightUnit(MG.constantOf(name));
        }
    }
}