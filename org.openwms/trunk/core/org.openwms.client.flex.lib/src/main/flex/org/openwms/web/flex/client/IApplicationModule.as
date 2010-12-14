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
package org.openwms.web.flex.client {
	
    import flash.events.IEventDispatcher;
    import flash.system.ApplicationDomain;    
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    
    /**
     * An IApplicationModule defines the contract between the major openwms.org
     * CORE Flex Application and Flex Modules that shall be loaded into the
     * application domain. An implementation class is typically an ITideModule as well.
     *
     * @version $Revision$
     * @see org.granite.tide.ITideModule
     */
    public interface IApplicationModule extends IEventDispatcher {
        /**
         * Returns a list of menu items which shall be integrated to the main
         * application menu bar.
         *
         * @return A list of XML menu items. 
         */
        function getMainMenuItems():XMLListCollection;

        /**
         * Returns a list of views which shall be populated to the parent
         * application.
         *
         * @return A list of DisplayObjects
         */
        function getViews():ArrayCollection;

        /**
         * Returns the name of the module as an unique String.
         *
         * @return the module name as String
         */
        function getModuleName():String;
        
        /**
         * Returns the current version of the module as a String.
         *
         * @return The version number as String
         */
        function getModuleVersion():String;
        
        /**
         * Returns a list of items which are handled as SecuityObjects.
         * A SecurityObject can be assigned to a Role and is monitored by a SecurityHandler
         * to allow or deny certain actions within the user interface.
         *
         * @return A list of SecurityObjects
         */
        function getSecurityObjects():ArrayCollection;

        /**
         * Does additional initial stuff when the Module is loaded.
         *
         * @param The ApplicationDomain of the main Application
         */
        function initializeModule(applicationDomain:ApplicationDomain = null):void;

        /**
         * Does addtional cleanup stuff before the Module is unloaded.
         */
        function destroyModule():void;
    }
}