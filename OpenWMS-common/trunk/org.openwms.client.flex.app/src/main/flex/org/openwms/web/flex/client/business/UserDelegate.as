/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import org.openwms.common.domain.system.usermanagement.User;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	
	/**
	 * A UserDelegate.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class UserDelegate
	{
		private var responder:IResponder;
		private var service:Object;
			
		public function UserDelegate(responder:IResponder):void {
			this.responder = responder;
			this.service = ServiceLocator.getInstance().getRemoteObject("userService");
		}
		public function getUsers():void {
			var call:AsyncToken = service.findAll();
			call.addResponder(responder);
		}
		public function addUser():void {
			var call:AsyncToken = service.getTemplate("PSEUDO");
			call.addResponder(responder);
		}
		public function saveUser(user:User):void {
			var call:AsyncToken = service.save(user);
			call.addResponder(responder);
		}
		public function deleteUser(user:User):void {
			var call:AsyncToken = service.remove(user);
			call.addResponder(responder);
		}

	}
}