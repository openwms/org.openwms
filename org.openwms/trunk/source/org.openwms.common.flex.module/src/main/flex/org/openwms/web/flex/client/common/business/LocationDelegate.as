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
package org.openwms.web.flex.client.common.business
{
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    
    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.Location;
    import org.openwms.common.domain.LocationType;
    import org.openwms.web.flex.client.common.event.LocationEvent;
    import org.openwms.web.flex.client.common.event.LocationTypeEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;

    /**
     * A LocationDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Name("locationDelegate")]
    [ManagedEvent(name="LOAD_ALL_LOCATION_TYPES")]
    [ManagedEvent(name="LOAD_ALL_LOCATIONS")]
    public class LocationDelegate
    {
        [In]
        [Bindable]
        public var tideContext:Context;
	    [In]
	    [Bindable]
	    public var commonModelLocator:CommonModelLocator;            

        public function LocationDelegate():void
        {
        }

        /**
         * Call to load all Locations from the service.
         */
        [Observer("LOAD_ALL_LOCATIONS")]
        public function getLocations():void
        {
            tideContext.locationService.getAllLocations(onLocationsLoaded, onFault);
        }
        private function onLocationsLoaded(event:TideResultEvent):void
        {
            commonModelLocator.allLocations = event.result as ArrayCollection;
        }

        [Observer("CREATE_LOCATION")]
        public function createLocation(event:LocationEvent):void
        {
            tideContext.locationService.addEntity(event.data as Location, onLocationCreated, onFault);
        }
        private function onLocationCreated(event:TideResultEvent):void
        {
            dispatchEvent(new LocationEvent(LocationEvent.LOAD_ALL_LOCATIONS));
        }

        [Observer("DELETE_LOCATION")]
        public function deleteLocation(event:LocationEvent):void
        {
            tideContext.locationService.remove(event.data as Location, onLocationDeleted, onFault);
        }
        private function onLocationDeleted(event:TideResultEvent):void
        {
            dispatchEvent(new LocationEvent(LocationEvent.LOAD_ALL_LOCATIONS));
        }

        [Observer("SAVE_LOCATION")]
        public function saveLocation(event:LocationEvent):void
        {
            tideContext.locationService.save(event.data as Location, onLocationSaved, onFault);
        }
        private function onLocationSaved(event:TideResultEvent):void
        {
            dispatchEvent(new LocationEvent(LocationEvent.LOAD_ALL_LOCATIONS));
        }

        /**
         * Call to load all LocationTypes from the service.
         */
        [Observer("LOAD_ALL_LOCATION_TYPES")]
        public function getLocationTypes():void
        {
            tideContext.locationService.getAllLocationTypes(onLocationTypesLoaded, onFault);
        }
        private function onLocationTypesLoaded(event:TideResultEvent):void
        {
            commonModelLocator.allLocationTypes = event.result as ArrayCollection;
        }
        
        [Observer("CREATE_LOCATION_TYPE")]
        public function createLocationType(event:LocationTypeEvent):void
        {
            tideContext.locationService.createLocationType(event.data as LocationType, onLocationTypeCreated, onFault);
        }
        private function onLocationTypeCreated(event:TideResultEvent):void
        {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        [Observer("DELETE_LOCATION_TYPE")]
        public function deleteLocationType(event:LocationTypeEvent):void
        {
            tideContext.locationService.deleteLocationTypes(event.data as ArrayCollection, onLocationTypesDeleted, onFault);
        }
        private function onLocationTypesDeleted(event:TideResultEvent):void
        {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        [Observer("SAVE_LOCATION_TYPE")]
        public function saveLocationType(event:LocationTypeEvent):void
        {
            tideContext.locationService.saveLocationType(event.data as LocationType, onLocationTypesSaved, onFault);
        }
        private function onLocationTypesSaved(event:TideResultEvent):void
        {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        private function onFault(event:TideFaultEvent):void
        {
            Alert.show("Error executing operation on Location service");
        }
    }
}