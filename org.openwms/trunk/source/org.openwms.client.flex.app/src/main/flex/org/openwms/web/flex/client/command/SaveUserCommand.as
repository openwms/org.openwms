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
    import mx.rpc.Fault;
    import mx.rpc.IResponder;

    import org.openwms.web.flex.client.business.UserDelegate;
    import org.openwms.web.flex.client.helper.UserHelper;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.common.domain.system.usermanagement.User;

    /**
     * A SaveUserCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    public class SaveUserCommand implements ICommand, IResponder
    {
        [Bindable]
        private var modelLocator:ModelLocator = ModelLocator.getInstance();

        public function SaveUserCommand()
        {
            super();
        }

        public function execute(event:CairngormEvent):void
        {
            var delegate:UserDelegate = new UserDelegate(this);
            delegate.saveUser(modelLocator.selectedUser);
        }

        public function result(data:Object):void
        {
            var user:User = User(data.result);
            if (user != null)
            {
                var len:int = modelLocator.allUsers.length;
                var found:Boolean = false;
                for (var i:int = 0; i < len; i++)
                {
                    if (user.id == modelLocator.allUsers[i].id)
                    {
                        found = true;
                        modelLocator.allUsers[i] = user;
                        trace("User found and replaced in List");
                        break;
                    }
                }
                if (!found)
                {
                    Alert.show("New user saved");
                    modelLocator.allUsers.addItemAt(user, modelLocator.allUsers.length);
                }
                else
                {
                    Alert.show("User data saved");
                }
            }
            UserHelper.traceUser(user);
        }

        public function fault(info:Object):void
        {
            Alert.show("Error while saving userdata");
        }

    }
}