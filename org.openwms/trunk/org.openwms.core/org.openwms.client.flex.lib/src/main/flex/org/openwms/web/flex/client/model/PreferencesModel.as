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
package org.openwms.web.flex.client.model {

    import mx.collections.ArrayCollection;

    import org.granite.tide.events.TideResultEvent;
    import org.openwms.core.domain.system.ApplicationPreference;
    import org.openwms.core.domain.system.ModulePreference;
    import org.openwms.core.domain.system.usermanagement.RolePreference;
    import org.openwms.core.domain.system.usermanagement.UserPreference;

    [Name("prefs")]
    [Bindable]
    /**
     * A PreferencesModel stores all Preferences
     * It is a Tide component and can be injected by name : prefs.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1461 $
     * @since 0.1
     */
    public class PreferencesModel {

        [Inject]
        /**
         * Inject the model.
         */
        public var modelLocator : ModelLocator;
        public var appPrefs : ArrayCollection = new ArrayCollection();
        public var modulePrefs : ArrayCollection = new ArrayCollection();
        public var rolePrefs : ArrayCollection = new ArrayCollection();
        public var userPrefs : ArrayCollection = new ArrayCollection();

        public static const APPLICATION : String = "Application";
        public static const MODULE : String = "Module";
        public var owners : ArrayCollection = new ArrayCollection([APPLICATION, MODULE]);
        public static const types : Array = [ApplicationPreference, ModulePreference, RolePreference, UserPreference];

        /**
         * Constructor.
         */
        public function PreferencesModel() {
        }

        /**
         * Clear all collections.
         */
        public function clearAll() : void {
            appPrefs.removeAll();
            modulePrefs.removeAll();
            rolePrefs.removeAll();
            userPrefs.removeAll();
        }

        [Observer("USER.COLLECTION_CHANGED")]
        public function onUsersLoaded(event : TideResultEvent) : void {
            owners.removeAll();
            owners.addItem(APPLICATION);
            owners.addItem(MODULE);
            owners.toArray().push(modelLocator.allRoles.source);
            owners.toArray().push(modelLocator.allUsers.source);
        }

    }
}