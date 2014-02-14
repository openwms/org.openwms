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
package org.openwms.web.flex.client.common.event {

    import flash.events.Event;

    /**
     * A LocationGroupEvent.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class LocationGroupEvent extends Event {

        /**
         * This type of event to trigger a reload of all LocationGroup entities.
         */
        public static const LOAD_ALL_LOCATION_GROUPS : String = "LOAD_ALL_LOCATION_GROUPS";
        /**
         * This type of event to save changes on an existing LocationGroup.
         * The data field is expected to store the LocationGroup to save.
         */
        public static const SAVE_LOCATION_GROUP : String = "SAVE_LOCATION_GROUP";
        /**
         * This type of event is fired to change the state of a LocationGroup.
         * The data field is expected to store the LocationGroup to save.
         */
        public static const CHANGE_STATE : String = "LG.CHANGE_STATE";
        /**
         * This type of event is fired when the list of LocationGroups in the model was refreshed.
         */
        public static const COLLECTION_REFRESHED : String = "LG.COLL_LOCATION_GROUPS_REFRESHED";

        /**
         * Store arbitrary data.
         */
        public var data : *;

        /**
         * Constructor.
         */
        public function LocationGroupEvent(type : String, bubbles : Boolean=true, cancelable : Boolean=false) {
            super(type, bubbles, cancelable);
        }

        /**
         * Just a copy of the event itself including the data field.
         *
         * @return a copy of this
         */
        public override function clone() : Event {
            var e : LocationGroupEvent = new LocationGroupEvent(type);
            e.data = data;
            return e;
        }

        /**
         * Simple override.
         *
         * @return the type of event
         */
        public override function toString() : String {
            return formatToString("LocationGroupEvent", "type");
        }
    }
}

