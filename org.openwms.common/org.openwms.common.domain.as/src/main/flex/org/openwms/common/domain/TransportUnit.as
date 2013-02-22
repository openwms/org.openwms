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
package org.openwms.common.domain {

    import mx.controls.dataGridClasses.DataGridColumn;
    import org.openwms.common.domain.values.Barcode;

    [Bindable]
    [RemoteClass(alias="org.openwms.common.domain.TransportUnit")]
    /**
     * A TransportUnit is something like a box, a toad, a bin or a palette that has
     * to be moved around.
     * <p>
     * Used as a container to transport items and <code>LoadUnit</code>s. It can be
     * moved between <code>Location</code>s.
     * </p>
     *
     * @version $Revision$
     * @since 0.1
     */
    public class TransportUnit extends TransportUnitBase {

        /**
         * Allows to create a TransportUnit with a given Barcode.
         *
         * @param barcode The barcode as String
         */
        public function TransportUnit(barcode : String=null) {
            if (barcode != null) {
                this._barcode = new Barcode(barcode);
            }
        }

        /**
         * Checks whether the <code>TransportUnit</code> is empty or not.
         *
         * @return <code>true</code> when empty, otherise <code>false</code>
         */
        public function isEmpty() : Boolean {
            return _empty;
        }

        /**
         * Resolve the <code>Barcode</code> from them items field dg.
         *
         * @param item The item to investigate
         * @param dg The datagrid column
         * @return The Barcode or '--' when <code>null</code>
         */
        public function formatBarcode(item : Object, dg : DataGridColumn) : String {
            if (item[dg.dataField] != null) {
                return String(item[dg.dataField].barcode);
            }
            return "--";
        }

        /**
         * Resolve the <code>Location</code> from them items field dg.
         *
         * @param item The item to investigate
         * @param dg The datagrid column
         * @return The <code>Location</code> or '--' when <code>null</code>
         */
        public function formatLocation(item : Object, dg : DataGridColumn) : String {
            if (item[dg.dataField] != null) {
                return String(item[dg.dataField].actualLocation);
            }
            return "--";
        }

        /**
         * For compliance.
         *
         * @param value New date
         */
        public function set creationDate(value : Date) : void {
            // Not allowed
        }
    }
}