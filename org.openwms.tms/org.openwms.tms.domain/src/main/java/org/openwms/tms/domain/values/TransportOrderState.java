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
package org.openwms.tms.domain.values;

/**
 * A TransportOrderState defines alll possible states of {@link org.openwms.tms.domain.order.TransportOrder}s.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.tms.domain.order.TransportOrder
 * @deprecated Use TransportOrder.State
 */
@Deprecated
public enum TransportOrderState {

    /**
     * Status of new created <code>TransportOrder</code>s.
     */
    CREATED(10),

    /**
     * Status of a full initialized <code>TransportOrder</code>, ready to be started.
     */
    INITIALIZED(20),

    /**
     * A started and active<code>TransportOrder</code>, ready to be executed.
     */
    STARTED(30),

    /**
     * Status to indicate that the <code>TransportOrder</code> is paused. Not active anymore.
     */
    INTERRUPTED(40),

    /**
     * Status to indicate a failure on the <code>TransportOrder</code>. Not active anymore.
     */
    ONFAILURE(50),

    /**
     * Status of a aborted <code>TransportOrder</code>. Not active anymore.
     */
    CANCELED(60),

    /**
     * Status to indicate that the <code>TransportOrder</code> completed successfully.
     */
    FINISHED(70);

    private int order;

    private TransportOrderState(int sortOrder) {
        this.order = sortOrder;
    }

    /**
     * Get the order.
     * 
     * @return the order.
     */
    public int getOrder() {
        return order;
    }

}
