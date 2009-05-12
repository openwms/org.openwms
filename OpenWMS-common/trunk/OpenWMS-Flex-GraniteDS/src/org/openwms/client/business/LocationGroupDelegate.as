package org.openwms.client.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	public class LocationGroupDelegate
	{
			private var responder:IResponder;
			private var service:Object;
			
			public function LocationGroupDelegate(responder:IResponder):void {
				this.responder = responder;
				this.service = ServiceLocator.getInstance().getRemoteObject("locationGroupService");
			}
			public function getLocationGroups():void {
				var call:AsyncToken = service.getLocationGroupsAsList();
				call.addResponder(responder);
			}

	}
}