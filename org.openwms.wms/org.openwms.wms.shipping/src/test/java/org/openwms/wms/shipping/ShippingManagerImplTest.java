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

import org.junit.Before;
import org.junit.Test;
import org.openwms.common.units.Piece;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.openwms.wms.inventory.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A ShippingManagerImplTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@ContextConfiguration("classpath:wms-service-test-context.xml")
public class ShippingManagerImplTest extends AbstractJpaSpringContextTests {

    @Autowired
    private Shipping srv;
    private Product product;

    /**
     * FIXME [scherrer] Comment this
     * 
     */
    @Before
    public void setUp() {
        product = new Product("Existing Product");
        entityManager.persist(product);
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test method for
     * {@link ShippingImpl#createOrder(org.openwms.wms.shipping.ShippingOrder)}
     * .
     * 
     * @throws InterruptedException
     */
    @Test
    public final void testCreateOrder() throws InterruptedException {
        ShippingOrder sOrder = new ShippingOrder("S00001");
        new ShippingOrderPosition(sOrder, 1, new Piece(20), product);
        srv.createOrder(sOrder);
        // Thread.sleep(10000);
    }
}