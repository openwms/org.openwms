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
package org.openwms.web.flex.client.command {
	
    import flash.events.EventDispatcher;
    import mx.rpc.events.FaultEvent;
    import org.granite.events.SecurityEvent;
    import org.openwms.web.flex.client.event.EventBroker;

    /**
     * DEPRECATED An AbstractCommand.
     *
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class AbstractCommand extends EventDispatcher {

        private var broker : EventBroker = EventBroker.getInstance();

        public function AbstractCommand() {
            super();
        }

        protected function onFault(e : Object) : Boolean {
            if (e is FaultEvent) {
                var event : FaultEvent = e as FaultEvent;
                if (event.fault.faultCode == "Server.Security.NotLoggedIn") {
                    broker.dispatchEvent(new SecurityEvent(SecurityEvent.NOT_LOGGED_IN, SecurityEvent.NOT_LOGGED_IN));
                    return true;
                } else if (event.fault.faultCode == "Server.Security.InvalidCredentials" || event.fault.faultCode == "Channel.Authentication.Error") {
                    broker.dispatchEvent(new SecurityEvent(SecurityEvent.INVALID_CREDENTIALS, SecurityEvent.INVALID_CREDENTIALS));
                    return true;
                } else if (event.fault.faultCode == "Server.Security.SessionExpired") {
                    broker.dispatchEvent(new SecurityEvent(SecurityEvent.SESSION_EXPIRED, SecurityEvent.SESSION_EXPIRED));
                    return true;
                } else if (event.fault.faultCode == "Server.Security.AccessDenied") {
                    broker.dispatchEvent(new SecurityEvent(SecurityEvent.ACCESS_DENIED, SecurityEvent.ACCESS_DENIED));
                    return true;
                }
            }
            return false;
        }
    }
}