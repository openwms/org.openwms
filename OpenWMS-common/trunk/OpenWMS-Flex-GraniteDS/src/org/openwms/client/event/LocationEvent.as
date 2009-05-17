package org.openwms.client.event
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class LocationEvent extends CairngormEvent
	{
		public static const LOAD_ALL_LOCATIONS:String = "LoadAllLocations";
		
		public function LocationEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}