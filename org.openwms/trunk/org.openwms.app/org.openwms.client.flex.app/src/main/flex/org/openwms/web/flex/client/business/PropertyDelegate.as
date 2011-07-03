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
package org.openwms.web.flex.client.business {

    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    
    import org.as3commons.reflect.Type;
    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.common.domain.values.Weight;
    import org.openwms.core.domain.system.AbstractPreference;
    import org.openwms.core.domain.system.ApplicationPreference;
    import org.openwms.core.domain.system.ModulePreference;
    import org.openwms.core.domain.system.usermanagement.UserPreference;
    import org.openwms.core.domain.values.Unit;
    import org.openwms.web.flex.client.event.PropertyEvent;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.model.PreferencesModel;
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name("propertyDelegate")]
    [ResourceBundle("appError")]
    [Bindable]
    /**
     * A PropertyDelegate serves as a controller and is responsible for all interactions with the service layer
     * regarding the handling with Properties.
     * Is named as : propertyDelegate
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class PropertyDelegate {

        [Inject]
        /**
         * Injected TideContext.
         */
        public var tideContext : Context;

        [Inject]
        /**
         * Injected ModelLocator.
         */
        public var modelLocator : ModelLocator;

        [Inject]
        /**
         * Injected PreferencesModel.
         */
        public var prefsModel : PreferencesModel;

        /**
         * Constructor.
         */
        public function PropertyDelegate() : void {
        }

        [Observer("LOAD_ALL_PROPERTIES")]
        /**
         * Connect to the ConfigurationService and find all units.
         * Tide event observers : LOAD_ALL_PROPERTIES
         *
         * @param event Unused
         */
        public function findProperties(event : PropertyEvent) : void {
            tideContext.configurationService.findAll(onPropertiesLoaded, onFault);
        }

        private function onPropertiesLoaded(event : TideResultEvent) : void {
        	prefsModel.clearAll();
        	for each (var pref : AbstractPreference in event.result as ArrayCollection) {
        		if (pref is ApplicationPreference) {
		            prefsModel.appPrefs.addItem(pref);
        		} else if (pref is ModulePreference) {
        			prefsModel.modulePrefs.addItem(pref);
        		} else if (pref is UserPreference) {
        			prefsModel.userPrefs.addItem(pref);
        		}
        	}
        }

        private function onUnitsLoaded(event : TideResultEvent) : void {
            for each (var prop : Unit in event.result) {
                if (prop is Weight) {
                    var type : Type = Type.forInstance((prop as Weight).unit);
                    modelLocator.allApplicationProperties.addItem((prop as Weight).unit);
                }
            }
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on ConfigurationService: " + event);
            Alert.show(I18nUtil.trans(I18nUtil.APP_ERROR, 'error_configuration_service_operation'));
        }
    }
}

