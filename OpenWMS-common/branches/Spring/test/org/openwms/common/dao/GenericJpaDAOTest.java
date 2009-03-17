/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import javax.persistence.Query;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.system.Message;

/**
 * A GenericJpaDAOTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class GenericJpaDAOTest extends AbstractJpaSpringContextTests {

	private GenericDao<Location, Long> locationDao;

	public void setLocationDao(GenericDao<Location, Long> locationDao) {
		this.locationDao = locationDao;
	}

	/**
	 * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDao#findAll()}.
	 */
	@Test
	public final void testFindAll() {
		try {
			locationDao.findAll();
		}
		catch (Exception e) {
			fail("Exception on calling findAll()");
		}
	}

	/**
	 * Test method for
	 * {@link org.openwms.common.dao.core.AbstractGenericJpaDao#findByQuery(java.lang.String, java.util.Map)}.
	 */
	@Test
	public final void testFindByQuery() {
		try {
			locationDao.findById(new Long(1));
		}
		catch (Exception e) {
			fail("Exception on calling findById()");
		}
	}

	/**
	 * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDao#findByUniqueId(java.lang.Object)}.
	 */
	@Test
	public final void testFindByUniqueId() {
		try {
			locationDao.findByUniqueId(new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
		}
		catch (Exception e) {
			fail("Exception on calling findByUniqueId()");
		}
	}

	/**
	 * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDao#save(java.io.Serializable)}.
	 */
	@Test
	public final void testSave() {
		Location l = new Location(new LocationPK("NEW", "NEW", "NEW", "NEW", "NEW"));
		assertNull("PK should be NULL", l.getId());
		assertTrue("isNew should return true for a transient entity", l.isNew());
		locationDao.persist(l);
		assertNotNull("PK should be set", l.getId());
		assertFalse("isNew should return false for a persistend entity", l.isNew());
	}

	/**
	 * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDao#persist(java.io.Serializable)}.
	 */
	@Test
	public final void testPersistAndRemove() {
		Location l = new Location(new LocationPK("NEW", "NEW", "NEW", "NEW", "NEW"));
		assertNull("PK should be NULL", l.getId());
		locationDao.persist(l);
		assertNotNull("PK should be set", l.getId());
		Location l2 = locationDao.findById(l.getId());
		assertNotNull("PK should be set", l2.getId());
		locationDao.remove(l);
		l2 = locationDao.findById(l2.getId());
		assertNull("PK should be NULL", l2);
	}

	/**
	 * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDao#persist(java.lang.Object)}.
	 */
	@Test
	public final void testPersist() {
		LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location location = new Location(pk);

		try {
			locationDao.persist(location);
		}
		catch (Exception pe) {
			pe.printStackTrace();
			fail("Persist of an Location fails");
		}

		Location location2 = new Location(pk);
		try {
			locationDao.persist(location2);
			//sharedEntityManager.flush();
			fail("Persist of an Location fails");
		}
		catch (Exception pe) {
			logger.debug("OK:Cannot persist Location with the same key!");
		}

		Location location3 = (Location) locationDao.findById(location.getId());
		assertNotNull("Cannot read location after persisting", location3);
	}

	/**
	 * Testing cascade persisting and removing of Messages.
	 */
	@Test
	public final void testAddingErrorMessages() {
		LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location location = new Location(pk);
		locationDao.persist(location);

		location.addMessage(new Message(1, "Errormessage 1"));
		location.addMessage(new Message(2, "Errormessage 2"));

		locationDao.save(location);

		assertEquals("Expected 2 persisted Messages added to the location", 2, location.getMessages().size());
		Location location2 = (Location) locationDao.findByUniqueId(pk);
		Query query = sharedEntityManager.createQuery("select count(m) from Message m");
		Long cnt = (Long) query.getSingleResult();
		assertEquals("Expected 2 persisted Messages in MESSAGE table", 2, cnt.intValue());
	}

}
