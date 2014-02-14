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
package org.openwms.web.flex.client.util {

    import org.openwms.web.flex.client.model.Constants;

    [ResourceBundle("corLibMain")]
    /**
     * A NumberUtil encapsulates AS functions to validate and format Numbers.
     *
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
     * @version $Revision: 1507 $
     * @since 0.1
     */
    public final class NumberUtil {

        /**
         * Constructor.
         */
        public function NumberUtil() {
        }

        /**
         * Check whether value is not assigned.
         *
         * @param value The Number to check
         * @return When value is not assigned return Constants.DEFAULT_NULL_NUMBER,
         *       otherwise return the value as String.
         */
        public static function formatFloatValue(value : Number) : String {
            if (isNaN(value)) {
                return I18nUtil.trans(I18nUtil.COR_LIB_MAIN, Constants.DEFAULT_NULL_NUMBER);
            }
            return value.toString();
        }

        /**
         * Return value as int value. When value is null or empty, defaultValue is returned.
         *
         * @param value The value to convert
         * @param defaultValue When value is null or empty
         * @return The converted value
         */
        public static function strToInt(value : String, defaultValue : int) : int {
            if (value == null || value.length == 0) {
                return defaultValue;
            }
            return value as int;
        }

        /**
         * Return value as Number object. When value is null or empty, defaultValue is returned.
         *
         * @param value The value to convert
         * @param defaultValue When value is null or empty
         * @return The converted value
         */
        public static function strToNumber(value : String, defaultValue : Number) : Number {
            if (value == null || value.length == 0) {
                return defaultValue;
            }
            return value as Number;
        }
    }
}