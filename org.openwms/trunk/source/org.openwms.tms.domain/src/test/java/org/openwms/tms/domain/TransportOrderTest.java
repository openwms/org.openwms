/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.tms.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.exception.InsufficientValueException;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.order.TransportOrder.TRANSPORT_ORDER_STATE;

/**
 * A TransportOrderTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 120 $
 */
public class TransportOrderTest extends AbstractJpaSpringContextTests {

    /**
     * Test method for
     * {@link org.openwms.tms.domain.order.TransportOrder#setState(org.openwms.tms.domain.order.TransportOrder.TRANSPORT_ORDER_STATE)}
     * .
     */
    @Test
    public final void testSetState() {
        TransportOrder transportOrder = new TransportOrder();
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            fail("Exception expected while switching to next state without transportUnit");
        }
        catch (InsufficientValueException tme) {
            logger.debug("OK:Exception while switching to next state without transportUnit");
        }

        assertEquals("TransportOrder must remain in state CREATED:", TRANSPORT_ORDER_STATE.CREATED, transportOrder
                .getState());
        TransportUnit transportUnit = new TransportUnit("TEST_UNIT");
        transportOrder.setTransportUnit(transportUnit);
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            fail("TransportOrder must not be switched in next mode without setting a target");
        }
        catch (InsufficientValueException tme) {
            logger.debug("OK:Exception while switching to next state without target");
        }

        Location targetLocation = new Location(new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN"));
        transportOrder.setTargetLocation(targetLocation);
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            logger.debug("transportUnit set and target set");
        }
        catch (Exception tme) {
            fail("TransportOrder could be switched in next mode");

        }

        TransportOrder transportOrder2 = new TransportOrder();
        LocationGroup targetLocationGroup = new LocationGroup("TEST_GROUP");
        transportOrder2.setTransportUnit(transportUnit);
        transportOrder2.setTargetLocationGroup(targetLocationGroup);
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            logger.debug("transportUnit set and targetLocationGroup set");
        }
        catch (Exception tme) {
            fail("TransportOrder could be switched in next mode");
        }

        // Try to persist
        TransportUnitType transportUnitType = new TransportUnitType("TEST_TYPE");
        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(targetLocation);

        entityManager.persist(targetLocation);
        entityManager.persist(transportUnitType);
        entityManager.persist(transportUnit);
        entityManager.persist(targetLocationGroup);
        entityManager.persist(transportOrder2);
    }

}
