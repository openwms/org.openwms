/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.web.flex.client.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;
	
	/**
	 * A LocationDelegate.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
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