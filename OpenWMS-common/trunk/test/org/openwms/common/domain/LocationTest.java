/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.common.domain.helper.AbstractPDOTestCase;
import org.openwms.common.domain.system.Message;

/**
 * A LocationTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class LocationTest extends AbstractPDOTestCase {

    /**
     * <li> Checks primary key constraint.
     * <li> Checks inserting and removing a Location.
     * 
     */
    @Test
    public final void testLocationLifecycle() {
	LocationPK pk = new LocationPK("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
	Location location = new Location(pk);
	EntityTransaction entityTransaction = em.getTransaction();
	try {
	    entityTransaction.begin();
	    em.persist(location);
	    entityTransaction.commit();
	}
	catch (Exception pe) {
	    fail("Persist of an Location fails");
	}

	Location location2 = new Location(pk);
	try {
	    entityTransaction.begin();
	    em.persist(location2);
	    entityTransaction.commit();
	    fail("Persist of an Location fails");
	}
	catch (Exception pe) {
	    entityTransaction.rollback();
	}

	Location location3 = em.find(Location.class, location.getId());
	assertNotNull("Cannot read location after persisting", location3);

	entityTransaction.begin();
	em.remove(location3);
	entityTransaction.commit();

	Location location4 = em.find(Location.class, location.getId());
	assertNull("Location should be REMOVED before", location4);
    }

    /**
     * <li>Testing cascade persisting and removing of Messages.
     * 
     */
    @Test
    public final void testAddingErrors() {
	EntityTransaction entityTransaction = em.getTransaction();
	Location location = createLocation("UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN", "UNKNOWN");
	assertNotNull("Cannot persist location", location);

	Long id = location.getId();
	Location location2 = em.find(Location.class, id);
	assertNotNull("Location must be persisted before", location2);
	location2.addMessage(new Message(1, "Errormessage 1"));
	location2.addMessage(new Message(2, "Errormessage 2"));
	entityTransaction.begin();
	location2 = em.merge(location2);
	entityTransaction.commit();

	Location location3 = em.find(Location.class, id);

	assertEquals("Expected 2 persisted Messages", 2, location3.getMessages().size());

	entityTransaction.begin();
	em.remove(location3);
	entityTransaction.commit();

	Query query = em.createQuery("select count(m) from Message m");
	Long cnt = (Long) query.getSingleResult();
	assertEquals("Expected 0 persisted Messages", 0, cnt.intValue());
    }

    private Location createLocation(String area, String aisle, String x, String y, String z) {
	LocationPK pk = new LocationPK(area, aisle, x, y, z);
	Location location = new Location(pk);
	EntityTransaction entityTransaction = em.getTransaction();
	try {
	    entityTransaction.begin();
	    em.persist(location);
	    entityTransaction.commit();
	}
	catch (PersistenceException pe) {
	    entityTransaction.rollback();
	    throw pe;
	}
	return location;
    }
}
