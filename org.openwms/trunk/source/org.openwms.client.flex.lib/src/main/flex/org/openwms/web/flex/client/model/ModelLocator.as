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

    import com.adobe.cairngorm.model.IModelLocator;
    
    import mx.collections.ArrayCollection;
    
    import org.openwms.common.domain.system.usermanagement.User;

    /**
     * A ModelLocator.
     *
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision$
     */
    [Bindable]
    public class ModelLocator implements IModelLocator
    {

        public static const MAIN_VIEW_STACK_LOGIN_VIEW:uint = 0;
        public static const MAIN_VIEW_STACK_EMPTY_VIEW:uint = 1;
        public static const MAIN_VIEW_STACK_MODULE_MGMT_VIEW:uint = 2;
        public static const MAIN_VIEW_STACK_USER_MGMT_VIEW:uint = 3;
        public static const MAIN_VIEW_STACK_LOCATION_VIEW:uint = 4;
        public static const MAIN_VIEW_STACK_LOCATIONGROUP_VIEW:uint = 5;
        public static const MAIN_VIEW_STACK_TRANSPORTUNIT_VIEW:uint = 6;
        public var mainViewStackIndex:uint = MAIN_VIEW_STACK_EMPTY_VIEW;

        public const UPLOAD_URL:String = "/openwms/upload";
        public const DIRECTORY_NAME:String = "data";
        public static const DT_FORMAT_STRING:String = "DD.MM.YYYY HH:NN:SS";

        public var allLocationGroups:ArrayCollection = new ArrayCollection();
        public var allLocations:ArrayCollection = new ArrayCollection();
        public var allTransportUnits:ArrayCollection = new ArrayCollection();
        public var allModules:ArrayCollection = new ArrayCollection();
        public var allUsers:ArrayCollection = new ArrayCollection();
        public var selectedUser:User = null;
        public var locationGroupTree:TreeNode;
        public var image:Object;
        private var views:Array = new Array();
        // Used to control the main viewStack
        public var actualView:String;

        private static var instance:ModelLocator;

        /**
         * Used to construct the Singleton instance.
         */
        public function ModelLocator(enforcer:SingletonEnforcer)
        {
        }

        /**
         * Return the instance of ModelLocator which is implemented
         * as Singleton.
         */
        public static function getInstance():ModelLocator
        {
            if (instance == null)
            {
                instance = new ModelLocator(new SingletonEnforcer);
            }
            return instance;
        }

        /**
         * Access the views array and add the viewObject to the defined
         * position. The postion is mandatory for the viewStack.
         */
        public static function addView(pos:int, view:Object):void
        {

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

class SingletonEnforcer
{
}