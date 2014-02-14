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
    import org.openwms.web.flex.client.model.ModelLocator;
    import org.openwms.web.flex.client.common.component.CoordinateColumn;

    /**
     * A LocationFormatter, is a helper class to format some fields specific to Locations.
     *
     * @version $Revision: 1409 $
     * @since 0.1
     * @see org.openwms.common.domain.Location
     */
    public class LocationFormatter {

        /**
         * Resolve the given dg.dataField from the properties of a Location. The location is
         * expected to be stored in item.
         *
         * @param item The object that stores the LocationId as locationId
         * @param dg The column used to resolve the location by its dataField
         * @return The dataField of the Location
         */
        public static function locationIdField(item : Object, dg : DataGridColumn) : String {
            if (dg is CoordinateColumn) {
                return item.locationId[(dg as CoordinateColumn).column];
            }
            return item.locationId[dg.dataField];
        }

        /**
         * Return a date in a defined format. As format definition ModelLocator.dateTimeFormatter is used.
         *
         * @param item The object that stores the field
         * @param dg The field name
         * @return The date as String or n/a if the date field is null
         */
        public static function formatDateField(item:Object, dg:DataGridColumn):String {
            if (item[dg.dataField] != null) {
                return ModelLocator.dateTimeFormatter.format(item[dg.dataField]);
            }
            return "n/a";
        }

    }
}

