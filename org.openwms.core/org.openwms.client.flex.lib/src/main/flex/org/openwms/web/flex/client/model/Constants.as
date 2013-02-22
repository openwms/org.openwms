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
package org.openwms.web.flex.client.model {

    [Bindable]
    /**
     * A Constants class to store some global definitions.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public final class Constants {

        /**
         * Constructor.
         */
        public function Constants() {
        }

        /**
         * Name of the ModuleManagement backend service.
         */
        public static const MODULEMGMT_SERVICE : String = "moduleManagementService";

        /**
         * Name of the UserManagement backend service.
         */
        public static const USERMGMT_SERVICE : String = "userService";

        /**
         * Name of the Preference used as default language.
         */
        public static const DEFAULT_LANG : String = "defaultLanguage";

        /**
         * A String used to represent not assigned (or null) Number fields.
         */
        public static var DEFAULT_NULL_NUMBER : String = "DEFAULT_NULL_NUMBER";
    }
}