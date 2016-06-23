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
package org.openwms.wms;

import javax.persistence.PersistenceException;

import org.hibernate.TransientPropertyValueException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwms.common.Location;
import org.openwms.common.LocationPK;
import org.openwms.common.TransportUnit;
import org.openwms.common.TransportUnitType;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.openwms.wms.inventory.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A LoadUnitTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class LoadUnitTest extends AbstractJpaSpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoadUnitTest.class);
    private TransportUnitType tut;
    private TransportUnit tu;
    private Location loc1;
    private Location loc2;
    private Product product;

    /**
     * Setup some test data.
     */
    @Before
    public void onBefore() {
        tut = new TransportUnitType("TUT");
        loc1 = new Location(new LocationPK("AREA", "ASL", "X", "Y", "Z"));
        loc2 = new Location(new LocationPK("ARE2", "ASL2", "X2", "Y2", "Z2"));
        product = new Product("tttt");
        entityManager.persist(product);

        entityManager.persist(tut);
        entityManager.persist(loc1);
        entityManager.persist(loc2);

        tu = new TransportUnit("TEST");
        tu.setTransportUnitType(tut);
        tu.setActualLocation(loc1);
        entityManager.persist(tu);
        entityManager.flush();
    }

    /**
     * Positive test to create a LoadUnit with an existing TU, existing Product
     * and a quantity > 0 on a physical Position.
     */
    @Test
    public final void testLoadUnitOk() {
        LoadUnit loadUnit = new LoadUnit(tu, "LEFT", product);
        entityManager.persist(loadUnit);
        entityManager.flush();
        entityManager.clear();
        LoadUnit lu = entityManager.find(LoadUnit.class, loadUnit.getPk());
        Assert.assertEquals(tu, lu.getTransportUnit());
        Assert.assertEquals("LEFT", lu.getPhysicalPosition());
        Assert.assertEquals(product, lu.getProduct());
        Assert.assertFalse(lu.isNew());
        Assert.assertNotNull(lu.getLastModifiedDt());
        Assert.assertNotNull(lu.getCreateDt());
    }

    /**
     * Test that it is possible to create a LoadUnit with no quantity set.
     */
    @Test
    public final void testLoadUnitQtyZero() {
        LoadUnit loadUnit = new LoadUnit(tu, "LEFT", product);
        entityManager.persist(loadUnit);
    }

    /**
     * Test that it is not possible to create a LoadUnit with a transient
     * TransportUnit.
     */
    @Test
    public final void testLoadUnitTransientTU() {
        try {
            LoadUnit loadUnit = new LoadUnit(new TransportUnit("TEST"), "LEFT", product);
            entityManager.persist(loadUnit);
        } catch (Exception e) {
            if (e instanceof PersistenceException) {
                return;
            } else if (e instanceof IllegalStateException
                    && (e.getCause() != null && (e.getCause() instanceof TransientPropertyValueException))) {
                return;
            }
            Assert.fail("Expected to run into an IllegalStateException or a PersistenceException when trying to persist a LoadUnit with transient TransportUnit. The type of exception depends on the JPA provider");
        }
    }

    /**
     * Test that it is not possible to create a LoadUnit with a transient
     * Product.
     */
    @Test
    public final void testLoadUnitTransientProduct() {
        try {
            LoadUnit loadUnit = new LoadUnit(tu, "LEFT", new Product("TRANSIENT"));
            entityManager.persist(loadUnit);
        } catch (Exception e) {
            if (e instanceof PersistenceException) {
                return;
            } else if (e instanceof IllegalStateException
                    && (e.getCause() != null && (e.getCause() instanceof TransientPropertyValueException))) {
                return;
            }
            Assert.fail("Expected to run into an IllegalStateException or a PersistenceException when trying to persist a LoadUnit with transient Product. The type of exception depends on the JPA provider");
        }
    }

    /**
     * Test method for
     * {@link org.openwms.wms.LoadUnit#LoadUnit(java.lang.String, org.openwms.common.values.Weight)}
     * .
     */
    @Test(expected = Exception.class)
    public final void testDuplicatedLoadUnit() {
        LoadUnit loadUnit = new LoadUnit(tu, "LEFT", product);
        entityManager.persist(loadUnit);
        entityManager.flush();
        LoadUnit loadUnit2 = new LoadUnit(tu, "LEFT", product);
        entityManager.persist(loadUnit2);
    }
}