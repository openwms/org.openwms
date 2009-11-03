/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	import org.openwms.client.business.UserDelegate;
	import org.openwms.client.model.ModelLocator;

	/**
	 * A ShowUserManagementViewCommand.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class ShowUserManagementViewCommand implements ICommand, IResponder {
		[Bindable]
		private var modelLocator:ModelLocator = ModelLocator.getInstance();

		public function ShowUserManagementViewCommand() {
			super();
		}

		public function execute(event:CairngormEvent):void {
			var delegate:UserDelegate = new UserDelegate(this)
			delegate.getUsers();
			modelLocator.mainViewStackIndex = ModelLocator.MAIN_VIEW_STACK_USER_MGMT_VIEW;
		}
		
		public function result(event:Object):void {
			var rawResult:ArrayCollection = (event as ResultEvent).result as ArrayCollection;
			modelLocator.allUsers = (event as ResultEvent).result as ArrayCollection;
		}
		
		public function fault(event:Object):void {
			Alert.show("Fault in ["+this+"] Errormessage : "+event);
		}
	
	}
}