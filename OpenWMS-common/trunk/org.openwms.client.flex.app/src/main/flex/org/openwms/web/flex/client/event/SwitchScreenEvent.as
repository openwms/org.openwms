/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.event
{
	import com.adobe.cairngorm.control.CairngormEvent;

	/**
	 * A SwitchScreenEvent.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class SwitchScreenEvent extends CairngormEvent
	{
		public static const SHOW_STARTSCREEN:String = "ShowStartscreen";
		public static const SHOW_LOCATION_VIEW:String = "ShowLocationView";
		public static const SHOW_LOCATIONGROUP_VIEW:String = "ShowLocationGroupView";
		public static const SHOW_USER_MGMT_VIEW:String = "ShowUserManagementView";
		public function SwitchScreenEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}