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

    import org.openwms.web.flex.client.event.SwitchScreenEvent;    

    /**
     * A CommonSwitchScreenEvent.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class CommonSwitchScreenEvent extends SwitchScreenEvent {

        public static const SHOW_TRANSPORTUNIT_VIEW:String = "transportUnitView";
        public static const SHOW_TRANSPORTUNITTYPE_VIEW:String = "transportUnitTypeView";
        public static const SHOW_LOCATIONTYPE_VIEW:String = "locationTypeView";
        public static const SHOW_LOCATION_VIEW:String = "locationView";
        public static const SHOW_LOCATIONGROUP_VIEW:String = "locationGroupView";

        public function CommonSwitchScreenEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false) {
            super(type, bubbles, cancelable);
        }
    }
}

