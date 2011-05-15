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
    import org.openwms.web.flex.client.common.event.LocationGroupEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;
    import org.openwms.web.flex.client.common.model.TreeNode;
    import org.openwms.common.domain.LocationGroup;

    [Name("locationGroupDelegate")]
    [ManagedEvent(name="LOAD_ALL_LOCATION_GROUPS")]
    /**
     * A LocationGroupDelegate.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class LocationGroupDelegate {
        [In]
        [Bindable]
        /**
         * Injected TideContext.
         */
        public var tideContext:Context;
        [In]
        [Bindable]
        /**
         * Inject a model to work on.
         */
        public var commonModelLocator:CommonModelLocator;            

        /**
         * Default constructor.
         */
        public function LocationGroupDelegate():void { }

        [Observer("LOAD_ALL_LOCATION_GROUPS")]
        /**
         * Load all LocationGroups from the service.
         *
         * @param event Not used here
         */
        public function getLocationGroups(event:LocationGroupEvent):void {
            commonModelLocator.registerEventListeners();
            tideContext.locationGroupService.getLocationGroupsAsList(onLocationGroupsLoaded, onFault);
        }
        private function onLocationGroupsLoaded(event:TideResultEvent):void {
            commonModelLocator.allLocationGroups = event.result as ArrayCollection;
            // Setup tree if not set before
            if (null == commonModelLocator.locationGroupTree) {
                commonModelLocator.locationGroupTree = new TreeNode();
                commonModelLocator.locationGroupTree.build(commonModelLocator.allLocationGroups);
            }
        }

        [Observer("SAVE_LOCATION_GROUP")]
        /**
         * Save an existing LocationGroup.
         * Tide event observers : SAVE_LOCATION_GROUP
         *
         * @param event The raised LocationGroupEvent that stores the LocationGroup within the data property.
         */
        public function saveLocationGroup(event : LocationGroupEvent) : void {
            if (event.data is LocationGroup) {
                tideContext.locationGroupService.save(event.data as LocationGroup, onLocationGroupSaved, onFault);
            }
        }
        private function onLocationGroupSaved(event : TideResultEvent) : void {
            dispatchEvent(new LocationGroupEvent(LocationGroupEvent.LOAD_ALL_LOCATION_GROUPS));
        }

        [Observer("LG.CHANGE_STATE")]
        /**
         * Change states of an existing LocationGroup.
         * Tide event observers : LG.CHANGE_STATE
         *
         * @param event The raised LocationGroupEvent that stores the LocationGroup within the data property.
         */
        public function changeState(event : LocationGroupEvent) : void {
            if (event.data is LocationGroup) {
                tideContext.locationGroupService.changeGroupState(event.data as LocationGroup, onStateChanged, onFault);
            }
        }
        private function onStateChanged(event : TideResultEvent) : void {
            dispatchEvent(new LocationGroupEvent(LocationGroupEvent.LOAD_ALL_LOCATION_GROUPS));
        }

        private function onFault(event:TideFaultEvent):void {
            trace("Error executing operation on Location Group service:" + event.fault);
            Alert.show("Error executing an operation on Location Group service");
        }
    }
}

