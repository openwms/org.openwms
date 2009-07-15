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

	import org.openwms.client.business.UserDelegate;
	import org.openwms.client.model.ModelLocator;
	import org.openwms.common.domain.system.usermanagement.User;

	/**
	 * A AddUserCommand.
	 *
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class AddUserCommand implements ICommand, IResponder {
		[Bindable]
		[Embed(source="/assets/images/userDefaultUniSex.jpg")]
		private var uniSexImage:Class;
		[Bindable]
		private var modelLocator:ModelLocator=ModelLocator.getInstance();

		public function AddUserCommand() {
			super();
		}

		public function execute(event:CairngormEvent):void {
			var delegate:UserDelegate=new UserDelegate(this);
			delegate.addUser();
		}

		public function result(data:Object):void {
			var user:User=User(data.result);
			user.username="";
			if (user.userDetails.image == null) {
			}
			//user.userDetails.image = uniSexImageBytes; 
			//modelLocator.allUsers.addItem(user);
			modelLocator.selectedUser=user;
			trace("Dispatching event");
			//new UserEvent(UserEvent.USER_ADDED).dispatch();
		}

		public function fault(info:Object):void {
			Alert.show("Fault in [" + this + "] Errormessage : " + info);
		}

	}
}