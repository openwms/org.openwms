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

import java.util.List;

import org.ameba.annotation.TxService;
import org.openwms.common.TransportUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * A TransportUnitRemovalListener is asked to allow or disallow the removal of a TransportUnit in a distributed system.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 0.1
 */
@TxService
class TransportUnitRemovalListener implements OnRemovalListener<TransportUnit> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransportUnitRemovalListener.class);
    @Autowired
    private TransportOrderRepository repository;

    /**
     * {@inheritDoc}
     * <p>
     * The implementation verifies that no active {@link TransportOrder}s exist, before a {@link TransportUnit} is going to be removed. <ul>
     * <li>In case where already 'started' {@link TransportOrder}s exist it is not allowed to remove the {@link TransportUnit} therefore an
     * exception is thrown.</li> <li>If {@link TransportOrder}s in a state less than 'started' exist they will be canceled and removed. The
     * removal of the {@link TransportUnit} is accepted.</li> </ul>
     *
     * @throws RemovalNotAllowedException when active {@link TransportOrder}s exist for the {@link TransportUnit} entity.
     */
    @Override
    public void preRemove(TransportUnit entity) throws RemovalNotAllowedException {
        Assert.notNull(entity, "Not allowed to call preRemove with null argument");
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Someone is trying to remove the TransportUnit [" + entity + " ], check for existing TransportOrders");
        }
        try {
            cancelInitializedOrders(entity);
            unlinkFinishedOrders(entity);
            unlinkCanceledOrders(entity);
        } catch (IllegalStateException ise) {
            LOGGER.warn("For one or more created TransportOrders it is not allowed to cancel them");
            throw new RemovalNotAllowedException(
                    "For one or more created TransportOrders it is not allowed to cancel them");
        }
    }

    protected void cancelInitializedOrders(TransportUnit transportUnit) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Trying to cancel and remove already created but not started TransportOrders");
        }
        List<TransportOrder> transportOrders = repository.findByTransportUnitBKAndState(transportUnit.getBk(), TransportOrder.State.CREATED,
                TransportOrder.State.INITIALIZED);
        if (!transportOrders.isEmpty()) {
            for (TransportOrder transportOrder : transportOrders) {
                try {
                    transportOrder.setState(TransportOrder.State.CANCELED);
                    transportOrder.setProblem(new Problem("TransportUnit " + transportUnit
                            + " was removed, order was canceled"));
                    transportOrder.setTransportUnitBK(null);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Successfully unlinked and canceled TransportOrder: " + transportOrder.getPk());
                    }
                } catch (StateChangeException sce) {
                    transportOrder.setProblem(new Problem(sce.getMessage()));
                } finally {
                    repository.save(transportOrder);
                }
            }
        }
    }

    protected void unlinkFinishedOrders(TransportUnit transportUnit) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Trying to unlink finished and failed TransportOrders for TransportUnit: " + transportUnit);
        }
        List<TransportOrder> transportOrders = repository.findByTransportUnitBKAndState(transportUnit.getBk(), TransportOrder.State.FINISHED,
                TransportOrder.State.ONFAILURE);
        if (!transportOrders.isEmpty()) {
            for (TransportOrder transportOrder : transportOrders) {
                transportOrder.setProblem(new Problem("TransportUnit " + transportUnit
                        + " was removed, order was unlinked"));
                transportOrder.setTransportUnitBK(null);
                repository.save(transportOrder);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Successfully unlinked TransportOrder: " + transportOrder.getPk());
                }
            }
        }
    }

    protected void unlinkCanceledOrders(TransportUnit transportUnit) {
        List<TransportOrder> transportOrders = repository.findByTransportUnitBKAndState(transportUnit.getBk(), TransportOrder.State.CANCELED);
        if (!transportOrders.isEmpty()) {
            for (TransportOrder transportOrder : transportOrders) {
                transportOrder.setProblem(new Problem("TransportUnit " + transportUnit
                        + " was removed, order was unlinked"));
                transportOrder.setTransportUnitBK(null);
                repository.save(transportOrder);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Successfully unlinked canceled TransportOrder: " + transportOrder.getPk());
                }
            }
        }
    }
}