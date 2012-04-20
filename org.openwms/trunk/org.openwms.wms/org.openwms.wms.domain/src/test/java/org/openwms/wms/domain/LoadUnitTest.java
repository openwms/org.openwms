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

import org.junit.Before;
import org.junit.Test;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.core.domain.values.Piece;
import org.openwms.core.exception.DomainModelRuntimeException;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * A LoadUnitTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class LoadUnitTest extends AbstractJpaSpringContextTests {

    private TransportUnitType tut;
    private TransportUnit tu;
    private Location loc1;
    private Location loc2;

    /**
     * Setup some test data.
     */
    @Before
    public void onBefore() {
        tut = new TransportUnitType("TUT");
        loc1 = new Location(new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
        loc2 = new Location(new LocationPK("AREA2", "AISLE2", "X2", "Y2", "Z2"));
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
     * Test method for
     * {@link org.openwms.wms.domain.LoadUnit#LoadUnit(java.lang.String, org.openwms.common.domain.values.Weight)}
     * .
     */
    @Test(expected = DomainModelRuntimeException.class)
    public final void testLoadUnit() {
        LoadUnit loadUnit = new LoadUnit(new TransportUnit("TEST"), "LEFT", new Piece(0));
        entityManager.persist(loadUnit);
    }

    /**
     * Test method for
     * {@link org.openwms.wms.domain.LoadUnit#LoadUnit(java.lang.String, org.openwms.common.domain.values.Weight)}
     * .
     */
    @Test
    public final void testLoadUnitOk() {
        LoadUnit loadUnit = new LoadUnit(new TransportUnit("TEST"), "LEFT", new Piece(100));
        entityManager.persist(loadUnit);
    }

    /**
     * Test method for
     * {@link org.openwms.wms.domain.LoadUnit#LoadUnit(java.lang.String, org.openwms.common.domain.values.Weight)}
     * .
     */
    @Test
    public final void testDuplicatedLoadUnit() {

        LoadUnit loadUnit = new LoadUnit(tu, "LEFT", new Piece(100));
        entityManager.persist(loadUnit);
        entityManager.flush();
        LoadUnit loadUnit2 = new LoadUnit(tu, "LEFT", new Piece(20));
        entityManager.persist(loadUnit2);
    }
}