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
package org.openwms.web.flex.client.business {
    import com.adobe.cairngorm.business.ServiceLocator;
    
    import flash.events.EventDispatcher;
    
    import mx.rpc.AsyncToken;
    import mx.rpc.IResponder;
    
    import org.openwms.common.domain.system.usermanagement.Role;
    import org.openwms.web.flex.client.model.Constants;

    /**
     * A RoleDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class RoleDelegate extends EventDispatcher {
        private var responder : IResponder;
        private var service : Object;

        public function RoleDelegate(responder : IResponder) : void {
            this.responder = responder;
            this.service = ServiceLocator.getInstance().getRemoteObject(Constants.USERMGMT_SERVICE);
        }

        public function getRoles() : void {
            var call : AsyncToken = service.findAllRoles();
            call.addResponder(responder);
        }

        public function addRole(role : Role) : void {
            var call : AsyncToken = service.addEntity(role);
            call.addResponder(responder);
        }

        public function saveRole(role : Role) : void {
            var call : AsyncToken = service.saveRole(role);
            call.addResponder(responder);
        }

        public function deleteRole(role : Role) : void {
            var call : AsyncToken = service.removeRole(role);
            call.addResponder(responder);
        }

    }
}