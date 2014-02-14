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
package org.openwms.core.domain.system.usermanagement {

    import org.openwms.core.domain.system.PreferenceKey;
    import org.openwms.core.domain.system.PropertyScope;

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.usermanagement.UserPreference")]
    /**
     * An UserPreference is a preference assigned to a particular User only.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class UserPreference extends UserPreferenceBase {

        private var _cKey : PreferenceKey;
        
        /**
         * Constructor.
         * Define at least an User and a key for a UserPreference.
         *
         * @param owner The username of the User who owns this Preference
         * @param key The key to set
         * @param value The value of the Preference
         */
        public function UserPreference(owner : String=null, key : String=null, value : String=null) {
            this._type = PropertyScope.USER;
            this._owner = owner;
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
         * Set the owner.
         *
         * @param value The username used as owner.
         */
        public function set owner(value : String) : void {
            _owner = value;
        }

        /**
         * Return "User".
         *
         * @return User
         */
        override public function toString() : String {
            return "User";
        }
    }
}