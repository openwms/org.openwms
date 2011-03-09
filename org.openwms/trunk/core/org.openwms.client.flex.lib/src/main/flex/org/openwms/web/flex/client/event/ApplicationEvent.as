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
package org.openwms.web.flex.client.event {
    import flash.events.Event;

    /**
     * An ApplicationEvent used for some management purpose.
     *
     * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class ApplicationEvent extends Event {
    	
    	/**
    	 * Name of the Event to load all modules.
    	 */
        public static const LOAD_ALL_MODULES:String = "LOAD_ALL_MODULES";
        /**
         * Name of the Event to unload all modules.
         */
        public static const UNLOAD_ALL_MODULES:String = "UNLOAD_ALL_MODULES";
        /**
         * Name of the Event to signal that the module configuration has changed.
         */
        public static const MODULE_CONFIG_CHANGED:String = "MODULE_CONFIG_CHANGED";
        /**
         * Name of the Event to signal that the module configuration has finished. 
         */
        public static const MODULES_CONFIGURED:String = "MODULES_CONFIGURED";
        /**
         * Name of the Event to save a Module.
         */
        public static const SAVE_MODULE:String = "SAVE_MODULE";
        /**
         * Name of the Event to delete a Module.
         */
        public static const DELETE_MODULE:String = "DELETE_MODULE";
        /**
         * Name of the Event to load a single Module.
         */
        public static const LOAD_MODULE:String = "LOAD_MODULE";
        /**
         * Name of the Event to unload a single Module.
         */
        public static const UNLOAD_MODULE:String = "UNLOAD_MODULE";
        /**
         * Name of the Event to signal that a Module was successfully loaded.
         */
        public static const MODULE_LOADED:String = "MODULE_LOADED";
        /**
         * Name of the Event to signal that a Module was successfully unloaded.
         */
        public static const MODULE_UNLOADED:String = "MODULE_UNLOADED";
        /**
         * Name of the Event to save the startupOrders of a list of Modules.
         */
        public static const SAVE_STARTUP_ORDERS:String = "SAVE_STARTUP_ORDERS";
        /**
         * Name of the Event to signal that an User successfully logged in.
         */
        public static const APP_LOGIN_OK:String = "APP_LOGIN_OK";
        /**
         * Name of the Event to force an User logout.
         */
        public static const LOGOUT:String = "APP_LOGOUT";
        /**
         * Name of the Event to force a screen lock.
         */
        public static const LOCK:String = "APP_LOCK";
        
        /**
         * Store arbitrary data.
         */
        public var data:*;

        /**
         * Constructor.
         */
        public function ApplicationEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false) {
            super(type, bubbles, cancelable);
        }
    }
}