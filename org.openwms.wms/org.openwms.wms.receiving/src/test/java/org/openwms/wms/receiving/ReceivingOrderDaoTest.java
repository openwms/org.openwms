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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwms.common.units.Piece;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.openwms.wms.inventory.Product;
import org.openwms.wms.order.OrderPosition;
import org.openwms.wms.order.OrderPositionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A ReceivingOrderDaoTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@ContextConfiguration("classpath:/wms-jpa-test-context.xml")
public class ReceivingOrderDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier(ReceivingOrderDaoImpl.COMPONENT_NAME)
    private ReceivingOrderDao dao;

    private ReceivingOrder order;
    private Product product;

    /**
     * Setup some test data.
     *
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        order = new ReceivingOrder("O00000011");
        product = new Product("Existing Product");
        entityManager.persist(product);

        ReceivingOrderPosition pos1 = new ReceivingOrderPosition(order, 1, new Piece(20), product);

        entityManager.persist(order);
        entityManager.persist(pos1);
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test method for {@link org.openwms.wms.receiving.ReceivingOrderDaoImpl#findByKey(org.openwms.wms.order.OrderPositionKey)}
     * .
     */
    @Test
    public final void testFindByKey() {
        OrderPosition pos1 = dao.findByKey(new OrderPositionKey("O00000011", 1));
        Assert.assertNotNull(pos1);
        Assert.assertEquals(order, pos1.getOrder());
    }
}