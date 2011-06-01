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
    import mx.logging.ILogger;
    import mx.logging.Log;

    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.events.TideContextEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.core.domain.system.usermanagement.Role;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.RoleEvent;
    import org.openwms.web.flex.client.model.ModelLocator;

    [Name("roleController")]
    [ManagedEvent(name="LOAD_ALL_ROLES")]
    [ManagedEvent(name="ROLE_ADDED")]
    [ManagedEvent(name="ROLE_SAVED")]
    [ManagedEvent(name="APP.SECURITY_OBJECTS_REFRESHED")]
    [Bindable]
    /**
     * A RoleDelegate serves as a controller and is responsible for all interactions with the service layer
     * regarding the handling with Roles.
     * Fires Tide events : LOAD_ALL_ROLES, ROLE_ADDED, ROLE_SAVED
     * Is named as : roleController
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class RoleDelegate {

        private static var logger : ILogger = Log.getLogger("org.openwms.web.flex.client.business.RoleDelegate");

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
        public function RoleDelegate() : void { }

        [Observer("LOAD_ALL_ROLES","ROLE_ADDED","ROLE_SAVED")]
        /**
         * Fetch a list of all roles from the service.
         * Tide event observers : LOAD_ALL_ROLES, ROLE_ADDED, ROLE_SAVED
         */
        public function getRoles() : void {
            tideContext.roleService.findAll(onRolesLoaded, onFault);
        }
        private function onRolesLoaded(event : TideResultEvent) : void {
            modelLocator.allRoles = event.result as ArrayCollection;
        }

        [Observer("ADD_ROLE")]
        /**
         * Save a new Role.
         * Tide event observers : ADD_ROLE
         *
         * @param event The raised RoleEvent that holds the new Role within the data property.
         */
        public function addRole(event : RoleEvent) : void {
            if (event.data is Role) {
                tideContext.roleService.save(event.data as Role, onRoleAdded, onFault);
            }
        }
        private function onRoleAdded(event : TideResultEvent) : void {
            dispatchEvent(new RoleEvent(RoleEvent.ROLE_ADDED));
        }

        [Observer("SAVE_ROLE")]
        /**
         * Save an existing Role.
         * Tide event observers : SAVE_ROLE
         *
         * @param event The raised RoleEvent that holds the Role within the data property.
         */
        public function saveRole(event : RoleEvent) : void {
            if (event.data is Role) {
                tideContext.roleService.save(event.data as Role, onRoleSaved, onFault);
            }
        }
        private function onRoleSaved(event : TideResultEvent) : void {
            dispatchEvent(new RoleEvent(RoleEvent.LOAD_ALL_ROLES));
            dispatchEvent(new RoleEvent(RoleEvent.ROLE_SAVED));
        }

        [Observer("DELETE_ROLE")]
        /**
         * Delete a Role.
         * Tide event observers : DELETE_ROLE
         *
         * @param event The raised RoleEvent that holds an ArrayCollection of Roles to delete within the data property.
         */
        public function deleteRoles(event : RoleEvent) : void {
            if (event.data != null) {
                tideContext.roleService.remove(event.data as ArrayCollection, onRoleDeleted, onFault);
            }
        }
        private function onRoleDeleted(event : TideResultEvent) : void {
            dispatchEvent(new RoleEvent(RoleEvent.LOAD_ALL_ROLES));
        }

        [Observer("APP.MERGE_GRANTS")]
        /**
         * Load all relevant UIComponents for a module.
         * Tide event observers : APP.MERGE_GRANTS
         *
         * @param event The raised must have the moduleName and a list of grants in its data field, otherwise an exception is thrown.
         */
        public function mergeGrants(event : ApplicationEvent) : void {
            if (event.data != null) {
                tideContext.securityService.mergeGrants(event.data.moduleName as String, event.data.grants as ArrayCollection, onGrantsMerged, onFault);
            } else {
                // throw Error();
            }
        }
        private function onGrantsMerged(event : TideResultEvent) : void {
            var e : ApplicationEvent = new ApplicationEvent(ApplicationEvent.SECURITY_OBJECTS_REFRESHED);
            e.data = event.result as ArrayCollection;
            dispatchEvent(e);
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on Role service:" + event.fault);
            logger.error("ERROR accessing Role : " + event.fault);
            Alert.show("Error executing operation on Role service");
        }
    }
}

