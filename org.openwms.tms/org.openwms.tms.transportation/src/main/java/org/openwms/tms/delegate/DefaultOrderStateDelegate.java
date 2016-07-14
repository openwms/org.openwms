/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
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
package org.openwms.tms.delegate;

import java.util.Collections;
import java.util.List;

import org.openwms.common.CommonGateway;
import org.openwms.common.TransportUnit;
import org.openwms.tms.StateChangeException;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportOrderRepository;
import org.openwms.tms.TransportStartComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A DefaultOrderStateDelegate. Lazy instantiated, only when needed. Thus it is possible to override this bean and prevent instantiation.
 *
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Lazy
@Component
public class DefaultOrderStateDelegate implements TransportOrderStateDelegate {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultOrderStateDelegate.class);
    private TransportOrderRepository dao;
    private TransportOrderStarter starter;
    private CommonGateway commonGateway;

    /**
     * Create a new DefaultOrderStateDelegate.
     *
     * @param dao TransportOrderDao is required
     * @param starter TransportOrderStarter is required
     * @param commonGateway The common gateway
     */
    @Autowired
    public DefaultOrderStateDelegate(TransportOrderRepository dao, TransportOrderStarter starter, CommonGateway commonGateway) {
        this.dao = dao;
        this.starter = starter;
        this.commonGateway = commonGateway;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Search for already {@link TransportOrder.State#CREATED} {@link TransportOrder}s for this transportUnit and try to initialize them.
     * When initialization is done try to start them.
     */
    @Override
    public void afterCreation(TransportUnit transportUnit) {
        List<TransportOrder> transportOrders = findInState(transportUnit.getBk(), TransportOrder.State.CREATED);
        for (TransportOrder transportOrder : transportOrders) {
            boolean go = initialize(transportOrder);
            if (go) {
                try {
                    starter.start(transportOrder);
                } catch (StateChangeException sce) {
                    // Not starting a transport here is not a problem, so be
                    // quiet
                    LOGGER.warn(sce.getMessage());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Just search the next TransportOrder for the TransportUnit and try to start it.
     */
    @Override
    public void afterFinish(Long id) {
        startNextForTu(id);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Just search the next TransportOrder for the TransportUnit and try to start it.
     */
    @Override
    public void onCancel(Long id) {
        startNextForTu(id);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Just search the next TransportOrder for the TransportUnit and try to start it.
     */
    @Override
    public void onFailure(Long id) {
        startNextForTu(id);
    }

    private void startNextForTu(Long id) {
        TransportOrder transportOrder = dao.findOne(id);
        if (null == transportOrder) {
            LOGGER.warn("TransportOrder with id:" + id + " could not be loaded");
            return;
        }
        List<TransportOrder> transportOrders = findInState(transportOrder.getTransportUnitBK(),
                TransportOrder.State.INITIALIZED);
        Collections.sort(transportOrders, new TransportStartComparator());
        for (TransportOrder to : transportOrders) {
            try {
                starter.start(to);
                break;
            } catch (StateChangeException sce) {
                if (LOGGER.isDebugEnabled()) {
                    // Not starting a transport here is not a problem, so be
                    // quiet
                    LOGGER.debug(sce.getMessage());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * Just search the next TransportOrder for the TransportUnit and try to start it.
     */
    @Override
    public void onInterrupt(Long id) {
        startNextForTu(id);
    }

    private boolean initialize(TransportOrder transportOrder) {
        try {
            transportOrder.setState(TransportOrder.State.INITIALIZED);
        } catch (StateChangeException sce) {
            LOGGER.info("Could not initialize TransportOrder [" + transportOrder.getPk() + "]. Message:"
                    + sce.getMessage());
            return false;
        }
        transportOrder.setSourceLocation(commonGateway.getLocation(transportOrder.getTransportUnitBK()).get().toString());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("TransportOrder " + transportOrder.getPk() + " INITIALIZED");
        }
        return true;
    }

    private List<TransportOrder> findInState(String transportUnitBK, TransportOrder.State... orderStates) {
        return dao.findByTransportUnitBKAndState(transportUnitBK, orderStates);
    }
}