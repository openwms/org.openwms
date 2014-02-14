/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
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
package org.openwms.core.domain.system.usermanagement {
    import mx.utils.UIDUtil;

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.usermanagement.Grant")]
    /**
     * A Grant is a security object.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class Grant extends GrantBase {

        /**
         * Constructor.
         *
         * @param name Name of the Grant
         * @param description Description of the Grant
         */
        public function Grant(name : String=null, description : String=null) {
            super.uid = UIDUtil.createUID();
            if (name != null) {
                this._name = name;
            }
            if (description != null) {
                this._description = description;
            }
        }

        /**
         * Format the Grant as String: description (name).
         *
         * @param item The Grant object
         * @return The formatted Grant
         */
        public static function formatWithDescriptionName(item : Grant) : String {
            return item.description + " (" + item.name + ")";
        }
    }
}