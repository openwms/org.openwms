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
package org.openwms.common.domain.system.usermanagement {

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.system.usermanagement.Role")]
	/**
	 * A Role is grouping multiple <code>User</code>s regarding security aspects.
	 * <p>
	 * Security access policies are assigned to Roles instead of to <code>User</code>s directly.
	 * </p>
	 * 
	 * @version $Revision$
	 * @since 0.1
	 */
    public class Role extends RoleBase {

        /**
         * Constructor.
         *
         * @param name The unique name of the role
         * @param description A descriptive text
         */
        public function Role(name : String=null, description : String=null) {
            this._name = name;
            this._description = description;
        }
        
        /**
         * Simple setter of the name as a helper method on the AS side ;-).
         *
         * @param value The name to set
         */
        public function set name(value:String):void {
            _name = value;
        }
    }
}