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
package org.openwms.web.flex.client.event
{
    import flash.events.Event;
    
    import mx.collections.ArrayCollection;

    /**
     * A SwitchScreenEvent.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public class SwitchScreenEvent extends Event
    {
        public static const SHOW_STARTSCREEN:String = "emptyScreenView";
        public static const SHOW_MODULE_MGMT_VIEW:String = "moduleManagementView";
        public static const SHOW_USER_MGMT_VIEW:String = "userManagementView";
        public static const SHOW_ROLE_MGMT_VIEW:String = "roleManagementView";
        
        public static var eventTypes:Array;

        public function SwitchScreenEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
        {
        	eventTypes = new Array();
            super(type, bubbles, cancelable);
        }
        
        public static function addType(type:String):void
        {
        	eventTypes.push(type);
        }
        
        public static function removeType(type:String):void
        {
        	var col:ArrayCollection = new ArrayCollection(eventTypes);
        	col.removeItemAt(eventTypes.indexOf(type));
        	eventTypes = col.toArray();
        }

    }
}