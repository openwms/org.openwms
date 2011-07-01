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

    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.spring.Context;
    import org.granite.tide.spring.Identity;

    import org.openwms.core.domain.system.usermanagement.User;
    import org.openwms.web.flex.client.event.ApplicationEvent;
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.util.I18nUtil;

    [Name("securityDelegate")]
    [ManagedEvent(name = "APP.LOGIN_OK")]
    [ManagedEvent(name = "APP.LOGIN_NOK")]
    [ResourceBundle("appError")]
    [Bindable]
    /**
     * A SecurityDelegate.
     * Fires Tide events : APP.LOGIN_OK, APP.LOGIN_NOK
     * Is named as : securityController
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1425 $
     * @since 0.1
     */
    public class SecurityDelegate {

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
         * Injected Tide identity object.
         */
        public var identity : Identity;

        /**
         * Constructor.
         */
        public function SecurityDelegate() : void {
        }

        [Observer("APP.REQUEST_LOGIN")]
        /**
         * Force an GDS remote login call.
         * Tide event observers : APP.REQUEST_LOGIN
         */
        public function login(event : ApplicationEvent) : void {
            identity.login(event.data.username, event.data.password, loginOk, onFault);
        }

        private function loginOk(event : TideResultEvent) : void {
            tideContext.securityContextHelper.getLoggedInUser(onComplete, onFault);
        }

        private function onComplete(event : TideResultEvent) : void {
            modelLocator.loggedInUser = event.result as User;
            dispatchEvent(new ApplicationEvent(ApplicationEvent.APP_LOGIN_OK));
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on security service:" + event.fault);
            var e : ApplicationEvent = new ApplicationEvent(ApplicationEvent.APP_LOGIN_NOK);
            e.data = {event: event};
            dispatchEvent(e);
        }
    }
}

