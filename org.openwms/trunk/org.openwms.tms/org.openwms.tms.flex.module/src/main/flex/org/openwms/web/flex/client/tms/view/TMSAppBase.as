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
package org.openwms.web.flex.client.tms.view {

    import flash.system.ApplicationDomain;

    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import mx.containers.ViewStack;
    import mx.controls.MenuBar;

    import org.granite.tide.ITideModule;
    import org.granite.tide.Tide;
    import org.granite.tide.spring.Spring;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.module.CommonModule;
    import org.openwms.web.flex.client.tms.business.TransportsDelegate;
    import org.openwms.web.flex.client.tms.model.TMSModelLocator;

    [Name("TMSAppBase")]
    /**
     * Base class of TMS Module.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class TMSAppBase extends CommonModule implements IApplicationModule, ITideModule {

        [Inject]
        [Bindable]
        public var modelLocator:ModelLocator;
        [Bindable]
        public var menuCollection:ArrayCollection;
        [Bindable]
        public var menuBarItemsCollection:XMLListCollection;
        [Bindable]
        public var tmsMenuBar:MenuBar;
        [Bindable]
        public var tmsViewStack:ViewStack;

        /**
         * Default constructor.
         */
        public function TMSAppBase() {
            super();
        }

        /**
         * This method is called first from the ModuleLocator to do the first initial work. The module registers itself on
         * the main applicationDomain, that means the context of the main application is extended with the subcontext of
         * this module.
         */
        public function start(applicationDomain:ApplicationDomain = null):void {
            trace("Starting Tide context in applicationDomain : "+applicationDomain);
            Spring.getInstance().addModule(TMSAppBase, applicationDomain);
        }

        /**
         * In a second step Tide tries to start the module calling this method. Here are all components added to the TideContext.
         *
         * @param tide not used here
         */
        public function init(tide:Tide):void {
            trace("Add components to Tide context");
            tide.addComponents([TMSModelLocator, TransportsDelegate]);
        }

        /**
         * This method returns a list of menu items which shall be expaned to the main
         * application menu bar.
         */
        public function getMainMenuItems():XMLListCollection {
            return tmsMenuBar.dataProvider as XMLListCollection;
        }

        /**
         * This method returns the name of the module as unique String identifier.
         */
        public function getModuleName():String {
            return "OPENWMS.ORG TMS MODULE";
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
        public function getSecurityObjects():ArrayCollection {
            return new ArrayCollection();
        }

        /**
         * This method returns a list of views which shall be populated to the parent
         * application.
         */
        public function getViews():ArrayCollection {
            return new ArrayCollection(tmsViewStack.getChildren());
        }

        /**
         * Do additional initial work when the module is loaded.
         */
        public function initializeModule(applicationDomain:ApplicationDomain = null):void {
            trace("Initialize module : " + getModuleName());
        }

        /**
         * Do addtional cleanup work before the module is unloaded.
         */
        public function destroyModule():void {
            trace("Destroying module : " + getModuleName());
            Spring.getInstance().removeModule(TMSAppBase);
        }
    }
}


