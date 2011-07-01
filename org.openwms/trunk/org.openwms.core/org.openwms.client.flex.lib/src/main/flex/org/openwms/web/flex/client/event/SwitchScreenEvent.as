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
    import mx.collections.ArrayCollection;

    /**
     * A SwitchScreenEvent. Used to execute screen changes.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class SwitchScreenEvent extends Event {

        /**
         * Show the start screen : emptyScreenView.
         */
        public static const SHOW_STARTSCREEN : String = "emptyScreenView";

        /**
         * Show the Modue Management screen : moduleManagementView.
         */
        public static const SHOW_MODULE_MGMT_VIEW : String = "moduleManagementView";

        /**
         * Show the User Management screen : userManagementView.
         */
        public static const SHOW_USER_MGMT_VIEW : String = "userManagementView";

        /**
         * Show the Role Management screen : roleManagementView.
         */
        public static const SHOW_ROLE_MGMT_VIEW : String = "roleManagementView";

        /**
         * Show the Settings/Properties screen : settingManagementView.
         */
        public static const SHOW_SETTING_MGMT_VIEW : String = "settingManagementView";

        /**
         * Stores all event types in an Array.
         */
        public static var eventTypes : Array = [SHOW_STARTSCREEN, SHOW_MODULE_MGMT_VIEW, SHOW_USER_MGMT_VIEW, SHOW_ROLE_MGMT_VIEW, SHOW_SETTING_MGMT_VIEW];

        /**
         * Constructor.
         */
        public function SwitchScreenEvent(type : String, bubbles : Boolean=true, cancelable : Boolean=false) {
            super(type, bubbles, cancelable);
        }

        /**
         * Add a new screen to the Array of screens.
         *
         * @param type The unique name of the new screen
         */
        public function addType(type : String) : void {
            eventTypes.push(type);
        }

        /**
         * Remove a screen of the Array of existing screens.
         *
         * @param The unique name of the screen to be removed
         */
        public function removeType(type : String) : void {
            var col : ArrayCollection = new ArrayCollection(eventTypes);
            col.removeItemAt(eventTypes.indexOf(type));
            eventTypes = col.toArray();
        }

        /**
         * Just a copy of the event itself.
         *
         * @return a copy of this
         */
        public override function clone() : Event {
            return new SwitchScreenEvent(type);
        }

        /**
         * Simple override.
         *
         * @return the type of event
         */
        public override function toString() : String {
            return formatToString("SwitchScreenEvent", "type");
        }
    }
}

