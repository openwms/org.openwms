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
package org.openwms.web.flex.client.tms.business
{
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    
    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.tms.domain.order.TransportOrder;
    import org.openwms.tms.domain.values.TransportOrderState;
    import org.openwms.web.flex.client.tms.event.TransportOrderEvent;
    import org.openwms.web.flex.client.tms.model.TMSModelLocator;

    /**
     * A TransportsDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    [Name("transportsDelegate")]
    [ManagedEvent(name="LOAD_TRANSPORT_ORDERS")]
    public class TransportsDelegate
    {
        [In]
        [Bindable]
        public var tideContext:Context;
        [In]
        [Bindable]
        public var tmsModelLocator:TMSModelLocator;            

        public function TransportsDelegate():void
        {
        }

        /**
         * Call to load all TransportOrders from the service.
         */
        [Observer("LOAD_TRANSPORT_ORDERS")]
        public function getAllTransports():void
        {
        	tideContext.transportService.findAll(onTransportsLoaded, onFault);
        }
        private function onTransportsLoaded(event:TideResultEvent):void
        {
            tmsModelLocator.allTransportOrders = event.result as ArrayCollection;
        }
        
        [Observer("CREATE_TRANSPORT_ORDER")]
        public function createTransportOrder(event:TransportOrderEvent):void
        {
        	var transportOrder:TransportOrder = event.data as TransportOrder; 
            tideContext.transportService.createTransportOrder(transportOrder.transportUnit.barcode, transportOrder.targetLocationGroup, transportOrder.targetLocation, transportOrder.priority, onTransportCreated, onFault);
        }
        private function onTransportCreated(event:TideResultEvent):void
        {
        	trace("TransportOrder successfully created");
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
        }

        /**
         * Call to cancel one or more TransportOrders.
         */
        [Observer("CANCEL_TRANSPORT_ORDER")]
        public function cancelTransportOrder(event:TransportOrderEvent):void
        {
            tideContext.transportService.cancelTransportOrders(event.data.ids as ArrayCollection, event.data.state as TransportOrderState, onTransportsCanceled, onFault);
        }
        private function onTransportsCanceled(event:TideResultEvent):void
        {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
        	if ((event.result as ArrayCollection).length > 0)
        	{
        		Alert.show("Not all Transport Orders could be canceled!");
        	}
        }

        /**
         * Call to redirect one or more TransportOrders.
         */
        [Observer("REDIRECT_TRANSPORT_ORDER")]
        public function redirectTransportOrders(event:TransportOrderEvent):void
        {
            tideContext.transportService.redirectTransportOrders(event.data as ArrayCollection, onTransportsRedirected, onFault);
        }
        private function onTransportsRedirected(event:TideResultEvent):void
        {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
            if ((event.result as ArrayCollection).length > 0)
            {
                Alert.show("Not all Transport Orders could be redirected!");
            }
        }

          
        [Observer("DELETE_TRANSPORT_ORDER")]
        public function deleteTransportOrder(event:TransportOrderEvent):void
        {
            var transportOrder:TransportOrder = event.data as TransportOrder; 
            tideContext.transportService.remove(transportOrder, onTransportDeleted, onFault);
        }
        private function onTransportDeleted(event:TideResultEvent):void
        {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
        }

        private function onFault(event:TideFaultEvent):void
        {
            Alert.show("Error executing operation on Transports service");
        }
    }
}