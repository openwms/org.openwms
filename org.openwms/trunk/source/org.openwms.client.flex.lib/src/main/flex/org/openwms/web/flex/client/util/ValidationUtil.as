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
package org.openwms.web.flex.client.util {
    import mx.controls.TextInput;
    import mx.events.ValidationResultEvent;
    import mx.formatters.Formatter;
    import mx.validators.Validator;

    /**
     * A ValidationUtil.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public final class ValidationUtil {
        public function ValidationUtil() {
        }

        public static function validateField(t:TextInput, val:Validator):Boolean {
            val.source=t
            return val.validate().type == ValidationResultEvent.VALID;
        }


        public static function validateAndFormat(id:Object, val:Validator, fmt:Formatter=null):Boolean {
            val.source=id;
            var res:ValidationResultEvent=val.validate();
            if (res.type == ValidationResultEvent.VALID) {
                if (fmt != null) {
                    id.text=fmt.format(id.text);
                }
                return true;
            } else {
                id.text="";
                return false;
            }
        }
    }
}