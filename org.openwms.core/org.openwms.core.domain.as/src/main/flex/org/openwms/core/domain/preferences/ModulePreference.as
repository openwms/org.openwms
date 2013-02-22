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
package org.openwms.core.domain.preferences {

    import org.openwms.core.domain.system.PreferenceKey;
    import org.openwms.core.domain.system.PropertyScope;

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.preferences.ModulePreference")]
    /**
     * A ModulePreference is a preference assigned to a particular Module only.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class ModulePreference extends ModulePreferenceBase {

        private var _cKey : PreferenceKey;

        /**
         * Constructor.
         * Define at least a key for a ModulePreference.
         *
         * @param module The Module where the Preference belongs to
         * @param key The key to set
         * @param value The value of the Preference
         */
        public function ModulePreference(module : String=null, key : String=null, value : String=null) {
            this._type = PropertyScope.MODULE;
            this._owner = module;
            this._key = key;
            this._value = value;
        }

        /**
         * Create and return a PreferenceKey from the owner and key field.
         * 
         * @return the PreferenceKey
         */
        public override function createCKey() : PreferenceKey {
            this._cKey = new PreferenceKey(this._owner, this._key);
            return this._cKey;
        }

        /**
         * Return "Module".
         *
         * @return Module
         */
        override public function toString() : String {
            return "Module";
        }
    }
}