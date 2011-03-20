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
package org.openwms.web.flex.client.event {
	
    import flash.events.Event;

    /**
     * A RoleEvent.
     *
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class RoleEvent extends Event {
        public static const LOAD_ALL_ROLES:String = "LOAD_ALL_ROLES";
        public static const ADD_ROLE:String = "ADD_ROLE";
        public static const SAVE_ROLE:String = "SAVE_ROLE";
        public static const DELETE_ROLE:String = "DELETE_ROLE";
        public static const ROLE_ADDED:String = "ROLE_ADDED";
        public static const ROLE_SAVED:String = "ROLE_SAVED";

        public var data:*;

        public function RoleEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false) {
            super(type, bubbles, cancelable);
        }
    }
}