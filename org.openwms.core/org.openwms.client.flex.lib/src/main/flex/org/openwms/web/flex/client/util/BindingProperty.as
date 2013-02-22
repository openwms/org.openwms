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
package org.openwms.web.flex.client.util {

    /**
     * A BindingProperty object encapsulates source and target used by BindingUtils.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class BindingProperty {

        private var _site : Object;
        private var _sitePropertyName : String;
        private var _host : *;
        private var _hostPropertyName : String;
        private var _clazz : Class;
        private var _fieldName : String;

        /**
         * Create a new BindingProperty.
         *
         * @param site The visual component that should be bound
         * @param sitePropertyName The property name of the site component
         * @param host The target object
         * @param hostPropertyName The name of the target property
         * @param hostObjectType Class of the target property or null
         */
        public function BindingProperty(site:Object, sitePropertyName:String, host:Object, hostPropertyName:String, hostObjectType:Class = null, fieldName : String = null) {
            this._site = site;
            this._sitePropertyName = sitePropertyName;
            this._host = host;
            this._clazz = hostObjectType;
            this._hostPropertyName = hostPropertyName;
            this._fieldName = fieldName;
        }

        public function set site(value:Object):void {
            _site = value;
        }
        public function get site():Object {
            return _site;
        }

        public function set sitePropertyName(value:String):void {
            _sitePropertyName = value;
        }
        public function get sitePropertyName():String {
            return _sitePropertyName;
        }

        public function set host(value:Object):void {
            _host = value;
        }
        public function get host():Object {
            return _host;
        }

        public function set hostPropertyName(value:String):void {
            _hostPropertyName = value;
        }
        public function get hostPropertyName():String {
            return _hostPropertyName;
        }

        public function set clazz(value:Class):void {
            _clazz = value;
        }
        public function get clazz():Class {
            return _clazz;
        }

        public function set fieldName(value:String):void {
            _fieldName = value;
        }
        public function get fieldName():String {
            return _fieldName;
        }
    }
}

