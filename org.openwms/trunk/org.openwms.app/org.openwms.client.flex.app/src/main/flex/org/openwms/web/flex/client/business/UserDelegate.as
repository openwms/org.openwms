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
    import org.openwms.core.domain.system.usermanagement.User;
    import org.openwms.core.domain.system.usermanagement.UserPassword;
    import org.openwms.web.flex.client.event.UserEvent;
    import org.openwms.web.flex.client.model.ModelLocator;

    [Name("userController")]
    [ManagedEvent(name="LOAD_ALL_USERS")]
    [Bindable]
    /**
     * An UserDelegate. Handles all interaction with the userService.
     * Provides simple CRUD methods.
     * Fires Tide events : LOAD_ALL_USERS
     * Is named as : userController
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class UserDelegate {
        [In]
        /**
         * Injected TideContext.
         */
        public var tideContext : Context;
        [In]
        /**
         * Injected ModelLocator.
         */
        public var modelLocator : ModelLocator;

        /**
         * Constructor.
         */
        public function UserDelegate() : void { }

        [Observer("LOAD_ALL_USERS")]
        /**
         * Fetch a list of all users from the service.
         * Tide event observers : LOAD_ALL_USERS
         */
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

        [Observer("ADD_USER")]
        /**
         * Call the service to create a new user.
         * Tide event observers : ADD_USER
         */
        public function addUser() : void {
            tideContext.userService.getTemplate("PSEUDO", onUserAdded, onFault);
        }
        private function onUserAdded(event : TideResultEvent) : void {
            var user : User = User(event.result);
            user.username = "";
            modelLocator.selectedUser = user;
        }

        [Observer("SAVE_USER")]
        /**
         * Call to save User data of the current selected User.
         * Tide event observers : SAVE_USER
         */
        public function saveUser() : void {
            tideContext.userService.save(modelLocator.selectedUser, onUserSaved, onFault);
        }
        private function onUserSaved(event : TideResultEvent) : void {
            dispatchEvent(new UserEvent(UserEvent.LOAD_ALL_USERS));
        }

        [Observer("DELETE_USER")]
        /**
         * Call to delete an existing User.
         * Tide event observers : DELETE_USER
         */
        public function deleteUser() : void {
            if (isNaN(modelLocator.selectedUser.id)) {
                modelLocator.selectedUser = modelLocator.allUsers.getItemAt(0) as User;
                return;
            }
            tideContext.userService.remove(modelLocator.selectedUser, onUserDeleted, onFault);
        }

        [Deprecated]
        /**
         * 
         * @param event
         */
        private function onUserDeleted(event : TideResultEvent) : void {
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

        [Observer("CHANGE_USER_PASSWORD")]
        /**
         * Call to change the current User's password.
         * Tide event observers : CHANGE_USER_PASSWORD
         * 
         * @param event The UserEvent that stores in its data variable the User and the password as String.
         */
        public function changeUserPassword(event : UserEvent) : void {
            if (event.data != null) {
                var uPassword : UserPassword = new UserPassword(event.data.user as User, event.data.password as String);
                tideContext.userService.changeUserPassword(uPassword, onPasswordChanged, onFault);
            }
        }
        private function onPasswordChanged(event : TideResultEvent) : void {
            trace("User password has been changed");
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on User service:" + event.fault);
            Alert.show("Cannot proceed action in user management, try reloading the view!");
        }
    }
}