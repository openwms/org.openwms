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
package org.openwms.tms;

import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * A TransportOrderTest.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 */
public class TransportOrderTests extends AbstractJpaSpringContextTests {

    /**
     * Test method for
     * {@link org.openwms.tms.domain.order.TransportOrder#setState(org.openwms.tms.domain.order.TransportOrder.TRANSPORT_ORDER_STATE)} .

    @Test
    public final void testSetState() {
        TransportOrder transportOrder = new TransportOrder();
        try {
            transportOrder.setState(TransportOrderState.INITIALIZED);
            fail("Exception expected while switching to next state without transportUnit");
        } catch (StateChangeException sce) {
            LOGGER.debug("OK:Exception while switching to next state without transportUnit");
        }

        assertEquals("TransportOrder must remain in state CREATED:", TransportOrderState.CREATED,
                transportOrder.getState());
        TransportUnit transportUnit = new TransportUnit("TEST_UNIT");
        transportOrder.setTransportUnit(transportUnit);
        try {
            transportOrder.setState(TransportOrderState.INITIALIZED);
            fail("TransportOrder must not be switched in next mode without setting a target");
        } catch (StateChangeException sce) {
            LOGGER.debug("OK:Exception while switching to next state without target");
        }

        Location targetLocation = new Location(new LocationPK("KNO", "KNO", "KNO", "KNO", "KNO"));
        transportOrder.setTargetLocation(targetLocation);
        try {
            transportOrder.setState(TransportOrderState.INITIALIZED);
            LOGGER.debug("transportUnit set and target set");
        } catch (Exception e) {
            fail("TransportOrder could be switched in next mode");
        }

        TransportOrder transportOrder2 = new TransportOrder();
        LocationGroup targetLocationGroup = new LocationGroup("TEST_GROUP");
        transportOrder2.setTransportUnit(transportUnit);
        transportOrder2.setTargetLocationGroup(targetLocationGroup);
        try {
            transportOrder.setState(TransportOrderState.INITIALIZED);
            LOGGER.debug("transportUnit set and targetLocationGroup set");
        } catch (Exception e) {
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
     */
}
