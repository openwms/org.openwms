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
package org.openwms.common.service.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.TransportUnitService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
@ContextConfiguration
public class TransportServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("transportUnitService")
    private TransportUnitService<TransportUnit> transportService;

    private LocationPK locationPk = new LocationPK("AREA", "AISLE", "X", "Y", "Z");
    private Location actualLocation = new Location(locationPk);
    private LocationPK targetLocationPk = new LocationPK("TARGET", "TARGET", "TARGET", "TARGET", "TARGET");
    private Location targetLocation = new Location(targetLocationPk);
    private TransportUnitType transportUnitType = new TransportUnitType("TestType");
    private TransportUnit transportUnit = new TransportUnit("KNOWN");

    /**
     * Setup some test data.
     */
    @Before
    public void onBefore() {
        entityManager.persist(actualLocation);
        entityManager.persist(targetLocation);
        entityManager.persist(transportUnitType);
        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(actualLocation);
        entityManager.persist(transportUnit);
    }

    /**
     * Negative test to create a new TransportUnit that already exists.
     */
    @Test
    public final void testCreateExistingTransportUnit() {
        try {
            transportService.create(new Barcode("KNOWN"), transportUnitType, locationPk);
            fail("Must throw a ServiceException while trying to create an already known TransportUnit");
        } catch (ServiceRuntimeException se) {
            logger.debug("OK:ServiceException expected while trying to create an already known TransportUnit");
        }
    }

    /**
     * Negative test to create a new TransportUnit on a Location that does not
     * exist.
     */
    @Test
    public final void testCreateTransportUnitOnUnknownLocation() {

        try {
            transportService.create(new Barcode("4711"), transportUnitType, new LocationPK("UNKNOWN", "UNKNOWN",
                    "UNKNOWN", "UNKNOWN", "UNKNOWN"));
            fail("Must throw a ServiceException while trying to create a TransportUnit with an unknown actual Location");
        } catch (ServiceRuntimeException se) {
            logger.debug("OK:ServiceException expected while trying to create a TransportUnit with an unknown actual Location");
        }
    }

    /**
     * Positive test to create a new TransportUnit.
     */
    @Test
    public final void testCreateTransportUnit() {
        TransportUnit transportUnit = transportService.create(new Barcode("4711"), transportUnitType, locationPk);
        assertNotNull("TransportService must create a new TransportUnit", transportUnit);
    }

    /**
     * Test to create a new TransportUnit that already exists.
     */
    @Test
    public final void testMoveUnknownTransportUnit() {
        try {
            transportService.moveTransportUnit(new Barcode("TEST"), targetLocationPk);
            fail("Must throw a ServiceException while trying to create a TransportUnit with unknown Barcode");
        } catch (ServiceRuntimeException se) {
            logger.debug("OK:ServiceException expected while trying to create a TransportUnit with unknown Barcode");
        }
    }

    /**
     * Positive test to move a TransportUnit.
     */
    @Test
    public final void testMoveTransportUnit() {
        TransportUnit transportUnit = transportService.create(new Barcode("4711"), transportUnitType, locationPk);
        assertNotNull("TransportService must create a new TransportUnit", transportUnit);
        assertEquals("The actual Location of the TransportUnit must be preset", locationPk, transportUnit
                .getActualLocation().getLocationId());
        transportService.moveTransportUnit(transportUnit.getBarcode(), targetLocationPk);
        assertEquals("The actual Location of the TransportUnit must be changed", targetLocationPk, transportUnit
                .getActualLocation().getLocationId());
    }
}