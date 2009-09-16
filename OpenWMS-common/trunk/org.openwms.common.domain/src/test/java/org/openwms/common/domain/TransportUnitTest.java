/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.common.domain.system.UnitError;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.Barcode.BARCODE_ALIGN;
import org.openwms.common.test.AbstractPDOTestCase;

/**
 * 
 * A TransportUnitTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class TransportUnitTest extends AbstractPDOTestCase {

	/**
	 * <li>Try to persist TransportUnit without TransportUnitType.
	 * 
	 */
	@Test
	public final void testTUwithoutType() {
		TransportUnit transportUnit = new TransportUnit("NEVER_PERSISTED");
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persisting without TransportUnitType not allowed!");
		}
		catch (PersistenceException pe) {
			// okay
			LOG.debug("OK:Execption while persisting TransportUnit without TransportUnitType.");
			entityTransaction.rollback();
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
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persisting with unknown TransportUnitType not allowed!");
		}
		catch (PersistenceException pe) {
			// okay
			LOG.debug("OK:Exception while persisting TransportUnit with unknown TransportUnitType.");
			entityTransaction.rollback();
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
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		entityTransaction.commit();

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(actualLocation);
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persisting with unknown actualLocation && targetLocation not allowed!");
		}
		catch (Exception pe) {
			// okay
			LOG.debug("OK:Execption while persisting TransportUnit with unknown actualLocation and targetLocation.");
			entityTransaction.rollback();
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
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		em.persist(location);
		entityTransaction.commit();

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(location);

		try {
			entityTransaction.begin();
			em.merge(transportUnit);
			entityTransaction.commit();
			LOG.debug("Also without targetLocation must be okay.");
		}
		catch (PersistenceException pe) {
			LOG
					.debug("NOT OK:Execption while persisting TransportUnit with known actualLocation and transportUnitType!");
			entityTransaction.rollback();
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
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		em.persist(location);
		entityTransaction.commit();

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(location);
		transportUnit.setTargetLocation(location);

		transportUnit.addError(new UnitError());
		try {
			Thread.sleep(100);
		}
		catch (InterruptedException e) {
			LOG.error("Error", e);
		}
		transportUnit.addError(new UnitError());
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
		}
		catch (Exception pe) {
			fail("Persisting with well known Location and TransportUnitType fails!");
		}

		Query query = em.createQuery("select count(ue) from UnitError ue");
		Long cnt = (Long) query.getSingleResult();
		assertEquals("Expected 2 persisted UnitErrors", 2, cnt.intValue());

		entityTransaction.begin();
		em.remove(transportUnit);
		entityTransaction.commit();

		cnt = (Long) query.getSingleResult();
		assertEquals("Expected 0 persisted UnitErrors", 0, cnt.intValue());
	}

	/**
	 * <tr>
	 * Try to persist a TransportUnit with well known actualLocation and a well known TransportUnitType. The
	 * targetLocation is unknown.
	 * 
	 */
	@Test
	public final void testTUwithKnownLocations() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU2");
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT_3");
		Location actualLocation = new Location(new LocationPK("KNOWN2", "KNOWN2", "KNOWN2", "KNOWN2", "KNOWN2"));
		Location targetLocation = new Location(new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN"));
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		em.persist(actualLocation);
		entityTransaction.commit();

		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(actualLocation);
		transportUnit.setTargetLocation(targetLocation);
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persisting with unknown targetLocation must fail!");
		}
		catch (Exception pe) {
			// okay
			LOG.debug("OK:Execption while persisting TransportUnit with unknown targetLocation.");
		}
	}

}
