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
package org.openwms.tms.state;

import java.util.List;
import java.util.Optional;

import org.ameba.exception.NotFoundException;
import org.openwms.common.CommonGateway;
import org.openwms.common.Location;
import org.openwms.common.LocationGroup;
import org.openwms.tms.StateChangeException;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportOrderRepository;
import org.openwms.tms.TransportOrderState;
import org.openwms.tms.TransportServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * A Starter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
//@Transactional(propagation = Propagation.MANDATORY)
@Component
class Starter implements ApplicationListener<TransportServiceEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Starter.class);
    @Autowired
    private TransportOrderRepository repository;
    @Autowired
    private CommonGateway commonGateway;

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(TransportServiceEvent event) {
        switch (event.getType()) {
            case INITIALIZED:
            case TRANSPORT_FINISHED:
            case TRANSPORT_ONFAILURE:
            case TRANSPORT_CANCELED:
            case TRANSPORT_INTERRUPTED:

                final TransportOrder to = repository.findOne((Long) event.getSource());
//                List<TransportOrder> transportOrders = repository.findByTransportUnitBKAndStates(to.getTransportUnitBK(), TransportOrderState.CREATED);
                LOGGER.debug("> Request to start the TransportOrder with PKey [{}]", to.getPersistentKey());
                Optional<LocationGroup> lg = commonGateway.getLocationGroup(to.getTargetLocationGroup());
                Optional<Location> loc = commonGateway.getLocation(to.getTargetLocation());
                if (!lg.isPresent() && !loc.isPresent()) {
                    // At least one target must be set
                    throw new NotFoundException(
                            "Neither a valid target LocationGroup nor a Location are set, hence it is not possible to start the TransportOrder");
                }
                lg.ifPresent(l -> {
                    if (l.isInfeedBlocked())
                        throw new StateChangeException("Cannot start the TransportOrder because TargetLocationGroup is blocked");
                });
                loc.ifPresent(l -> {
                    if (l.isInfeedBlocked())
                        throw new StateChangeException("Cannot start the TransportOrder because TargetLocation is blocked");
                });
                lg.ifPresent(l -> to.setTargetLocationGroup(l.toString()));
                loc.ifPresent(l -> to.setTargetLocation(l.toString()));

                List<TransportOrder> others = repository.findByTransportUnitBKAndStates(to.getTransportUnitBK(), TransportOrderState.STARTED);
                if (!others.isEmpty()) {
                    throw new StateChangeException(
                            "Cannot start TransportOrder for TransportUnit [" + to.getTransportUnitBK() + "] because " + others.size() + " TransportOrders already started [" + others.get(0).getPersistentKey() + "]");
                }
                to.changeState(TransportOrderState.STARTED);
                repository.save(to);
                LOGGER.info("TransportOrder for TransportUnit with Barcode {} STARTED at {}. Persisted key is {}", to.getTransportUnitBK(), to.getStartDate(), to.getPk());
                break;
        }
    }
}
