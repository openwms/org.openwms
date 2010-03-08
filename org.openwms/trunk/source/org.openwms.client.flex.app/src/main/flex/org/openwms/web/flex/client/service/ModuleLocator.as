package org.openwms.web.flex.client.service {
	import mx.controls.List;
	import mx.modules.IModuleInfo;
	

	/**
	 * A ModuleLocator.
	 * 
	 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
	 * @version $Revision: 235 $
	 * @since 0.1
	 */
	[Bindable]
	public class ModuleLocator {
        private static var instance:ModuleLocator;

        public function ModuleLocator(enforcer:SingletonEnforcer) {}

        public static function getInstance():ModuleLocator {
            if ( instance == null ) {
                instance = new ModuleLocator( new SingletonEnforcer );
            }
            return instance;
        }
        
        public static function registerModule(name:String, module: IModuleInfo) {}
        public static function isRegistered(module: IModuleInfo):Boolean {}
        public static function getRegisteredModules():List {}
    }
}
class SingletonEnforcer {}	
