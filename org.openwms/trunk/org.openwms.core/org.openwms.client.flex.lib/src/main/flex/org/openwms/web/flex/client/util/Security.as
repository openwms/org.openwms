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

    import flash.utils.Dictionary;
    import flash.utils.Proxy;
    import flash.utils.flash_proxy;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.core.domain.system.usermanagement.Grant;

    [Name]
    [Bindable]
    /**
     * A Security.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1301 $
     * @since 0.1
     */
    public class Security {

        [Inject]
        /**
         * Injected Model.
         */
        private static var modelLocator : ModelLocator;

        /**
         * Constructor.
         */
        public function Security() : void {
            super();
        }

        /**
         *
         */
        public static function hasPermissions(grantName : String) : Boolean {
            if (null != modelLocator.securityObjects[grantName]) {
                // check logged in user and his roles for permission
                var grant : Grant = modelLocator.securityObjects[grantName];
                return modelLocator.userLoggedin.roles.grants.contains(grant);
            }
            return false;
        }
    }
}

