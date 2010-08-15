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
package org.openwms.web.flex.client.common.view
{
    import flash.system.ApplicationDomain;
    
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import mx.containers.ViewStack;
    import mx.controls.MenuBar;
    
    import org.granite.tide.ITideModule;
    import org.granite.tide.Tide;
    import org.granite.tide.spring.Spring;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.common.business.LocationDelegate;
    import org.openwms.web.flex.client.common.business.LocationGroupDelegate;
    import org.openwms.web.flex.client.common.business.TransportUnitDelegate;
    import org.openwms.web.flex.client.common.business.TransportUnitTypeDelegate;
    import org.openwms.web.flex.client.common.event.TransportUnitTypeEvent;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.module.CommonModule;

    [Name]
    [ManagedEvent(name="LOAD_ALL_TRANSPORT_UNIT_TYPES")]
    public class CommonAppBase extends CommonModule implements IApplicationModule, ITideModule
    {

        [In]
        [Bindable]
        public var modelLocator:ModelLocator;
        [In]
        [Bindable]
        public var commonModelLocator:CommonModelLocator;
        [Bindable]
        public var commonMenuBar:MenuBar;
        [Bindable]
        public var commonViewStack:ViewStack;

        /**
         * Constructor.
         */
        public function CommonAppBase()
        {
            super();
        }
        
        public function start(applicationDomain:ApplicationDomain = null):void
        {
        	Spring.getInstance().addModule(CommonAppBase, applicationDomain);
        }
        
        public function init(tide:Tide):void {
            tide.addComponents([CommonModelLocator, TransportUnitTypeDelegate, TransportUnitDelegate, LocationDelegate, LocationGroupDelegate]);
        }

        /**
         * This method returns a list of menu items which shall be expaned to the main
         * application menu bar.
         */
        public function getMainMenuItems():XMLListCollection
        {
            return commonMenuBar.dataProvider as XMLListCollection;
        }

        /**
         * This method returns the name of the module as unique String identifier.
         */
        public function getModuleName():String
        {
            return "OPENWMS.ORG CORE MODULE";
        }

        /**
         * This method returns a list of items which are handled as SecuityObjects.
         * A SecurityObject can be assigned to a Role and is monitored by the SecurityHandler
         * to allow or deny certain functionality within the user interface.
         */
        public function getSecurityObjects():ArrayCollection
        {
            return new ArrayCollection();
        }

        /**
         * This method returns a list of views which shall be populated to the parent
         * application.
         */
        public function getViews():ArrayCollection
        {
            return new ArrayCollection(commonViewStack.getChildren());
        }

        /**
         * Do additional initial work when the module is loaded.
         */
        public function initializeModule(applicationDomain:ApplicationDomain = null):void
        {
            trace("Initialize module : "+getModuleName());
            loadAllStaticEntities();
        }

        private function loadAllStaticEntities():void
        {
            dispatchEvent(new TransportUnitTypeEvent(TransportUnitTypeEvent.LOAD_ALL_TRANSPORT_UNIT_TYPES));
        }

        /**
         * Do addtional cleanup work before the module is unloaded.
         */
        public function destroyModule():void
        {
            trace("Destroying module : "+getModuleName());
        	Spring.getInstance().removeModule(CommonAppBase);
        }
   }
}