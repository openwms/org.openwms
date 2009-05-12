package org.openwms.client.event
{
	import com.adobe.cairngorm.control.CairngormEvent;
	
	public class LoadLocationGroupsEvent extends CairngormEvent
	{
		public static const LOAD_ALL_LOCATION_GROUPS:String = "LoadAllLocationGroups";

		public function LoadLocationGroupsEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}

	}
}