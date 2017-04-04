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
package org.openwms.wms.receiving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ameba.annotation.TxService;
import org.ameba.exception.ServiceLayerException;
import org.openwms.common.TransportUnit;
import org.openwms.common.TransportUnitService;
import org.openwms.common.values.Barcode;
import org.openwms.core.annotation.FireAfterTransactionAsynchronous;
import org.openwms.core.values.UnitType;
import org.openwms.wms.LoadUnit;
import org.openwms.wms.LoadUnitRepository;
import org.openwms.wms.PackagingUnit;
import org.openwms.wms.inventory.Product;
import org.openwms.wms.inventory.ProductRepository;
import org.openwms.wms.order.OrderPositionKey;
import org.openwms.wms.order.WMSOrderDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * A ReceivingManagerImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@TxService
class ReceivingImpl implements Receiving {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReceivingImpl.class);
    @Autowired
    private ReceivingOrderDao rcvOrderDao;
    @Autowired
    private WMSOrderDao<ReceivingOrder, ReceivingOrderPosition> wmsOrderDao;
    @Autowired
    private ProductRepository productDao;
    @Autowired
    private LoadUnitRepository loadUnitDao;
    @Autowired
    private TransportUnitService transportUnitSrv;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReceivingOrder> findAllOrders() {
        return rcvOrderDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ReceivingOrderPosition> findAllPositions(String orderId) {
        ReceivingOrder order = rcvOrderDao.findByOrderId(orderId);
        if (null == order || order.getNoPositions() == 0 || order.getPositions() == null) {
            return Collections.<ReceivingOrderPosition> emptyList();
        }
        return new ArrayList<ReceivingOrderPosition>(order.getPositions());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceivingOrderPosition createOrderPosition(OrderPositionKey orderPositionKey, String productId,
            UnitType quantity, String barcode) {

        // Get order data
        ReceivingOrder order = rcvOrderDao.findByOrderId(orderPositionKey.getOrderId());

        // Search Product
        Product product = productDao.findBySku(productId);

        List<LoadUnit> loadUnits = loadUnitDao.findByTransportUnit(new Barcode(barcode));
        String physicalPosition = "";
        if (!loadUnits.isEmpty()) {
            String currentMaxPosition = loadUnits.get(loadUnits.size() - 1).getPhysicalPosition();
            try {
                int val = Integer.valueOf(currentMaxPosition);
                val++;
                physicalPosition = String.valueOf(val);
            } catch (NumberFormatException e) {}
        }

        TransportUnit transportUnit = transportUnitSrv.findByBarcode(new Barcode(barcode));

        LoadUnit loadUnit = new LoadUnit(transportUnit, physicalPosition, product);

        PackagingUnit pu = new PackagingUnit(loadUnit, quantity);
        ReceivingOrderPosition rcvOrderPosition = new ReceivingOrderPosition(order, orderPositionKey.getPositionNo(),
                quantity, product);
        return wmsOrderDao.createOrderPosition(rcvOrderPosition);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ReceivingOrder createOrder(String ordId) {
        Assert.notNull(ordId, "The orderId to create an Order with is null");
        ReceivingOrder order = rcvOrderDao.findByOrderId(ordId);
        if (null != order) {
            throw new ServiceLayerException("An order with the id " + ordId + " already exists");
        }
        order = new ReceivingOrder(ordId);
        return wmsOrderDao.createOrder(order);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FireAfterTransactionAsynchronous(events = { ReceivingOrderCreatedEvent.class })
    public ReceivingOrder createOrder(ReceivingOrder ord) {
        if (ord == null || !ord.isNew()) {
            throw new ServiceLayerException(
                    "Argument is null or the order identified by the argument already exists: " + ord);
        }
        ReceivingOrder order = rcvOrderDao.findByOrderId(ord.getOrderId());
        if (null != order) {
            throw new ServiceLayerException("The order " + ord + " already exists");
        }
        try {
            return wmsOrderDao.createOrder(ord);
        } finally {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("ReceivingOrder created.");
            }
        }
    }
}