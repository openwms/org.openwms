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
    import mx.resources.ResourceManager;
    
    import org.granite.tide.spring.Context;
    import org.granite.tide.spring.Identity;
    import org.granite.tide.spring.Spring;
    import org.openwms.common.domain.values.Weight;
    import org.openwms.tms.domain.order.TransportOrder;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.business.I18nDelegate;
    import org.openwms.web.flex.client.business.PropertyDelegate;
    import org.openwms.web.flex.client.business.RoleDelegate;
    import org.openwms.web.flex.client.business.UserDelegate;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.SwitchScreenEvent;
    import org.openwms.web.flex.client.event.I18nEvent;
    import org.openwms.web.flex.client.model.I18nMap;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.module.ModuleLocator;
    import org.openwms.web.flex.client.util.DisplayUtility;
    import org.openwms.web.flex.client.view.dialogs.LoginView;

    /**
     * An AppBase class is the main Flex Application of the CORE framework. This class
     * cares about all the essential stuff like security and Tide framework initialization
     * and instantiates the main menu bar and the view stack.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class AppBase extends Application {

        [In]
        [Bindable]
        /**
         * Injected Model.
         */
        public var modelLocator : ModelLocator;
        [In]
        [Bindable]
        /**
         * Injected Module controller.
         */
        public var moduleLocator : ModuleLocator;
        [In]
        [Bindable]
        /**
         * Injected Tide identity object.
         */
        public var identity : Identity;
        [Bindable]
        /**
         * LoginView defined in the mxml page.
         */
        public var loginView : LoginView;
        [Bindable]
        /**
         * MenuBar defined in the mxml page.
         */
        public var mainMenuBar : MenuBar;
        [Bindable]
        /**
         * Standard menu defined in the mxml page.
         */
        public var stdMenu : XMLList;
        [Bindable]
        /**
         * ViewStack defined in the mxml page.
         */
        public var appViewStack : ViewStack;

        private var applicationDomain : ApplicationDomain = new ApplicationDomain(ApplicationDomain.currentDomain);
        // Manager classes are loaded to the application domain
        private var moduleManager : ModuleManager;
        private var popUpManager : PopUpManager;
        private var dragManager : DragManager;
        private static var log : ILogger = Log.getLogger("org.openwms.web.flex.client.view.App");
        private static var _link : Array = [org.openwms.tms.domain.order.TransportOrder,org.openwms.common.domain.values.Weight];

        [Bindable]
        /**
         * TideContext instance.
         */
        public var tideContext : Context = Spring.getInstance().getSpringContext();
        Spring.getInstance().addComponents([ModelLocator, ModuleLocator, UserDelegate, RoleDelegate, PropertyDelegate, I18nMap, I18nDelegate]);


        /**
         * Suppress warnings and add a constructor.
         */
        public function AppBase() { }

        /**
         * Called in pre-initialize phase of the application.
         * Put your code here, to initialize 3rd party frameworks.
         *
         * @param event
         */
        public function preInit(event : Event) : void {
            Spring.getInstance().initApplication();
        }

        /**
         * Called in initialize phase of the application.
         * Put your code here, to initialize application specific structures.
         */
        public function init() : void {
            tideContext.mainAppUI = this;
            var l:Array = ResourceManager.getInstance().localeChain;
            for each (var s:String in l){
            	trace("LOCALE:"+s);
            } 
            trace("Load all internationalized texts");
            tideContext.raiseEvent(I18nEvent.LOAD_ALL);
        }

        /**
         * Called when a menu item of the main menu bar is clicked.
         *
         * @param event The MenuEvent
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

        [Observer("APP_LOGOUT")]
        /**
         * Called when logout is proceeded and the application should switch to the initial screen (with login dialog).
         * Tide event observers : APP_LOGOUT
         *
         * @param event Unused
         */
        public function logout(event : ApplicationEvent) : void {
            modelLocator.actualView = SwitchScreenEvent.SHOW_STARTSCREEN;
            appViewStack.selectedIndex = DisplayUtility.getView(SwitchScreenEvent.SHOW_STARTSCREEN, appViewStack);
            tideContext.raiseEvent(ApplicationEvent.UNLOAD_ALL_MODULES);
        }

        [Observer("APP_LOCK")]
        /**
         * Called when screen lock is requested and the application should switch to the initial screen (with login dialog).
         * No modules are unloaded.
         * Tide event observers : APP_LOCK
         *
         * @param event Unused
         */
        public function lock(event : ApplicationEvent) : void {
            if (modelLocator.fired) {
                modelLocator.fired = false;
                return;
            }
            modelLocator.SCREEN_LOCKED = true;
            modelLocator.actualView = SwitchScreenEvent.SHOW_STARTSCREEN;
            appViewStack.selectedIndex = DisplayUtility.getView(SwitchScreenEvent.SHOW_STARTSCREEN, appViewStack);
        }

        [Observer("APP_LOGIN_OK")]
        /**
         * Called when the user logged in successfully.
         * Tide event observers : APP_LOGIN_OK
         *
         * @param event Unused
         */
        public function loggedIn(event : ApplicationEvent) : void {
            if (modelLocator.SCREEN_LOCKED) {
                modelLocator.SCREEN_LOCKED = false;
                modelLocator.viewLockedBy = null;
                modelLocator.actualView = modelLocator.viewBeforeLock;
            }
            tideContext.raiseEvent(ApplicationEvent.LOAD_ALL_MODULES);
        }

        [Observer("MODULE_UNLOADED")]
        /**
         * In the case a Module was successfully unloaded, menues and views must be re-organized.
         * Tide event observers : MODULE_UNLOADED
         *
         * @param event event.data stores the IApplicationModule
         */
        public function moduleUnloaded(event : ApplicationEvent) : void {
            if (event.data != null && event.data is IApplicationModule) {
                var appModule : IApplicationModule = (event.data as IApplicationModule);
                mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
                    //removeViewsFromStack(appModule);
            }
        }

        [Observer("MODULE_LOADED")]
        /**
         * In the case a Module was successfully loaded menues and views must be re-organized.
         * Tide event observers : MODULE_LOADED
         *
         * @param event event.data stores the IApplicationModule
         */
        public function moduleConfigChanged(event : ApplicationEvent) : void {
            if (event.data != null && event.data is IApplicationModule) {
                var appModule : IApplicationModule = (event.data as IApplicationModule);
                mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
                addViewsToViewStack(appModule);
                appModule.initializeModule(applicationDomain);
            }
        }

        [Observer("MODULES_CONFIGURED")]
        /**
         * When all modules are loaded and started properly an MODULES_CONFIGURED event is fired,
         * the standard menu is loaded then by default.
         * Tide event observers : MODULES_CONFIGURED
         *
         * @param event Unused
         */
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
            var views : ArrayCollection = module.getViews();
            for each (var view : DisplayObject in views) {
                appViewStack.addChild(view as DisplayObject);
            }
        }

    }
}

