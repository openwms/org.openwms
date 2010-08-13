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
package org.openwms.web.flex.client.tms.view
{

    import flash.system.ApplicationDomain;
    
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import mx.containers.ViewStack;
    import mx.controls.MenuBar;
    import mx.events.FlexEvent;
    
    import org.granite.tide.spring.Spring;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.control.MainController;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.module.CommonModule;
    import org.openwms.web.flex.client.tms.event.TMSSwitchScreenEvent;
    import org.openwms.web.flex.client.tms.event.TransportOrderEvent;

    public class TMSAppBase extends CommonModule implements IApplicationModule
    {

        [Bindable]
        public var menuCollection:ArrayCollection;
        [Bindable]
        public var menuBarItemsCollection:XMLListCollection;
        [Bindable]
        protected var modelLocator:ModelLocator = ModelLocator.getInstance();
        [Bindable]
        public var tmsMenuBar:MenuBar;
        [Bindable]
        public var tmsViewStack:ViewStack;
        [Bindable]
        private var mainController:MainController = MainController.getInstance();

        /**
         * A backing class for modules coded in XML.
         */
        public function TMSAppBase()
        {
            super();
            addEventListener(FlexEvent.APPLICATION_COMPLETE, creationCompleteHandler);
            addEventListener(FlexEvent.CREATION_COMPLETE, creationCompleteHandler);
        }

        public function creationCompleteHandler(event:FlexEvent):void
        {
            trace("TTT:");
        }


        protected override function initApp():void
        {
            trace("InitApp called");
        }

        /**
         * This method returns a list of menu items which shall be expaned to the main
         * application menu bar.
         */
        public function getMainMenuItems():XMLListCollection
        {
            return tmsMenuBar.dataProvider as XMLListCollection;
        }

        /**
         * This method returns the name of the module as unique String identifier.
         */
        public function getModuleName():String
        {
            return "TMS";
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

        public function getViews():ArrayCollection
        {
            return new ArrayCollection(tmsViewStack.getChildren());
        }

        public function initializeModule(applicationDomain:ApplicationDomain = null):void
        {
        	trace("Initialize module : "+getModuleName());
        	Spring.getInstance().addModule(TMSAppBase, applicationDomain);
        }

        public function destroyModule():void
        {
        }
    }
}
