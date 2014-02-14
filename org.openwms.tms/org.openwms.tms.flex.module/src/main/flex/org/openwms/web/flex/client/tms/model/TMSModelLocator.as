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
package org.openwms.web.flex.client.tms.model {

    import mx.collections.ArrayCollection;
    import mx.formatters.DateFormatter;
    import org.openwms.tms.domain.values.TransportOrderState;

    [Name]
    [Bindable]
    /**
     * A TMSModelLocator.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class TMSModelLocator {

        public var allTransportOrders:ArrayCollection = new ArrayCollection();
        public var allStates:ArrayCollection = new ArrayCollection(TransportOrderState.constants);
        // --------------------------------------------------------------------
        // General stuff
        // --------------------------------------------------------------------
        /**
         * Date format including the time.
         */
        public static const DT_FORMAT_STRING:String = "DD.MM.YYYY HH:NN:SS";
        /**
         * Date format without timestamp.
         */
        public static const SIMPLE_DT_FORMAT:String = "DD.MM.YYYY";
        /**
         * A DateFormatter that uses the DT_FORMAT_STRING.
         */
        public const dateTimeFormatter:DateFormatter = new DateFormatter();
        /**
         * A DateFormatter that uses the SIMPLE_DT_FORMAT.
         */
        public const dateFormatter:DateFormatter = new DateFormatter();

        // TransportOrderView
        public static const WIDTH_STATE:Number = 70;
        public static const WIDTH_PRIORITY:Number = 70;

        /**
         * Constructor.
         */
        public function TMSModelLocator() {
            allStates.removeItemAt(allStates.getItemIndex(TransportOrderState.CREATED));
            allStates.removeItemAt(allStates.getItemIndex(TransportOrderState.INITIALIZED));
            allStates.removeItemAt(allStates.getItemIndex(TransportOrderState.STARTED));
            dateFormatter.formatString = SIMPLE_DT_FORMAT;
            dateTimeFormatter.formatString = DT_FORMAT_STRING;
        }
    }
}

