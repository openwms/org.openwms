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
	 * A UserEvent.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class UserEvent extends CairngormEvent
	{
		public static const LOAD_ALL_USERS:String = "LoadAllUsers";
		public static const ADD_USER:String = "AddUser";
		public static const USER_ADDED:String = "UserAdded";
		public static const SAVE_USER:String = "SaveUser";
		public static const DELETE_USER:String = "DeleteUser";
		
		public function UserEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}