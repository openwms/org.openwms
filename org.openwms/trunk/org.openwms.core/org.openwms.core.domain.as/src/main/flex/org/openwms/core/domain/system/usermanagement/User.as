/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part -of openwms.org.
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
package org.openwms.core.domain.system.usermanagement {

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.usermanagement.User")]
    /**
     * An User represents a human user of the system.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class User extends UserBase {

        /**
         * Constructor.
         * Preset fields. Per default a user is enabled to log in, is not locked and is no external user.
         *
         * @param username Optional username to set
         */
        public function User(username : String=null) {
            if (null != username) {
                this._username = username;
            }
            this._enabled = true;
            this._locked = false;
            this._extern = false;
            this._userDetails = new UserDetails();
        }

        public function resetUsername() : void {
            _username = "";
        }
    }
}

