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
package org.openwms.web.flex.client.tms.event {

    import flash.events.Event;

    /**
     * A TransportOrderEvent.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class TransportOrderEvent extends Event {

        /**
         * Name of the Event to load all TransportOrders from the backend.
         */
        public static const LOAD_TRANSPORT_ORDERS : String = "LOAD_TRANSPORT_ORDERS";

        /**
         * Name of the Event to create a new TransportOrder. Property data is expected as the TransportOrder to be created.
         */
        public static const CREATE_TRANSPORT_ORDER : String = "CREATE_TRANSPORT_ORDER";

        /**
         * Name of the Event to delete an existing TransportOrder. Property data is expected as the TransportOrder to be removed.
         */
        public static const DELETE_TRANSPORT_ORDER : String = "DELETE_TRANSPORT_ORDER";

        /**
         * Name of the Event to cancel an active TransportOrder. Property data is expected as the TransportOrder to be canceled.
         */
        public static const CANCEL_TRANSPORT_ORDER : String = "CANCEL_TRANSPORT_ORDER";

        /**
         * Name of the Event to redirect a TransportOrder. Property data is expected as the TransportOrder to be redirected.
         */
        public static const REDIRECT_TRANSPORT_ORDER : String = "REDIRECT_TRANSPORT_ORDER";

        /**
         * Store arbitrary data.
         */
        public var data : *;

        /**
         * Constructor.
         */
        public function TransportOrderEvent(type : String, bubbles : Boolean=true, cancelable : Boolean=false) {
            super(type, bubbles, cancelable);
        }

        /**
         * Just a copy of the event itself including the data field.
         *
         * @return a copy of this
         */
        public override function clone() : Event {
            var e : TransportOrderEvent = new TransportOrderEvent(type);
            e.data = data;
            return e;
        }

        /**
         * Simple override.
         *
         * @return the type of event
         */
        public override function toString() : String {
            return formatToString("TransportOrderEvent", "type");
        }
    }
}
