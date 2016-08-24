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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwms.common.Location;
import org.openwms.common.LocationPK;
import org.openwms.common.TransportUnit;
import org.openwms.common.TransportUnitType;
import org.openwms.common.units.Piece;
import org.openwms.common.units.PieceUnit;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.openwms.wms.inventory.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A PackagingUnitTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
public class PackagingUnitTest extends AbstractJpaSpringContextTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackagingUnitTest.class);
    private TransportUnitType tut;
    private TransportUnit tu;
    private Location loc1;
    private Location loc2;
    private Product product;
    private LoadUnit lu1;

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

        lu1 = new LoadUnit(tu, "LEFT");
        lu1.setProduct(product);
        entityManager.persist(tu);
        entityManager.persist(lu1);
        entityManager.flush();
    }

    /**
     * Test method for
     * {@link org.openwms.wms.PackagingUnit#PackagingUnit()}.
     */
    @Test
    public final void testPackagingUnit() {
        PackagingUnit pu = new PackagingUnit(new LoadUnit(new TransportUnit("BARCODE"), "123456789"), new Piece(20));

        LoadUnit lu1 = new LoadUnit(new TransportUnit("BARCODE"), "TOP_RIGHT");
        PackagingUnit pu1 = new PackagingUnit(lu1, new Piece(20), new Product("SKU9999999"));
        LOGGER.debug("Product set on the PackagingUnit: " + pu1.getProduct());
        LOGGER.debug("Product set on the LoadUnit: " + lu1.getProduct());
        Assert.assertEquals(lu1.getProduct(), pu1.getProduct());
        LOGGER.debug("PackagingUnit: " + pu1);
    }

    /**
     * Test method for
     * {@link org.openwms.wms.PackagingUnit#PackagingUnit()}.
     */
    @Test
    public final void testPersistPackagingUnit() {
        PackagingUnit pu = new PackagingUnit(lu1, new Piece(48));
        pu.setProduct(lu1.getProduct());
        entityManager.persist(pu);
        entityManager.flush();
        entityManager.clear();
        PackagingUnit pu2 = entityManager.find(PackagingUnit.class, pu.getPk());
        Assert.assertTrue(pu2.getQuantity().equals(pu.getQuantity()));
        Assert.assertTrue(pu2.getQuantity().equals(new Piece(4, PieceUnit.DOZ)));
    }
}
