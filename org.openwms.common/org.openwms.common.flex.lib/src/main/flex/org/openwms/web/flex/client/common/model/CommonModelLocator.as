/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.web.flex.client.common.model {

    import mx.collections.ArrayCollection;
    import mx.formatters.DateFormatter;
    import mx.events.CollectionEvent;
    import org.openwms.web.flex.client.common.event.LocationGroupEvent;
    import org.openwms.web.flex.client.common.model.TreeNode;

    [Name]
    [ManagedEvent(name = "LG.COLL_LOCATION_GROUPS_REFRESHED")]
    [Bindable]
    /**
     * A CommonModelLocator.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public class CommonModelLocator {

        private var registered : Boolean = false;

        // --------------------------------------------------------------------
        // General stuff
        // --------------------------------------------------------------------
        /**
         * Date format including the time.
         */
        public static const DT_FORMAT_STRING : String = "DD.MM.YYYY HH:NN:SS";

        /**
         * Date format without timestamp.
         */
        public static const SIMPLE_DT_FORMAT : String = "DD.MM.YYYY";

        /**
         * A DateFormatter that uses the DT_FORMAT_STRING.
         */
        public const dateTimeFormatter : DateFormatter = new DateFormatter();

        /**
         * A DateFormatter that uses the SIMPLE_DT_FORMAT.
         */
        public const dateFormatter : DateFormatter = new DateFormatter();

        /**
         * Collection of all TransportUnitTypes.
         */
        public var allTransportUnitTypes : ArrayCollection = new ArrayCollection();

        /**
         * Collection of all TransportUnits.
         */
        public var allTransportUnits : ArrayCollection = new ArrayCollection();

        /**
         * Collection of all Locations.
         */
        public var allLocations : ArrayCollection = new ArrayCollection();

        /**
         * Collection of all LocationTypes.
         */
        public var allLocationTypes : ArrayCollection = new ArrayCollection();

        // --------------------------------------------------------------------
        // LocationGroupView
        // --------------------------------------------------------------------
        /**
         * Collection of all LocationGroups.
         */
        public var allLocationGroups : ArrayCollection = new ArrayCollection();

        /**
         * Generated Tree of all groups.
         */
        public var locationGroupTree : TreeNode;

        public static const WIDTH_LOCATION_GROUP_NAME : Number = 60;

        // --------------------------------------------------------------------
        // LocationView
        // --------------------------------------------------------------------
        public static const WIDTH_LOCATION : Number = 80;

        // --------------------------------------------------------------------
        // TransportUnitView
        // --------------------------------------------------------------------
        public static const WIDTH_BARCODE : Number = 80;

        /**
         * Constructor.
         */
        public function CommonModelLocator() {
            dateFormatter.formatString = SIMPLE_DT_FORMAT;
            dateTimeFormatter.formatString = DT_FORMAT_STRING;
        }

        /**
         * Register event listeners to all managed collections. A Tide event is fired on
         * collectionChange event. This is useful to update any visual components that
         * rely on these collections dynamically. Registration happens only the first time,
         * hence it doesn't matter how often this method is called.
         */
        public function registerEventListeners() : void {
            if (registered) {
                return;
            }
            allLocationGroups.addEventListener(CollectionEvent.COLLECTION_CHANGE, fireEvent);
            registered = true;
        }

        private function fireEvent(e : Event) : void {
            dispatchEvent(new LocationGroupEvent(LocationGroupEvent.COLLECTION_REFRESHED));
        }
    }
}

