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
package org.openwms.web.flex.client.common.command
{
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    import mx.rpc.events.FaultEvent;
    
    import org.openwms.common.domain.TransportUnit;
    import org.openwms.web.flex.client.common.business.TransportUnitDelegate;
    import org.openwms.web.flex.client.common.event.CommonSwitchScreenEvent;

    /**
     * A CreateTransportUnitCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class CreateTransportUnitCommand implements IResponder, ICommand
    {

        public function CreateTransportUnitCommand()
        {
            super();
        }

        public function result(data:Object):void
        {
        	new CommonSwitchScreenEvent(CommonSwitchScreenEvent.SHOW_TRANSPORTUNIT_VIEW).dispatch();
        }

        public function fault(info:Object):void
        {
            var fault:FaultEvent = info as FaultEvent;
            Alert.show("Could not create TransportUnit");
        }

        public function execute(event:CairngormEvent):void
        {
            var delegate:TransportUnitDelegate = new TransportUnitDelegate(this)
            delegate.createTransportUnit(event.data as TransportUnit);
        }

    }
}