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
package org.openwms.wms.service.spring.shipping;

import org.openwms.core.annotation.FireAfterTransactionAsynchronous;
import org.openwms.core.event.EventPublisher;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.wms.domain.shipping.ShippingOrder;
import org.openwms.wms.domain.shipping.ShippingOrderCreatedNotification;
import org.openwms.wms.domain.shipping.ShippingOrderPosition;
import org.openwms.wms.integration.order.WMSOrderDao;
import org.openwms.wms.service.shipping.Shipping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A ShippingManagerImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Transactional
@Service(ShippingManagerImpl.COMPONENT_NAME)
public class ShippingManagerImpl implements Shipping {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShippingManagerImpl.class);
    @Autowired
    private WMSOrderDao<ShippingOrder, ShippingOrderPosition> dao;
    @Autowired
    private EventPublisher<ShippingOrderCreatedNotification> publisher;
    /** Springs component name. */
    public static final String COMPONENT_NAME = "shippingManager";

    /**
     * {@inheritDoc}
     */
    @Override
    @FireAfterTransactionAsynchronous(events = { ShippingOrderCreatedNotification.class })
    public ShippingOrder createOrder(ShippingOrder order) {
        if (!order.isNew()) {
            throw new ServiceRuntimeException("ShippingOrder is already persisted");
        }
        ShippingOrder newOrder = dao.createOrder(order);
        // publisher.publish(new ShippingOrderCreatedEvent(newOrder));
        try {
            return newOrder;
        } finally {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Created ShippingOrder: " + newOrder);
            }
        }
    }
}