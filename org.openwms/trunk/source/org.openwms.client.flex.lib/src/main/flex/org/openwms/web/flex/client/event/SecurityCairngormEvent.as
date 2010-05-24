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
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import org.granite.events.SecurityEvent;

    /**
     * A SecurityCairngormEvent.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public class SecurityCairngormEvent extends SecurityEvent
    {
    	
    	private var _rootEvent:CairngormEvent;

        public function set rootEvent(rootEvent:CairngormEvent):void {
            _rootEvent = rootEvent;
        }
        public function get rootEvent():CairngormEvent {
            return _rootEvent;
        }

        public function SecurityCairngormEvent(event_type:String, rootEvent:CairngormEvent = null, message:String = null, bubbles:Boolean = true, cancelable:Boolean = false):void
        {
            super(event_type, message, bubbles, cancelable);
            _rootEvent = rootEvent;
        }

    }
}
