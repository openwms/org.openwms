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
package org.openwms.web.flex.client.tms.command
{
    import com.adobe.cairngorm.commands.ICommand;
    import com.adobe.cairngorm.control.CairngormEvent;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import mx.rpc.IResponder;
    
    import org.openwms.tms.domain.order.TransportOrder;
    import org.openwms.web.flex.client.tms.business.TransportsDelegate;
    import org.openwms.web.flex.client.tms.model.TMSModelLocator;

    /**
     * A DeleteTransportOrderCommand.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class DeleteTransportOrderCommand implements ICommand, IResponder
    {
        [Bindable]
        private var modelLocator:TMSModelLocator = TMSModelLocator.getInstance();

        public function DeleteTransportOrderCommand()
        {
            super();
        }

        public function execute(event:CairngormEvent):void
        {
            var delegate:TransportsDelegate = new TransportsDelegate(this)
            delegate.deleteTransportOrder(event.data as TransportOrder);
        }

        public function result(data:Object):void
        {
            trace("TransportOrder deleted");
        }

        public function fault(info:Object):void
        {
            Alert.show("Fault in [" + this + "] Errormessage : " + info);
        }

    }
}