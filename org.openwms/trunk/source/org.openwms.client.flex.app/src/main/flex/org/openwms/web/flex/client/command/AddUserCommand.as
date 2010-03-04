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
package org.openwms.web.flex.client.command
{
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;

    import mx.controls.Alert;
    import mx.rpc.IResponder;

    import org.openwms.web.flex.client.business.UserDelegate;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.common.domain.system.usermanagement.User;

    /**
     * A AddUserCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public class AddUserCommand implements ICommand, IResponder
    {
        [Bindable]
        [Embed(source="/assets/images/userDefaultUniSex.jpg")]
        private var uniSexImage:Class;
        [Bindable]
        private var modelLocator:ModelLocator = ModelLocator.getInstance();

        public function AddUserCommand()
        {
            super();
        }

        public function execute(event:CairngormEvent):void
        {
            var delegate:UserDelegate = new UserDelegate(this);
            delegate.addUser();
        }

        public function result(data:Object):void
        {
            var user:User = User(data.result);
            user.username = "";
            if (user.userDetails.image == null)
            {
            }
            //user.userDetails.image = uniSexImageBytes; 
            //modelLocator.allUsers.addItem(user);
            modelLocator.selectedUser = user;
            trace("Dispatching event");
            //new UserEvent(UserEvent.USER_ADDED).dispatch();
        }

        public function fault(info:Object):void
        {
            Alert.show("Fault in [" + this + "] Errormessage : " + info);
        }

    }
}