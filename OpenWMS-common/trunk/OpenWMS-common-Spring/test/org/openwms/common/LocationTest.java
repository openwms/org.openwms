/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.dao.LocationDao;
import org.openwms.common.dao.LocationGroupDao;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * A LocationTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class LocationTest extends AbstractJpaSpringContextTests {

	@Qualifier("locationDao")
	@Autowired
	protected LocationDao locationDao;

	@Autowired
	@Qualifier("locationGroupDao")
	protected LocationGroupDao locationGroupDao;

	LocationGroup locationGroup = new LocationGroup("TEST_LOCATION_GROUP1");
	LocationGroup locationGroup2 = new LocationGroup("TEST_LOCATION_GROUP2");
	Location actualLocation = new Location(new LocationPK("KNOWN", "KNOWN", "KNOWN", "KNOWN", "KNOWN"));
	Location virtualLocation = new Location(new LocationPK("VIRTUAL", "VIRTUAL", "VIRTUAL", "VIRTUAL", "VIRTUAL"));

	@Override
	protected void onSetUpInTransaction() throws Exception {
		super.onSetUpInTransaction();

		locationGroupDao.persist(locationGroup);
		locationDao.persist(actualLocation);
		locationDao.persist(virtualLocation);

	}

	@Test
	public final void testSettingGroupOnLocation() {

		try {
			locationGroup.addLocation(null);
			fail("Not allowed to add null as location to a group");
		}
		catch (IllegalArgumentException iae) {
			logger.debug("OK: Exception when trying to add null");
		}

		int noLocations = locationGroup.getLocations().size();

		assertNull(actualLocation.getLocationGroup());
		locationGroup.addLocation(actualLocation);
		assertEquals("OK: Group set on location", locationGroup, actualLocation.getLocationGroup());
		assertTrue("OK: Number of locations increased by 1", noLocations + 1 == locationGroup.getLocations().size());

		locationGroup2.addLocation(actualLocation);
		assertEquals("OK: Group set on location", locationGroup2, actualLocation.getLocationGroup());
		assertTrue("OK: Number of locations decreased by 1", noLocations == locationGroup.getLocations().size());

	}

}
