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

    import org.openwms.core.domain.system.usermanagement.User;

    /**
     * An UserHelper is a utility class to centralize often used methods according to user handling.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public final class UserHelper {

        /**
         * Constructor.
         */
        public function UserHelper() {
        }

        /**
         * Check whether the User has UserDetails.
         *
         * @param userData The User to check.
         * @return <code>true</code> when the User has UserDetails, otherwise <code>false</code>.
         */
        public static function hasDetails(userData : User) : Boolean {
            if (userData != null && userData.userDetails != null) {
                return true;
            }
            return false;
        }
    }
}