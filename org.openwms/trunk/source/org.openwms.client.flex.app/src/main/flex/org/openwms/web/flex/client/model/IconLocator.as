/*
 * OpenWMS, the Open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.web.flex.client.model {

	import com.adobe.cairngorm.model.IModelLocator;

    /**
     * An IconLocator.
     * 
     * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
     * @version $Revision: 235 $
     */
    [Bindable]
    public class IconLocator implements IModelLocator {

        [Embed(source="/assets/icons/fuegue/user--plus.png")]
        public static var iconUserAdd:Class
 
        private static var instance:IconLocator;

        public function IconLocator(enforcer:SingletonEnforcer) {}

        public static function getInstance():IconLocator {
            if ( instance == null ) {
                instance = new IconLocator( new SingletonEnforcer );
            }
            return instance;
        }
    }
}
class SingletonEnforcer {}