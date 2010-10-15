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
package org.openwms.web.flex.client.view {
    import flash.display.DisplayObject;
    import flash.events.Event;
    import flash.system.ApplicationDomain;
    
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import mx.containers.ViewStack;
    import mx.controls.Alert;
    import mx.controls.MenuBar;
    import mx.core.Application;
    import mx.events.MenuEvent;
    import mx.logging.ILogger;
    import mx.logging.Log;
    import mx.managers.DragManager;
    import mx.managers.PopUpManager;
    import mx.modules.ModuleManager;
    
    import org.granite.tide.spring.Context;
    import org.granite.tide.spring.Identity;
    import org.granite.tide.spring.Spring;
    import org.openwms.common.domain.values.Weight;
    import org.openwms.tms.domain.order.TransportOrder;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.business.PropertyDelegate;
    import org.openwms.web.flex.client.business.RoleDelegate;
    import org.openwms.web.flex.client.business.UserDelegate;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.SwitchScreenEvent;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.module.ModuleLocator;
    import org.openwms.web.flex.client.util.DisplayUtility;
    import org.openwms.web.flex.client.view.dialogs.LoginView;

    public class AppBase extends Application {

        [In]
        [Bindable]
        public var modelLocator : ModelLocator;
        [In]
        [Bindable]
        public var moduleLocator : ModuleLocator;
        [In]
        [Bindable]
        public var identity : Identity;
        
        [Bindable]
        public var loginView : LoginView;
        [Bindable]
        public var mainMenuBar : MenuBar;
        [Bindable]
        public var stdMenu : XMLList;
        [Bindable]
        public var appViewStack : ViewStack;

        private var applicationDomain : ApplicationDomain = new ApplicationDomain(ApplicationDomain.currentDomain);

        // Manager classes are loaded to the application domain
        private var moduleManager : ModuleManager;

        private var popUpManager : PopUpManager;

        private var dragManager : DragManager;

        private static var _link : Array = [org.openwms.tms.domain.order.TransportOrder,org.openwms.common.domain.values.Weight];

        [Bindable]
        public var tideContext : Context = Spring.getInstance().getSpringContext();
        Spring.getInstance().addComponents([ModelLocator, ModuleLocator, UserDelegate, RoleDelegate, PropertyDelegate]);

        private static var log : ILogger = Log.getLogger("org.openwms.web.flex.client.view.App");

        /**
         * Suppress warnings and add a constructor.
         */
        public function AppBase() {
        }

        /**
         * Called in pre-initialize phase of the application.
         *
         * Put your code here, to initialize 3rd party frameworks.
         */
        public function preInit(event : Event) : void {
            Spring.getInstance().initApplication();
            //Chimp.load(null);
        }

        /**
         * Called in initialize phase of the application.
         *
         * Put your code here, to initialize application specific structures.
         */
        public function init() : void {
            tideContext.mainAppUI = this;
        }

        /**
         * Called when a menu item of the main menu bar is clicked.
         */
        public function onMenuChange(event : MenuEvent) : void {
            if (appViewStack.getChildByName(event.item.@action) == null) {
                Alert.show("Screen with name " + event.item.@action + " not found!");
                return;
            }
            dispatchEvent(new SwitchScreenEvent(event.item.@action));
            modelLocator.actualView = event.item.@action;
            appViewStack.selectedIndex = appViewStack.getChildIndex(appViewStack.getChildByName(event.item.@action));
        }

        /**
         * Called when logout is proceeded and the application should switch to the initial screen (with login dialog).
         */
        [Observer("APP_LOGOUT")]
        public function logout(event : ApplicationEvent) : void {
            modelLocator.actualView = SwitchScreenEvent.SHOW_STARTSCREEN;
            appViewStack.selectedIndex = DisplayUtility.getView(SwitchScreenEvent.SHOW_STARTSCREEN, appViewStack);
            tideContext.raiseEvent(ApplicationEvent.UNLOAD_ALL_MODULES);
        }

        /**
         * Called when the user logged in successfully.
         */
        [Observer("APP_LOGIN_OK")]
        public function loggedIn(event : ApplicationEvent) : void {
        	trace("User logged in successfully");
            tideContext.raiseEvent(ApplicationEvent.LOAD_ALL_MODULES);
        }
        /**
         * In the case a Module was successfully unloaded, menues and views must be re-organized.
         */
        [Observer("MODULE_UNLOADED")]
        public function moduleUnloaded(event : ApplicationEvent) : void {
            if (event.data != null && event.data is IApplicationModule) {
                var appModule : IApplicationModule = (event.data as IApplicationModule);
                trace("Module was unloaded : " + appModule.getModuleName());
                mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
                removeViewsFromStack(appModule);
            }
        }

        /**
         * In the case a Module was successfully loaded menues and views must be re-organized.
         */
        [Observer("MODULE_LOADED")]
        public function moduleConfigChanged(event : ApplicationEvent) : void {
            if (event.data != null && event.data is IApplicationModule) {
                var appModule : IApplicationModule = (event.data as IApplicationModule);
                trace("Module was loaded : " + appModule.getModuleName());
                mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
                addViewsToViewStack(appModule);
                appModule.initializeModule(applicationDomain);
            }
        }

        /**
         * When all modules are loaded and started properly an MODULES_CONFIGURED event is fired,
         * the standard menu is loaded then by default.
         */
        [Observer("MODULES_CONFIGURED")]
        public function modulesConfigured(event : ApplicationEvent) : void {
            mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
            modelLocator.securityObjectNames = getStdSecurityObjects();
        }

        private function getStdSecurityObjects() : ArrayCollection {
            // Go through all components that implement ISecured and add them to the modelLocator.secured...
            return new ArrayCollection(new Array({}));
        }

        /**
         * This method rebuilds the viewStack of the application, and should be called,
         * in case application modules are loaded or unloaded.
         */
        private function removeViewsFromStack(module : IApplicationModule) : void {
            trace("Remove views from main ViewStack for module : " + module.getModuleName());
            var views : ArrayCollection = module.getViews();
            for each (var view : DisplayObject in views) {
                if (appViewStack.contains(view)) {
                    appViewStack.removeChild(appViewStack.getChildByName(view.name));
                }
            }
        }

        /**
         * This method rebuilds the viewStack of the application, and should be called,
         * in case application modules are loaded or unloaded.
         */
        private function addViewsToViewStack(module : IApplicationModule) : void {
            trace("Add menu items to main ViewStack for module : " + module.getModuleName());
            var views : ArrayCollection = module.getViews();
            for each (var view : DisplayObject in views) {
                appViewStack.addChild(view as DisplayObject);
            }
        }

    }
}