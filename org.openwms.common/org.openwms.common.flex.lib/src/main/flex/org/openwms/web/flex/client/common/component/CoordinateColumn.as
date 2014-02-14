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
package org.openwms.web.flex.client.common.component {

    import mx.controls.dataGridClasses.DataGridColumn;

    [Bindable]
    /**
     * An extension of the standard DataGridColumn to provide additional
     * sort behavior regarding Location coordinates.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: 1415 $
     * @since 0.1
     */
    public class CoordinateColumn extends DataGridColumn {

        public var column : String;

        /**
         * Constructor.
         */
        public function CoordinateColumn(columnName : String=null) {
            this.sortCompareFunction = sortCoordinate;
            super(columnName);
        }

        private function sortCoordinate(obj1 : Object, obj2 : Object) : int {
            return (obj1.locationId[column] > obj2.locationId[column]) ? -1 : 1;
        }

    }
}

