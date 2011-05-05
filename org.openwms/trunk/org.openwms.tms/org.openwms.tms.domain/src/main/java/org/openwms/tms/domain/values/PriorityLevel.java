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

/**
 * A PriorityLevel is used to prioritize
 * {@link org.openwms.tms.domain.order.TransportOrder}s.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.tms.domain.order.TransportOrder
 */
public enum PriorityLevel {

    /**
     * Lowest priority.
     */
    LOWEST(10),

    /**
     * Low priority.
     */
    LOW(20),

    /**
     * Standard priority.
     */
    NORMAL(30),

    /**
     * High priority.
     */
    HIGH(40),

    /**
     * Highest priority.
     */
    HIGHEST(50);

    private int order;

    private PriorityLevel(int order) {
        this.order = order;
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
