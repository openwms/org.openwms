package org.openwms.client.command
{
	import com.adobe.cairngorm.commands.ICommand;
	import com.adobe.cairngorm.control.CairngormEvent;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;
	import mx.rpc.IResponder;
	import mx.rpc.events.ResultEvent;
	
	import org.openwms.client.business.LocationDelegate;
	import org.openwms.client.model.ModelLocator;
	import org.openwms.common.domain.Location;

	public class ShowLocationViewCommand implements ICommand, IResponder
	{
		[Bindable]
		private var modelLocator:ModelLocator = ModelLocator.getInstance();

		public function ShowLocationViewCommand()
		{
			super();
		}

		public function execute(event:CairngormEvent):void
		{
			trace("Show LocationView");
			var delegate:LocationDelegate = new LocationDelegate(this)
			delegate.getLocations();
			modelLocator.mainViewStackIndex = ModelLocator.MAIN_VIEW_STACK_LOCATION_VIEW;
		}
		
		public function result(event:Object):void {
			var rawResult:ArrayCollection = (event as ResultEvent).result as ArrayCollection;
			modelLocator.allLocations = (event as ResultEvent).result as ArrayCollection;
		}
		
		public function fault(event:Object):void {
			Alert.show("Fault in ["+this+"] Errormessage : "+event);
		}
	
	}
}