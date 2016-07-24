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
package org.openwms.tms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.openwms.common.CommonGateway;
import org.openwms.tms.exception.TransportOrderServiceException;
import org.openwms.tms.target.Target;
import org.openwms.tms.target.TargetResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * A TransportService.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @see TransportationService
 * @since 0.1
 */
@TxService
class TransportationServiceImpl implements TransportationService<TransportOrder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportationServiceImpl.class);

    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private CommonGateway commonGateway;
    @Autowired
    private TransportOrderRepository repository;
    @Autowired(required = false)
    private List<TargetResolver<Target>> targetResolvers;
    @Autowired(required = false)
    private List<UpdateFunction> updateFunctions;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNoTransportOrdersToTarget(String target, String... states) {
        int i = 0;
        for (TargetResolver<Target> tr : targetResolvers) {
            Optional<Target> t = tr.resolve(target);
            if (t.isPresent()) {
                i = +tr.getHandler().getNoTOToTarget(t.get());
            }
        }
        return i;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Checks that all necessary data to create a TransportOrder is given, does not do any logical checks, whether a target is blocked or a
     * {@link TransportOrder} for the {@code TransportUnit} exist.
     *
     * @throws TransportOrderServiceException when the barcode is {@literal null} or no transportUnit with barcode can be found or no target
     * can be found.
     */
    @Override
    public TransportOrder create(String barcode, String target, PriorityLevel priority) {
        if (barcode == null) {
            throw new TransportOrderServiceException("Barcode cannot be null when creating a TransportOrder");
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Create TransportOrder with Barcode " + barcode + ", to Target " + target
                    + ", with Priority " + priority + " ...");
        }
        TransportOrder transportOrder = new TransportOrder();
        transportOrder.setTransportUnitBK(barcode);

        transportOrder.setTargetLocation(target);
        transportOrder.setTargetLocationGroup(target);
        if (priority != null) {
            transportOrder.setPriority(priority);
        }
        transportOrder = repository.save(transportOrder);
        ctx.publishEvent(new TransportServiceEvent(transportOrder.getPk(),
                TransportServiceEvent.TYPE.TRANSPORT_CREATED));
        return transportOrder;
    }

    @Override
    public TransportOrder update(TransportOrder transportOrder) {
        TransportOrder saved = repository.findByPKey(transportOrder.getPersistentKey()).orElseThrow(NotFoundException::new);

        for (UpdateFunction up : updateFunctions) {
            up.update(saved, transportOrder);
        }
/*
        if (saved.getTransportUnitBK().equalsIgnoreCase(transportOrder.getTransportUnitBK())) {
            tuChange(saved, transportOrder);
        }

        if (saved.getPriority() != transportOrder.getPriority()) {
            prioritize(saved, transportOrder.getPriority());
        }

        if (transportOrder.hasProblem()) {
            reportProblem(saved, transportOrder.getProblem());
        }

        if (saved.getState() != transportOrder.getState()) {
            stateChange(saved, transportOrder.getState());
        }

        if (saved.hasTargetChanged(transportOrder)) {
            targetChange(saved, transportOrder);
        }
        */
        return saved;
    }
/*
    private void targetChange(TransportOrder saved, TransportOrder transportOrder) {
    }

    private void stateChange(TransportOrder saved, TransportOrder.State state) {

    }

    private void reportProblem(TransportOrder saved, Problem problem) {

    }

    private void prioritize(TransportOrder saved, PriorityLevel priority) {

    }

    private void tuChange(TransportOrder saved, TransportOrder transportOrder) {

    }
*/
    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<String> cancelTransportOrders(Collection<String> bks, TransportOrder.State state) {
        List<String> failure = new ArrayList<>(bks.size());
        List<TransportOrder> transportOrders = repository.findByBk(new ArrayList<>(bks));
        for (TransportOrder transportOrder : transportOrders) {
            try {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Trying to turn TransportOrder [" + transportOrder.getPk() + "] into: " + state);
                }
                transportOrder.setState(state);
                ctx.publishEvent(new TransportServiceEvent(transportOrder.getPk(), TransportOrderUtil
                        .convertToEventType(state)));
            } catch (StateChangeException sce) {
                LOGGER.error("Could not turn TransportOrder: [" + transportOrder.getPk() + "] into " + state
                        + " with reason : " + sce.getMessage());
                Problem problem = new Problem(sce.getMessage());
                transportOrder.setProblem(problem);
                failure.add(transportOrder.getPk().toString());
            }
        }
        return failure;
    }
}