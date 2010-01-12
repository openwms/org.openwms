/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.web.flex.client.model {

	import com.adobe.cairngorm.model.IModelLocator;
	import mx.collections.ArrayCollection;
	import org.openwms.common.domain.system.usermanagement.User;

	/**
	 * A ModelLocator.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 */
	[Bindable]
	public class ModelLocator implements IModelLocator {

        public static const MAIN_VIEW_STACK_EMPTY_SCREEN_VIEW:uint = 0;
		public static const MAIN_VIEW_STACK_LOCATION_VIEW:uint = 1;
		public static const MAIN_VIEW_STACK_LOCATIONGROUP_VIEW:uint = 2;
		public static const MAIN_VIEW_STACK_USER_MGMT_VIEW:uint = 3;
		public static const MAIN_VIEW_STACK_TRANSPORTUNIT_VIEW:uint = 4;
		public var mainViewStackIndex:uint = MAIN_VIEW_STACK_EMPTY_SCREEN_VIEW;
		
		public const UPLOAD_URL:String = "/OpenWMS/upload";
		public const DIRECTORY_NAME:String = "data";
		
		public var allLocationGroups:ArrayCollection = new ArrayCollection();
		public var allLocations:ArrayCollection = new ArrayCollection();
		public var allUsers:ArrayCollection = new ArrayCollection();
		public var selectedUser:User = null;
		public var locationGroupTree:TreeNode;
		public var image:Object;

		private static var instance:ModelLocator;

		public function ModelLocator(enforcer:SingletonEnforcer) {}

		public static function getInstance():ModelLocator {
			if ( instance == null ) {
				instance = new ModelLocator( new SingletonEnforcer );
			}
			return instance;
		}
	}
}

class SingletonEnforcer {}    