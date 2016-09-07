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

import java.util.Collections;
import java.util.List;

import org.ameba.exception.NotFoundException;
import org.openwms.common.CommonGateway;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportOrderRepository;
import org.openwms.tms.TransportOrderState;
import org.openwms.tms.TransportServiceEvent;
import org.openwms.tms.TransportStartComparator;
import org.openwms.tms.exception.StateChangeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A Initializer.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Transactional(propagation = Propagation.MANDATORY)
@Lazy
@Component
class Initializer implements ApplicationListener<TransportServiceEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(Initializer.class);
    @Autowired
    private TransportOrderRepository repository;
    @Autowired
    private CommonGateway commonGateway;
    @Autowired
    private ApplicationContext ctx;

    /**
     * Handle an application event.
     *
     * @param event the event to respond to
     */
    @Override
    public void onApplicationEvent(TransportServiceEvent event) {
        if (event.getType() == TransportServiceEvent.TYPE.TRANSPORT_CREATED) {
            TransportOrder to = repository.findOne((Long) event.getSource());
            List<TransportOrder> transportOrders = repository.findByTransportUnitBKAndStates(to.getTransportUnitBK(), TransportOrderState.CREATED);
            Collections.sort(transportOrders, new TransportStartComparator());
            for (TransportOrder transportOrder : transportOrders) {
                try {
                    transportOrder.setState(TransportOrderState.INITIALIZED);
                    transportOrder.setSourceLocation(commonGateway.getTransportUnit(transportOrder.getTransportUnitBK()).orElseThrow(NotFoundException::new).getActualLocation().toString());
                    transportOrder = repository.save(transportOrder);
                    LOGGER.debug("TransportOrder with PK [{}] INITIALIZED", transportOrder.getPk());
                } catch (StateChangeException sce) {
                    LOGGER.warn("Could not initialize TransportOrder with PK [{}]. Message: [{}]", transportOrder.getPk(), sce.getMessage());
                }
                try {
                    ctx.publishEvent(new TransportServiceEvent(transportOrder.getPk(),
                            TransportServiceEvent.TYPE.INITIALIZED));
                } catch (StateChangeException sce) {
                    LOGGER.warn("Post-processing of TransportOrder with PK [{}] failed with message: [{}]", transportOrder.getPk(), sce.getMessage());
                }
            }
        }
    }
}
