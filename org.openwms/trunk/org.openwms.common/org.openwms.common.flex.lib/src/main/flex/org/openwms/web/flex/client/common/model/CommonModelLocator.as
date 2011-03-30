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
package org.openwms.web.flex.client.common.model {

    import mx.collections.ArrayCollection;
    import org.openwms.web.flex.client.common.model.TreeNode;
    
    /**
     * A CommonModelLocator.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    [Name("commonModelLocator")]
    [Bindable]
    public class CommonModelLocator {

        public static const DT_FORMAT_STRING:String = "DD.MM.YYYY HH:NN:SS";
        
        public var allTransportUnitTypes:ArrayCollection = new ArrayCollection();
        public var allTransportUnits:ArrayCollection = new ArrayCollection();
        public var allLocations:ArrayCollection = new ArrayCollection();
        public var allLocationTypes:ArrayCollection = new ArrayCollection();
        public var securityObjects:ArrayCollection = new ArrayCollection();

        // LocationGroupView
        public var allLocationGroups:ArrayCollection = new ArrayCollection();
        public var locationGroupTree:TreeNode;
        public static const WIDTH_LOCATION_GROUP_NAME:Number = 60;

        // LocationView        
        public static const WIDTH_LOCATION:Number = 80;

        // TransportUnitView
        public static const WIDTH_BARCODE:Number = 80;

        /**
         * Constructor.
         */
        public function CommonModelLocator() { }
    }
}