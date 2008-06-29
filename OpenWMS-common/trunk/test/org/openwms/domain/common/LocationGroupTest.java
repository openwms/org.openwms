/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.util.Set;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.domain.common.helper.AbstractPDOTestCase;

/**
 * A LocationGroupTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class LocationGroupTest extends AbstractPDOTestCase {

	private static final String GROUP1_ID = "TEST_GROUP_1";
	private static final String GROUP2_ID = "TEST_GROUP_2";
	private static final String KNOWN = "KNOWN";

	@Test
	public void testCreatingLocationGroup() {
		LocationGroup locationGroup = new LocationGroup(GROUP1_ID);
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(locationGroup);
			entityTransaction.commit();
		} catch (Exception pe) {
			fail("Persist of an LocationGroup fails");
		}

	}

	@Test
	public void testLocationGroup() {
		LocationGroup locationGroup1 = new LocationGroup(GROUP1_ID);
		LocationGroup locationGroup2 = new LocationGroup(GROUP2_ID);
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(locationGroup1);
			em.persist(locationGroup2);
			entityTransaction.commit();
		} catch (Exception pe) {
			fail("Persist of an LocationGroup fails");
		}

		locationGroup1.addLocationGroup(locationGroup2);
		entityTransaction.begin();
		em.merge(locationGroup1);
		entityTransaction.commit();

		Query query = em.createQuery("select lg from LocationGroup lg where lg.groupId = :groupId");
		query.setParameter("groupId", locationGroup1.getGroupId());
		LocationGroup parent = (LocationGroup) query.getSingleResult();
		assertNotNull("Parent LocationGroup must be persisted", parent);
		assertTrue("Child must be visible in parent", (parent.getLocationGroups()).size() > 0);

		assertTrue("The child has to be the right one and not the parent", parent.getLocationGroups().contains(
				locationGroup2));

		Set<LocationGroup> clients = parent.getLocationGroups();
		LocationGroup test = null;
		for (LocationGroup client : clients) {
			test = client.getParent();
			assertEquals("The parent of child must match", locationGroup1, test);
		}
	}

	@Test
	public void testLocationGroupWithLocations() {
		LocationGroup locationGroup = new LocationGroup(GROUP1_ID);
		Location location = new Location(new LocationPK(KNOWN, KNOWN, KNOWN, KNOWN, KNOWN));
		EntityTransaction entityTransaction = em.getTransaction();
		try {
			entityTransaction.begin();
			em.persist(locationGroup);
			em.persist(location);
			entityTransaction.commit();
		} catch (Exception pe) {
			fail("Persist of an LocationGroup or Location fails");
		}

		locationGroup.addLocation(location);
		entityTransaction.begin();
		em.merge(locationGroup);
		entityTransaction.commit();

		Query query = em.createQuery("select lg from LocationGroup lg where lg.groupId = :groupId");
		query.setParameter("groupId", locationGroup.getGroupId());
		LocationGroup parent = (LocationGroup) query.getSingleResult();
		assertNotNull("Parent LocationGroup must be persisted", parent);
		assertTrue("LocationGroup must have a Location", (parent.getLocations()).size() > 0);

		for (Location childLocation : parent.getLocations()) {
			assertEquals("The Location must belong to this LocationGroup", locationGroup, childLocation
					.getLocationGroup());
		}
		
		locationGroup.removeLocation(location);
		entityTransaction.begin();
		em.merge(locationGroup);
		entityTransaction.commit();
		assertNull("Location doesnt have an LocationGroup anymore", location.getLocationGroup());
		

	}

}
