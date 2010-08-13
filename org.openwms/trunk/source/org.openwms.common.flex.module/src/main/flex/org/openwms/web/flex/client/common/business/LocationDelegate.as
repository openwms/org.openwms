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
    import com.adobe.cairngorm.business.ServiceLocator;
    
    import mx.collections.ArrayCollection;
    import mx.rpc.AsyncToken;
    import mx.rpc.IResponder;
    
    import org.granite.tide.spring.Context;
    import org.granite.tide.events.TideResultEvent;
    import org.openwms.common.domain.Location;
    import org.openwms.common.domain.LocationType;
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
        [Observer("LOAD_ALL_TRANSPORT_UNIT_TYPES")]
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
        	trace("LOAD_ALL_LOCATION_TYPES delegate");
            tideContext.locationService.getLocationTypes(onLocationTypesLoaded);
        }

        private function onLocationTypesLoaded(event:TideResultEvent):void
        {
        	trace("LOCATION TYPES LOADED");
            commonModelLocator.allLocationTypes = event.result as ArrayCollection;
        }
        
        public function createLocationType(locationType:LocationType):void
        {
            var call:AsyncToken = service.createLocationType(locationType);
            call.addResponder(responder);        	
        }

        public function deleteLocationType(locationTypes:ArrayCollection):void
        {
            var call:AsyncToken = service.deleteLocationTypes(locationTypes);
            call.addResponder(responder);            
        }

        public function saveLocationType(locationType:LocationType):void
        {
            var call:AsyncToken = service.saveLocationType(locationType);
            call.addResponder(responder);            
        }
    }
}