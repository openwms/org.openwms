/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client.control
{
    import com.adobe.cairngorm.control.FrontController;

    import org.openwms.web.flex.client.command.*;
    import org.openwms.web.flex.client.event.*;

    /**
     * A MainController.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 235 $
     */
    public class MainController extends FrontController
    {
        public function MainController():void
        {
            super();
            setupEventHandler();
        }

        private function setupEventHandler():void
        {
            this.addCommand(UserEvent.LOAD_ALL_USERS, LoadUsersCommand);
            this.addCommand(UserEvent.ADD_USER, AddUserCommand);
            this.addCommand(UserEvent.SAVE_USER, SaveUserCommand);
            this.addCommand(UserEvent.DELETE_USER, DeleteUserCommand);
            this.addCommand(SwitchScreenEvent.SHOW_STARTSCREEN, ShowStartscreenCommand);
            this.addCommand(SwitchScreenEvent.SHOW_LOCATION_VIEW, ShowLocationViewCommand);
            this.addCommand(SwitchScreenEvent.SHOW_LOCATIONGROUP_VIEW, ShowLocationGroupCommand);
            this.addCommand(SwitchScreenEvent.SHOW_USER_MGMT_VIEW, ShowUserManagementViewCommand);
            this.addCommand(SwitchScreenEvent.SHOW_TRANSPORTUNIT_VIEW, ShowTransportUnitCommand);
            this.addCommand(LoadLocationGroupsEvent.LOAD_ALL_LOCATION_GROUPS, LoadLocationGroupsCommand);
            this.addCommand(LocationEvent.LOAD_ALL_LOCATIONS, LoadLocationsCommand);
        }
    }
}
