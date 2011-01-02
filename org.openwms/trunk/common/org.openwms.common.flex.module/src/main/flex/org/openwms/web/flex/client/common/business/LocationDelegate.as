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
    import org.openwms.common.domain.Location;
    import org.openwms.common.domain.LocationType;
    import org.openwms.web.flex.client.common.event.LocationEvent;
    import org.openwms.web.flex.client.common.event.LocationTypeEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;

    [Name("locationDelegate")]
    [ManagedEvent(name="LOAD_ALL_LOCATION_TYPES")]
    [ManagedEvent(name="LOAD_ALL_LOCATIONS")]
    /**
     * A LocationDelegate serves as a controller and is responsible for all interactions with the service layer
     * regarding the handling with Locations and LocationTypes.
     * Fires Tide events : LOAD_ALL_LOCATION_TYPES, LOAD_ALL_LOCATIONS
     * Is named as : locationDelegate
     *
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class LocationDelegate {
    	
        [In]
        [Bindable]
        /**
         * Injected TideContext.
         */
        public var tideContext:Context;
	    [In]
	    [Bindable]
	    /**
	     * Injected Model.
	     */
	    public var commonModelLocator:CommonModelLocator;            

        /**
         * Constructor.
         */
        public function LocationDelegate():void { }

        [Observer("LOAD_ALL_LOCATIONS")]
        /**
         * Loads all Locations from the service layer.
         * Tide event observers : LOAD_ALL_LOCATIONS
         */
        public function getLocations():void {
            tideContext.locationService.getAllLocations(onLocationsLoaded, onFault);
        }
        private function onLocationsLoaded(event:TideResultEvent):void {
            commonModelLocator.allLocations = event.result as ArrayCollection;
        }

        [Observer("CREATE_LOCATION")]
        /**
         * Creates a new Location by calling the corresponding service.
         * Tide event observers : CREATE_LOCATION
         * 
         * @param event A LocationEvent that stores the new Location in its data field.
         */
        public function createLocation(event:LocationEvent):void {
            tideContext.locationService.addEntity(event.data as Location, onLocationCreated, onFault);
        }
        private function onLocationCreated(event:TideResultEvent):void {
            dispatchEvent(new LocationEvent(LocationEvent.LOAD_ALL_LOCATIONS));
        }

        [Observer("DELETE_LOCATION")]
        /**
         * Removes an existing Location by calling the corresponding service.
         * Tide event observers : DELETE_LOCATION
         * 
         * @param event A LocationEvent that stores the Location to be removed in its data field.
         */
        public function deleteLocation(event:LocationEvent):void {
            tideContext.locationService.remove(event.data as Location, onLocationDeleted, onFault);
        }
        private function onLocationDeleted(event:TideResultEvent):void {
            dispatchEvent(new LocationEvent(LocationEvent.LOAD_ALL_LOCATIONS));
        }

        [Observer("SAVE_LOCATION")]
        /**
         * Updates an existing Location by calling the corresponding service.
         * Tide event observers : SAVE_LOCATION
         * 
         * @param event A LocationEvent that stores the Location to be updated in its data field.
         */
        public function saveLocation(event:LocationEvent):void {
            tideContext.locationService.save(event.data as Location, onLocationSaved, onFault);
        }
        private function onLocationSaved(event:TideResultEvent):void {
            dispatchEvent(new LocationEvent(LocationEvent.LOAD_ALL_LOCATIONS));
        }

        [Observer("LOAD_ALL_LOCATION_TYPES")]
        /**
         * Loads all LocationTypes from the service.
         * Tide event observers : LOAD_ALL_LOCATION_TYPES
         */
        public function getLocationTypes():void {
            trace("Loading all LocationTypes....");
            tideContext.locationService.getAllLocationTypes(onLocationTypesLoaded, onFault);
        }
        private function onLocationTypesLoaded(event:TideResultEvent):void {
            trace("LocationTypes loaded, result:"+event.result);
            commonModelLocator.allLocationTypes = event.result as ArrayCollection;
        }
        
        [Observer("CREATE_LOCATION_TYPE")]
        /**
         * Creates a new LocationType by calling the corresponding service.
         * Tide event observers : CREATE_LOCATION_TYPE
         * 
         * @param event A LocationTypeEvent that stores the new LocationType in its data field.
         */
        public function createLocationType(event:LocationTypeEvent):void {
            tideContext.locationService.createLocationType(event.data as LocationType, onLocationTypeCreated, onFault);
        }
        private function onLocationTypeCreated(event:TideResultEvent):void {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        [Observer("DELETE_LOCATION_TYPE")]
        /**
         * Removes an existing LocationType by calling the corresponding service.
         * Tide event observers : DELETE_LOCATION_TYPE
         * 
         * @param event A LocationTypeEvent that stores the LocationType to be removed in its data field.
         */
        public function deleteLocationType(event:LocationTypeEvent):void {
            tideContext.locationService.deleteLocationTypes(event.data as ArrayCollection, onLocationTypesDeleted, onFault);
        }
        private function onLocationTypesDeleted(event:TideResultEvent):void {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        [Observer("SAVE_LOCATION_TYPE")]
        /**
         * Updates an existing LocationType by calling the corresponding service.
         * Tide event observers : SAVE_LOCATION_TYPE
         * 
         * @param event A LocationTypeEvent that stores the LocationType to be updated in its data field.
         */
        public function saveLocationType(event:LocationTypeEvent):void {
            tideContext.locationService.saveLocationType(event.data as LocationType, onLocationTypesSaved, onFault);
        }
        private function onLocationTypesSaved(event:TideResultEvent):void {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        private function onFault(event:TideFaultEvent):void {
        	trace("Error executing operation on Location service:"+event.fault);
            Alert.show("Error executing operation on Location service");
        }
    }
}