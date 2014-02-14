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
package org.openwms.tms.domain.order {

    [Bindable]
    [RemoteClass(alias="org.openwms.tms.domain.order.TransportOrder")]
    /**
     * A TransportOrder is used to move <code>TransportUnit</code>s from a current
     * <code>Location</code> to a target <code>Location</code>.
     *
     * @version $Revision$
     * @since 0.1
     */
    public class TransportOrder extends TransportOrderBase {

        import org.openwms.common.domain.TransportUnit;
        import org.openwms.tms.domain.values.TransportOrderState;

        /**
         * Constructor. Setting the state to CREATED. Allows to set a TransportUnit.
         *
         * @param transportUnit The TransportUnit for this order.
         */
        public function TransportOrder(transportUnit:TransportUnit = null) {
            if (transportUnit != null) {
                this._transportUnit = transportUnit;
            }
            this.state = TransportOrderState.CREATED;
        }

        /**
         * Accessor.
         *
         * @param value The startDate
         */
        public function set startDate(value:Date):void {
            _startDate = value;
        }

        /**
         * Accessor.
         *
         * @param value The endDate
         */
        public function set endDate(value:Date):void {
            _endDate = value;
        }
    }
}

