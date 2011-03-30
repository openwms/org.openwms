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
import org.openwms.core.integration.GenericDao;
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
@ContextConfiguration("classpath:META-INF/spring/TransportServiceTest-context.xml")
public class TransportServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("transportUnitService")
    protected TransportUnitService transportService;

    @Autowired
    @Qualifier("locationDao")
    protected GenericDao<Location, Long> locationDao;

    @Autowired
    @Qualifier("transportUnitTypeDao")
    protected GenericDao<TransportUnitType, String> transportUnitTypeDao;

    @Autowired
    @Qualifier("transportUnitDao")
    protected GenericDao<TransportUnit, Long> transportUnitDao;

    LocationPK locationPk = new LocationPK("AREA", "AISLE", "X", "Y", "Z");
    Location actualLocation = new Location(locationPk);
    LocationPK targetLocationPk = new LocationPK("TARGET", "TARGET", "TARGET", "TARGET", "TARGET");
    Location targetLocation = new Location(targetLocationPk);
    TransportUnitType transportUnitType = new TransportUnitType("TestType");
    TransportUnit transportUnit = new TransportUnit("KNOWN");

    public TransportServiceTest() {
        super();
        // setPopulateProtectedVariables(true);
    }

    @Before
    public void inTransaction() {
        locationDao.persist(actualLocation);
        actualLocation = locationDao.save(actualLocation);
        locationDao.persist(targetLocation);
        transportUnitTypeDao.persist(transportUnitType);
        transportUnit.setTransportUnitType(transportUnitType);
        transportUnit.setActualLocation(actualLocation);
        transportUnitDao.persist(transportUnit);
    }

    @Test
    public final void testCreateExistingTransportUnit() {
        try {
            transportService.createTransportUnit(new Barcode("KNOWN"), transportUnitType, locationPk);
            fail("Must throw a ServiceException while trying to create an already known TransportUnit");
        } catch (ServiceRuntimeException se) {
            logger.debug("OK:ServiceException expected while trying to create an already known TransportUnit");
        }
    }

    @Test
    public final void testCreateTransportUnitOnUnknownLocation() {

        try {
            transportService.createTransportUnit(new Barcode("4711"), transportUnitType, new LocationPK("UNKNOWN",
                    "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));
            fail("Must throw a ServiceException while trying to create a TransportUnit with an unknown actual Location");
        } catch (ServiceRuntimeException se) {
            logger.debug("OK:ServiceException expected while trying to create a TransportUnit with an unknown actual Location");
        }
    }

    @Test
    public final void testCreateTransportUnit() {
        TransportUnit transportUnit = transportService.createTransportUnit(new Barcode("4711"), transportUnitType,
                locationPk);
        assertNotNull("TransportService must create a new TransportUnit", transportUnit);
    }

    @Test
    public final void testMoveUnknownTransportUnit() {
        try {
            transportService.moveTransportUnit(new Barcode("TEST"), targetLocationPk);
            fail("Must throw a ServiceException while trying to create a TransportUnit with unknown Barcode");
        } catch (ServiceRuntimeException se) {
            logger.debug("OK:ServiceException expected while trying to create a TransportUnit with unknown Barcode");
        }
    }

    @Test
    public final void testMoveTransportUnit() {
        TransportUnit transportUnit = transportService.createTransportUnit(new Barcode("4711"), transportUnitType,
                locationPk);
        assertNotNull("TransportService must create a new TransportUnit", transportUnit);
        assertEquals("The actual Location of the TransportUnit must be preset", locationPk, transportUnit
                .getActualLocation().getLocationId());
        transportService.moveTransportUnit(transportUnit.getBarcode(), targetLocationPk);
        assertEquals("The actual Location of the TransportUnit must be changed", targetLocationPk, transportUnit
                .getActualLocation().getLocationId());
    }
}
