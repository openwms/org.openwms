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
package org.openwms.tms.service.spring.delegate;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openwms.common.domain.TransportUnit;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.values.TransportOrderState;
import org.openwms.tms.domain.values.TransportStartComparator;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.delegate.TransportOrderStarter;
import org.openwms.tms.service.delegate.TransportOrderStateDelegate;
import org.openwms.tms.service.exception.StateChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A DefaultOrderStateDelegate. Lazy instantiated, only when needed. Thus it is
 * possible to override this bean and prevent instantiation.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
@Lazy
@Component
@Transactional(propagation = Propagation.MANDATORY)
public class DefaultOrderStateDelegate implements TransportOrderStateDelegate {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private TransportOrderDao dao;
    private TransportOrderStarter starter;

    /**
     * Create a new DefaultOrderStateDelegate.
     * 
     * @param dao
     *            TransportOrderDao is required
     * @param starter
     *            TransportOrderStarter is required
     */
    @Autowired
    public DefaultOrderStateDelegate(TransportOrderDao dao, TransportOrderStarter starter) {
        this.dao = dao;
        this.starter = starter;
    }

    /**
     * {@inheritDoc} Search for already {@link TransportOrderState#CREATED}
     * {@link TransportOrder}s for this transportUnit and try to initialize
     * them. When initialization is done try to start them.
     * 
     * @see org.openwms.tms.service.delegate.TransportOrderStateDelegate#afterCreation(java.lang.Long)
     */
    @Override
    public void afterCreation(TransportUnit transportUnit) {
        List<TransportOrder> transportOrders = findInState(transportUnit, TransportOrderState.CREATED);
        for (TransportOrder transportOrder : transportOrders) {
            boolean go = initialize(transportOrder);
            if (go) {
                try {
                    starter.start(transportOrder);
                } catch (StateChangeException sce) {
                    // Not starting a transport here is not a problem, so be
                    // quiet
                    logger.warn(sce.getMessage());
                }
            }
        }
    }

    /**
     * {@inheritDoc} Just search the next TransportOrder for the TransportUnit
     * and try to start it.
     * 
     * @see org.openwms.tms.service.delegate.TransportOrderStateDelegate#afterFinish(java.lang.Long)
     */
    @Override
    public void afterFinish(Long id) {
        startNextForTu(id);
    }

    /**
     * {@inheritDoc}
     * 
     * Just search the next TransportOrder for the TransportUnit and try to
     * start it.
     * 
     * @see org.openwms.tms.service.delegate.TransportOrderStateDelegate#onCancel(java.lang.Long)
     */
    @Override
    public void onCancel(Long id) {
        startNextForTu(id);
    }

    /**
     * {@inheritDoc}
     * 
     * Just search the next TransportOrder for the TransportUnit and try to
     * start it.
     * 
     * @see org.openwms.tms.service.delegate.TransportOrderStateDelegate#onFailure(java.lang.Long)
     */
    @Override
    public void onFailure(Long id) {
        startNextForTu(id);
    }

    private void startNextForTu(Long id) {
        TransportOrder transportOrder = dao.findById(id);
        if (null == transportOrder) {
            logger.warn("TransportOrder with id:" + id + " could not be loaded");
            return;
        }
        List<TransportOrder> transportOrders = findInState(transportOrder.getTransportUnit(),
                TransportOrderState.INITIALIZED);
        Collections.sort(transportOrders, new TransportStartComparator());
        if (transportOrders == null) {
            if (logger.isDebugEnabled()) {
                logger.debug("No waiting TransportOrders for TransportUnit [" + transportOrder.getTransportUnit()
                        + "] found");
            }
            return;
        }
        for (TransportOrder to : transportOrders) {
            try {
                starter.start(to);
                break;
            } catch (StateChangeException sce) {
                if (logger.isDebugEnabled()) {
                    // Not starting a transport here is not a problem, so be
                    // quiet
                    logger.debug(sce.getMessage());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Just search the next TransportOrder for the TransportUnit and try to
     * start it.
     * 
     * @see org.openwms.tms.service.delegate.TransportOrderStateDelegate#onInterrupt(java.lang.Long)
     */
    @Override
    public void onInterrupt(Long id) {
        startNextForTu(id);
    }

    private boolean initialize(TransportOrder transportOrder) {
        try {
            transportOrder.setState(TransportOrderState.INITIALIZED);
        } catch (IllegalStateException ise) {
            logger.info("Could not initialize TransportOrder [" + transportOrder.getId() + "]. Message:"
                    + ise.getMessage());
            return false;
        }
        transportOrder.setSourceLocation(transportOrder.getTransportUnit().getActualLocation());
        if (logger.isDebugEnabled()) {
            logger.debug("TransportOrder " + transportOrder.getId() + " INITIALIZED");
        }
        return true;
    }

    private List<TransportOrder> findInState(TransportUnit transportUnit, TransportOrderState... orderStates) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("transportUnit", transportUnit);
        params.put("states", Arrays.asList(orderStates));
        List<TransportOrder> transportOrders = dao
                .findByNamedParameters(TransportOrder.NQ_FIND_FOR_TU_IN_STATE, params);
        return transportOrders;
    }
}
