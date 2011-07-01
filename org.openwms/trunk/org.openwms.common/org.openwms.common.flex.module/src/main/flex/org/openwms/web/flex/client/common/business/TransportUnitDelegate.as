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
package org.openwms.web.flex.client.common.business {

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;

    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.TransportUnit;
    import org.openwms.web.flex.client.common.event.TransportUnitEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;

    [Name("transportUnitDelegate")]
    [ManagedEvent(name="LOAD_TRANSPORT_UNITS")]
    [ManagedEvent(name="TRANSPORT_UNIT_CREATED")]
    /**
     * A TransportUnitDelegate.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class TransportUnitDelegate {

        [In]
        [Bindable]
        /**
         * Injected TideContext.
         */
        public var tideContext:Context;
        [Inject]
        [Bindable]
        /**
         * Injected Model.
         */
        public var commonModelLocator:CommonModelLocator;            

        /**
         * Default constructor.
         */
        public function TransportUnitDelegate( ) : void { }

        [Observer("LOAD_TRANSPORT_UNITS")]
        public function getTransportUnits(event : TransportUnitEvent) : void {
            tideContext.transportUnitService.getAllTransportUnits(onTransportUnitsLoaded, onFault);
        }
        private function onTransportUnitsLoaded(event:TideResultEvent):void {
            commonModelLocator.allTransportUnits = event.result as ArrayCollection;
        }

        [Observer("CREATE_TRANSPORT_UNIT")]
        public function createTransportUnit(event : TransportUnitEvent) : void {
            var transportUnit:TransportUnit = event.data as TransportUnit;
            tideContext.transportUnitService.createTransportUnit(transportUnit.barcode, transportUnit.transportUnitType, transportUnit.actualLocation.locationId, onTransportUnitCreated, onFault);
        }
        private function onTransportUnitCreated(event : TideResultEvent) : void {
            dispatchEvent(new TransportUnitEvent(TransportUnitEvent.LOAD_TRANSPORT_UNITS));
            dispatchEvent(new TransportUnitEvent(TransportUnitEvent.TRANSPORT_UNIT_CREATED));
        }

        [Observer("DELETE_TRANSPORT_UNIT")]
        public function deleteTransportUnits(event : TransportUnitEvent) : void {
            tideContext.transportUnitService.deleteTransportUnits(event.data as ArrayCollection, onTransportUnitDeleted, onFault);
        }
        private function onTransportUnitDeleted(event : TideResultEvent) : void {
            dispatchEvent(new TransportUnitEvent(TransportUnitEvent.LOAD_TRANSPORT_UNITS));
        }

        [Observer("SAVE_TRANSPORT_UNIT")]
        public function saveTransportUnits(event : TransportUnitEvent) : void {
            tideContext.transportUnitService.save(event.data as TransportUnit, onTransportUnitSaved, onFault);
        }
        private function onTransportUnitSaved(event : TideResultEvent) : void {
            dispatchEvent(new TransportUnitEvent(TransportUnitEvent.LOAD_TRANSPORT_UNITS));
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on Transport Unit service:" + event.fault);
            Alert.show("Error executing operation on Transport Unit service");
        }
    }
}

