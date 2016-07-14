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

import java.util.List;

import org.openwms.common.location.Location;
import org.openwms.common.location.LocationGroup;
import org.openwms.core.exception.StateChangeException;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportOrderRepository;
import org.openwms.tms.TransportOrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderStarterImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY, noRollbackFor = { StateChangeException.class })
@Component
public class TransportOrderStarterImpl implements TransportOrderStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportOrderStarterImpl.class);
    @Autowired
    private TransportOrderRepository dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(TransportOrder transportOrder) throws StateChangeException {
        LocationGroup lg = transportOrder.getTargetLocationGroup();
        Location loc = transportOrder.getTargetLocation();
        if (null == lg && null == loc) {
            // At least one target must be set
            throw new StateChangeException(
                    "Neither a LocationGroup nor a Location are set as target, thus impossible to start the TransportOrder");
        }
        if (lg != null && lg.isInfeedBlocked() && loc != null && !loc.isIncomingActive()) {
            // At least one target must be free for infeed
            throw new StateChangeException("Cannot start the TransportOrder because both targets are blocked");
        }
        if (lg != null && lg.isInfeedBlocked()) {
            throw new StateChangeException("Cannot start the TransportOrder because TargetLocationGroup is blocked");
        }
        if (loc != null && !loc.isIncomingActive()) {
            throw new StateChangeException("Cannot start the TransportOrder because TargetLocation is blocked");
        }
        List<TransportOrder> others = dao.findForTUinState(transportOrder.getTransportUnit(),
                TransportOrderState.STARTED, TransportOrderState.INTERRUPTED);
        if (!others.isEmpty()) {
            throw new StateChangeException(
                    "Cannot start the TransportOrder because one or more active TransportOrders exist");
        }
        transportOrder.setState(TransportOrderState.STARTED);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("TransportOrder " + transportOrder.getId() + " started at " + transportOrder.getStartDate());
        }
    }
}