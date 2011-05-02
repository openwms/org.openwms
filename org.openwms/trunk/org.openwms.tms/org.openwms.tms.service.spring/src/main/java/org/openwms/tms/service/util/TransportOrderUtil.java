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
package org.openwms.tms.service.util;

import java.util.ArrayList;
import java.util.List;

import org.openwms.tms.domain.values.TransportOrderState;
import org.openwms.tms.util.event.TransportServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A TransportOrderUtil.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public final class TransportOrderUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Hide constructor of utility classes.
     */
    private TransportOrderUtil() {}

    public static TransportServiceEvent.TYPE convertToEventType(TransportOrderState newState) {
        switch (newState) {
        case FINISHED:
            return TransportServiceEvent.TYPE.TRANSPORT_FINISHED;
        case CANCELED:
            return TransportServiceEvent.TYPE.TRANSPORT_CANCELED;
        case INTERRUPTED:
            return TransportServiceEvent.TYPE.TRANSPORT_INTERRUPTED;
        case ONFAILURE:
            return TransportServiceEvent.TYPE.TRANSPORT_ONFAILURE;
        }
        return TransportServiceEvent.TYPE.TRANSPORT_CANCELED;
    }

    /**
     * Unfortunately Flex clients can't handle Long values and only understand
     * integers. Hence we need a small utility method to convert one list in
     * another.
     * 
     * @param values
     *            The list of intergers to be converted
     * @return a new list of long values
     */
    public static List<Long> getLongList(List<Integer> values) {
        List<Long> longList = new ArrayList<Long>(values.size());
        for (Integer number : values) {
            longList.add(number.longValue());
        }
        return longList;
    }

}
