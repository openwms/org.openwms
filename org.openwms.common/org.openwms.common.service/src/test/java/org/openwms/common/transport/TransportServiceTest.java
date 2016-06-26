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
package org.openwms.common.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.ameba.exception.ServiceLayerException;
import org.junit.Before;
import org.junit.Test;
import org.openwms.common.location.Location;
import org.openwms.common.location.LocationPK;
import org.openwms.common.values.Barcode;
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
@ContextConfiguration("classpath:/org/openwms/common/service/transport/TransportServiceTest-context.xml")
public class TransportServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("transportUnitService")
    private TransportUnitService<TransportUnit> transportService;

    private final LocationPK locationPk = new LocationPK("AREA", "AISL", "X", "Y", "Z");
    private final Location actualLocation = new Location(locationPk);
    private final LocationPK targetLocationPk = new LocationPK("TRGT", "TRGT", "TRGT", "TRGT", "TRGT");
    private final Location targetLocation = new Location(targetLocationPk);
    private final TransportUnitType transportUnitType = new TransportUnitType("TestType");
    private final TransportUnit transportUnit = new TransportUnit("KNOWN");

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
        } catch (ServiceLayerException se) {
            LOGGER.debug("OK:ServiceException expected while trying to create an already known TransportUnit");
        }
    }

    /**
     * Negative test to create a new TransportUnit on a Location that does not exist.
     */
    @Test
    public final void testCreateTransportUnitOnUnknownLocation() {

        try {
            transportService.create(new Barcode("4711"), transportUnitType, new LocationPK("UNKN", "UNKN", "UNKN",
                    "UNKN", "UNKN"));
            fail("Must throw a ServiceException while trying to create a TransportUnit with an unknown actual Location");
        } catch (ServiceLayerException se) {
            LOGGER.debug("OK:ServiceException expected while trying to create a TransportUnit with an unknown actual Location");
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
        } catch (ServiceLayerException se) {
            LOGGER.debug("OK:ServiceException expected while trying to create a TransportUnit with unknown Barcode");
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