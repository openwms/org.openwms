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
	 * A LoadLocationGroupEvent.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class LoadLocationGroupsEvent extends CairngormEvent
	{
		public static const LOAD_ALL_LOCATION_GROUPS:String = "LoadAllLocationGroups";

		public function LoadLocationGroupsEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}

	}
}