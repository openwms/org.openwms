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
package org.openwms.web.flex.client.business
{

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;

    import org.granite.tide.spring.Context;
    import org.granite.tide.events.TideResultEvent;
    import org.openwms.common.domain.system.usermanagement.User;
    import org.openwms.web.flex.client.model.ModelLocator;

    /**
     * A UserDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Name("userController")]
    [Bindable]
    public class UserDelegate
    {
        [In]
        public var tideContext:Context;
        [In]
        public var modelLocator:ModelLocator;

        public function UserDelegate():void
        {
        }

        /**
         * Fetch a list of all users from the service.
         */
        [Observer("LOAD_ALL_USERS")]
        public function getUsers():void
        {
            trace("Load Users");
            tideContext.userService.findAll(onUsersLoaded);
        }

        /**
         * Call the service to create a new user.
         */
        [Observer("ADD_USER")]
        public function addUser():void
        {
            tideContext.userService.getTemplate("PSEUDO", onUserAdded);
        }

        [Observer("SAVE_USER")]
        public function saveUser():void
        {
            tideContext.userService.save(modelLocator.selectedUser, onUserSaved);
        }

        [Observer("DELETE_USER")]
        public function deleteUser():void
        {
            if (isNaN(modelLocator.selectedUser.id))
            {
                modelLocator.selectedUser = modelLocator.allUsers.getItemAt(0) as User;
                return;
            }
            tideContext.userService.remove(modelLocator.selectedUser, onUserDeleted);
        }


        private function onUsersLoaded(event:TideResultEvent):void
        {
            trace("Users were loaded");
            modelLocator.allUsers = event.result as ArrayCollection;
            if (modelLocator.allUsers.length > 0)
            {
                modelLocator.selectedUser = modelLocator.allUsers.getItemAt(0) as User;
            }
        }

        private function onUserAdded(event:TideResultEvent):void
        {
            var user:User = User(event.result);
            user.username = "";
            if (user.userDetails.image == null)
            {
            }
            //user.userDetails.image = uniSexImageBytes; 
            //modelLocator.allUsers.addItem(user);
            modelLocator.selectedUser = user;
        }

        private function onUserSaved(event:TideResultEvent):void
        {
            if (event != null && event.result is User)
            {
                var user:User = User(event.result);
                var len:int = modelLocator.allUsers.length;
                var found:Boolean = false;
                for (var i:int = 0; i < len; i++)
                {
                    if (user.id == modelLocator.allUsers[i].id)
                    {
                        found = true;
                        modelLocator.allUsers[i] = user;
                        modelLocator.allUsers.refresh();
                        trace("User found and replaced in List");
                        break;
                    }
                }
                if (!found)
                {
                    Alert.show("New user saved");
                    modelLocator.allUsers.addItemAt(user, modelLocator.allUsers.length);
                }
                else
                {
                    Alert.show("User data saved");
                }
            }
            //UserHelper.traceUser(user);
        }

        public function onUserDeleted(event:TideResultEvent):void
        {
            var len:int = modelLocator.allUsers.length;
            for (var i:int = 0; i < len; i++)
            {
                if (modelLocator.selectedUser.id == modelLocator.allUsers[i].id)
                {
                    modelLocator.allUsers.removeItemAt(i);
                    if (modelLocator.allUsers.length > 0)
                    {
                        modelLocator.selectedUser = modelLocator.allUsers[0];
                    }
                    break;
                }
            }
        }

    }
}