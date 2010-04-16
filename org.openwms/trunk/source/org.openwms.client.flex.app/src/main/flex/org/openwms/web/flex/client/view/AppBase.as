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
package org.openwms.web.flex.client.view
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.collections.XMLListCollection;
	import mx.controls.Alert;
	import mx.controls.MenuBar;
	import mx.core.Application;
	import mx.events.MenuEvent;
	import mx.managers.DragManager;
	import mx.managers.PopUpManager;
	import mx.modules.IModuleInfo;
	import mx.modules.ModuleManager;
	
	import org.granite.events.SecurityEvent;
	import org.granite.rpc.remoting.mxml.SecureRemoteObject;
	import org.openwms.common.domain.MenuItem;
	import org.openwms.common.domain.Module;
	import org.openwms.web.flex.client.HashMap;
	import org.openwms.web.flex.client.IApplicationModule;
	import org.openwms.web.flex.client.command.*;
	import org.openwms.web.flex.client.control.MainController;
	import org.openwms.web.flex.client.event.ApplicationEvent;
	import org.openwms.web.flex.client.event.EventBroker;
	import org.openwms.web.flex.client.event.ModulesEvent;
	import org.openwms.web.flex.client.event.SwitchScreenEvent;
	import org.openwms.web.flex.client.event.UserEvent;
	import org.openwms.web.flex.client.model.ModelLocator;
	import org.openwms.web.flex.client.module.ModuleLocator;

    public class AppBase extends Application
    {

		[Bindable]
		protected var modelLocator:ModelLocator = ModelLocator.getInstance();
		[Bindable]
		private var moduleLocator:ModuleLocator = ModuleLocator.getInstance();
		[Bindable]
		private var broker:EventBroker = EventBroker.getInstance();
		[Bindable]
		private var srv:SecureRemoteObject = null;
		[Bindable]
		protected var mainController:MainController = new MainController();
		[Bindable]
		public var menuBarCollection:XMLListCollection;
		[Bindable]
		protected var menuItems:ArrayCollection;
		[Bindable]
		public var loginView:LoginView;
		[Bindable]
		public var mainMenuBar:MenuBar;
		
		// Manager classes are loaded to the application domain
		private var moduleManager:ModuleManager;
		private var popUpManager:PopUpManager;
		private var dragManager:DragManager;
		private var mInfo:IModuleInfo
	    
	    /**
	     * Constructor.
	     */
	    public function AppBase()
	    {
	    	super();
	    }
	
	    public function init():void
	    {
	        srv = new SecureRemoteObject("userService");
	        //srv.login();
	        registerEventListeners();
	        bindCommands();
	        detectModules();
	        //configService.send();
	    }
	
	    private function bindCommands():void
	    {
	        mainController.addCommand(UserEvent.LOAD_ALL_USERS, LoadUsersCommand);
		    mainController.addCommand(UserEvent.ADD_USER, AddUserCommand);
		    mainController.addCommand(UserEvent.SAVE_USER, SaveUserCommand);
		    mainController.addCommand(UserEvent.DELETE_USER, DeleteUserCommand);
		
		    mainController.addCommand(SwitchScreenEvent.SHOW_STARTSCREEN, ShowStartscreenCommand);
		    mainController.addCommand(SwitchScreenEvent.SHOW_MODULE_MGMT_VIEW, ShowModuleManagementViewCommand);
		    /*
		       mainController.addCommand(SwitchScreenEvent.SHOW_TRANSPORTUNIT_VIEW, ShowTransportUnitCommand);
		     */
		
		    mainController.addCommand(SwitchScreenEvent.SHOW_USER_MGMT_VIEW, ShowUserManagementViewCommand);
		
		    mainController.addCommand(ApplicationEvent.LOAD_ALL_MODULES, LoadModulesCommand);
		    mainController.addCommand(ApplicationEvent.SAVE_MODULE, SaveModuleCommand);
		    mainController.addCommand(ApplicationEvent.DELETE_MODULE, DeleteModuleCommand);
		}
	
		public function onSecurityEvent(event:SecurityEvent):void
		{
		    trace("onSecurityEvent: " + event);
		    switch (event.type)
		    {
		        case SecurityEvent.INVALID_CREDENTIALS:
		            loginView.loginMessageText = "Invalid username or password";
		            loginView.authenticated = false;
		            break;
		        case SecurityEvent.NOT_LOGGED_IN:
		            srv.logout();
		            loginView.loginMessageText = "";
		            loginView.authenticated = false;
		            break;
		        case SecurityEvent.SESSION_EXPIRED:
		            srv.logout();
		            loginView.loginMessageText = "Session expired";
		            loginView.authenticated = false;
		            break;
		        case SecurityEvent.ACCESS_DENIED:
		            Alert.show("You don't have required rights to execute this action");
		            break;
		    }
		}
		
		public function onMenuChange(event:MenuEvent):void
		{
		    trace("Switching to view:" + event.item.@action);
		    new SwitchScreenEvent(event.item.@action).dispatch();
		}
		
		public function login(username:String, password:String):void
		{
		    srv.setCredentials(username, password);
		    srv.login();
		}
		
		public function logout():void
		{
		    srv.logout();
		    loginView.authenticated = false;
		    loginView.loginMessageText = "";
		}
		
		private function registerEventListeners():void
		{
		    var broker:EventBroker = EventBroker.getInstance();
		    broker.addEventListener(ApplicationEvent.MODULE_CONFIG_CHANGED, moduleConfigChanged);
		    // When all modules are loaded from DB then notify application to start modules automatically ...
		    broker.addEventListener(ModulesEvent.MODULES_LOADED, loadAllModules);
		}
	
		/**
		 * In the case the Module confifuration has changed, a re-organize of menus and
		 * views becomes necessary.
		 */
		private function moduleConfigChanged(event:ApplicationEvent):void
		{
		    // Modules were loaded, hence update viewStack, menuBar and popUps...
		    if (event.data != null && event.data is IApplicationModule)
		    {
		        var appModule:IApplicationModule = (event.data as IApplicationModule);
		        trace("Configuration changed for Module: " + appModule.getModuleName());
		        refreshMainMenu(appModule);
		        refreshViewStack(appModule);
		    }
		}
		
		/**
		 * This method rebuilds the viewStack of the application, and should be called,
		 * in case application modules are loaded or unloaded.
		 */
		private function refreshViewStack(module:IApplicationModule):void
		{
		
		}
		
		/**
		 * This method rebuilds the main application menu and should be called
		 * in case an application module is loaded or unloaded.
		 * The main menu, could be an MenuBar or any other kind of menu.
		 */
		private function refreshMainMenu(module:IApplicationModule):void
		{
		    trace("Resolve MenuItems to populate application menu");
		    var map:HashMap = module.getMainMenuItems();
		    if (map == null)
		    {
		    	return;
		    }
		    var keys:Array = map.getKeys();
		    var itemPos:int = 0;
		    var item:XML;
		    for (var i:int = 0; i < keys.length; i++)
		    {
		        itemPos = keys[i];
		        item = XML(map.get(itemPos));
		        mainMenuBar.dataProvider.addItemAt(item, itemPos);
		    }
		}
		
		/**
		 * Load all modules that are configured to start automatically. This method is event triggered
		 * and called when the module configuration is loaded from the service layer.
		 */
		public function loadAllModules(event:Event):void
		{
			moduleLocator.loadAllModules();
			return;
		    for each (var module:Module in moduleLocator.allModules)
		    {
		        if (module.loadOnStartup)
		        {
		        	trace("Loading module... "+module.moduleName); 
		            moduleLocator.loadModule(module);
		        }
		        else
		        {
		            trace("Module not configured to load on startup:" + module.moduleName);
		        }
		    }
		    return;
		}
		
		private function updateViewStack(module:Module):void
		{
		    for each (var menuItem:MenuItem in module.menuItems)
		    {
		        trace("MenuItem:" + menuItem.label);
		    }
		}
		
		private function detectModules():void
		{
		    new ApplicationEvent(ApplicationEvent.LOAD_ALL_MODULES).dispatch();
		}
    }
}