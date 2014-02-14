/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client.model {

    import flash.utils.Dictionary;
    import mx.collections.ArrayCollection;
    
    import org.granite.util.DictionaryUtil;

    import org.openwms.core.domain.preferences.ApplicationPreference;
    import org.openwms.core.domain.preferences.ModulePreference;
    import org.openwms.core.domain.system.AbstractPreference;
    import org.openwms.core.domain.system.usermanagement.RolePreference;
    import org.openwms.core.domain.system.usermanagement.UserPreference;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.PropertyEvent;
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name("prefs")]
    [ManagedEvent(name="PREFERENCE.MODEL_CHANGED")]
    [ResourceBundle("corLibMain")]
    [Bindable]
    /**
     * A PreferencesModel stores all Preferences
     * It is a Tide component and can be injected by name.
     * Fires Tide events : PREFERENCE.MODEL_CHANGED
     * Is named as : prefs
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1461 $
     * @since 0.1
     */
    public class PreferencesModel {

        /**
         * All ApplicationPreferences.
         */
        public var appPrefs : Dictionary = new Dictionary();
        /**
         * All ModulePreferences.
         */
        public var modulePrefs : Dictionary = new Dictionary();
        /**
         * All RolePreferences.
         */
        public var rolePrefs : Dictionary = new Dictionary();
        /**
         * All UserPreferences.
         */
        public var userPrefs : Dictionary = new Dictionary();

        /**
         * All possible types of preferences as Object Array. Each entry stores the actual type as
         * 'type' field and the corresponding collection of data as 'dataCollection'.
         */
        public var types : Array;

        [Inject]
        /**
         * Inject the model.
         */
        public var modelLocator : ModelLocator;
        /**
         * The currently selected Preference.
         */
        public var selected : AbstractPreference;

        /**
         * Constructor.
         */
        public function PreferencesModel() {
            createTypesArray();
        }

        /**
         * Filter a list of AbstractPreferences, find out which type and assign each one
         * to the list of preference types. Usually only called by the controller after the
         * preferences where loaded from the backend.
         *
         * @param preferences The list to examine and to filter
         */
        public function assignPreferences(preferences : ArrayCollection) : void {
            this.clearAll();
            for each (var pref : AbstractPreference in preferences) {
                if (pref is ApplicationPreference) {
                    appPrefs[(pref as ApplicationPreference).createCKey()] = pref;
                } else if (pref is ModulePreference) {
                    modulePrefs[(pref as ModulePreference).createCKey()] = pref;
                } else if (pref is RolePreference) {
                    rolePrefs[(pref as RolePreference).createCKey()] = pref;
                } else if (pref is UserPreference) {
                    userPrefs[(pref as UserPreference).createCKey()] = pref;
                        //this.userPrefs.addItem(pref);
                }
            }
            dispatchEvent(new PropertyEvent(PropertyEvent.PREFERENCE_MODEL_CHANGED));
        }

        /**
         * Takes an AbstractPreference and add it to the appropriate collection.
         *
         * @param preference The AbstractPreference to assign
         */
        public function addPreference(preference : AbstractPreference) : void {
            if (preference is ApplicationPreference) {
                appPrefs[(preference as ApplicationPreference).createCKey()] = preference;
            } else if (preference is ModulePreference) {
                modulePrefs[(preference as ModulePreference).createCKey()] = preference;
            } else if (preference is RolePreference) {
                rolePrefs[(preference as RolePreference).createCKey()] = preference;
            } else if (preference is UserPreference) {
                userPrefs[(preference as UserPreference).createCKey()] = preference;
            }
            dispatchEvent(new PropertyEvent(PropertyEvent.PREFERENCE_MODEL_CHANGED));
        }

        /**
         * Fins and retrieve an ApplicationPreference by name.
         *
         * @param name The name to search for
         * @return The ApplicationPreference
         */
        public function getAppPreference(name : String) : ApplicationPreference {
            return appPrefs[name];
        }

        /**
         * Return all ApplicationPreferences as ArrayCollection.
         *
         * @return All ApplicationPreferences as ArrayCollection
         */
        public function get appPrefsAsCollection() : ArrayCollection {
            var ar : Array = DictionaryUtil.getValues(appPrefs);
            if (ar.length > 0) {
                return new ArrayCollection(ar);
            }
            return null;
        }

        /**
         * Format a type Object, defined in #createTypesArray().
         *
         * @param item Object type {type : AbstractPreference, dataDictionary : Dictionary}
         * @return The preferences as String (translated)
         */
        public function formatType(item : *) : String {
            return I18nUtil.trans(I18nUtil.COR_LIB_MAIN, "txt_preferences_" + item.type.toString());
        }

        /**
         * Create a new Preference of type type.
         *
         * @param type The type of the new Preference
         * @param key The key of the new Preference
         * @param description The description of the new Preference
         * @param owner The owner of the new Preference
         * @param value The value of the new Preference
         * @param floatValue The floatValue of the new Preference
         * @param minimumValue The minimumValue of the new Preference
         * @param maximumValue The maximumValue of the new Preference
         * @return The created Preference of type type
         */
        public static function createPreference(type : AbstractPreference, key : String, description : String=null, owner : String=null, value : String=null, floatValue : Number=NaN, minimumValue : int=-1, maximumValue : int=-1) : AbstractPreference {
            if (type is ApplicationPreference) {
                var appPref : ApplicationPreference = new ApplicationPreference(key, value);
                assignValues(appPref, floatValue, description, minimumValue, maximumValue);
                return appPref;
            } else if (type is ModulePreference) {
                var modPref : ModulePreference = new ModulePreference(owner, key, value);
                return assignValues(modPref, floatValue, description, minimumValue, maximumValue);
            } else if (type is RolePreference) {
                var rolePref : RolePreference = new RolePreference(owner, key, value);
                return assignValues(rolePref, floatValue, description, minimumValue, maximumValue);
            } else if (type is UserPreference) {
                var userPref : UserPreference = new UserPreference(owner, key, value);
                return assignValues(userPref, floatValue, description, minimumValue, maximumValue);
            }
            return null;
        }

        [Observer("APP.CLEAR_MODEL")]
        /**
         * Usually called after logout to reset model data.
         *
         * @param event Unused
         */
        public function clearModel(event : ApplicationEvent) : void {
            this.selected = null;
        }

        private static function assignValues(preference : AbstractPreference, floatValue : Number=NaN, description : String=null, minimumValue : int=-1, maximumValue : int=-1) : AbstractPreference {
            if (!isNaN(floatValue)) {
                preference.floatValue = floatValue;
            }
            preference.description = description;
            preference.minimum = minimumValue;
            preference.maximum = maximumValue;
            preference.fromFile = false;
            return preference;
        }

        private function clearAll() : void {
            appPrefs = new Dictionary();
            modulePrefs = new Dictionary();
            rolePrefs = new Dictionary();
            userPrefs = new Dictionary();
            createTypesArray();
        }

        private function createTypesArray() : void {
            types = [{type: new ApplicationPreference(), dataDictionary: appPrefs}, {type: new ModulePreference(), dataDictionary: modulePrefs}, {type: new RolePreference(), dataDictionary: rolePrefs}, {type: new UserPreference(), dataDictionary: userPrefs}];
        }
    }
}