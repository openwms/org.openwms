/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.system.Message;

/**
 * A LocationDaoTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDaoTest extends AbstractJpaSpringContextTests {

	private LocationDao locationDao;
	private List<Location> locations;

	public void setLocationDao(LocationDao locationDao) {
		this.locationDao = locationDao;
	}

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();
		locationDao.persist(new Location(new LocationPK("area", "aisle", "x", "y", "z")));
	}

	@Test
	public final void testFindAll() {
		assertNotNull("Cannot query a list of all Locations", locationDao.findAll());
		logger.debug("OK:All Location found by query");
	}

	@Test
	public final void testFindById() {
		locations = locationDao.findAll();
		assertNotNull("Cannot find Location by Id", locationDao.findById(locations.get(0).getId()));
		logger.debug("OK:Location found by id query");
	}

	@Test
	public final void testFindByUniqueId() {
		Location known = locationDao.findByUniqueId(new LocationPK("area", "aisle", "x", "y", "z"));
		assertNotNull("Location not found by id", known);
		logger.debug("OK:Location found by unique id query");

		Location unknown = locationDao.findByUniqueId(new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
		assertNull("Unknown Location found by key", unknown);
		logger.debug("OK:Unknown Location not found by unique id query");
	}

	@Test
	public final void testPersist() {
		Location l = new Location(new LocationPK("NEW", "NEW", "NEW", "NEW", "NEW"));
		assertNull("PK should be NULL", l.getId());
		assertTrue("isNew should return true for a transient entity", l.isNew());
		locationDao.persist(l);
		assertNotNull("PK should be set", l.getId());
		assertFalse("isNew should return false for a persistend entity", l.isNew());
	}

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

	@Test
	public final void testAddingErrorMessages() {
		LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
		Location location = new Location(pk);
		locationDao.persist(location);

		location.addMessage(new Message(1, "Errormessage 1"));
		location.addMessage(new Message(2, "Errormessage 2"));

		locationDao.save(location);

		assertEquals("Expected 2 persisted Messages added to the location", 2, location.getMessages().size());
		Query query = sharedEntityManager.createQuery("select count(m) from Message m");
		Long cnt = (Long) query.getSingleResult();
		assertEquals("Expected 2 persisted Messages in MESSAGE table", 2, cnt.intValue());
	}

}
