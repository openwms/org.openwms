/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.helper.AbstractPDOTestCase;

/**
 * 
 * A LocationSystemTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class LocationSystemTest extends AbstractPDOTestCase{
	
	private static final String TEST_LOCATION_GROUP = "TEST_LG";
	private static final String KNOWN = "KNOWN";
	private static final String VIRTUAL = "VIRTUAL";

	//TODO: Finish
	@Test
	public void testCascadingOnLocationGroup(){
		EntityTransaction entityTransaction = em.getTransaction();
		LocationGroup locationGroup = new LocationGroup(TEST_LOCATION_GROUP);
		Location actualLocation = new Location(new LocationPK(KNOWN, KNOWN, KNOWN, KNOWN, KNOWN));
		Location virtualLocation = new Location(new LocationPK(VIRTUAL, VIRTUAL, VIRTUAL, VIRTUAL, VIRTUAL));
		locationGroup.addLocation(actualLocation);
		locationGroup.addLocation(virtualLocation);

		entityTransaction.begin();
		em.persist(actualLocation);
		em.persist(virtualLocation);
		em.persist(locationGroup);
		entityTransaction.commit();
		
		

	}
	
	
}
