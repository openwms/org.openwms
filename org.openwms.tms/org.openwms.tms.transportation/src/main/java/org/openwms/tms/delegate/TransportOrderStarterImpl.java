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
import java.util.Optional;

import org.ameba.exception.NotFoundException;
import org.openwms.common.CommonGateway;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportOrderRepository;
import org.openwms.tms.TransportOrderState;
import org.openwms.tms.exception.StateChangeException;
import org.openwms.tms.targets.Location;
import org.openwms.tms.targets.LocationGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderStarterImpl is responsible to start initialized {@link TransportOrder}s.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@Transactional(propagation = Propagation.MANDATORY, noRollbackFor = {StateChangeException.class})
@Component
class TransportOrderStarterImpl implements TransportOrderStarter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportOrderStarterImpl.class);
    @Autowired
    private TransportOrderRepository repository;
    @Autowired
    private CommonGateway commonGateway;

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(TransportOrder transportOrder) throws StateChangeException {
        Optional<LocationGroup> lg = commonGateway.getLocationGroup(transportOrder.getTargetLocationGroup());
        Optional<Location> loc = commonGateway.getLocation(transportOrder.getTargetLocation());
        if (!lg.isPresent() && !loc.isPresent()) {
            // At least one target must be set
            throw new NotFoundException(
                    "Neither a valid target LocationGroup nor a Location are set, hence it is not possible to start the TransportOrder");
        }
        if (lg.isPresent() && lg.get().isInfeedBlocked()) {
            throw new StateChangeException("Cannot start the TransportOrder because TargetLocationGroup is blocked");
        }
        if (loc.isPresent() && !loc.get().isIncomingActive()) {
            throw new StateChangeException("Cannot start the TransportOrder because TargetLocation is blocked");
        }
        if (lg.isPresent()) {
            transportOrder.setTargetLocationGroup(lg.get().toString());
        }
        if (loc.isPresent()) {
            transportOrder.setTargetLocation(loc.get().toString());
        }
        List<TransportOrder> others = repository.findByTransportUnitBKAndStates(transportOrder.getTransportUnitBK(), TransportOrderState.STARTED, TransportOrderState.INTERRUPTED);
        if (!others.isEmpty()) {
            throw new StateChangeException(
                    "Cannot start a TransportOrder for TransportUnit [" + transportOrder.getTransportUnitBK() + "] because " + others.size() + " TransportOrders exist in state STARTED or INTERRUPTED");
        }
        transportOrder.setState(TransportOrderState.STARTED);
        LOGGER.info("TransportOrder for TransportUnit with Barcode {} STARTED at {}. Persisted key is {}", transportOrder.getTransportUnitBK(), transportOrder.getStartDate(), transportOrder.getPk());
    }
}