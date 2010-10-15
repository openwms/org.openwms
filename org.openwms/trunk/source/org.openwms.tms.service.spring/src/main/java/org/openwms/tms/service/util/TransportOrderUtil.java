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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.openwms.common.domain.TransportUnit;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.values.TransportOrderState;
import org.openwms.tms.integration.TransportOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * A TransportOrderUtil.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 * @since 0.1
 */
@Service
public class TransportOrderUtil {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("transportOrderDao")
    private TransportOrderDao dao;

    public TransportOrderUtil(TransportOrderDao dao) {
        this.dao = dao;
    }

    /**
     * Find and return a list of {@link TransportOrder}s in state
     * {@link TransportOrderState#STARTED} or
     * {@link TransportOrderState#INTERRUPTED}.
     * 
     * @param transportUnit
     *            The {@link TransportUnit} to search for
     * @return A list of {@link TransportOrder}s or an empty list
     */
    public List<TransportOrder> findActiveOrders(TransportUnit transportUnit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transportUnit", transportUnit);
        Set<TransportOrderState> states = new HashSet<TransportOrderState>(2);
        states.add(TransportOrderState.STARTED);
        states.add(TransportOrderState.INTERRUPTED);
        params.put("states", states);
        List<TransportOrder> others = dao.findByNamedParameters(TransportOrder.NQ_FIND_FOR_TU_IN_STATE, params);
        if (others == null || others.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("No active TransportOrders found for TransportUnit with ID : " + transportUnit.getId());
            }
            return Collections.emptyList();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Active TransportOrder found for TransportUnit with ID : " + transportUnit.getId());
        }
        return others;
    }

    /**
     * Find and return all {@link TransportOrder}s which are waiting to be
     * started, that means in state {@link TransportOrderState#CREATED} and
     * {@link TransportOrderState#INITIALIZED}.
     * 
     * @param transportUnit
     *            The {@link TransportUnit} to search for
     * @return A list of {@link TransportOrder}s or an empty list
     */
    public List<TransportOrder> findOrdersToStart(TransportUnit transportUnit) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transportUnit", transportUnit);
        Set<TransportOrderState> states = new HashSet<TransportOrderState>(2);
        states.add(TransportOrderState.CREATED);
        states.add(TransportOrderState.INITIALIZED);
        params.put("states", states);
        List<TransportOrder> others = dao.findByNamedParameters(TransportOrder.NQ_FIND_FOR_TU_IN_STATE, params);
        if (others == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No initialized TransportOrders found for TransportUnit with ID : "
                        + transportUnit.getId());
            }
            return Collections.emptyList();
        }
        if (logger.isDebugEnabled()) {
            logger.debug("Initialized TransportOrder found for TransportUnit with ID : " + transportUnit.getId());
        }
        return others;
    }
}
