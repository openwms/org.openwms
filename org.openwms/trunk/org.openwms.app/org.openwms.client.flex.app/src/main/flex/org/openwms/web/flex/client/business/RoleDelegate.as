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
    import org.granite.tide.events.TideContextEvent;
    import org.granite.tide.spring.Context;

    import org.openwms.core.domain.system.usermanagement.Role;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.RoleEvent;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name("roleDelegate")]
    [ManagedEvent(name = "LOAD_ALL_ROLES")]
    [ManagedEvent(name = "ROLE_ADDED")]
    [ManagedEvent(name = "ROLE_SAVED")]
    [ManagedEvent(name = "APP.SECURITY_OBJECTS_REFRESHED")]
    [ResourceBundle("appError")]
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
        public function RoleDelegate() : void {
        }

        [Observer("LOAD_ALL_ROLES")]
        /**
         * Fetch a list of all roles from the service.
         * Tide event observers : LOAD_ALL_ROLES
         *
         * @param event Unused
         */
        public function getRoles(event : RoleEvent=null) : void {
            trace("Loading Roles");
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

        [Observer("ROLE_ADDED")]
        /**
         * Fired whenever a new Role was successfully saved. A reload of all Roles is triggered.
         * Tide event observers : ROLE_ADDED
         *
         * @param event Unused
         */
        public function roleAdded(event : RoleEvent) : void {
            getRoles();
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

        [Observer("ROLE_SAVED")]
        /**
         * Fired whenever a new Role was successfully saved. A reload of all Roles is triggered.
         * Tide event observers : ROLE_SAVED
         *
         * @param event Unused
         */
        public function roleSaved(event : RoleEvent) : void {
            getRoles();
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
            trace("Error executing operation on Role service2:" + event);
            Alert.show(I18nUtil.trans(I18nUtil.APP_ERROR, 'error_role_service_operation'));
        }
    }
}

