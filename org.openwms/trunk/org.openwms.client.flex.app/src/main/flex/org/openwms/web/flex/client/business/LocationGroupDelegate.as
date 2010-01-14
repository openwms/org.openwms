/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.web.flex.client.business
{
	import com.adobe.cairngorm.business.ServiceLocator;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.IResponder;

	/**
	 * A LocationGroupDelegate.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
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