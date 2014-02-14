/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client.event {

    import flash.events.Event;

    /**
     * A RoleEvent. Used to trigger actions regarding Role entities.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class RoleEvent extends Event {

        /**
         * Type of event to load all Roles from the backend.
         */
        public static const LOAD_ALL_ROLES : String = "LOAD_ALL_ROLES";

        /**
         * Type of event to add a new Role. The Role to add must be stored in the data property.
         */
        public static const ADD_ROLE : String = "ADD_ROLE";

        /**
         * Type of event to save an existing Role. The Role to save must be stored in the data property.
         */
        public static const SAVE_ROLE : String = "SAVE_ROLE";

        /**
         * Type of event to delete an existing Role. The Role to delete must be stored in the data property.
         */
        public static const DELETE_ROLE : String = "DELETE_ROLE";

        /**
         * Type of event that is fired when a Role was added successfully.
         */
        public static const ROLE_ADDED : String = "ROLE_ADDED";

        /**
         * Type of event that is fired when a Role was saved successfully.
         */
        public static const ROLE_SAVED : String = "ROLE_SAVED";

        /**
         * Store arbitrary data.
         */
        public var data : *;

        /**
         * Constructor.
         */
        public function RoleEvent(type : String, bubbles : Boolean=true, cancelable : Boolean=true) {
            super(type, bubbles, cancelable);
        }

        /**
         * Just a copy of the event itself including the data field.
         *
         * @return a copy of this
         */
        public override function clone() : Event {
            var e : RoleEvent = new RoleEvent(type);
            e.data = data;
            return e;
        }

        /**
         * Simple override.
         *
         * @return the type of event
         */
        public override function toString() : String {
            return formatToString("RoleEvent", "type");
        }
    }
}