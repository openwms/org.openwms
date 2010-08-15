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
    import org.openwms.web.flex.client.common.event.LoadLocationGroupsEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;
    import org.openwms.web.flex.client.model.TreeNode;

    /**
     * A LocationGroupDelegate.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Name("locationGroupDelegate")]
    public class LocationGroupDelegate
    {
        [In]
        [Bindable]
        public var tideContext:Context;
        [In]
        [Bindable]
        public var commonModelLocator:CommonModelLocator;            

        public function LocationGroupDelegate():void
        {
        }

        [Observer("LOAD_ALL_LOCATION_GROUPS")]
        public function getLocationGroups(event:LoadLocationGroupsEvent):void
        {
            tideContext.locationGroupService.getLocationGroupsAsList(onLocationGroupsLoaded, onFault);
        }

        private function onLocationGroupsLoaded(event:TideResultEvent):void
        {
            trace("Load Location Groups");
        	commonModelLocator.allLocationGroups = event.result as ArrayCollection;
            // Setup tree if not set before
            if (null == commonModelLocator.locationGroupTree)
            {
                commonModelLocator.locationGroupTree = new TreeNode();
                commonModelLocator.locationGroupTree.build(commonModelLocator.allLocationGroups);
            }
        }

        private function onFault(event:TideFaultEvent):void
        {
            Alert.show("Error when loading Location Groups");
        }
    }
            
}