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
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name("userDelegate")]
    [ManagedEvent(name="LOAD_ALL_USERS")]
    [ManagedEvent(name="USER.USER_ADDED")]
    [ManagedEvent(name="USER.USER_SAVED")]
    [ResourceBundle("appError")]
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

        [Inject]
        /**
         * Injected TideContext.
         */
        public var tideContext : Context;

        [Inject]
        /**
         * Injected ModelLocator.
         */
        public var modelLocator : ModelLocator;

        /**
         * Constructor.
         */
        public function UserDelegate() : void {
        }

        [Observer("LOAD_ALL_USERS")]
        /**
         * Fetch a list of all users from the service.
         * Tide event observers : LOAD_ALL_USERS, USER.USER_ADDED
         *
         * @param event Unused
         */
        public function loadUsers(event : UserEvent=null) : void {
            tideContext.userService.findAll(onUsersLoaded, onFault);
        }

        private function onUsersLoaded(event : TideResultEvent) : void {
            modelLocator.allUsers = event.result as ArrayCollection;
            if (modelLocator.allUsers.length > 0 && modelLocator.selectedUser == null) {
                modelLocator.selectedUser = modelLocator.allUsers.getItemAt(0) as User;
            }
        }

        [Observer("ADD_USER")]
        /**
         * Call the service to create a new user.
         * Tide event observers : ADD_USER
         *
         * @param event Unused
         */
        public function addUser(event : UserEvent) : void {
            tideContext.userService.getTemplate("PSEUDO", onUserAdded, onFault);
        }

        private function onUserAdded(event : TideResultEvent) : void {
            var user : User = event.result as User;
            user.username = "";
            modelLocator.selectedUser = user;
            //loadUsers();
            dispatchEvent(new UserEvent(UserEvent.USER_ADDED));
        }

        [Observer("SAVE_USER")]
        /**
         * Call to save User data of the current selected User.
         * Tide event observers : SAVE_USER
         *
         * @param event data variable holds the User to store
         */
        public function saveUser(event : UserEvent) : void {
            tideContext.userService.save(event.data as User, onUserSaved, onFault);
        }

        private function onUserSaved(event : TideResultEvent) : void {
            modelLocator.selectedUser = null;
            loadUsers();
            dispatchEvent(new UserEvent(UserEvent.USER_SAVED));
        }

        [Observer("DELETE_USER")]
        /**
         * Call to delete an existing User.
         * Tide event observers : DELETE_USER
         *
         * @param event Unused
         */
        public function deleteUser(event : UserEvent) : void {
            tideContext.userService.remove(modelLocator.selectedUser, onUserDeleted, onFault);
        }

        /**
         * After an User was successfully deleted, go and remove it from the model manually.
         *
         * @param event Unused
         */
        private function onUserDeleted(event : TideResultEvent) : void {
            for (var i : int = 0; i < modelLocator.allUsers.length; i++) {
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
            loadUsers();
        }

        [Observer("USER.SAVE_USER_PROFILE")]
        /**
         * Call to save changes done in the UserPreferenceDialog. To change the Users password, or store UserPreferences.
         * UserDetails can be saved as well.
         *
         * @param event data property is expected to store an object of type {User, UserPassword, [UserPreference]}
         */
        public function saveUserProfile(event : UserEvent) : void {
            if (event.data != null) {
                var pw : Object = null;
                if (event.data.password != null) {
                    pw = event.data.password;
                }
                tideContext.userService.saveUserProfile(event.data.user as User, event.data.password as UserPassword, event.data.preferences as Array, onUserProfileSaved, onFault);
            }
        }

        private function onUserProfileSaved(event : TideResultEvent) : void {
            loadUsers();
            dispatchEvent(new UserEvent(UserEvent.USER_SAVED));
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on User service:" + event.fault);
            Alert.show(I18nUtil.trans(I18nUtil.APP_ERROR, 'error_user_service_operation'));
        }
    }
}

