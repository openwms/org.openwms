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
package org.openwms.tms.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.order.TransportOrder.TRANSPORT_ORDER_STATE;
import org.openwms.tms.util.event.TransportServiceEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportOrderStateTracker.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 */
@Service
@Transactional
public class TransportOrderStateTracker implements ApplicationListener<TransportServiceEvent> {

    @PersistenceContext
    protected EntityManager em;

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Create a new TransportOrderStateTracker.
     */
    public TransportOrderStateTracker() {}

    /**
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(TransportServiceEvent event) {
        switch (event.getType()) {
        case TRANSPORT_CREATED:
            doAfterCreation((Long) event.getSource());
            break;
        case TRANSPORT_INTERRUPTED:
            doOnInterrupt((Long) event.getSource());
            break;
        case TRANSPORT_ONFAILURE:
            doOnFailure((Long) event.getSource());
            break;
        case TRANSPORT_CANCELED:
            doAfterCancel((Long) event.getSource());
            break;
        case TRANSPORT_FINISHED:
            doAfterFinish((Long) event.getSource());
            break;
        }
    }

    private TransportOrder findTransportOrder(Long id) {
        return em.find(TransportOrder.class, id);
    }

    protected void doAfterCreation(Long id) {
        TransportOrder transportOrder = findTransportOrder(id);
        boolean go = initialize(transportOrder);
        if (go) go = start(transportOrder);

    }

    private boolean start(TransportOrder transportOrder) {
        if (transportOrder.getTargetLocationGroup() != null
                && transportOrder.getTargetLocationGroup().isInfeedBlocked()) {
            if (logger.isDebugEnabled()) {
                logger.debug("TLG is blocked, do not start");
            }
            return false;
        }

        // Check for other active transports
        try {
            em.createNamedQuery(TransportOrder.NQ_FIND_ACTIVE_FOR_TU).setParameter("transportUnit",
                    transportOrder.getTransportUnit()).setParameter("state1", TRANSPORT_ORDER_STATE.STARTED)
                    .setParameter("state2", TRANSPORT_ORDER_STATE.INTERRUPTED).getSingleResult();
            if (logger.isDebugEnabled()) {
                logger.debug("There is an already started one");
            }
            return false;
        }
        catch (NoResultException nre) {
            if (logger.isDebugEnabled()) {
                logger.debug("No active transportOrder found for transportUnit : " + transportOrder.getTransportUnit());
            }

        }

        transportOrder.setState(TRANSPORT_ORDER_STATE.STARTED);
        return true;
    }

    private boolean initialize(TransportOrder transportOrder) {
        transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
        return true;
    }

    protected void doAfterCancel(Long id) {
        TransportOrder orderToStart = findOrderToStart(id);
        if (orderToStart != null) {
            start(orderToStart);
        } else {
            logger.warn("TransportOrder with ID not found to do processing after cancel:" + id);
        }
    }

    protected void doAfterFinish(Long id) {
        TransportOrder orderToStart = findOrderToStart(id);
        if (orderToStart != null) {
            start(orderToStart);
        }
    }

    protected void doOnFailure(Long id) {
        TransportOrder orderToStart = findOrderToStart(id);
        if (orderToStart != null) {
            start(orderToStart);
        }
    }

    protected void doOnInterrupt(Long id) {}

    // TODO [russelltina] : Better to find and retrieve the best one to always
    // fetch the whole list into heap.
    @SuppressWarnings("unchecked")
    private TransportOrder findOrderToStart(Long id) {
        TransportOrder transportOrder = findTransportOrder(id);
        if (transportOrder == null) {
            logger.warn("No TransportOrder found with id:" + id);
            return null;
        }
        List<TransportOrder> transportOrders = em.createNamedQuery(TransportOrder.NQ_FIND_ORDERS_TO_START)
                .setParameter("transportUnit", transportOrder.getTransportUnit()).getResultList();
        if (transportOrders != null) {
            return transportOrders.get(0);
        }
        return null;
    }
}
