/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import javax.persistence.EntityTransaction;

import org.junit.Test;

public class LocationSystemTest extends AbstractPDOTestCase{
	
	private static final String TEST_LOCATION_GROUP = "TEST_LG";
	private static final String KNOWN = "KNOWN";
	private static final String VIRTUAL = "VIRTUAL";

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
		entityTransaction.commit();
		
		

	}
	
	
}
