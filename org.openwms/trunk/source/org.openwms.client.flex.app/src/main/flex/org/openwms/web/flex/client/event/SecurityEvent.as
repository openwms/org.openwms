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
package org.openwms.web.flex.client.event.SecurityEvent
{
    import flash.events.Event;

    /**
     * A SecurityEvent.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 235 $
     */
    public class SecurityEvent extends Event
    {
        private var _user:String;
        private var _pass:String;

        public function SecurityEvent(event_type:String, bubbles:Boolean = true, cancelable:Boolean = false):void
        {
            super(event_type, bubbles, cancelable);
        }

        public function set user(pValue:String):void
        {
            _user = pValue;
        }

        public function get user():String
        {
            return _user;
        }

        public function set pass(pValue:String):void
        {
            _pass = pValue;
        }

        public function get pass():String
        {
            return _pass;
        }

        public function get loginStatus():Boolean
        {
            if ((_user == "openwms") && (_pass == "openwms"))
                return true;
            else
                return false;
        }
    }
}
