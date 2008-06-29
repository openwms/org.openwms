/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.domain.common.helper.AbstractPDOTestCase;
import org.openwms.domain.common.system.Message;

/**
 * A LocationTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class LocationTest extends AbstractPDOTestCase {

	@Test
	public void testLocationLifecycle() {
		LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location location = new Location(pk);
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(location);
			entityTransaction.commit();
		} catch (Exception pe) {
			fail("Persist of an Location fails");
		}

		Location location2 = new Location(pk);
		try {
			entityTransaction.begin();
			em.persist(location2);
			entityTransaction.commit();
			fail("Persist of an Location fails");
		} catch (Exception pe) {
			entityTransaction.rollback();
		}

		location2 = em.find(Location.class, location.getId());
		assertNotNull("Cannot read location after persisting", location2);

		entityTransaction.begin();
		em.remove(location2);
		entityTransaction.commit();

		location2 = em.find(Location.class, location.getId());
		assertNull("Location should be REMOVED before", location2);
	}

	/**
	 * - Test unique key constraint on Location
	 * - Testing casde persiting and removing of Messages.
	 *
	 */
	public void testAddingErrors() {
		EntityTransaction entityTransaction = em.getTransaction();
		Location location = createLocation("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location location2 = null;
		assertNotNull("Cannot persist location", location);

		try {
			location2 = createLocation("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
			fail("May not persist Location with the same LocationPk");
		} catch (PersistenceException pe) {
			LOG.debug("OK:Exception, while persisting existing Location");
		}

		Long id = location.getId();
		location2 = em.find(Location.class, id);
		assertNotNull("Location must be persisted before", location2);
		location2.addMessage(new Message(1, "Errormessage 1"));
		location2.addMessage(new Message(2, "Errormessage 2"));
		entityTransaction.begin();
		em.merge(location2);
		entityTransaction.commit();

		location2 = em.find(Location.class, id);
		
		assertEquals("Expected 2 persisted Messages", 2, location2.getMessages().size());

		entityTransaction.begin();
		em.remove(location2);
		entityTransaction.commit();
		
		Query query = em.createQuery("select count(m) from Message m");
		Long cnt = (Long) query.getSingleResult();
		cnt = (Long) query.getSingleResult();
		assertEquals("Expected 0 persisted Messages", 0, cnt.intValue());
		
		LOG.debug("Test closed.");

	}

	private Location createLocation(String area, String aisle, String x, String y, String z) {
		LocationPK pk = new LocationPK(area, aisle, x, y, z);
		Location location = new Location(pk);
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(location);
			entityTransaction.commit();
		} catch (PersistenceException pe) {
			entityTransaction.rollback();
			throw pe;
		}
		return location;
	}
}
