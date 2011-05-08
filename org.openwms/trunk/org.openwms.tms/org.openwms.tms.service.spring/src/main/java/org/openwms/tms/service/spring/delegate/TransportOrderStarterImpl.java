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

import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.values.TransportOrderState;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.delegate.TransportOrderStarter;
import org.openwms.tms.service.exception.StateChangeException;
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
 * @version $Revision: $
 * @since 0.1
 */
@Component
@Transactional(propagation = Propagation.MANDATORY, noRollbackFor = { StateChangeException.class })
public class TransportOrderStarterImpl implements TransportOrderStarter {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private TransportOrderDao dao;

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.service.delegate.TransportOrderStarter#start(org.openwms.tms.domain.order.TransportOrder)
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
        logger.debug("STATE IS NOW"+transportOrder.getState());
        transportOrder.setState(TransportOrderState.STARTED);
        if (logger.isDebugEnabled()) {
            logger.debug("TransportOrder " + transportOrder.getId() + " started at " + transportOrder.getStartDate());
        }
    }

}
