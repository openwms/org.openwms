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
    import mx.rpc.AsyncToken;
    import mx.rpc.IResponder;
    
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.Location;
    import org.openwms.common.domain.LocationType;
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
    public class LocationDelegate
    {
        [In]
        [Bindable]
        public var tideContext:Context;
	    [In]
	    [Bindable]
	    public var commonModelLocator:CommonModelLocator;            

        private var responder:IResponder;
        private var service:Object;

        public function LocationDelegate():void
        {
        }

        /**
         * Call to load all LocationTypes from the service.
         */
        public function getLocations():void
        {
            tideContext.locationService.getLocationTypes(onLocationTypesLoaded);
        }

        
        
        public function createLocation(location:Location):void
        {
            var call:AsyncToken = service.addEntity(location);
            call.addResponder(responder);           
        }

        public function deleteLocation(location:Location):void
        {
            var call:AsyncToken = service.remove(location);
            call.addResponder(responder);            
        }

        public function saveLocation(location:Location):void
        {
            var call:AsyncToken = service.save(location);
            call.addResponder(responder);            
        }

        /**
         * Call to load all LocationTypes from the service.
         */
        [Observer("LOAD_ALL_LOCATION_TYPES")]
        public function getLocationTypes():void
        {
            tideContext.locationService.getAllLocationTypes(onLocationTypesLoaded);
        }
        private function onLocationTypesLoaded(event:TideResultEvent):void
        {
            commonModelLocator.allLocationTypes = event.result as ArrayCollection;
        }
        
        [Observer("CREATE_LOCATION_TYPE")]
        public function createLocationType(event:LocationTypeEvent):void
        {
            tideContext.locationService.createLocationType(event.data as LocationType, onLocationTypeCreated);
        }
        private function onLocationTypeCreated(event:TideResultEvent):void
        {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        [Observer("DELETE_LOCATION_TYPE")]
        public function deleteLocationType(event:LocationTypeEvent):void
        {
            tideContext.locationService.deleteLocationTypes(event.data as ArrayCollection, onLocationTypesDeleted);
        }
        private function onLocationTypesDeleted(event:TideResultEvent):void
        {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }

        [Observer("SAVE_LOCATION_TYPE")]
        public function saveLocationType(event:LocationTypeEvent):void
        {
            tideContext.locationService.saveLocationType(event.data as LocationType, onLocationTypesSaved);
        }
        private function onLocationTypesSaved(event:TideResultEvent):void
        {
            dispatchEvent(new LocationTypeEvent(LocationTypeEvent.LOAD_ALL_LOCATION_TYPES));
        }
    }
}