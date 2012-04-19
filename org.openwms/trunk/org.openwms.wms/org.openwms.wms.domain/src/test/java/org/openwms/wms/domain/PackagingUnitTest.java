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
package org.openwms.wms.domain;

import org.junit.Assert;
import org.junit.Test;
import org.openwms.common.domain.TransportUnit;
import org.openwms.wms.domain.inventory.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A PackagingUnitTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
public class PackagingUnitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PackagingUnitTest.class);

    /**
     * Test method for
     * {@link org.openwms.wms.domain.PackagingUnit#PackagingUnit()}.
     */
    @Test
    public final void testPackagingUnit() {
        PackagingUnit pu = new PackagingUnit(new LoadUnit(new TransportUnit("BARCODE"), "TOP_RIGHT"), "123456789");

        LoadUnit lu1 = new LoadUnit(new TransportUnit("BARCODE"), "TOP_RIGHT");
        PackagingUnit pu1 = new PackagingUnit(lu1, "123456789", new Product("SKU9999999"));
        LOGGER.debug("Product set on the PackagingUnit: " + pu1.getProduct());
        LOGGER.debug("Product set on the LoadUnit: " + lu1.getProduct());
        Assert.assertEquals(lu1.getProduct(), pu1.getProduct());
        LOGGER.debug("PackagingUnit: " + pu1);
    }
}
