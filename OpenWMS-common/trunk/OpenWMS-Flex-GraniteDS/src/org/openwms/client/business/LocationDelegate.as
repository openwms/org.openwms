package org.openwms.client.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	
	public class LocationDelegate
	{
			private var responder:IResponder;
			private var service:Object;
			
			public function LocationDelegate(responder:IResponder):void {
				this.responder = responder;
				this.service = ServiceLocator.getInstance().getRemoteObject("locationService");
			}
			public function getLocations():void {
				var call:AsyncToken = service.findAll();
				call.addResponder(responder);
			}

	}
}