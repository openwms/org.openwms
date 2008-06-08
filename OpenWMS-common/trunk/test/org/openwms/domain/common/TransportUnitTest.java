/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.openwms.domain.common.system.UnitError;

public class TransportUnitTest extends AbstractPDOTestCase {

	/**
	 * Try to persist TransportUnit without TransportUnitType.
	 * 
	 */
	@Test
	public final void testTUwithoutType() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		try {
			em.persist(transportUnit);
			em.flush();
			fail("Persist without TransportUnitType not allowed");
		} catch (PersistenceException pe) {
			// okay
			LOG.debug("OK:Execption while persisting TransportUnit without TransportUnitType");
		}
	}

	/**
	 * Try to instanciate TransportUnit with unknown TransportUnitType.
	 * 
	 */
	@Test
	public final void testTUwithUnknownType() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		TransportUnitType transportUnitType = new TransportUnitType("UNKNOWN_TUT");
		transportUnit.setTransportUnitType(transportUnitType);
		try {
			em.persist(transportUnit);
			em.flush();
			fail("Persist with unknown TransportUnitType not allowed");
		} catch (PersistenceException pe) {
			// okay
			LOG.debug("OK:Exception while persisting TransportUnit with unknown TransportUnitType");
		}
	}

	/**
	 * Try to persist TransportUnit with unknown actualLocation and unknown
	 * targetLocation.
	 * 
	 */
	@Test
	public final void testTUwithUnknownLocations() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN_TUT");
		LocationPK unknownLocationPk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location actualLocation = new Location(unknownLocationPk);
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		entityTransaction.commit();
		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setTargetLocation(actualLocation);
		transportUnit.setActualLocation(actualLocation);
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persist with unknown actualLocation && targetLocation not allowed");
		} catch (Exception pe) {
			// okay
			entityTransaction.rollback();
			LOG.debug("OK:Execption while persisting TransportUnit with unknown actualLocation and targetLocation");
		}
	}

	/**
	 * Try to persist TransportUnit with known TransportUnitType. Also with one
	 * known Location.
	 * 
	 */
	@Test
	public void testTUwithKnownLocation() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		TransportUnitType transportUnitType = new TransportUnitType("KNOWN_TUT");
		LocationPK knownLocationPk = new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN");
		Location location = new Location(knownLocationPk);
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		em.persist(location);
		entityTransaction.commit();
		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(location);
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persist with unknown targetLocation not allowed");
		} catch (PersistenceException pe) {
			// okay
			entityTransaction.rollback();
			LOG.debug("OK:Execption while persisting TransportUnit with unknown targetLocation");
		}

		transportUnit.setActualLocation(null);
		transportUnit.setTargetLocation(location);
		try {
			entityTransaction.begin();
			em.merge(transportUnit);
			entityTransaction.commit();
			fail("Persist with unknown actualLocation not allowed");
		} catch (PersistenceException pe) {
			// okay
			entityTransaction.rollback();
			LOG.debug("OK:Execption while persisting TransportUnit with unknown actualLocation");
		}

		entityTransaction = em.getTransaction();
		transportUnit.setActualLocation(location);
		try {
			entityTransaction.begin();
			em.merge(transportUnit);
			entityTransaction.commit();
			LOG.debug("OK:Execption while persisting TransportUnit with known Locations");
		} catch (PersistenceException pe) {
			fail("Persist with unknown actualLocation not allowed");
		}

	}

	/**
	 * Try to persist a TransportUnit with well known actualLocation as well as
	 * targetLocation and also well known TransportUnitType.
	 * 
	 */
	@Test
	public void testTUwithKnownLocations() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		TransportUnitType transportUnitType = new TransportUnitType("KNOWN_TUT");
		LocationPK unknownLocationPk = new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN");
		Location actualLocation = new Location(unknownLocationPk);
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		em.persist(actualLocation);
		entityTransaction.commit();
		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(actualLocation);
		transportUnit.setTargetLocation(actualLocation);
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
		} catch (Exception pe) {
			fail("Persist with well known Location and TransportUnitType fails.");
		}
	}

	@Test
	public void testTUwithErrors() {
		TransportUnit transportUnit = new TransportUnit("TEST_TU");
		TransportUnitType transportUnitType = new TransportUnitType("KNOWN_TUT");
		LocationPK unknownLocationPk = new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN");
		Location location = new Location(unknownLocationPk);
		EntityTransaction entityTransaction = em.getTransaction();

		entityTransaction.begin();
		em.persist(transportUnitType);
		em.persist(location);
		entityTransaction.commit();
		transportUnit.setTransportUnitType(transportUnitType);
		transportUnit.setActualLocation(location);
		transportUnit.setTargetLocation(location);

		UnitError error = new UnitError();
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
		} catch (Exception pe) {
			fail("Persist with well known Location and TransportUnitType fails.");
		}
	}
}
