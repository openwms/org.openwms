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
 * @version $Revision$
 * @since 0.1
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
        } catch (InsufficientValueException tme) {
            logger.debug("OK:Exception while switching to next state without transportUnit");
        }

        assertEquals("TransportOrder must remain in state CREATED:", TRANSPORT_ORDER_STATE.CREATED, transportOrder
                .getState());
        TransportUnit transportUnit = new TransportUnit("TEST_UNIT");
        transportOrder.setTransportUnit(transportUnit);
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            fail("TransportOrder must not be switched in next mode without setting a target");
        } catch (InsufficientValueException tme) {
            logger.debug("OK:Exception while switching to next state without target");
        }

        Location targetLocation = new Location(new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN"));
        transportOrder.setTargetLocation(targetLocation);
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            logger.debug("transportUnit set and target set");
        } catch (Exception tme) {
            fail("TransportOrder could be switched in next mode");

        }

        TransportOrder transportOrder2 = new TransportOrder();
        LocationGroup targetLocationGroup = new LocationGroup("TEST_GROUP");
        transportOrder2.setTransportUnit(transportUnit);
        transportOrder2.setTargetLocationGroup(targetLocationGroup);
        try {
            transportOrder.setState(TRANSPORT_ORDER_STATE.INITIALIZED);
            logger.debug("transportUnit set and targetLocationGroup set");
        } catch (Exception tme) {
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
