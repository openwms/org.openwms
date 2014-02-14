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
package org.openwms.web.flex.client.event {

    import flash.events.Event;

    /**
     * An UserEvent. Used to trigger actions regarding User entities.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class UserEvent extends Event {

        /**
         * Type of event to load all User entities from the backend.
         */
        public static const LOAD_ALL_USERS : String = "LOAD_ALL_USERS";
        /**
         * Type of event to add a new User. The User to add must be stored in the data property
         */
        public static const ADD_USER : String = "ADD_USER";
        /**
         * Type of event that is fired when an User was added successfully.
         */
        public static const USER_ADDED : String = "USER.USER_ADDED";
        /**
         * Type of event to save an existing User. The User to add must be stored in the data property
         */
        public static const SAVE_USER : String = "SAVE_USER";
        /**
         *
         */
        public static const SAVE_USER_PROFILE : String = "USER.SAVE_USER_PROFILE";
        /**
         * Type of event that is fired when an User was saved successfully.
         */
        public static const USER_SAVED : String = "USER.USER_SAVED";
        /**
         * Type of event to delete an existing User. The User to add must be stored in the data property
         */
        public static const DELETE_USER : String = "DELETE_USER";
        /**
         * Type of event to change the Users password. The User to add must be stored in the data property
         */
        public static const CHANGE_USER_PASSWORD : String = "CHANGE_USER_PASSWORD";
        /**
         * Type of event that is fired whenever the selection of Users in the UserManagement screen has changed.
         */
        public static const USER_SELECTION_CHANGED : String = "USER.SELECTION_CHANGED";
        /**
         * Type of event that is fired whenever the list of Users has changed.
         */
        public static const USER_COLLECTION_CHANGED : String = "USER.COLLECTION_CHANGED";

        /**
         * Store arbitrary data.
         */
        public var data : *;

        /**
         * Constructor.
         */
        public function UserEvent(type : String, bubbles : Boolean=true, cancelable : Boolean=false) {
            super(type, bubbles, cancelable);
        }

        /**
         * Just a copy of the event itself including the data field.
         *
         * @return a copy of this
         */
        public override function clone() : Event {
            var e : UserEvent = new UserEvent(type);
            e.data = data;
            return e;
        }

        /**
         * Simple override.
         *
         * @return the type of event
         */
        public override function toString() : String {
            return formatToString("UserEvent", "type");
        }
    }
}