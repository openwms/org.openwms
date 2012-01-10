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
package org.openwms.core.domain.system.usermanagement {

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.usermanagement.UserPassword")]
    /**
     * Encapsulates the password of an <code>User</code>.
     * <p>
     * When an <code>User</code> changes his password, the current password is stored to the
     * history list of passwords.
     * </p>
     *
     * @version $Revision$
     * @since 0.1
     * @see org.openwms.core.domain.system.usermanagement.User
     */
    public class UserPassword extends UserPasswordBase {

        /**
         * Constructor.
         *
         * @param user The owning <code>User</code>
         * @param password The password to store for the <code>User</code>
         */
        public function UserPassword(user : User=null, password : String=null) : void {
            if (user != null) {
                this._user = user;
            }
            if (password != null) {
                this._password = password;
            }
        }
    }
}