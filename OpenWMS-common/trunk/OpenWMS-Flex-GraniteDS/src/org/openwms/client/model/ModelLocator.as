package org.openwms.client.model {
  import com.adobe.cairngorm.model.IModelLocator;
  
  import mx.collections.ArrayCollection;
  [Bindable]
  public class ModelLocator implements IModelLocator {
    // Singleton Instanz
    private static var instance:ModelLocator;
    // Singleton Konstruktor
    public function ModelLocator(enforcer:SingletonEnforcer) {
    }
    // Zugriff auf Singleton-Instanz
    public static function getInstance():ModelLocator {
      if ( instance == null ) {
        instance = new ModelLocator( new SingletonEnforcer );
      }
      return instance;
    }
    // Konstanten fuer die einzelnen Views
    public static const MAIN_VIEW_STACK_START_SCREEN:uint = 0;
    public static const MAIN_VIEW_STACK_LOCATION_VIEW:uint = 1;
    public static const MAIN_VIEW_STACK_LOCATIONGROUP_VIEW:uint = 2;
    // Die aktuelle View
    public var mainViewStackIndex:uint = MAIN_VIEW_STACK_START_SCREEN;
    public var allLocationGroups:ArrayCollection = new ArrayCollection();
    public var allLocations:ArrayCollection = new ArrayCollection();
  }// end class
}// end package

class SingletonEnforcer {}    