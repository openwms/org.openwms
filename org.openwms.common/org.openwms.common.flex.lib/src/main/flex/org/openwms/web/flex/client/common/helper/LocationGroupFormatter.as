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
package org.openwms.web.flex.client.common.helper {

    import mx.controls.dataGridClasses.DataGridColumn;
    import org.openwms.web.flex.client.common.model.TreeNode;
    import org.openwms.common.domain.LocationGroup;

    /**
     * A LocationGroupFormatter, is a helper class to format some fields specific to LocationGroups.
     *
     * @version $Revision: 1409 $
     * @since 0.1
     * @see org.openwms.common.domain.LocationGroup
     */
    public class LocationGroupFormatter {

        /**
         * Format a TreeNode as a String. Try to return the name attribute.
         *
         * @param item The TreeNode
         * @return The name of the LocationGroup
         */
        public static function formatLocationGroup(item : TreeNode) : String {
            if (item != null && item.getData() != null) {
                return item.getData()['name'];
            }
            return "";
        }

        /**
         * Check whether the locationGroup has a parent and return its name, otherwise return --.
         *
         * @locationGroup The LocationGroup to read the parent from
         * @return The name of the parent LocationGroup
         */
        public static function formatParentLocationGroup(locationGroup : LocationGroup) : String {
            if (locationGroup.parent != null) {
                return locationGroup.parent.name;
            }
            return "--";
        }

        /**
         * Resolve the given dg.dataField from the properties of a LocationGroup. The locationGroup is
         * expected to be stored in item.
         *
         * @param item The object that stores the LocationGroup as locationGroup
         * @param dg The column used to resolve the locationGroup by its dataField
         * @return The dataField of the LocationGroup or --
         */
        public static function locationGroupField(item : Object, dg : DataGridColumn) : String {
            if (item.locationGroup != null) {
                return item.locationGroup[dg.dataField];
            }
            return "--";
        }
    }
}

