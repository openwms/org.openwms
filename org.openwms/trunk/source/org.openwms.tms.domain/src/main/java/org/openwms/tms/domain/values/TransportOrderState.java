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
package org.openwms.tms.domain.values;

import org.openwms.tms.domain.order.TransportOrder;

/**
 * A TransportOrderState - A {@link TransportOrder} must be in one of these
 * states.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.openwms.tms.domain.order.TransportOrder
 */
public enum TransportOrderState {

    /**
     * Status of new created TransportOrders.
     */
    CREATED,

    /**
     * Status of a full initialized TransportOrder, ready to be started.
     */
    INITIALIZED,

    /**
     * A started TransportOrder, active to be executed. Only one per
     * TransportUnit allowed.
     */
    STARTED,

    /**
     * Status to indicate that the TransportOrder is paused. Not active anymore.
     */
    INTERRUPTED,

    /**
     * Status to indicate a failure on the TransportOrder. Not active anymore.
     */
    ONFAILURE,

    /**
     * Status of a aborted TransportOrder. Not active anymore.
     */
    CANCELED,

    /**
     * Status to indicate that the TransportOrder completed successfully.
     */
    FINISHED

}
