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

    import mx.controls.TextInput;
    import mx.events.ValidationResultEvent;
    import mx.formatters.Formatter;
    import mx.validators.Validator;

    /**
     * A ValidationUtil.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public final class ValidationUtil {

        /**
         * Constructor.
         */
        public function ValidationUtil() {
        }

        /**
         * Use a validator <code>val</code> to validate <code>obj</code>.
         * The source of the validator <code>val</code> is set to <code>obj</code>.
         * A given formatter <code>fmt</code> is used to format the result afterwards.
         * <code>fmt</code> can also be <code>null</code>, then no formatting is performed.
         * Provide a <code>property</code> name to format and validate that <code>propery</code>.
         *
         * If no <code>property</code> is given or <code>null</code>, the <code>text</code> property
         * of <code>obj</code> is formatted and it is expected that <code>val</code> already has set a
         * property to validate.
         *
         * @param obj The component to be validated.
         * @param val The validator to use for validation.
         * @param fmt The formatter to use, can be <code>null</code>.
         * @param property The property name of the component <code>obj</code> that shall be formatted, can be <code>null</code>.
         * @return <code>true</code> if valid, otherwise <code>false</code>.
         */
        public static function validateAndFormat(obj : Object, val : Validator, fmt : Formatter=null, property : Object=null) : Boolean {
            val.source = obj;
            if (property == null) {
                val.property = "text";
            }
            if (val.validate().type == ValidationResultEvent.VALID) {
                if (fmt != null) {
                    if (property == null) {
                        obj.text = fmt.format(obj.text);
                    } else {
                        obj[property] = fmt.format(obj[property]);
                    }
                }
                return true;
            }
            return false;
        }
    }
}