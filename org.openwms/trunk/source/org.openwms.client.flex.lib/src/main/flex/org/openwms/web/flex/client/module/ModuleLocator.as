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
package org.openwms.web.flex.client.module
{

    import flash.system.ApplicationDomain;
    import flash.utils.getQualifiedClassName;
    
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    import mx.controls.Alert;
    import mx.events.ModuleEvent;
    import mx.modules.IModuleInfo;
    import mx.modules.ModuleManager;
    
    import org.granite.tide.ITideModule;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.granite.tide.spring.Spring;
    import org.openwms.common.domain.Module;
    import org.openwms.web.flex.client.IApplicationModule;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.model.ModelLocator;

    /**
     * A ModuleLocator.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 235 $
     * @since 0.1
     */
    [Name("moduleLocator")]
    [ManagedEvent(name="MODULE_CONFIG_CHANGED")]
    [ManagedEvent(name="MODULES_CONFIGURED")]
    [ManagedEvent(name="MODULE_UNLOADED")]
    [Bindable]
    public class ModuleLocator
    {
        private static var instance:ModuleLocator;

        [In]
        public var modelLocator:ModelLocator;
        [In]
        public var tideContext:Context;

        private var toRemove:Module;
        private var applicationDomain:ApplicationDomain = new ApplicationDomain(ApplicationDomain.currentDomain);

        public function ModuleLocator()
        {
        }

        /**
         * Usually this method is called when the application is initialized,
         * to load all modules and module configuration from the service.
         * After this startup configuration is read, the application can really
         * LOAD the swf modules into it.
         */
        [Observer("LOAD_ALL_MODULES")]
        public function loadModulesFromService():void
        {
            tideContext.moduleManagementService.getModules(onModulesLoad);
        }

        /**
         * Tries to save the module data via the service.
         */
        [Observer("SAVE_MODULE")]
        public function saveModule(event:ApplicationEvent):void
        {
            tideContext.moduleManagementService.save(event.data as Module, onModuleSaved);
        }

        /**
         * Tries to remove the module data via the service.
         */
        [Observer("DELETE_MODULE")]
        public function deleteModule(event:ApplicationEvent):void
        {
            toRemove = event.data as Module;
            tideContext.moduleManagementService.remove(event.data as Module, onModuleRemoved);
        }

        /**
         * This methods firts checks whether the module is known as module.
         * Then we try to load the module and set the loaded flag to true. If something goes
         * wrong we set loaded to false.
         */
        [Observer("LOAD_MODULE")]
        public function loadModule(event:ApplicationEvent):void
        {
            var module:Module = event.data as Module;
            if (module == null)
            {
                trace("Module instance is NULL, skip loading");
                return;
            }
            if (!isRegistered(module))
            {
                trace("Module was not found in list of all modules");
                return;
            }
            trace("Load:" + module.moduleName);
            var mInf:IModuleInfo = ModuleManager.getModule(module.url);
            if (mInf != null)
            {
                if (mInf.loaded)
                {
                    trace("Module was already loaded:" + module.moduleName);
                    return;
                }
                else
                {
                    mInf.addEventListener(ModuleEvent.READY, onModuleLoaded, false, 0, true);
                    mInf.addEventListener(ModuleEvent.ERROR, onModuleLoaderError);
                    mInf.data = module;
                    modelLocator.loadedModules.put(module.url, mInf);
                    mInf.load(applicationDomain);
                    return;
                }
            }
            trace("No module to load with url: " + module.url);
            return;
        }

        /**
         * This method tries to unload a module. The module must be known.
         */
        [Observer("UNLOAD_MODULE")]
        public function unloadModule(event:ApplicationEvent):void
        {
            var module:Module = event.data as Module;
            if (module == null)
            {
                trace("Module instance is NULL, skip unloading");
                return;
            }
            if (!isRegistered(module))
            {
                trace("Module was not found in list of registered modules");
                return;
            }
            var mInf:IModuleInfo = ModuleManager.getModule(module.url);
            if (mInf != null)
            {
                if (mInf.loaded)
                {
                    mInf.addEventListener(ModuleEvent.UNLOAD, onModuleUnloaded);
                    mInf.addEventListener(ModuleEvent.ERROR, onModuleLoaderError);
                    mInf.data = module;
                    modelLocator.unloadedModules.put(module.url, mInf);
                    mInf.unload();
                    return;
                }
                else
                {
                    trace("Module was not loaded before, nothing to unload");
                }
            }
            trace("No module to unload with url: " + module.url);
            return;
        }

        /**
         * Returns an ArrayCollection of MenuItems of all loaded modules.
         */
        public function getActiveMenuItems(stdItems:XMLListCollection = null):XMLListCollection
        {
            var all:XMLListCollection = new XMLListCollection();

            if (stdItems != null)
            {
                for each (var stdNode:XML in stdItems)
                {
                    all.addItem(stdNode);
                }
            }

            for each (var module:Module in modelLocator.allModules)
            {
                if (modelLocator.loadedModules.containsKey(module.url))
                {
                    trace("Module is in the list of loaded modules");

                    // Get an handle to IApplicationModule here to retrieve the list of items
                    // not like it is here to get the info from the db
                    var mInf:IModuleInfo = modelLocator.loadedModules.get(module.url) as IModuleInfo;
                    var appModule:Object = mInf.factory.create();
                    if (appModule is IApplicationModule)
                    {
                        var tree:XMLListCollection = appModule.getMainMenuItems();
                        // TODO: In Flex 3.5 replace with addAll()
                        for each (var node:XML in tree)
                        {
                            all.addItem(node);
                        }
                        trace("Add menu items of module:" + appModule);
                    }
                }
            }
            return all;
        }

        /**
         * Checks whether a module is known as module and was loaded before.
         * Returns true only if the module is loaded, returns false if it is unknown
         * or it was not loaded.
         */
        public function isRegistered(module:Module):Boolean
        {
            if (module == null)
            {
                return false;
            }
            for each (var m:Module in modelLocator.allModules)
            {
                if (m.moduleName == module.moduleName)
                {
                    return true;
                }
            }
            return false;
        }

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //
        // privates
        //
        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

        /**
         * Callback function when module configuration is retrieved from the service.
         * The configuration is stored in allModules and the model is set to initialized.
         */
        private function onModulesLoad(event:TideResultEvent):void
        {
            modelLocator.allModules = event.result as ArrayCollection;
            modelLocator.isInitialized = true;
            dispatchEvent(new ApplicationEvent(ApplicationEvent.MODULES_CONFIGURED));
            startAllModules();
        }

        /**
         * This function checks all known modules if they are configured to be loaded
         * on startup and tries to start each Module if it hasn't been loaded so far.
         */
        private function startAllModules():void
        {
            for each (var module:Module in modelLocator.allModules)
            {
                if (module.loadOnStartup)
                {
                    if (modelLocator.loadedModules.containsKey(module.url))
                    {
                        module.loaded = true;
                        continue;
                    }
                    trace("Trying to start module:" + module.url);
                    var mInf:IModuleInfo = ModuleManager.getModule(module.url);
                    if (mInf != null)
                    {
                        if (mInf.loaded)
                        {
                            module.loaded = true;
                            trace("Module was already started:" + module.moduleName);
                        }
                        else
                        {
                            mInf.addEventListener(ModuleEvent.READY, onModuleLoaded, false, 0, true);
                            mInf.addEventListener(ModuleEvent.ERROR, onModuleLoaderError);
                            mInf.data = module;
                            modelLocator.loadedModules.put(module.url, mInf);
                            mInf.load(applicationDomain);
                        }
                    }
                    else
                    {
                        trace("Module was not found on server:" + module.moduleName);
                    }
                }
                else
                {
                    trace("Module not set to be started on startup:" + module.moduleName);
                }
            }
        }

        /**
         * When module data was saved successfully it is updated in the list of modules
         * and set as actual selected module.
         */
        private function onModuleSaved(event:TideResultEvent):void
        {
            addModule(event.result as Module);
            modelLocator.selectedModule = event.result as Module;
        }

        /**
         * When module data was removed successfully it is updated in the list of modules
         * and unset as selected module.
         */
        private function onModuleRemoved(event:TideResultEvent):void
        {
            removeFromModules(toRemove);
            toRemove = null;
        }

        /**
         * Fire an event to notify others that configuration data of a module has changed.
         * The event data (e.data) contains the changed module.
         */
        private function fireChangedEvent(module:IApplicationModule):void
        {
            var e:ApplicationEvent = new ApplicationEvent(ApplicationEvent.MODULE_CONFIG_CHANGED);
            e.data = module;
            dispatchEvent(e);
        }

        /**
         * This method is called when an application module was successfully loaded.
         * Loading a module can be triggered by the Module Management screen or at application
         * startup if the module is configured to behave so.
         */
        private function onModuleLoaded(e:ModuleEvent):void
        {
            trace("Successfully loaded module: " + e.module.url);
            var module:Module = (e.module.data as Module);
            module.loaded = true;
            if (!modelLocator.loadedModules.containsKey(module.url))
            {
                modelLocator.loadedModules.put(module.url, ModuleManager.getModule(module.url));
            }
            modelLocator.unloadedModules.remove(module.url);
            var appModule:Object = e.module.factory.create();
            if (appModule is IApplicationModule)
            {
          		appModule.start(applicationDomain);
                fireChangedEvent(appModule as IApplicationModule);
            }
            var mInf:IModuleInfo = (modelLocator.loadedModules.get(module.url) as IModuleInfo);
            mInf.removeEventListener(ModuleEvent.READY, onModuleLoaded);
            mInf.removeEventListener(ModuleEvent.ERROR, onModuleLoaderError);
        }

        /**
         * This method is called when an application module was successfully unloaded. Unloading
         * a module is usually triggered by the Module Management screen. As a result an event is
         * fired to inform the main application about the unload. For instance the main application
         * could rebuild the menu bar.
         */
        private function onModuleUnloaded(e:ModuleEvent):void
        {
            trace("Successfully unloaded Module: " + e.module.url);
            var module:Module = (e.module.data as Module);
            module.loaded = false;
            if (!modelLocator.unloadedModules.containsKey(module.url))
            {
                modelLocator.unloadedModules.put(module.url, ModuleManager.getModule(module.url));
            }
            modelLocator.loadedModules.remove(module.url);
            var appModule:Object = e.module.factory.create();
            if (appModule is IApplicationModule)
            {
                fireUnloadedEvent(appModule as IApplicationModule);
            }
            var mInf:IModuleInfo = (modelLocator.unloadedModules.get(module.url) as IModuleInfo);
            mInf.removeEventListener(ModuleEvent.UNLOAD, onModuleUnloaded);
            mInf.removeEventListener(ModuleEvent.ERROR, onModuleLoaderError);
        }

        /**
         * Fire an event to notify others that a module was successfully unloaded.
         * The event data (e.data) contains the changed module.
         */
        private function fireUnloadedEvent(module:IApplicationModule):void
        {
            var e:ApplicationEvent = new ApplicationEvent(ApplicationEvent.MODULE_UNLOADED);
            e.data = module;
            dispatchEvent(e);
        }

        /**
         * This method is called when an error occurred while loading or unloading a module.
         */
        private function onModuleLoaderError(e:ModuleEvent):void
        {
            if (e.module != null)
            {
                trace("Loading/Unloading a module [" + e.module.url + "] failed with error:" + e.errorText);
                if (e.module.data != null)
                {
                    var module:Module = (e.module.data as Module);
                    module.loaded = false;
                    var mInf:IModuleInfo = (modelLocator.loadedModules.get(module.url) as IModuleInfo);
                    if (mInf != null)
                    {
                        // TODO: Also remove other listeners here
                        mInf.removeEventListener(ModuleEvent.ERROR, onModuleLoaderError);
                        modelLocator.unloadedModules.put(module.url, mInf);
                    }
                    modelLocator.loadedModules.remove(module.url);
                }
                Alert.show("Loading/Unloading a module [" + e.module.url + "] failed with error:" + e.errorText);
            }
            else
            {
                trace("Loading/Unloading a module failed, no further module data available here");
                Alert.show("Loading/Unloading a module failed, no further module data available here");
            }
        }

        /**
         * Add a module to the list of all modules.
         */
        private function addModule(module:Module):void
        {
            if (module == null)
            {
                return;
            }
            var found:Boolean = false;
            for each (var m:Module in modelLocator.allModules)
            {
                if (m.moduleName == module.moduleName)
                {
                    found = true;
                    m = module;
                }
            }
            if (!found)
            {
                modelLocator.allModules.addItem(module);
            }
            if (modelLocator.loadedModules.containsKey(module.url))
            {
                modelLocator.loadedModules.put(module.url, ModuleManager.getModule(module.url));
            }
            if (modelLocator.unloadedModules.containsKey(module.url))
            {
                modelLocator.unloadedModules.put(module.url, ModuleManager.getModule(module.url));
            }
        }

        /**
         * Removes a module from the list of all modules and unloads it in the case it was loaded before.
         */
        private function removeFromModules(module:Module, unload:Boolean = false):Boolean
        {
            if (module == null)
            {
                return false;
            }
            var modules:Array = modelLocator.allModules.toArray();
            for (var i:int = 0; i < modules.length; i++)
            {
                if (modules[i].moduleName == module.moduleName)
                {
                    modelLocator.allModules.removeItemAt(i);
                    if (unload)
                    {
                        var mInfo:IModuleInfo = ModuleManager.getModule(module.url);
                        mInfo.addEventListener(ModuleEvent.READY, onModuleLoaded);
                        mInfo.addEventListener(ModuleEvent.ERROR, onModuleLoaderError);
                        if (mInfo != null && mInfo.loaded)
                        {
                            mInfo.data = module;
                            mInfo.unload();
                        }
                    }
                    return true;
                }
            }
            return false;
        }

    }
}