/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.system.Message;

/**
 * A LocationDAOTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationDAOTest extends AbstractJpaSpringContextTests {

    private GenericDAO<Location, Long> locationDAO;

    public void setLocationDAO(GenericDAO<Location, Long> locationDAO) {
	this.locationDAO = locationDAO;
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findById(java.io.Serializable)}.
     */
    @Test
    public final void testFindById() {
    // fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findAll()}.
     */
    @Test
    public final void testFindAll() {
    // fail("Not yet implemented");
    }

    /**
     * Test method for
     * {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findByQuery(java.lang.String, java.util.Map)}.
     */
    @Test
    public final void testFindByQuery() {
    // fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#findByUniqueId(java.lang.Object)}.
     */
    @Test
    public final void testFindByUniqueId() {
    // fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#save(java.lang.Object)}.
     */
    @Test
    public final void testSave() {
    // fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#remove(java.lang.Object)}.
     */
    @Test
    public final void testRemove() {
    // fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.openwms.common.dao.core.AbstractGenericJpaDAO#persist(java.lang.Object)}.
     */
    @Test
    public final void testPersist() {
	LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
	Location location = new Location(pk);

	try {
	    locationDAO.persist(location);
	}
	catch (Exception pe) {
	    pe.printStackTrace();
	    fail("Persist of an Location fails");
	}

	Location location2 = new Location(pk);
	try {
	    locationDAO.persist(location2);
	    sharedEntityManager.flush();
	    fail("Persist of an Location fails");
	}
	catch (Exception pe) {
	    logger.debug("OK:Cannot persist Location with the same key!");
	}

	Location location3 = (Location) locationDAO.findById(location.getId());
	assertNotNull("Cannot read location after persisting", location3);
    }

    /**
     * Testing cascade persisting and removing of Messages.
     */
    @Test
    public final void testAddingErrorMessages() {
	LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
	Location location = new Location(pk);
	locationDAO.persist(location);

	location.addMessage(new Message(1, "Errormessage 1"));
	location.addMessage(new Message(2, "Errormessage 2"));

	locationDAO.save(location);

	assertEquals("Expected 2 persisted Messages", 2, location.getMessages().size());
	Location location2 = (Location) locationDAO.findByUniqueId(pk);
System.out.println("Hallo");
//	 Query query = em.createQuery("select count(m) from Message m");
//	 Long cnt = (Long) query.getSingleResult();
//	 assertEquals("Expected 0 persisted Messages", 0, cnt.intValue());
    }

}
