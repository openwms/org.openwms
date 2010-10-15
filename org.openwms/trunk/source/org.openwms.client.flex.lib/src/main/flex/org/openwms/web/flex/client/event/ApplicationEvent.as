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
package org.openwms.web.flex.client.event
{
    import flash.events.Event;

    /**
     * An ApplicationEvent.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public class ApplicationEvent extends Event
    {
        public static const LOAD_ALL_MODULES:String = "LOAD_ALL_MODULES";
        public static const UNLOAD_ALL_MODULES:String = "UNLOAD_ALL_MODULES";
        public static const MODULE_CONFIG_CHANGED:String = "MODULE_CONFIG_CHANGED";
        public static const MODULES_CONFIGURED:String = "MODULES_CONFIGURED";
        public static const SAVE_MODULE:String = "SAVE_MODULE";
        public static const DELETE_MODULE:String = "DELETE_MODULE";
        public static const LOAD_MODULE:String = "LOAD_MODULE";
        public static const UNLOAD_MODULE:String = "UNLOAD_MODULE";
        public static const MODULE_LOADED:String = "MODULE_LOADED";
        public static const MODULE_UNLOADED:String = "MODULE_UNLOADED";
        public static const SAVE_STARTUP_ORDERS:String = "SAVE_STARTUP_ORDERS";

        public static const APP_LOGIN_OK:String = "APP_LOGIN_OK";
        public static const LOGOUT:String = "APP_LOGOUT";
        public var data:*;

        public function ApplicationEvent(type:String, bubbles:Boolean = true, cancelable:Boolean = false)
        {
            super(type, bubbles, cancelable);
        }

    }
}