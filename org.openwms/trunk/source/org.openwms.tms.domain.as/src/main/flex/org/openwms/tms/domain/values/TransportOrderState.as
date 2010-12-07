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
package org.openwms.tms.domain.values {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="org.openwms.tms.domain.values.TransportOrderState")]
    /**
     * A TransportOrderState defines possible states used for <code>TransportOrder<code>s.
     * 
     * @version $Revision$
     * @since 0.1
     */
    public class TransportOrderState extends Enum {

        /**
         * Order is created.
         */
        public static const CREATED:TransportOrderState = new TransportOrderState("CREATED", _);
        /**
         * Order is initialized.
         */
        public static const INITIALIZED:TransportOrderState = new TransportOrderState("INITIALIZED", _);
        /**
         * Order is started.
         */
        public static const STARTED:TransportOrderState = new TransportOrderState("STARTED", _);
        /**
         * Order has been interrupted.
         */
        public static const INTERRUPTED:TransportOrderState = new TransportOrderState("INTERRUPTED", _);
        /**
         * Order is on failure.
         */
        public static const ONFAILURE:TransportOrderState = new TransportOrderState("ONFAILURE", _);
        /**
         * Order has been canceled.
         */
        public static const CANCELED:TransportOrderState = new TransportOrderState("CANCELED", _);
        /**
         * Order has been finished.
         */
        public static const FINISHED:TransportOrderState = new TransportOrderState("FINISHED", _);

        /**
         * Internal constructor.
         * 
         * @param value Enum value
         * @param restrictor see Enum class
         */
        function TransportOrderState(value:String = null, restrictor:* = null) {
            super((value || CREATED.name), restrictor);
        }

        /**
         * Return all available states in an Array.
         * 
         * @return All states in an Array
         */
        override protected function getConstants():Array {
            return constants;
        }

        /**
         * Getter to return all states in an Array.
         * 
         * @return All states in an Array
         */
        public static function get constants():Array {
            return [CREATED, INITIALIZED, STARTED, INTERRUPTED, ONFAILURE, CANCELED, FINISHED];
        }

        /**
         * Resolve a state by name.
         * 
         * @param name The name as String
         * @return The enum constant.
         */
        public static function valueOf(name:String):TransportOrderState {
            return TransportOrderState(CREATED.constantOf(name));
        }
    }
}