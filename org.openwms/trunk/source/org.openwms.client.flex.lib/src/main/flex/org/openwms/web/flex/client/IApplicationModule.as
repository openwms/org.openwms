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
package org.openwms.web.flex.client
{
    import flash.events.IEventDispatcher;
    import flash.system.ApplicationDomain;
    
    import mx.collections.ArrayCollection;
    import mx.collections.XMLListCollection;
    
    /**
     * An IApplicationModule.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 700 $
     */
    public interface IApplicationModule extends IEventDispatcher 
    {
        /**
         * This method returns a list of menu items which shall be expaned to the main
         * application menu bar.
         */
        function getMainMenuItems():XMLListCollection;

        /**
         * This method returns a list of views which shall be populated to the parent
         * application.
         */
        function getViews():ArrayCollection;

        /**
         * This method returns the name of the module as unique String identifier.
         */
        function getModuleName():String;
        
        /**
         * This method returns the current version of the module as String.
         */
        function getModuleVersion():String;
        
        /**
         * This method returns a list of items which are handled as SecuityObjects.
         * A SecurityObject can be assigned to a Role and is monitored by the SecurityHandler
         * to allow or deny certain functionality within the user interface.
         */
        function getSecurityObjects():ArrayCollection;

        /**
         * Do additional initial work when the module is loaded.
         */
        function initializeModule(applicationDomain:ApplicationDomain = null):void;

        /**
         * Do addtional cleanup work before the module is unloaded.
         */
        function destroyModule():void;
    }
}