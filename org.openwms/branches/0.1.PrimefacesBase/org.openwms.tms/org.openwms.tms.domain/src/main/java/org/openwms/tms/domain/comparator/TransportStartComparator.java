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
package org.openwms.tms.domain.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.openwms.tms.domain.order.TransportOrder;

/**
 * A TransportStartComparator. I used to sort TransportOrders is a particular
 * order. Unfortunately some fields of the TransportOrder class are defined as
 * Enums for a better handling in business logic. Persisting these fields as
 * Strings makes it impossible to do a proper sorting in the database with JPA.
 * Hence we must do it with Comparators in the application layer.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.tms.domain.values.PriorityLevel
 */
public class TransportStartComparator implements Comparator<TransportOrder>, Serializable {

    private static final long serialVersionUID = -5977273516346830448L;

    /**
     * First the priority or orders is compared, when both are equals the id is
     * compared too.
     * 
     * @param o1
     *            FirstOrder to compare
     * @param o2
     *            Second order to compare
     * @return a negative integer, zero, or a positive integer as the first
     *         argument is less than, equal to, or greater than the second.
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(TransportOrder o1, TransportOrder o2) {
        if (o1.getPriority().getOrder() > o2.getPriority().getOrder()) {
            return -1;
        } else if (o1.getPriority().getOrder() < o2.getPriority().getOrder()) {
            return 1;
        }
        if (o1.getId() < o2.getId()) {
            return -1;
        } else if (o1.getId() > o2.getId()) {
            return 1;
        }
        return 0;
    }
}