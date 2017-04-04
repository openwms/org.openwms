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

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwms.common.Location;
import org.openwms.common.LocationPK;
import org.openwms.common.TransportUnit;
import org.openwms.common.TransportUnitType;
import org.openwms.common.units.Piece;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.openwms.wms.inventory.Product;
import org.openwms.wms.order.OrderPositionKey;
import org.openwms.wms.order.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A ReceivingManagerImplTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@ContextConfiguration("classpath:wms-service-test-context.xml")
public class ReceivingManagerImplTest extends AbstractJpaSpringContextTests {

    @Autowired
    private Receiving srv;
    private ReceivingOrder order;

    private TransportUnitType tut;
    private TransportUnit tu;
    private Location loc1;
    private Location loc2;
    private Product product;

    /**
     * Setup some test data.
     *
     * @throws Exception in any case of errors
     */
    @Before
    public void setUp() throws Exception {
        tut = new TransportUnitType("TUT");
        loc1 = new Location(new LocationPK("ARE_", "ASL_", "X", "Y", "Z"));
        loc2 = new Location(new LocationPK("ARE2", "ASL2", "X2", "Y2", "Z2"));
        product = new Product("Existing Product");
        entityManager.persist(product);

        entityManager.persist(tut);
        entityManager.persist(loc1);
        entityManager.persist(loc2);

        tu = new TransportUnit("BARCODE000004711");
        tu.setTransportUnitType(tut);
        tu.setActualLocation(loc1);
        entityManager.persist(tu);

        order = new ReceivingOrder("111");
        entityManager.persist(order);
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test method for {@link ReceivingImpl#create(org.openwms.wms.order.OrderPositionKey, java.lang.String,
     * org.openwms.core.values.Unit, java.lang.String)} .
     */
    @Test
    public final void testCreate() {
        ReceivingOrderPosition pos = srv.createOrderPosition(new OrderPositionKey("111", 1), "Existing Product",
                new Piece(29), "BARCODE000004711");
        Assert.assertNotNull(pos);
    }

    /**
     * Positive test to create a ReceivingOrder.
     */
    @Test
    public final void testCreateOrderString() {
        ReceivingOrder rOrder = srv.createOrder("000000002");
        assertBasics(rOrder);
    }

    /**
     * Positive test to test that adding new position works.
     */
    @Test
    public final void testAddPositions() throws Exception {
        ReceivingOrder ord = new ReceivingOrder("000000002");
        ReceivingOrder rOrder = srv.createOrder(ord);
        ReceivingOrderPosition pos = srv.createOrderPosition(new OrderPositionKey("000000002", 1), "Existing Product",
                new Piece(1), "BARCODE000004711");
        rOrder.addPostions(pos);
        Assert.assertTrue(rOrder.getNoPositions() == 1);
    }

    /**
     * Positive test to create a ReceivingOrder.
     */
    @Test
    public final void testCreateOrderOrder() throws Exception {
        ReceivingOrder ord = new ReceivingOrder("000000002");
        ReceivingOrder rOrder = srv.createOrder(ord);
        assertBasics(rOrder);

        long now = System.currentTimeMillis();
        ReceivingOrder ord2 = new ReceivingOrder("000000003");
        ord2.lock();
        ord2.setPriority(100);
        ord2.setLatestDueDate(new Date(now + 10000));
        ReceivingOrder rOrder2 = srv.createOrder(ord2);
        assertBasics(rOrder2);
        Assert.assertEquals(100, rOrder2.getPriority());
        Assert.assertTrue(rOrder2.isLocked());
        Assert.assertTrue(rOrder2.getLatestDueDate().after(new Date(now)));
    }

    /**
     * Positive test to create a ReceivingOrder. Tests Cascade persist opertion of orderPositons.
     */
    @Test
    public final void testCreateOrderWithPositions() throws Exception {
        // Set all possible fields on the Order instance
        long now = System.currentTimeMillis();
        ReceivingOrder ord2 = new ReceivingOrder("000000003");
        ord2.lock();
        ord2.setPriority(100);
        ord2.setLatestDueDate(new Date(now + 10000));
        // Also add two postions to test whether transient positions are
        // persisted as well
        ReceivingOrderPosition pos1 = new ReceivingOrderPosition(ord2, 1, new Piece(20), product);
        ReceivingOrderPosition pos2 = new ReceivingOrderPosition(ord2, 2, new Piece(20), product);
        ord2.addPostions(pos1, pos2);

        ReceivingOrder rOrder2 = srv.createOrder(ord2);
        assertBasics(rOrder2);
        Assert.assertEquals(100, rOrder2.getPriority());
        Assert.assertTrue(rOrder2.isLocked());
        Assert.assertTrue(rOrder2.getLatestDueDate().after(new Date(now)));
        Assert.assertTrue(rOrder2.getNoPositions() == 2);
    }

    public final void testFindAllOrders() {

    }

    private void assertBasics(ReceivingOrder order) {
        Assert.assertNotNull(order);
        Assert.assertFalse(order.isNew());
        Assert.assertEquals(order.getOrderState(), OrderState.CREATED);
    }
}