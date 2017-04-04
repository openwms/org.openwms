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
package org.openwms.wms.shipping;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.ameba.annotation.TxService;
import org.ameba.exception.ServiceLayerException;
import org.openwms.core.event.EventBroker;
import org.openwms.core.event.EventListener;
import org.openwms.core.event.RootApplicationEvent;
import org.openwms.wms.PackagingUnit;
import org.openwms.wms.inventory.Allocation;
import org.openwms.wms.inventory.AllocationException;
import org.openwms.wms.inventory.AllocationRule;
import org.openwms.wms.order.OrderState;
import org.openwms.wms.order.WMSOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ShippingOrderStarter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class ShippingOrderStarter implements EventListener, ApplicationListener<RootApplicationEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingOrderStarter.class);
    /** Springs component name. */
    public static final String COMPONENT_NAME = "shippingOrderStarter";
    @Autowired
    private WMSOrderDao<ShippingOrder, ShippingOrderPosition> dao;
    @Autowired
    private ShippingOrderDao shippingOrderDao;
    @Autowired
    private EventBroker broker;
    @Autowired
    private Allocation allocator;

    @PostConstruct
    void postConstruct() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Subscribed ShippingOrderStarter to the ShippingOrderCreatedNotification");
        }
        broker.subscribe(ShippingOrderCreatedNotification.class, ShippingOrderStarter.COMPONENT_NAME);
    }

    // @PreDestroy
    void preDesctroy() {
        broker.unsubscribe(ShippingOrderCreatedNotification.class, ShippingOrderStarter.COMPONENT_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEvent(RootApplicationEvent event) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Got Event to allocate a ShippingOrder: ");
        }
        if (ShippingOrderCreatedNotification.class.equals(event.getClass())) {
            ShippingOrder order = dao.findByOrderId(((ShippingOrder) event.getSource()).getOrderId());
            if (null == order || order.getOrderState() != OrderState.CREATED) {
                throw new ServiceLayerException("Order not found or not in expected state CREATED");
            }
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Got Event to allocate a ShippingOrder: " + order.getOrderId());
            }
            allocateOrders();
        }
    }

    /**
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void onApplicationEvent(RootApplicationEvent event) {
        if (ShippingOrderCreatedNotification.class.equals(event.getClass())) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Got Notification to allocate open ShippingOrders");
            }
            allocateOrders();
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // @Scheduled(fixedDelay = 1000)
    public void timeTriggered() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Timeout elapsed. Trying to allocate ShippingOrderPositions");
        }
        allocateOrders();
    }

    private void allocateOrders() {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Start allocating open ShippingOrders");
        }
        List<ShippingOrder> processOrders = shippingOrderDao.findInState(OrderState.CREATED);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Number of open ShippingOrders to allocate:" + processOrders.size());
        }
        for (ShippingOrder shippingOrder : processOrders) {
            Set<ShippingOrderPosition> positions = shippingOrder.getPositions();
            for (ShippingOrderPosition shippingOrderPosition : positions) {
                allocateOrderPosition(shippingOrderPosition);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Allocation of open ShippingOrders done");
        }
    }

    /**
     * FIXME [scherrer] Comment this
     *
     * @param shippingOrderPosition
     */
    private void allocateOrderPosition(ShippingOrderPosition shippingOrderPosition) {
        ShippingOrderPositionSplit split;
        PackagingUnit pUnit;
        int splitNo = 1;
        while (!shippingOrderPosition.isAllocated()) {
            splitNo = shippingOrderPosition.getNextSplitNumber();
            split = new ShippingOrderPositionSplit(shippingOrderPosition, splitNo);
            try {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Trying to allocate OrderPosition: " + shippingOrderPosition.getPositionId());
                }
                pUnit = allocator.allocate(new AllocationRule(shippingOrderPosition.getQtyOrdered(),
                        shippingOrderPosition.getProduct()));
            } catch (AllocationException ae) {
                shippingOrderPosition.setNextAllocationDate(new Date());
            }
        }
    }
}