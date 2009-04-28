/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.dao.GenericDao;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.TransportService;
import org.openwms.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@ContextConfiguration
public final class TransportServiceTest extends AbstractJpaSpringContextTests {

	@Autowired
	protected TransportService transportService;

	@Qualifier("locationDao")
	@Autowired
	protected GenericDao<Location, Long> locationDao;

	@Qualifier("transportUnitTypeDao")
	@Autowired
	protected GenericDao<TransportUnitType, String> transportUnitTypeDao;

	@Qualifier("transportUnitDao")
	@Autowired
	protected GenericDao<TransportUnit, Long> transportUnitDao;

	private String testDataFile = "load-TransportUnits.sql";
	LocationPK locationPk = new LocationPK("AREA", "AISLE", "X", "Y", "Z");
	LocationPK targetLocation = new LocationPK("TARGET", "TARGET", "TARGET", "TARGET", "TARGET");
	TransportUnitType transportUnitType = new TransportUnitType("TestType");

	public TransportServiceTest() {
		super();
		// setPopulateProtectedVariables(true);
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		Location actualLocation = new Location(locationPk);
		locationDao.persist(actualLocation);
		actualLocation = locationDao.save(actualLocation);
		locationDao.persist(new Location(targetLocation));
		transportUnitTypeDao.persist(transportUnitType);
		TransportUnit transportUnit = new TransportUnit("KNOWN");
		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(actualLocation);
		transportUnitDao.persist(transportUnit);
	}

	@Override
	protected String getTestDataFile() {
		return null;
	}

	@Test
	public final void testCreateExistingTransportUnit() {
		try {
			transportService.createTransportUnit(new Barcode("KNOWN"), transportUnitType, locationPk);
			fail("Must throw a ServiceException while trying to create an already known TransportUnit");
		}
		catch (ServiceException se) {
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
		}
		catch (ServiceException se) {
			logger.debug("OK:ServiceException expected while trying to create a TransportUnit with an unknown actual Location");
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
			transportService.moveTransportUnit(new Barcode("TEST"), targetLocation);
			fail("Must throw a ServiceException while trying to create a TransportUnit with unknown Barcode");
		}
		catch (ServiceException se) {
			logger.debug("OK:ServiceException expected while trying to create a TransportUnit with unknown Barcode");
			se.printStackTrace();
		}
	}

	@Test
	public final void testMoveTransportUnit() {
		TransportUnit transportUnit = transportService.createTransportUnit(new Barcode("4711"), transportUnitType,
				locationPk);
		assertNotNull("TransportService must create a new TransportUnit", transportUnit);
		assertEquals("The actual Location of the TransportUnit must be preset", locationPk, transportUnit.getActualLocation().getLocationId());
		transportService.moveTransportUnit(transportUnit.getBarcode(), targetLocation);
		assertEquals("The actual Location of the TransportUnit must be changed", targetLocation, transportUnit.getActualLocation().getLocationId());
	}
}
