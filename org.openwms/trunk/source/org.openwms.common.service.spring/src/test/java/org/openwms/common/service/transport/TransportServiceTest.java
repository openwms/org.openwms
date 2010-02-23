/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
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
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.TransportUnitService;
import org.openwms.common.service.exception.ServiceException;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
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
        } catch (ServiceException se) {
            logger.debug("OK:ServiceException expected while trying to create an already known TransportUnit");
            se.printStackTrace();
        }
    }

    @Test
    public final void testCreateTransportUnitOnUnknownLocation() {

        try {
            transportService.createTransportUnit(new Barcode("4711"), transportUnitType, new LocationPK("UNKNOWN",
                    "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));
            fail("Must throw a ServiceException while trying to create a TransportUnit with an unknown actual Location");
        } catch (ServiceException se) {
            logger
                    .debug("OK:ServiceException expected while trying to create a TransportUnit with an unknown actual Location");
            se.printStackTrace();
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
        } catch (ServiceException se) {
            logger.debug("OK:ServiceException expected while trying to create a TransportUnit with unknown Barcode");
            se.printStackTrace();
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
