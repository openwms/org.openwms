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
    import org.openwms.core.domain.system.AbstractPreference;
    import org.openwms.core.domain.system.ApplicationPreference;
    import org.openwms.core.domain.system.ModulePreference;
    import org.openwms.core.domain.system.usermanagement.RolePreference;
    import org.openwms.core.domain.system.usermanagement.UserPreference;
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name("prefs")]
    [ResourceBundle("corLibMain")]
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
        public const types : Array = [new ApplicationPreference(), new ModulePreference(), new RolePreference(), new UserPreference()];

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

        /**
         * Filter a list of AbstractPreferences, find out which type and assign each one
         * to the list of preference types.
         *
         * @param preferences The list to examine and to filter
         */
        public function assignPreferences(preferences : ArrayCollection) : void {
            this.clearAll();
            for each (var pref : AbstractPreference in preferences) {
                if (pref is ApplicationPreference) {
                    this.appPrefs.addItem(pref);
                } else if (pref is ModulePreference) {
                    this.modulePrefs.addItem(pref);
                } else if (pref is RolePreference) {
                    this.rolePrefs.addItem(pref);
                } else if (pref is UserPreference) {
                    this.userPrefs.addItem(pref);
                }
            }
        }

        /**
         * Define how a Preference name is displayed.
         */
        public function formatType(item : *) : String {
            return I18nUtil.trans(I18nUtil.COR_LIB_MAIN, "txt_preferences_" + item.toString());
        }

        /**
         * Whenever the collection of Users change, update the list preferences.
         *
         * @param event not used
         */
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