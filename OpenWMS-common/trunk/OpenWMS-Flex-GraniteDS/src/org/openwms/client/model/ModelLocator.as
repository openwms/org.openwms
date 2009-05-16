package org.openwms.client.model {
  import com.adobe.cairngorm.model.IModelLocator;
  
  import mx.collections.ArrayCollection;
  [Bindable]
  public class ModelLocator implements IModelLocator {

    private static var instance:ModelLocator;

    public function ModelLocator(enforcer:SingletonEnforcer) {
    }

    public static function getInstance():ModelLocator {
      if ( instance == null ) {
        instance = new ModelLocator( new SingletonEnforcer );
      }
      return instance;
    }

    public static const MAIN_VIEW_STACK_START_SCREEN:uint = 0;
    public static const MAIN_VIEW_STACK_LOCATION_VIEW:uint = 1;
    public static const MAIN_VIEW_STACK_LOCATIONGROUP_VIEW:uint = 2;

    public var mainViewStackIndex:uint = MAIN_VIEW_STACK_START_SCREEN;
    public var allLocationGroups:ArrayCollection = new ArrayCollection();
    public var allLocations:ArrayCollection = new ArrayCollection();
    public var locationGroupTree:TreeNode;
  }
}

class SingletonEnforcer {}    