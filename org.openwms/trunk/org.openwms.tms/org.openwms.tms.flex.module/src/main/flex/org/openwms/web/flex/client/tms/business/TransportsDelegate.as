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
package org.openwms.web.flex.client.tms.business {

    import flash.events.Event;

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;

    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.Location;
    import org.openwms.common.domain.LocationGroup;
    import org.openwms.tms.domain.order.TransportOrder;
    import org.openwms.tms.domain.values.TransportOrderState;
    import org.openwms.web.flex.client.tms.event.TransportOrderEvent;
    import org.openwms.web.flex.client.tms.model.TMSModelLocator;

    [Name("transportsDelegate")]
    [ManagedEvent(name="LOAD_TRANSPORT_ORDERS")]
    /**
     * A TransportsDelegate.
     *
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class TransportsDelegate {

        [In]
        [Bindable]
        /**
         * Injected TideContext.
         */
        public var tideContext : Context;
        [In]
        [Bindable]
        /**
         * Inject a model to work on.
         */
        public var tmsModelLocator : TMSModelLocator;

        /**
         * Default constructor.
         */
        public function TransportsDelegate() : void { }

        [Observer("LOAD_TRANSPORT_ORDERS")]
        /**
         * Load all TransportOrders from the backend.
         */
        public function getAllTransports() : void {
            tideContext.transportService.findAll(onTransportsLoaded, onFault);
        }

        private function onTransportsLoaded(event : TideResultEvent) : void {
            tmsModelLocator.allTransportOrders = event.result as ArrayCollection;
        }

        [Observer("CREATE_TRANSPORT_ORDER")]
        /**
         * Create a new TransportOrder.
         *
         * @param event Expected to hold the new TransportOrder in its data field
         */
        public function createTransportOrder(event : TransportOrderEvent) : void {
            var transportOrder : TransportOrder = event.data as TransportOrder;
            tideContext.transportService.createTransportOrder(transportOrder.transportUnit.barcode, transportOrder.targetLocationGroup, transportOrder.targetLocation, transportOrder.priority, onTransportCreated, onFault);
        }

        private function onTransportCreated(event : TideResultEvent) : void {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
        }

        [Observer("CANCEL_TRANSPORT_ORDER")]
        /**
         * Turn the state of TransportOrders in a new state.
         *
         * @param event Expected to store an object with the list of TransportOrder called ids,
         *              and the new state with name state
         */
        public function cancelTransportOrder(event : TransportOrderEvent) : void {
            tideContext.transportService.cancelTransportOrders(event.data.ids as ArrayCollection, event.data.state as TransportOrderState, onTransportsCanceled, onFault);
        }

        private function onTransportsCanceled(event : TideResultEvent) : void {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
            if ((event.result as ArrayCollection).length > 0) {
                Alert.show("Not all Transport Orders could be canceled!");
            }
        }

        [Observer("REDIRECT_TRANSPORT_ORDER")]
        /**
         * Redirect one or more TransportOrders to a new target.
         *
         * @param event Expected to store an object with the list of TransportOrders called ids,
         *              the new target LocationGroup as targetLocationGroup and the new target
         *              Location as targetLocation
         */
        public function redirectTransportOrders(event : TransportOrderEvent) : void {
            tideContext.transportService.redirectTransportOrders(event.data.ids as ArrayCollection, event.data.targetLocationGroup as LocationGroup, event.data.targetLocation as Location, onTransportsRedirected, onFault);
        }

        private function onTransportsRedirected(event : TideResultEvent) : void {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
            if ((event.result as ArrayCollection).length > 0) {
                Alert.show("Not all Transport Orders could be redirected!");
            }
        }

        [Observer("DELETE_TRANSPORT_ORDER")]
        /**
         * Call to remove a TransportOrder.
         *
         * @param event Expected to hold the TransportOrder to be deleted in its data field
         */
        public function deleteTransportOrder(event : TransportOrderEvent) : void {
            var transportOrder : TransportOrder = event.data as TransportOrder;
            tideContext.transportService.remove(transportOrder, onTransportDeleted, onFault);
        }

        private function onTransportDeleted(event : TideResultEvent) : void {
            dispatchEvent(new TransportOrderEvent(TransportOrderEvent.LOAD_TRANSPORT_ORDERS));
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on Transports service:" + event.fault);
            Alert.show("Error executing operation on Transports service");
        }
    }
}

