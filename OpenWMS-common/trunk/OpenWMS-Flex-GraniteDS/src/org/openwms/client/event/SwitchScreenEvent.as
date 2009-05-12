package org.openwms.client.event
{
	import com.adobe.cairngorm.control.CairngormEvent;

	public class SwitchScreenEvent extends CairngormEvent
	{
		public static const SHOW_STARTSCREEN:String = "ShowStartscreen";
		public static const SHOW_LOCATION_VIEW:String = "ShowLocationView";
		public static const SHOW_LOCATIONGROUP_VIEW:String ="ShowLocationGroupView";
		public function SwitchScreenEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			//TODO: implement function
			super(type, bubbles, cancelable);
		}
		
	}
}