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

public class TransportUnitTest extends AbstractPDOTestCase {

	/**
	 * Try to instanciate existent TransportUnits.
	 * 
	 */
	@Test
	public final void testTUwithoutType() {
		TransportUnit transportUnit = new TransportUnit("TEST1");
		try {
			em.persist(transportUnit);
			em.flush();
			fail("Persist with NULL TransportUnitType not allowed");
		} catch (PersistenceException pe) {
			// okay
			LOG.debug("Execption while persisting TransportUnit with TransportUnitType set to NULL");
		} finally {
			//entityTransaction.rollback();
		}


	}
	
	/**
	 * Try to instanciate existent TransportUnits.
	 * 
	 */
	@Test
	public final void testTUwithUnknownType(){
		TransportUnit transportUnit = new TransportUnit("TEST1");
		TransportUnitType transportUnitType = new TransportUnitType("UNKNOWN");
		transportUnit.setTransportUnitType(transportUnitType);
		//entityTransaction.begin();
		try {
			em.persist(transportUnit);
			em.flush();
			fail("Persist with unknown TransportUnitType not allowed");
		} catch (PersistenceException pe) {
			// okay
			//entityTransaction.rollback();
			LOG.debug("Exception while persisting TransportUnit with not-existing TransportUnitType");
		}

	}
	
	@Test
	public final void testTUwithUnknownLocation(){
		TransportUnit transportUnit = new TransportUnit("TEST1");
		TransportUnitType transportUnitType = new TransportUnitType("WELL_KNOWN");
		LocationPK unknownLocationPk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location actualLocation = new Location(unknownLocationPk);
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
			fail("Persist with unknown actualLocation not allowed");
		} catch (Exception pe) {
			// okay
			entityTransaction.rollback();
			LOG.debug("Execption while persisting TransportUnit with not-existing Location as actualLocation");
		}
	}

	@Test
	public void testTUwithKnownLocation() {
		TransportUnit transportUnit = new TransportUnit("TEST2");
		TransportUnitType transportUnitType = new TransportUnitType("KNOWN");
		LocationPK unknownLocationPk = new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN");
		Location actualLocation = new Location(unknownLocationPk);
		EntityTransaction entityTransaction = em.getTransaction();
		
		entityTransaction.begin();
		em.persist(transportUnitType);
		entityTransaction.commit();
		transportUnit.setTransportUnitType(transportUnitType);
		entityTransaction.begin();
		em.persist(actualLocation);
		entityTransaction.commit();
		transportUnit.setActualLocation(actualLocation);
//		transportUnit.setActualLocation(persistValidTransportUnitType(actualLocation));
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
			fail("Persist with unknown actualLocation not allowed");
		} catch (PersistenceException pe) {
			// okay
			entityTransaction.rollback();
			LOG.debug("Execption while persisting TransportUnit with not-existing Location as actualLocation");
		}
	}
	
	@Test
	public void testTUwithKnownLocations() {
		TransportUnit transportUnit = new TransportUnit("TEST2");
		TransportUnitType transportUnitType = new TransportUnitType("KNOWN");
		LocationPK unknownLocationPk = new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN");
		Location actualLocation = new Location(unknownLocationPk);
		EntityTransaction entityTransaction = em.getTransaction();
		
		entityTransaction.begin();
		em.persist(transportUnitType);
		entityTransaction.commit();
		transportUnit.setTransportUnitType(transportUnitType);
		entityTransaction.begin();
		em.persist(actualLocation);
		entityTransaction.commit();
		transportUnit.setActualLocation(actualLocation);
		transportUnit.setTargetLocation(actualLocation);
		try {
			entityTransaction.begin();
			em.persist(transportUnit);
			entityTransaction.commit();
		} catch (PersistenceException pe) {
			// okay
			//entityTransaction.rollback();
			fail("Persist with unknown actualLocation not allowed");
			LOG.debug("Execption while persisting TransportUnit with not-existing Location as actualLocation");
		}
	}

	private <T> T persistValidTransportUnitType(T entity) {
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(entity);
			entityTransaction.commit();
		} catch (PersistenceException p) {
			LOG.error("Entity already exists?");
			LOG.error(p.getStackTrace());
		}
		return entity;
	}
}
