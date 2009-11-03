/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.domain.LocationGroup;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A LocationGroupDaoTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationGroupDaoTest extends AbstractJpaSpringContextTests {

	@Autowired
	protected LocationGroupDao dao;

	@Test
	public final void testDuplicateLocationGroups() {
		LocationGroup locationGroup = new LocationGroup("FIRST_LG");
		LocationGroup locationGroup2 = new LocationGroup("FIRST_LG");
		dao.persist(locationGroup);
		sharedEntityManager.flush();
		try {
			dao.persist(locationGroup2);
			sharedEntityManager.flush();
			fail("Persisting two LocationGroups with same id must be permitted");
		}
		catch (Exception e) {
			logger.debug("OK:Duplicate id for LocationGroup must be prevented by unique constraint.");
		}
	}

	@Test
	public final void testAddLocationGroup() {
		LocationGroup parent = new LocationGroup("TEST_GROUP_1");
		LocationGroup child = new LocationGroup("TEST_GROUP_2");
		dao.persist(parent);

		try {
			parent.addLocationGroup(null);
			fail("Not allowed to add null as LocationGroup on parent");
		}
		catch (IllegalArgumentException iae) {
			logger.debug("OK: Exception when trying to add null as parent LocationGroup");
		}
		int noChildren = parent.getLocationGroups().size();
		parent.addLocationGroup(child);
		sharedEntityManager.flush();
		parent = dao.save(parent);
		assertTrue("The number of child LocationGroups must be increased by one", noChildren + 1 == parent
				.getLocationGroups().size());
		assertTrue("Parent LocationGroup is not the right one", child.getParent() == parent);
		assertFalse("Child LocationGroup must also be persisted", child.isNew());
	}

	@Test
	public final void testRemoveLocationGroups() {
		LocationGroup parent = new LocationGroup("TEST_GROUP_1");
		LocationGroup child = new LocationGroup("TEST_GROUP_2");
		parent.addLocationGroup(child);
		dao.persist(parent);
		sharedEntityManager.flush();
		try {
			parent.removeLocationGroup(null);
			fail("Not allowed to remove null as LocationGroup on parent");
		}
		catch (IllegalArgumentException iae) {
			logger.debug("OK: Exception when trying to remove null as parent LocationGroup");
		}
		int noChildren = parent.getLocationGroups().size();
		assertEquals("The parent must be set on the child", child.getParent(), parent);
		parent.removeLocationGroup(child);
		assertNull("The parent has to be removed as LocationGroup from the child", child.getParent());
		assertTrue("The number of child LocationGroups must be decreased by one", noChildren - 1 == parent
				.getLocationGroups().size());;
	}

}
