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
package org.openwms.web.flex.client.common.view {
    import flash.system.ApplicationDomain;

    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import mx.containers.ViewStack;
    import mx.controls.MenuBar;

    import org.granite.tide.ITideModule;
    import org.granite.tide.Tide;
    import org.granite.tide.spring.Spring;
    import org.granite.tide.spring.Context;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.common.business.LocationDelegate;
    import org.openwms.web.flex.client.common.business.LocationGroupDelegate;
    import org.openwms.web.flex.client.common.business.TransportUnitDelegate;
    import org.openwms.web.flex.client.common.business.TransportUnitTypeDelegate;
    import org.openwms.web.flex.client.common.model.CommonModelLocator;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.module.CommonModule;

    [Name("CommonAppBase")]
    public class CommonAppBase extends CommonModule implements IApplicationModule, ITideModule {

        [In]
        [Bindable]
        public var modelLocator : ModelLocator;
        [Bindable]
        public var commonMenuBar : MenuBar;
        [Bindable]
        public var commonViewStack : ViewStack;
        [In]
        public var tideContext : Context;

        /**
         * Constructor.
         */
        public function CommonAppBase() {
            super();
        }

        /**
         * This method is called first from the ModuleLocator to do the first initial work. The module registers itself on
         * the main applicationDomain, that means the context of the main application is extended with the subcontext of
         * this module.
         */
        public function start(applicationDomain : ApplicationDomain=null) : void {
            trace("Add context to main context in applicationDomain : " + applicationDomain);
            Spring.getInstance().addModule(CommonAppBase, applicationDomain);
        }

        /**
         * In a second step Tide tries to start the module calling this method. Here are all components added to the TideContext.
         */
        public function init(tide : Tide) : void {
            trace("Add components to Tide context");
            tide.addComponents([CommonModelLocator, TransportUnitTypeDelegate, TransportUnitDelegate, LocationDelegate, LocationGroupDelegate]);
        }

        /**
         * This method returns a list of menu items which shall be expaned to the main
         * application menu bar.
         */
        public function getMainMenuItems() : XMLListCollection {
            return commonMenuBar.dataProvider as XMLListCollection;
        }

        /**
         * This method returns the name of the module as unique String identifier.
         */
        public function getModuleName() : String {
            return "OPENWMS.ORG CORE MODULE";
        }

        /**
         * This method returns the current version of the module as String.
         */
        public function getModuleVersion():String {
        	return "1.0.0";
        }

        /**
         * This method returns a list of items which are handled as SecuityObjects.
         * A SecurityObject can be assigned to a Role and is monitored by the SecurityHandler
         * to allow or deny certain functionality within the user interface.
         */
        public function getSecurityObjects() : ArrayCollection {
            return new ArrayCollection();
        }

        /**
         * This method returns a list of views which shall be populated to the parent
         * application.
         */
        public function getViews() : ArrayCollection {
            return new ArrayCollection(commonViewStack.getChildren());
        }

        /**
         * Do additional initial work when the module is loaded.
         */
        public function initializeModule(applicationDomain : ApplicationDomain=null) : void {
            trace("Initialize module : " + getModuleName());
        }

        /**
         * Do addtional cleanup work before the module is unloaded.
         */
        public function destroyModule() : void {
            trace("Destroying module : " + getModuleName());
            Spring.getInstance().removeModule(CommonAppBase);
        }
    }
}