/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.common.domain.system.UnitError;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.Barcode.BARCODE_ALIGN;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * A TransportUnitTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
public final class TransportUnitTest extends AbstractJpaSpringContextTests {

	/**
	 * <li>Try to persist TransportUnit without TransportUnitType.
	 * 
	 */
	@Test
	public final void testTUwithoutType() {
		TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
		try {
			sharedEntityManager.persist(transportUnit);
			fail("Persisting without TransportUnitType not allowed!");
		}
		catch (PersistenceException pe) {
			// okay
			logger.debug("OK:Execption while persisting TransportUnit without TransportUnitType.");
		}
	}

	/**
	 * <li>Try to instanciate TransportUnit with unknown TransportUnitType.
	 * 
	 */
	@Test
	public final void testTUwithUnknownType() {
		TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
		TransportUnitType transportUnitType = new TransportUnitType("UNKNOWN_TUT");
		transportUnit.setTransportUnitType(transportUnitType);
		try {
			sharedEntityManager.persist(transportUnit);
			fail("Persisting with unknown TransportUnitType not allowed!");
		}
		catch (PersistenceException pe) {
			// okay
			logger.debug("OK:Exception while persisting TransportUnit with unknown TransportUnitType.");
		}
	}

	/**
	 * <li>Try to persist TransportUnit with an unknown actualLocation.
	 * 
	 */
	@Test
	public final void testTUwithUnknownLocations() {
		TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT");
		Location actualLocation = new Location(new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));

		sharedEntityManager.persist(transportUnitType);

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(actualLocation);
		try {
			sharedEntityManager.persist(transportUnit);
			fail("Persisting with unknown actualLocation && targetLocation not allowed!");
		}
		catch (Exception pe) {
			// okay
			logger.debug("OK:Execption while persisting TransportUnit with unknown actualLocation and targetLocation.");
		}
	}

	/**
	 * <li>Try to persist TransportUnit with known TransportUnitType and a known actualLocation.
	 * 
	 */
	@Test
	public final void testTUwithKnownLocation() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_2");
		Location location = new Location(new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN"));

		sharedEntityManager.persist(transportUnitType);
		sharedEntityManager.persist(location);

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(location);

		try {
			sharedEntityManager.merge(transportUnit);
			logger.debug("Also without targetLocation must be okay.");
		}
		catch (PersistenceException pe) {
			logger
					.debug("NOT OK:Execption while persisting TransportUnit with known actualLocation and transportUnitType!");
			fail("Persisting transportUnit with known actualLocation and transportUnitType not committed!");
		}
	}

	/**
	 * <li>Test cascading UnitErrors with TransportUnits.
	 * 
	 */
	@Test
	public final void testTUwithErrors() {
		Barcode.setPadder('0');
		Barcode.setLength(20);
		Barcode.setAlignment(BARCODE_ALIGN.RIGHT);
		Barcode.setPadded(true);
		TransportUnit transportUnit = new TransportUnit(new Barcode("TEST_TU3"));
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_4");
		Location location = new Location(new LocationPK("KNOWN3", "KNOWN3", "KNOWN3", "KNOWN3", "KNOWN3"));

		sharedEntityManager.persist(transportUnitType);
		sharedEntityManager.persist(location);

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(location);
		transportUnit.setTargetLocation(location);

		transportUnit.addError(new UnitError());
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			logger.error("Error", e);
		}
		transportUnit.addError(new UnitError());
		try {
			sharedEntityManager.persist(transportUnit);
		}
		catch (Exception pe) {
			fail("Persisting with well known Location and TransportUnitType fails!");
		}

		Query query = sharedEntityManager.createQuery("select count(ue) from UnitError ue");
		Long cnt = (Long) query.getSingleResult();
		assertEquals("Expected 2 persisted UnitErrors", 2, cnt.intValue());

		sharedEntityManager.remove(transportUnit);

		cnt = (Long) query.getSingleResult();
		assertEquals("Expected 0 persisted UnitErrors", 0, cnt.intValue());
	}

	/**
	 * <tr>
	 * Try to persist a TransportUnit with well known actualLocation and a well known TransportUnitType. The
	 * targetLocation is unknown.
	 * 
	 */
	@Test()
	public final void testTUwithKnownLocations() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU2");
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_3");
		Location actualLocation = new Location(new LocationPK("KNOWN2", "KNOWN2", "KNOWN2", "KNOWN2", "KNOWN2"));
		Location targetLocation = new Location(new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));

		sharedEntityManager.persist(transportUnitType);
		sharedEntityManager.persist(actualLocation);

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(actualLocation);
		transportUnit.setTargetLocation(targetLocation);
		try {
			sharedEntityManager.persist(transportUnit);
			//FIXME fail("Persisting with unknown targetLocation must fail!");
		}
		catch (Exception pe) {
			// okay
			logger.debug("OK:Execption while persisting TransportUnit with unknown targetLocation.");
		}
	}

}
