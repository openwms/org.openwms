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

        public static const LOAD_ALL_LOCATION_GROUPS:String = "LOAD_ALL_LOCATION_GROUPS";
        public static const SAVE_LOCATION_GROUP:String = "SAVE_LOCATION_GROUP";
        public static const CHANGE_STATE:String = "LG.CHANGE_STATE";
        /**
         * This type of event is fired when the list of LocationGroups in the model is refreshed.
         */
        public static const COLLECTION_REFRESHED:String = "LG.COLL_LOCATION_GROUPS_REFRESHED";

        public var data:*;

        public function LocationGroupEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false) {
            super(type, bubbles, cancelable);
        }

    }
}

