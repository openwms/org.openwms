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
package org.openwms.web.flex.client.model
{

    import mx.collections.ArrayCollection;
    import mx.formatters.DateFormatter;
    
    import org.openwms.common.domain.Module;
    import org.openwms.common.domain.system.usermanagement.User;
    import org.openwms.web.flex.client.HashMap;
    import org.openwms.web.flex.client.event.SwitchScreenEvent;

    /**
     * A ModelLocator.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Name("modelLocator")]
    [Bindable]
    public class ModelLocator
    {

        // --------------------------------------------------------------------
        // Default Views
        // --------------------------------------------------------------------
        public static const MAIN_VIEW_STACK_LOGIN_VIEW:uint = 0;
        public static const MAIN_VIEW_STACK_EMPTY_VIEW:uint = 1;
        public static const MAIN_VIEW_STACK_MODULE_MGMT_VIEW:uint = 2;
        public static const MAIN_VIEW_STACK_USER_MGMT_VIEW:uint = 3;
        public static const MAIN_VIEW_STACK_LOCATION_VIEW:uint = 4;
        public static const MAIN_VIEW_STACK_LOCATIONGROUP_VIEW:uint = 5;
        public static const MAIN_VIEW_STACK_TRANSPORTUNIT_VIEW:uint = 6;

        // --------------------------------------------------------------------
        // Main Application Management
        // --------------------------------------------------------------------
        // Used to control the main viewStack
        public var actualView:String = SwitchScreenEvent.SHOW_STARTSCREEN;
        public var authenticated:Boolean = false;
        public const UPLOAD_URL:String = "/openwms/upload";
        public const DIRECTORY_NAME:String = "data";
        public static const DT_FORMAT_STRING:String = "DD.MM.YYYY HH:NN:SS";
        public static const SIMPLE_DT_FORMAT:String = "DD.MM.YYYY";
        public const dateFormatter:DateFormatter = new DateFormatter();
        public const dateTimeFormatter:DateFormatter = new DateFormatter();

        // --------------------------------------------------------------------
        // User and Role Management
        // --------------------------------------------------------------------
        public var allUsers:ArrayCollection = new ArrayCollection();
        public var allRoles:ArrayCollection = new ArrayCollection();
        public var selectedUser:User = null;
        public var image:Object;

        // --------------------------------------------------------------------
        // Module Management
        // --------------------------------------------------------------------
        public var allModules:ArrayCollection = new ArrayCollection();
        public var loadedModules:HashMap = new HashMap();
        public var unloadedModules:HashMap = new HashMap();
        public var selectedModule:Module = null;
        
        // --------------------------------------------------------------------
        // Module Management
        // --------------------------------------------------------------------
        public var allProperties:ArrayCollection = new ArrayCollection();

        // --------------------------------------------------------------------
        // Security
        // --------------------------------------------------------------------
        // Credentials, set by the login screen
        public var isInitialized:Boolean = false;
        public var securityObjectNames:ArrayCollection = new ArrayCollection();

        // WIDTH
        public static const WIDTH_ID:Number = 70;
        public static const WIDTH_DATE:Number = 40;
        public static const WIDTH_TIME:Number = 40;
        public static const WIDTH_DATETIME:Number = 60;
        
        /**
         * Constructor.
         */
        public function ModelLocator()
        {
        	dateFormatter.formatString = SIMPLE_DT_FORMAT;
        	dateTimeFormatter.formatString = DT_FORMAT_STRING;
        }

        /**
         * Access the views array and add the viewObject to the defined
         * position. The postion is mandatory for the viewStack.
         */
        public static function addView(pos:int, view:Object):void
        {

        }
        
        public static function today():Date {
        	var today:Date = new Date();
        	today.setHours(0,0,0,0);
        	return today;
        }

        /**
         * Search the viewObject from the array of views and remove it.
         */
        public static function removeView(view:Object):void
        {
        }

        /**
         * Shift the view to a new position.
         **/
        public static function moveView(destPos:int, view:Object):void
        {
        }
    }
}