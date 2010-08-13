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
    
    import org.granite.tide.data.events.TideDataConflictsEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.system.usermanagement.Role;
    import org.openwms.web.flex.client.event.RoleEvent;
    import org.openwms.web.flex.client.model.ModelLocator;

    /**
     * A RoleDelegate.
     *
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
     * @version $Revision: 700 $
     */
    [Name("roleController")]
    [ManagedEvent(name="LOAD_ALL_ROLES")]
    [ManagedEvent(name="ROLE_ADDED")]
    [ManagedEvent(name="ROLE_SAVED")]
    [Bindable]
    public class RoleDelegate
    {
        [In]
        public var tideContext:Context;
        [In]
        public var modelLocator:ModelLocator;

        public function RoleDelegate():void
        {
        }

        [Observer("LOAD_ALL_ROLES")]
        [Observer("ROLE_ADDED")]
        [Observer("ROLE_SAVED")]
        public function getRoles():void
        {
            tideContext.userService.findAllRoles(onRolesLoaded);
        }

        [Observer("ADD_ROLE")]
        public function addRole(event:RoleEvent):void
        {
            if (event.data is Role)
            {
                tideContext.userService.saveRole(event.data as Role, onRoleAdded);
            }
        }

        [Observer("SAVE_ROLE")]
        public function saveRole(event:RoleEvent):void
        {
            if (event.data is Role)
            {
                tideContext.userService.saveRole(event.data as Role, onRoleSaved, onFault);
            }
        }

        [Observer("DELETE_ROLE")]
        public function deleteRoles(event:RoleEvent):void
        {
            if (event.data != null)
            {
                tideContext.userService.removeRoles(event.data as ArrayCollection, onRoleDeleted);
            }
        }

        private function onRolesLoaded(event:TideResultEvent):void
        {
            modelLocator.allRoles = event.result as ArrayCollection;
        }

        private function onRoleAdded(event:TideResultEvent):void
        {
            dispatchEvent(new RoleEvent(RoleEvent.ROLE_ADDED));
        }

        private function onRoleSaved(event:TideResultEvent):void
        {
            dispatchEvent(new RoleEvent(RoleEvent.ROLE_SAVED));
        }

        private function onRoleDeleted(event:TideResultEvent):void
        {
            dispatchEvent(new RoleEvent(RoleEvent.LOAD_ALL_ROLES));
        }

        private function onFault(event:TideDataConflictsEvent):void
        {
            Alert.show("Error when saving the Role");
        }
    }
}