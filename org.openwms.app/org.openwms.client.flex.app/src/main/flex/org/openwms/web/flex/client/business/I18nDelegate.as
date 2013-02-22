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
    import mx.logging.ILogger;
    import mx.logging.Log;

    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.openwms.core.domain.system.I18n;
    import org.openwms.web.flex.client.event.I18nEvent;
    import org.openwms.web.flex.client.model.I18nMap;
    import flash.utils.Proxy;

    [Name("i18nDelegate")]
    [Bindable]
    /**
     * A I18nDelegate is responsible to do all interactions with the backend I18nService. Loading translations and updating them.
     * Is named as : i18nController
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class I18nDelegate {

        private static var logger : ILogger = Log.getLogger("org.openwms.web.flex.client.business.I18nDelegate");

        [Inject]
        /**
         * Injected TideContext.
         */
        public var tideContext : Context;

        [Inject("i18nMap")]
        /**
         * Injected i18n dictionary.
         */
        public var i18nMap : I18nMap;

        /**
         * Constructor.
         */
        public function I18nDelegate() : void {
        }

        [Observer("I18N_LOAD_ALL")]
        /**
         * Fetch a list of all i18n translations from the service.
         * Tide event observers : I18N_LOAD_ALL
         *
         * @param event Unused
         */
        public function loadI18n(event : I18nEvent) : void {
            tideContext.i18nService.findAllTranslations(onI18nLoaded, onFault);
        }

        private function onI18nLoaded(event : TideResultEvent) : void {
            var translations : ArrayCollection = event.result as ArrayCollection;
            for each (var translation : I18n in translations) {
                i18nMap[translation.cKey] = translation.lang;
            }
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on I18n service:" + event.fault);
            logger.error("Error accessing I18n service : " + event.fault);
            Alert.show("Error executing operation on I18n service");
        }
    }
}

