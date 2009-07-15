/*
 * OpenWMS, the Open Warehouse Management System
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.command {
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;

	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;

	import org.openwms.client.business.UserDelegate;
	import org.openwms.client.model.ModelLocator;
	import org.openwms.common.domain.system.usermanagement.User;

	/**
	 * A DeleteUserCommand.
	 *
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class DeleteUserCommand implements ICommand, IResponder {
		[Bindable]
		private var modelLocator:ModelLocator=ModelLocator.getInstance();

		public function DeleteUserCommand() {
			super();
		}

		public function execute(event:CairngormEvent):void {
			if (isNaN(modelLocator.selectedUser.id)) {
				modelLocator.selectedUser=modelLocator.allUsers.getItemAt(0) as User;
				return;
			}
			var delegate:UserDelegate=new UserDelegate(this);
			delegate.deleteUser(modelLocator.selectedUser);
		}

		private function removeUserFromList(user:User):void {
			var len:int=modelLocator.allUsers.length;
			for (var i:int=0; i < len; i++) {
				if (user.id == modelLocator.allUsers[i].id) {
					modelLocator.allUsers.removeItemAt(i);
					if (modelLocator.allUsers.length > 0) {
						modelLocator.selectedUser=modelLocator.allUsers[0];
					}
					break;
				}
			}
		}

		public function result(data:Object):void {
			var event:ResultEvent=data as ResultEvent;
			removeUserFromList(modelLocator.selectedUser);
		}

		public function fault(info:Object):void {
			Alert.show("Fault in [" + this + "] Errormessage : " + info);
		}

	}
}