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
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationGroup.STATE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * A LocationGroupDaoTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationGroupDaoTest extends AbstractJpaSpringContextTests {

	@Autowired
	@Qualifier("locationGroupDaoImpl")
	protected GenericDAO<LocationGroup, Long> dao;

	@Autowired
	@Qualifier("locationDaoImpl")
	protected GenericDAO<Location, Long> locationDao;

	public LocationGroupDaoTest() {
		setPopulateProtectedVariables(true);
	}

	@Test
	public final void testLocationGroupConstraint() {
		LocationGroup locationGroup = new LocationGroup("FIRST_LG");
		LocationGroup locationGroup2 = new LocationGroup("FIRST_LG");
		dao.persist(locationGroup);
		sharedEntityManager.flush();
		try {
			dao.persist(locationGroup2);
			sharedEntityManager.flush();
		}
		catch (Exception e) {
			logger.debug("OK:Duplicate name of locGroup must be prevented by unique constraint.");
		}

		System.out.println("stop");
	}

	@Test
	public final void testLocationGroupInheritance() {
		LocationGroup locationGroup1 = new LocationGroup("TEST_GROUP_1");
		LocationGroup locationGroup2 = new LocationGroup("TEST_GROUP_2");

		dao.persist(locationGroup1);

		locationGroup1.addLocationGroup(locationGroup2);
		sharedEntityManager.flush();

		locationGroup1 = dao.save(locationGroup1);

		// Save the second one (merge) to retrieve the PK
		locationGroup2 = dao.save(locationGroup2);

		assertTrue(locationGroup2.getParent() == locationGroup1);

		// Second locGroup must also be persisted.
		assertFalse(locationGroup2.isNew());

		locationGroup1.setGroupStateIn(STATE.NOT_AVAILABLE);

		assertTrue(locationGroup2.getGroupStateIn() == STATE.NOT_AVAILABLE);

	}

}
