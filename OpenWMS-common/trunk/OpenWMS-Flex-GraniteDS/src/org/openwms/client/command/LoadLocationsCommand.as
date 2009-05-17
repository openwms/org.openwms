package org.openwms.client.command
{
	import com.adobe.cairngorm.control.CairngormEvent;
	import com.adobe.cairngorm.commands.ICommand;
	import mx.collections.ArrayCollection;
	import org.openwms.client.model.ModelLocator;
	import org.openwms.client.business.LocationDelegate;
	import mx.rpc.IResponder;
	import mx.controls.Alert;
	import mx.rpc.events.ResultEvent;

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