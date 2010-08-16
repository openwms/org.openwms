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
    import org.openwms.web.flex.client.tms.event.TransportOrderEvent;
    import org.openwms.web.flex.client.tms.model.TMSModelLocator;

    /**
     * A TransportsDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    [Name("transportsDelegate")]
    [ManagedEvent(name="LOAD_ALL_LOCATION_TYPES")]
    [ManagedEvent(name="LOAD_ALL_LOCATIONS")]
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
         * Call to load all Locations from the service.
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
            tideContext.transportService.createTransportOrder(transportOrder.transportUnit.barcode, transportOrder.targetLocationGroup, transportOrder.targetLocation, "HIGH", onTransportCreated, onFault);
        }
        private function onTransportCreated(event:TideResultEvent):void
        {
        	trace("TransportOrder successfully created");
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
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