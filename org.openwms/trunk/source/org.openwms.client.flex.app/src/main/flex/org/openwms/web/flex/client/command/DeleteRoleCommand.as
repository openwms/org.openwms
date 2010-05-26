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
package org.openwms.web.flex.client.command {
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;

    import mx.controls.Alert;
    import mx.rpc.IResponder;

    import org.openwms.web.flex.client.business.RoleDelegate;
    import org.openwms.web.flex.client.event.RoleEvent;

    /**
     * A DeleteRoleCommand.
     *
     * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
     * @version $Revision: 840 $
     */
    public class DeleteRoleCommand extends AbstractCommand implements ICommand, IResponder {

        public function DeleteRoleCommand() {
            super();
        }

        public function execute(event : CairngormEvent) : void {
            if (event.data != null) {
                var delegate : RoleDelegate = new RoleDelegate(this);
                delegate.deleteRoles(event.data);
            }
        }

        public function result(data : Object) : void {
            new RoleEvent(RoleEvent.LOAD_ALL_ROLES).dispatch();
        }

        public function fault(event : Object) : void {
            if (onFault(event)) {
                return;
            }
            Alert.show("Coulde not delete Roles");
        }
    }
}
