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
    import org.openwms.common.domain.Rule;
    import org.openwms.common.domain.TransportUnitType;
    import org.openwms.common.domain.TypePlacingRule;
    import org.openwms.common.domain.TypeStackingRule;
    import org.openwms.web.flex.client.common.event.TransportUnitTypeEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;

    /**
     * A TransportUnitTypeDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 771 $
     */
    [Name("transportUnitTypeDelegate")]
    [ManagedEvent(name="LOAD_ALL_TRANSPORT_UNIT_TYPES")]
    public class TransportUnitTypeDelegate {
    	
        [In]
        [Bindable]
        public var tideContext:Context;

        [In]
        [Bindable]
    	public var commonModelLocator:CommonModelLocator;
        private var transportUnitType:TransportUnitType;

        public function TransportUnitTypeDelegate():void { }

        /**
         * Call to load all TransportUnitTypes from the service.
         */
        [Observer("LOAD_ALL_TRANSPORT_UNIT_TYPES")]
        public function getTransportUnitTypes():void {
        	tideContext.transportUnitService.getAllTransportUnitTypes(onTransportUnitTypesLoaded, onFault);
        }
        private function onTransportUnitTypesLoaded(event:TideResultEvent):void {
            commonModelLocator.allTransportUnitTypes = event.result as ArrayCollection;        	
        }
        
        /**
         * Call to create a new TransportUnitType.
         */
        [Observer("CREATE_TRANSPORT_UNIT_TYPE")]
        public function createTransportUnitType(event:TransportUnitTypeEvent):void {
            if (event.data != null) {
                tideContext.transportUnitService.createTransportUnitType(event.data as TransportUnitType, onTransportUnitTypeCreated, onFault);
            }
        }
        private function onTransportUnitTypeCreated(event:TideResultEvent):void {
            dispatchEvent(new TransportUnitTypeEvent(TransportUnitTypeEvent.LOAD_ALL_TRANSPORT_UNIT_TYPES));
        }

        /**
         * Call to delete a TransportUnitType.
         */
        [Observer("DELETE_TRANSPORT_UNIT_TYPE")]
        public function deleteTransportUnitTypes(event:TransportUnitTypeEvent):void {
            if (event.data != null) {
                tideContext.transportUnitService.deleteTransportUnitTypes(event.data as ArrayCollection, onTransportUnitTypeDeleted, onFault);
            }
        }
        private function onTransportUnitTypeDeleted(event:TideResultEvent):void {
            dispatchEvent(new TransportUnitTypeEvent(TransportUnitTypeEvent.LOAD_ALL_TRANSPORT_UNIT_TYPES));
        }

        /**
         * Call to save an already existing TransportUnitType.
         */
        [Observer("SAVE_TRANSPORT_UNIT_TYPE")]
        public function saveTransportUnitType(event:TransportUnitTypeEvent):void {
            if (event.data != null) {
                tideContext.transportUnitService.saveTransportUnitType(event.data as TransportUnitType, onTransportUnitTypeSaved, onFault);
            }
        }
        private function onTransportUnitTypeSaved(event:TideResultEvent):void {
            dispatchEvent(new TransportUnitTypeEvent(TransportUnitTypeEvent.LOAD_ALL_TRANSPORT_UNIT_TYPES));
        }

        /**
         * Lazy load all Rules belonging to a TransportUnitType.
         */
        [Observer("LOAD_TUT_RULES")]
        public function loadRules(event:TransportUnitTypeEvent):void {
            if (event.data != null) {
            	transportUnitType = event.data as TransportUnitType;
                tideContext.transportUnitService.loadRules(transportUnitType.type, onRulesLoaded, onFault);
            }
        }
        private function onRulesLoaded(event:TideResultEvent):void {
            var rules:ArrayCollection = event.result as ArrayCollection;
            for each (var rule:Rule in rules) {
                if (rule is TypePlacingRule) {
                    transportUnitType.typePlacingRules.addItem(rule);
                }
                if (rule is TypeStackingRule) {
                    transportUnitType.typeStackingRules.addItem(rule);
                }
            }
        }

        private function onFault(event:TideFaultEvent):void {
            trace("Error executing operation on Transport Unit service:" + event.fault);
            Alert.show("Error executing operation on Transport Unit service");
        }
    }
}