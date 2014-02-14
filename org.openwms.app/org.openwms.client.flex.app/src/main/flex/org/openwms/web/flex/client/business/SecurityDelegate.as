/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
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
    [ManagedEvent(name="APP.LOGIN_OK")]
    [ManagedEvent(name="APP.LOGIN_NOK")]
    [ManagedEvent(name="APP.CREDENTIALS_VALID")]
    [ManagedEvent(name="APP.CREDENTIALS_INVALID")]
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
         *
         * @param event expected to store data.username and data.password as credentials
         */
        public function login(event : ApplicationEvent) : void {
            identity.login(event.data.username, event.data.password, loginOk, onFault);
        }

        [Observer("APP.UNLOCK")]
        /**
         * Call a service to check whether the credentials are valid.
         * Tide event observers : APP.CHECK_CREDENTIAL
         *
         * @param event expected to store data.username and data.password as credentials
         */
        public function unlock(event : ApplicationEvent) : void {
            tideContext.securityContextHelper.checkCredentials(event.data.username, event.data.password, onValid, onLoginError);
        }

        private function onValid(event : TideResultEvent) : void {
            var res : Boolean = event.result as Boolean;
            if (res == true) {
                dispatchEvent(new ApplicationEvent(ApplicationEvent.APP_CREDENTIALS_VALID));
            } else {
                trace("Invalid credentials");
                dispatchEvent(new ApplicationEvent(ApplicationEvent.APP_CREDENTIALS_INVALID));
            }
        }

        private function loginOk(event : TideResultEvent) : void {
            tideContext.securityContextHelper.getLoggedInUser(onComplete, onFault);
        }

        private function onComplete(event : TideResultEvent) : void {
            modelLocator.loggedInUser = event.result as User;
            dispatchEvent(new ApplicationEvent(ApplicationEvent.APP_LOGIN_OK));
        }

        private function onLoginError(event : TideFaultEvent) : void {
            var e : ApplicationEvent = new ApplicationEvent(ApplicationEvent.APP_CREDENTIALS_INVALID);
            e.data = {event: event};
            dispatchEvent(e);
        }

        private function onFault(event : TideFaultEvent) : void {
            trace("Error executing operation on security service:" + event.fault);
            var e : ApplicationEvent = new ApplicationEvent(ApplicationEvent.APP_LOGIN_NOK);
            e.data = {event: event};
            dispatchEvent(e);
        }
    }
}

