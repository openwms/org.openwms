/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import org.junit.Test;
import org.openwms.common.domain.helper.AbstractPDOTestCase;

/**
 * 
 * A TransportUnitSystemTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class TransportUnitSystemTest extends AbstractPDOTestCase {

    /**
     * <li>Try to test cascading location booking.
     */
    @Test
    public final void testCascadingOnLocation() {
    // EntityTransaction entityTransaction = em.getTransaction();
    // LocationPK locationPk = new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN");
    // Location actualLocation = new Location(locationPk);
    // Location virtualLocation = new Location(new LocationPK("VIRTUAL", "VIRTUAL", "VIRTUAL", "VIRTUAL", "VIRTUAL"));
    //
    // TransportUnit transportUnit = new TransportUnit("TEST_TU");
    // TransportUnitType transportUnitType = new TransportUnitType("TEST_TUT");
    // transportUnit.setTransportUnitType(transportUnitType);
    // entityTransaction.begin();
    // em.persist(actualLocation);
    // em.persist(virtualLocation);
    // em.persist(transportUnitType);
    // entityTransaction.commit();
    //
    // transportUnit.setActualLocation(actualLocation);
    //
    // entityTransaction.begin();
    // em.persist(transportUnit);
    // entityTransaction.commit();
    // assertEquals("Number of transportUnits in location must be 1:", 1, transportUnit.getActualLocation()
    // .getNoTransportUnits());
    //
    // transportUnit.setActualLocation(virtualLocation);
    // entityTransaction.begin();
    // em.persist(transportUnit);
    // entityTransaction.commit();
    // assertEquals("Number of transportUnits in OLD location must be 0:", 0, actualLocation.getNoTransportUnits());
    // assertEquals("Number of transportUnits in NEW location must be 1:", 1, transportUnit.getActualLocation()
    // .getNoTransportUnits());
    //
    // assertEquals("Number of incoming transportUnits in location must be 0:", 0, transportUnit.getActualLocation()
    // .getNoIncomingTransportUnits());
    // transportUnit.setTargetLocation(actualLocation);
    // entityTransaction.begin();
    // em.merge(transportUnit);
    // entityTransaction.commit();
    // assertEquals("Number of incoming transportUnits in location must be 1", 1, transportUnit.getTargetLocation()
    // .getNoIncomingTransportUnits());
    // assertEquals("Number of transportUnits in actualLocation must be 1", 1, transportUnit.getTargetLocation()
    // .getNoIncomingTransportUnits());
    }
}
