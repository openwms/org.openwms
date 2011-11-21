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
    import mx.logging.targets.TraceTarget;
    import mx.managers.DragManager;
    import mx.managers.PopUpManager;
    import mx.messaging.ChannelSet;
    import mx.messaging.config.ServerConfig;
    import mx.modules.ModuleManager;
    import mx.resources.IResourceManager;
    import mx.resources.ResourceManager;

    import org.granite.rpc.remoting.mxml.SecureRemoteObject;
    import org.granite.tide.spring.Context;
    import org.granite.tide.spring.Identity;
    import org.granite.tide.spring.Spring;
    import org.openwms.common.domain.values.Weight;
    import org.openwms.core.domain.Module;
    import org.openwms.core.domain.system.usermanagement.Grant;
    import org.openwms.tms.domain.order.TransportOrder;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.business.I18nDelegate;
    import org.openwms.web.flex.client.business.PropertyDelegate;
    import org.openwms.web.flex.client.business.RoleDelegate;
    import org.openwms.web.flex.client.business.SecurityDelegate;
    import org.openwms.web.flex.client.business.UserDelegate;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.I18nEvent;
    import org.openwms.web.flex.client.event.PropertyEvent;
    import org.openwms.web.flex.client.event.SwitchScreenEvent;
    import org.openwms.web.flex.client.helper.I18nHelper;
    import org.openwms.web.flex.client.model.Constants;
    import org.openwms.web.flex.client.model.I18nMap;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.model.PreferencesModel;
    import org.openwms.web.flex.client.module.ModuleLocator;
    import org.openwms.web.flex.client.util.DisplayUtility;
    import org.openwms.web.flex.client.util.I18nUtil;
    import org.openwms.web.flex.client.util.SecurityUtil;
    import org.openwms.web.flex.client.util.XMLUtil;
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

        [Inject]
        [Bindable]
        /**
         * Injected Model.
         */
        public var modelLocator : ModelLocator;

        [Inject]
        [Bindable]
        /**
         * Injected Module controller.
         */
        public var moduleLocator : ModuleLocator;

        [Inject]
        [Bindable]
        /**
         * Injected Module controller.
         */
        public var prefs : PreferencesModel;

        [Inject]
        [Bindable]
        /**
         * Injected Tide identity object.
         */
        public var identity : Identity;

        [Bindable]
        /**
         * LoginView defined in the mxml page.
         */
        public var _loginView : LoginView;

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

        [Bindable]
        /**
         * TideContext instance.
         */
        public var tideContext : Context = Spring.getInstance().getSpringContext();
        Spring.getInstance().addComponents([SecurityUtil, ModelLocator, ModuleLocator, UserDelegate, RoleDelegate, PropertyDelegate, I18nMap, I18nDelegate, SecurityDelegate, PreferencesModel]);

        private var applicationDomain : ApplicationDomain = new ApplicationDomain(ApplicationDomain.currentDomain);

        // Manager classes are loaded to the application domain
        private var moduleManager : ModuleManager;

        private var popUpManager : PopUpManager;

        private var dragManager : DragManager;

        private static var log : ILogger = Log.getLogger("org.openwms.web.flex.client.view.App");

        private static var _link : Array = [org.openwms.tms.domain.order.TransportOrder, org.openwms.common.domain.values.Weight];

        [Bindable]
        private var rman : IResourceManager = ResourceManager.getInstance();

        [Bindable]
        private var moduleService : SecureRemoteObject = new SecureRemoteObject("moduleServiceRemote");

        [Bindable]
        private var userService : SecureRemoteObject = new SecureRemoteObject("userServiceRemote");

        [Bindable]
        private var roleService : SecureRemoteObject = new SecureRemoteObject("roleServiceRemote");

        [Bindable]
        private var i18nService : SecureRemoteObject = new SecureRemoteObject("i18nServiceRemote");

        [Bindable]
        private var configurationService : SecureRemoteObject = new SecureRemoteObject("configurationServiceRemote");

        [Bindable]
        private var securityService : SecureRemoteObject = new SecureRemoteObject("securityServiceRemote");

        [Embed(source="/assets/security/secured-objects.xml", mimeType="application/octet-stream")]
        private var _xml : Class;

        private var blacklisted : ArrayCollection = new ArrayCollection();

        /**
         * Suppress warnings and add a constructor.
         */
        public function AppBase() {
        }

        /**
         * Called in pre-initialize phase of the application.
         * Put your code here, to initialize 3rd party frameworks.
         *
         * @param event Unused
         */
        public function preInit(event : Event) : void {
            Spring.getInstance().initApplication();
            //I18nHelper.switchLanguage(modelLocator.availableLocales[0]);
        }

        /**
         * Called within initialize phase of the application.
         * Put your code here, to start application specific structures.
         */
        public function init() : void {
            tideContext.mainAppUI = this;
            setupServices([moduleService, roleService, userService, i18nService, configurationService, securityService]);
            dispatchEvent(new I18nEvent(I18nEvent.LOAD_ALL));
            dispatchEvent(new PropertyEvent(PropertyEvent.LOAD_ALL_PROPERTIES));
            //tideContext.raiseEvent(PropertyEvent.LOAD_ALL_PROPERTIES);
            //tideContext.raiseEvent(I18nEvent.LOAD_ALL);
            readAndMergeGrantsList();
        }

        /**
         * Load all main security objects and merge them with the backend
         */
        private function readAndMergeGrantsList() : void {
            if (blacklisted.length > 0) {
                return;
            }
            var xml : XML = XMLUtil.getXML(new _xml());
            for each (var g : XML in xml.grant) {
                blacklisted.addItem(new Grant(g.name, g.description));
            }
            var event : ApplicationEvent = new ApplicationEvent(ApplicationEvent.MERGE_GRANTS);
            event.data = {moduleName: "APP", grants: blacklisted};
            dispatchEvent(event);
        }

        /**
         * Called when a menu item of the main menu bar is clicked.
         *
         * @param event The MenuEvent
         */
        public function onMenuChange(event : MenuEvent) : void {
            if (appViewStack.getChildByName(event.item.@action) == null) {
                Alert.show(I18nUtil.trans(I18nUtil.APP_ERROR, 'error_screenNameNotFound', [event.item.@action]));
                return;
            }
            dispatchEvent(new SwitchScreenEvent(event.item.@action));
            modelLocator.actualView = event.item.@action;
            appViewStack.selectedIndex = appViewStack.getChildIndex(appViewStack.getChildByName(event.item.@action));
        }

        [Observer("PROPERTY.PROPERTIES_LOADED")]
        /**
         * @param event Unused
         */
        public function onPropertiesLoaded(event : PropertyEvent=null) : void {
            // when props are loaded, go and change to the default language
            if (prefs.getAppPreference(Constants.DEFAULT_LANG) != null) {
                I18nHelper.switchLanguage(prefs.getAppPreference(Constants.DEFAULT_LANG).value);
            }

        }

        [Observer("APP_LOGOUT")]
        /**
         * Called when logout is proceeded and the application should switch to the initial screen (with login dialog).
         * Tide event observers : APP_LOGOUT
         *
         * @param event Unused
         */
        public function logout(event : ApplicationEvent) : void {
            if (modelLocator.fired) {
                modelLocator.fired = false;
                return;
            }
            dispatchEvent(new ApplicationEvent(ApplicationEvent.APP_CLEAR_MODEL));
            tideContext.meta_clearCache();
            modelLocator.actualView = SwitchScreenEvent.SHOW_STARTSCREEN;
            appViewStack.selectedIndex = DisplayUtility.getView(SwitchScreenEvent.SHOW_STARTSCREEN, appViewStack);
            mainMenuBar.dataProvider = null;
            appViewStack.validateDisplayList();
            dispatchEvent(new ApplicationEvent(ApplicationEvent.UNLOAD_ALL_MODULES));
            try {
                identity.logout();
            } catch (e : Error) {
                trace("Error on logout:" + e.message);
                Alert.show(I18nUtil.trans(I18nUtil.APP_ERROR, 'error_logout_failed'));
            }
            _loginView = LoginView(PopUpManager.createPopUp(this, LoginView, true));
            _loginView.loginMessageText = I18nUtil.trans(I18nUtil.APP_MAIN, 'txt_login_not_logged_in');
            PopUpManager.centerPopUp(_loginView);
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
            modelLocator.fired = true;
            modelLocator.SCREEN_LOCKED = true;
            modelLocator.actualView = SwitchScreenEvent.SHOW_STARTSCREEN;
            appViewStack.selectedIndex = DisplayUtility.getView(SwitchScreenEvent.SHOW_STARTSCREEN, appViewStack);
            _loginView = LoginView(PopUpManager.createPopUp(this, LoginView, true));
            _loginView.loginMessageText = I18nUtil.trans(I18nUtil.APP_MAIN, 'txt_login_locked_by', modelLocator.viewLockedBy);
            PopUpManager.centerPopUp(_loginView);
        }

        [Observer("APP.CREDENTIALS_VALID")]
        /**
         * Called to unlock the application.
         * Tide event observers : APP.CREDENTIALS_VALID
         *
         * @param event Unused
         */
        public function unlock(event : ApplicationEvent) : void {
            modelLocator.SCREEN_LOCKED = false;
            modelLocator.viewLockedBy = "";
            modelLocator.actualView = modelLocator.viewBeforeLock;
            PopUpManager.removePopUp(_loginView);
            rman.update();
        }

        [Observer("APP.LOGIN_OK")]
        /**
         * Called when the user logged in successfully.
         * Tide event observers : APP.LOGIN_OK
         *
         * @param event Unused
         */
        public function loggedIn(event : ApplicationEvent=null) : void {
            dispatchEvent(new ApplicationEvent(ApplicationEvent.LOAD_ALL_MODULES));
            rman.update();
        }

        [Observer("APP.BEFORE_MODULE_UNLOAD")]
        /**
         * In the case a Module was successfully unloaded, menues and views must be re-organized.
         * Tide event observers : APP.BEFORE_MODULE_UNLOAD
         *
         * @param event event.data stores a structure: {appModule, mInf, module}
         */
        public function onBeforeUnload(event : ApplicationEvent) : void {
            if (event.data != null && !modelLocator.unloadedModules.hasOwnProperty((event.data.module as Module).url)) {
                mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
                removeViewsFromStack(event.data.appModule as IApplicationModule);
            }
            fireReadyToUnload(event);
        }

        [Observer("MODULE_LOADED")]
        /**
         * In the case a Module was successfully loaded menues and views must be re-organized.
         * Tide event observers : MODULE_LOADED
         *
         * @param event event.data stores the IApplicationModule
         */
        public function moduleConfigChanged(event : ApplicationEvent) : void {
            event.stopPropagation();
            if (event.data != null && event.data.appModule is IApplicationModule) {
                var appModule : IApplicationModule = event.data.appModule as IApplicationModule;
                mainMenuBar.dataProvider = moduleLocator.getActiveMenuItems(new XMLListCollection(stdMenu));
                addViewsToViewStack(appModule);
                addSecurityObjects(appModule);
                this.validateNow();
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
        }

        [Observer("APP.SECURITY_OBJECTS_REFRESHED")]
        /**
         * Adds a list of new security objects to the model.
         * Tide event observers : APP.SECURITY_OBJECTS_REFRESHED
         *
         * @param event event.data is expected to hold an ArrayCollection of new securityObjects
         */
        public function secureObjectsRefreshed(event : ApplicationEvent) : void {
            if (event.data != null) {
                var securityObjects : ArrayCollection = event.data as ArrayCollection;
                for each (var grant : Grant in securityObjects) {
                    if (!modelLocator.securityObjects.contains(grant)) {
                        modelLocator.securityObjects.addItem(grant);
                    }
                }
            }
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
                    view = null;
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

        private function fireReadyToUnload(event : ApplicationEvent) : void {
            var e : ApplicationEvent = new ApplicationEvent(ApplicationEvent.READY_TO_UNLOAD);
            e.data = event.data
            dispatchEvent(e);
        }

        private function setUpLogging() : void {
            var t : TraceTarget = new TraceTarget();
            t.filters = ["org.openwms.*"];
            Log.addTarget(t);
        }

        private function setupServices(services : Array) : void {
            var endpoint : String = ServerConfig.getChannel("my-graniteamf").endpoint;
            for each (var service : SecureRemoteObject in services) {
                service.endpoint = endpoint;
                service.showBusyCursor = true;
                service.channelSet = new ChannelSet();
                service.channelSet.addChannel(ServerConfig.getChannel("my-graniteamf"));
            }
        }

        private function addSecurityObjects(module : IApplicationModule) : void {
            var securityObjects : ArrayCollection = module.getSecurityObjects();
            var event : ApplicationEvent = new ApplicationEvent(ApplicationEvent.MERGE_GRANTS);
            event.data = {moduleName: module.getModuleName(), grants: securityObjects};
            dispatchEvent(event);
        }

    }
}

