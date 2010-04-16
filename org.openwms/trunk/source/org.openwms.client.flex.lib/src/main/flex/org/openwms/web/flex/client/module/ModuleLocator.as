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

    import com.adobe.cairngorm.model.IModelLocator;
    import mx.events.ModuleEvent;
    import mx.events.FlexEvent;
    import mx.modules.IModuleInfo;
    import mx.modules.ModuleManager;
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    import org.openwms.common.domain.Module;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.event.EventBroker;
    import org.openwms.web.flex.client.IApplicationModule;

    /**
     * A ModuleLocator.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 235 $
     * @since 0.1
     */
    [Bindable]
    public class ModuleLocator implements IModelLocator
    {
        private static var instance:ModuleLocator;


        public var allModules:ArrayCollection = new ArrayCollection();
        public var selectedModule:Module = null;
        private var mInfo:IModuleInfo;
        private var broker:EventBroker = EventBroker.getInstance();

        public function ModuleLocator(enforcer:SingletonEnforcer)
        {
        }

        public static function getInstance():ModuleLocator
        {
            if (instance == null)
            {
                instance = new ModuleLocator(new SingletonEnforcer);
            }
            return instance;
        }

        /**
         * This method checks all known modules if they shall be loaded and tries to load
         * them if they aren't loaded so far.
         */
        public function loadAllModules():void
        {
            var modules:Array = allModules.toArray();
            var e:ApplicationEvent;
            var mInfo:IModuleInfo;
            for each (var module:Module in allModules)
            {
                if (module.loadOnStartup)
                {
                    trace("Trying to load module:" + module.url);
                    mInfo = ModuleManager.getModule(module.url);
                    mInfo.addEventListener(ModuleEvent.READY, moduleLoaded);
                    mInfo.addEventListener(ModuleEvent.ERROR, moduleLoaderError);
                    if (mInfo != null)
                    {
                        if (!mInfo.loaded)
                        {
                            mInfo.data = module;
                            mInfo.load();
                        }
                        else
                        {
                            trace("Module was already loaded:" + module.moduleName);
                        }
                    }
                    else
                    {
                        trace("Module was not found on server:" + module.moduleName);
                    }
                }
                else
                {
                    trace("Module not set to be loaded on startup:" + module.moduleName);
                }

            }
            return;
        }

        /**
         * This method is called when an application module was successfully loaded. Loading
         * a module can be triggered by the Module Management screen or at application startup
         * if the module is configured to behave so.
         */
        public function moduleLoaded(e:ModuleEvent):void
        {
            trace("Successfully loaded module: " + e.module.url);
            var module:Module = (e.module.data as Module);
            // Dont set this flag because we can have many clients
            //module.loaded = true;
            //fireSaveEvent(module);
            var appModule:Object = e.module.factory.create();
            if (appModule is IApplicationModule)
            {
                fireChangedEvent(appModule as IApplicationModule);
            }
            mInfo.removeEventListener(ModuleEvent.READY, moduleLoaded);
        }

        /**
         * This method is called when an application module was successfully loaded. Loading
         * a module can be triggered by the Module Management screen or at application startup
         * if the module is configured to behave so.
         */
        public function moduleUnloaded(e:ModuleEvent):void
        {
            trace("Successfully unloaded a Module:" + e.module.url);
            var module:Module = (e.module.data as Module);
            // Dont set this flag because we can have many clients
            //module.loaded = false;
            //fireSaveEvent(module);
            var appModule:Object = e.module.factory.create();
            if (appModule is IApplicationModule)
            {
                fireChangedEvent(appModule as IApplicationModule);
            }
            mInfo.removeEventListener(ModuleEvent.UNLOAD, moduleUnloaded);
        }

        /**
         * This method is called when some error occurred loading or unloading a module.
         */
        public function moduleLoaderError(e:ModuleEvent):void
        {
            if (e.module != null)
            {
                trace("Loading/Unloading a Module [" + e.module.url + "] failed with error:" + e.errorText);
                Alert.show("Loading/Unloading a Module [" + e.module.url + "] failed with error:" + e.errorText);
            }
            else
            {
                trace("Loading/Unloading a Module failed, no further module data available here");
            }
            mInfo.removeEventListener(ModuleEvent.ERROR, moduleLoaderError);
        }

        /**
         * This methods firts checks whether the module is known as module.
         * Then we try to load the module and set the loaded flag to true. If something goes
         * wrong we set loaded to false.
         */
        public function loadModule(module:Module):Boolean
        {
            if (module == null)
            {
                return false;
            }
            if (!isRegistered(module))
            {
                trace("Module was not found in list of all modules");
                return false;
            }

            mInfo = ModuleManager.getModule(module.url);
            mInfo.addEventListener(ModuleEvent.READY, moduleLoaded);
            mInfo.addEventListener(ModuleEvent.ERROR, moduleLoaderError);
            if (mInfo != null)
            {
                if (!mInfo.loaded)
                {
                    mInfo.data = module;
                    mInfo.load();
                }
                return true;
            }
            if (module.loaded)
            {
                module.loaded = false;
                fireSaveEvent(module);
            }
            return false;
        }

        /**
         * This method tries to unload a module. The module must be known.
         */
        public function unloadModule(module:Module):void
        {
            if (module == null)
            {
                return;
            }
            if (!isRegistered(module))
            {
                trace("Module was not found in list of all modules");
                return;
            }
            mInfo = ModuleManager.getModule(module.url);
            mInfo.addEventListener(ModuleEvent.UNLOAD, moduleUnloaded);
            mInfo.addEventListener(ModuleEvent.ERROR, moduleLoaderError);
            if (mInfo != null)
            {
                if (mInfo.loaded)
                {
                    mInfo.data = module;
                    mInfo.unload();
                }
                else
                {
                    trace("Module was not loaded before, nothing to unload");
                }
            }
            else
            {
                trace("Module was not found on server:" + module.moduleName);
            }
            return;
        }

        /**
         * Fire an event to notify others that configuration data of a module has changed.
         * The event data (e.data) contains the changed module.
         */
        private function fireSaveEvent(module:Module):void
        {
            var e:ApplicationEvent = new ApplicationEvent(ApplicationEvent.SAVE_MODULE);
            e.data = module;
            e.dispatch();
        }

        /**
         * Fire an event to notify others that configuration data of a module has changed.
         * The event data (e.data) contains the changed module.
         */
        private function fireChangedEvent(module:IApplicationModule):void
        {
            var e:ApplicationEvent = new ApplicationEvent(ApplicationEvent.MODULE_CONFIG_CHANGED);
            e.data = module;
            broker.dispatchEvt(e);
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
            for each (var m:Module in allModules)
            {
                if (m.moduleName == module.moduleName)
                {
                    return true;
                }
            }
            return false;
        }

        public function refreshModule(module:Module, select:Boolean):Boolean
        {
            if (module == null)
            {
                return false;
            }
            var found:Boolean = false;
            var modules:Array = allModules.toArray();
            for (var i:int = 0; i < modules.length; i++)
            {
                if (modules[i].moduleName == module.moduleName)
                {
                    modules[i] = module;
                    if (select)
                    {
                        this.selectedModule = module;
                    }
                    trace("Module refreshed:" + module.moduleName);
                    dispatchEvent(new ApplicationEvent(ApplicationEvent.MODULE_CONFIG_CHANGED));
                    return true;
                }
            }
            return false;
        }

        /**
         * Adds a module to the list of all modules.
         */
        public function addModule(module:Module):void
        {
            if (module == null)
            {
                return;
            }
            var found:Boolean = false;
            for each (var m:Module in allModules)
            {
                if (m.moduleName == module.moduleName)
                {
                    found = true;
                    m = module;
                }
            }
            if (!found)
            {
                allModules.addItem(module);
            }
        }

        /**
         * Removes a module from the list of all modules and unloads it in the case it was loaded before.
         */
        public function removeFromModules(module:Module, unload:Boolean = false):Boolean
        {
            if (module == null)
            {
                return false;
            }
            var modules:Array = allModules.toArray();
            for (var i:int = 0; i < modules.length; i++)
            {
                if (modules[i].moduleName == module.moduleName)
                {
                    allModules.removeItemAt(i);
                    if (unload)
                    {
                        var mInfo:IModuleInfo = ModuleManager.getModule(module.url);
                        mInfo.addEventListener(ModuleEvent.READY, moduleLoaded);
                        mInfo.addEventListener(ModuleEvent.ERROR, moduleLoaderError);
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

class SingletonEnforcer
{
}