/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.event.SecurityEvent
{
	import flash.events.Event;

	/**
	 * A SecurityEvent.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class SecurityEvent extends Event
	{
		private var _user:String;
		private var _pass:String;

		public function SecurityEvent( event_type:String, bubbles:Boolean=true, cancelable:Boolean=false ):void {
			super( event_type, bubbles, cancelable );
		}

		public function set user( pValue:String ):void {
			_user = pValue;
		}
		public function get user():String {
			return _user;
		}
		public function set pass( pValue:String ):void {
			_pass = pValue;
		}
		public function get pass():String {
			return _pass;
		}

		public function get loginStatus():Boolean {
			if ( (_user == "openwms") && (_pass == "openwms") ) 
				return true;
			else
				return false;
		}
	}
}  
