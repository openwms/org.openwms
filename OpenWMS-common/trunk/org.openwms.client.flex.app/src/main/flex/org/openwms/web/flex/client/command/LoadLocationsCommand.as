/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.web.flex.client.command
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.adobe.cairngorm.commands.ICommand;
	import mx.collections.ArrayCollection;
	import org.openwms.web.flex.client.model.ModelLocator;
	import org.openwms.web.flex.client.business.LocationDelegate;
	import mx.rpc.IResponder;
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;

	/**
	 * A LoadLocationsCommand.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class LoadLocationsCommand implements IResponder, ICommand
	{
		[Bindable]
		private var modelLocator:ModelLocator = ModelLocator.getInstance();

		public function LoadLocationsCommand()
		{
			super();
		}

		public function result(data:Object):void
		{
			var rawResult:ArrayCollection = (data as ResultEvent).result as ArrayCollection;
			modelLocator.allLocations = (data as ResultEvent).result as ArrayCollection;
		}
		
		public function fault(info:Object):void
		{
			Alert.show("Fault in ["+this+"] Errormessage : "+info);
		}
		
		public function execute(event:CairngormEvent):void
		{
			if (modelLocator.allLocations.length == 0) {
				var delegate:LocationDelegate = new LocationDelegate(this)
				delegate.getLocations();
			}
		}
		
	}
}