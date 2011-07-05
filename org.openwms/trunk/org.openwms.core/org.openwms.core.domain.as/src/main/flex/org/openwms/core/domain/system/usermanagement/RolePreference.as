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
package org.openwms.core.domain.system.usermanagement {

    [Bindable]
    [RemoteClass(alias="org.openwms.core.domain.system.usermanagement.RolePreference")]
    public class RolePreference extends RolePreferenceBase {

        /**
         * Constructor.
         * Define at least a Role and a key for a RolePreference.
         *
         * @param role The Role who owns the Preference
         * @param key The key to set
         * @param value The value of the Preference
         */
        public function RolePreference(role : Role=null, key : String=null, value : String=null) {
        	if (role != null) {
            	this._owner = role.name;
            	this._role = role;
         	}
            this._key = key;
            this._value = value;
        }

    	/**
    	 * Return "Role".
    	 * 
    	 * @return Role
    	 */
    	override public function toString() : String {
    		return "Role";
    	}
    }
}