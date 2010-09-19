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
package org.openwms.web.flex.client.business {

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;

    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.system.usermanagement.User;
    import org.openwms.common.domain.system.usermanagement.UserPassword;
    import org.openwms.web.flex.client.event.UserEvent;
    import org.openwms.web.flex.client.model.ModelLocator;

    /**
     * An UserDelegate. Handles all interaction with the server-side userService.
     * Provides simple CRUD methods.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Name("userController")]
    [ManagedEvent(name="LOAD_ALL_USERS")]
    [Bindable]
    public class UserDelegate {
        [In]
        public var tideContext : Context;
        [In]
        public var modelLocator : ModelLocator;

        public function UserDelegate() : void {
        }

        /**
         * Fetch a list of all users from the service.
         */
        [Observer("LOAD_ALL_USERS")]
        public function getUsers() : void {
            tideContext.userService.findAll(onUsersLoaded, onFault);
        }

        private function onUsersLoaded(event : TideResultEvent) : void {
            modelLocator.allUsers = event.result as ArrayCollection;
            if (modelLocator.allUsers.length > 0) {
                if (modelLocator.selectedUser == null) {
                    modelLocator.selectedUser = modelLocator.allUsers[0];
                } else {
                    modelLocator.selectedUser = modelLocator.allUsers[(event.result as ArrayCollection).getItemIndex(modelLocator.selectedUser)];
                }
            }
        }

        /**
         * Call the service to create a new user.
         */
        [Observer("ADD_USER")]
        public function addUser() : void {
            tideContext.userService.getTemplate("PSEUDO", onUserAdded, onFault);
        }

        private function onUserAdded(event : TideResultEvent) : void {
            var user : User = User(event.result);
            user.username = "";
            modelLocator.selectedUser = user;
        }

        /**
         * Call to save User data of the current selected User.
         */
        [Observer("SAVE_USER")]
        public function saveUser() : void {
            tideContext.userService.save(modelLocator.selectedUser, onUserSaved, onFault);
        }

        private function onUserSaved(event : TideResultEvent) : void {
            dispatchEvent(new UserEvent(UserEvent.LOAD_ALL_USERS));
        }

        /**
         * Call to delete an existing User.
         */
        [Observer("DELETE_USER")]
        public function deleteUser() : void {
            if (isNaN(modelLocator.selectedUser.id)) {
                modelLocator.selectedUser = modelLocator.allUsers.getItemAt(0) as User;
                return;
            }
            tideContext.userService.remove(modelLocator.selectedUser, onUserDeleted, onFault);
        }

        [Deprecated]
        public function onUserDeleted(event : TideResultEvent) : void {
            var len : int = modelLocator.allUsers.length;
            for (var i : int = 0; i < len; i++) {
                if (modelLocator.selectedUser.id == modelLocator.allUsers[i].id) {
                    modelLocator.allUsers.removeItemAt(i);
                    if (modelLocator.allUsers.length > 0) {
                        modelLocator.selectedUser = modelLocator.allUsers[0];
                    }
                    break;
                }
            }
        }

        /**
         * Call to change the current User's password.
         */
        [Observer("CHANGE_USER_PASSWORD")]
        public function changeUserPassword(event : UserEvent) : void {
            if (event.data != null) {
                var uPassword : UserPassword = new UserPassword();
                uPassword.password = event.data.password as String;
                uPassword.user = event.data.user as User;
                tideContext.userService.changeUserPassword(uPassword, onPasswordChanged, onFault);
            }
        }

        private function onPasswordChanged(event : TideResultEvent) : void {
            trace("Users has changed password");
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on User service:" + event.fault);
            Alert.show("Error executing operation on User service");
        }
    }
}