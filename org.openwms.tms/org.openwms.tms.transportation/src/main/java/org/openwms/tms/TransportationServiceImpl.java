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

import org.ameba.annotation.TxService;
import org.openwms.common.CommonGateway;
import org.openwms.tms.exception.TransportOrderServiceException;
import org.openwms.tms.voter.DecisionVoter;
import org.openwms.tms.voter.DeniedException;
import org.openwms.tms.voter.RedirectVote;
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
    /** 0..* voters, can be overridden and extended with XML configuration. So far we define only one (default) voter directly. */
    @Autowired(required = false)
    private List<DecisionVoter<RedirectVote>> redirectVoters;
    @Autowired(required = false)
    private List<TargetResolver> targetResolvers;

    /**
     * {@inheritDoc}
     *
     * @throws TransportOrderServiceException when both targets are {@literal null}
     */
    @Override
    public Collection<String> redirectTransportOrders(Collection<String> bks, String target) {

        if (null == target || target.isEmpty()) {
            throw new TransportOrderServiceException("Both targets can may not be null, at least one target must be specififed");
        }
        List<String> failure = new ArrayList<>(bks.size());
        List<TransportOrder> transportOrders = repository.findByBk(new ArrayList<>(bks));
        for (TransportOrder transportOrder : transportOrders) {
            try {
                if (null != redirectVoters) {
                    RedirectVote rv = new RedirectVote(target, transportOrder);
                    // TODO [openwms]: 13/07/16 the concept of a voter is misused in that a voter changes the state of a TO
                    for (DecisionVoter<RedirectVote> voter : redirectVoters) {
                        voter.voteFor(rv);
                    }
                }
            } catch (DeniedException de) {
                LOGGER.error("Could not redirect TransportOrder with ID [" + transportOrder.getPk() + "], reason is: "
                        + de.getMessage());
                Problem problem = new Problem(de.getMessage());
                transportOrder.setProblem(problem);
                failure.add(transportOrder.getPk().toString());
            }
        }
        return failure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNoTransportOrdersToTarget(String target, String... states) {
        int i = 0;
        for (TargetResolver tr : targetResolvers) {
            i = +tr.resolve(target).getNoTOToTarget(target);
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
    public TransportOrder createTransportOrder(String barcode, String target, PriorityLevel priority) {
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