/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.client.control
{
	import com.adobe.cairngorm.control.FrontController;
	
	import org.openwms.client.command.*;
	import org.openwms.client.event.*;

	/**
	 * A MainController.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	public class MainController extends FrontController
	{
		public function MainController():void {
			super();
			setupEventHandler();
		}
		private function setupEventHandler():void {
			this.addCommand( UserEvent.LOAD_ALL_USERS, LoadUsersCommand );
			this.addCommand( UserEvent.ADD_USER, AddUserCommand );
			this.addCommand( UserEvent.SAVE_USER, SaveUserCommand );
			this.addCommand( UserEvent.DELETE_USER, DeleteUserCommand );
			this.addCommand( SwitchScreenEvent.SHOW_STARTSCREEN, ShowStartscreenCommand );
			this.addCommand( SwitchScreenEvent.SHOW_LOCATION_VIEW, ShowLocationViewCommand );
			this.addCommand( SwitchScreenEvent.SHOW_LOCATIONGROUP_VIEW, ShowLocationGroupCommand );
			this.addCommand( SwitchScreenEvent.SHOW_USER_MGMT_VIEW, ShowUserManagementViewCommand );
			this.addCommand( LoadLocationGroupsEvent.LOAD_ALL_LOCATION_GROUPS, LoadLocationGroupsCommand );
			this.addCommand( LocationEvent.LOAD_ALL_LOCATIONS, LoadLocationsCommand );
		}
	}
} 
