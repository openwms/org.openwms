/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.web.flex.client.event
{
	import com.adobe.cairngorm.control.CairngormEvent;

	/**
	 * A LocationEvent.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class LocationEvent extends CairngormEvent
	{
		public static const LOAD_ALL_LOCATIONS:String = "LoadAllLocations";
		
		public function LocationEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}