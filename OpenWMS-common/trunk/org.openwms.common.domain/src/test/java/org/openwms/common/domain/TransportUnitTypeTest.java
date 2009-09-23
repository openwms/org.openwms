/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.openwms.common.test.AbstractJpaSpringContextTests;

/**
 * 
 * A TransportUnitTypeTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class TransportUnitTypeTest extends AbstractJpaSpringContextTests {

	/**
	 * Test unique constraint on type.
	 */
	@Test
	public final void testTransportUnitType() {
		TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
		TransportUnitType transportUnitType2 = new TransportUnitType("JU_TEST");

		sharedEntityManager.persist(transportUnitType);
		try {
			sharedEntityManager.persist(transportUnitType2);
			fail("Expecting exception when persisting existing entity with same identifier!");
		}
		catch (PersistenceException pe) {}

		TransportUnitType tt = sharedEntityManager.find(TransportUnitType.class, "JU_TEST");
		assertNotNull("TransportUnitType should be SAVED before", tt);

		sharedEntityManager.remove(tt);

		tt = sharedEntityManager.find(TransportUnitType.class, "JU_TEST");
		assertNull("TransportUnitType should be REMOVED before", tt);
	}

	@Test
	public final void testCascadingTypePlacingRule() {
		TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
		LocationType locationType = new LocationType("JU_LOC_TYPE");
		TypePlacingRule typePlacingRule = new TypePlacingRule(1, locationType);

		transportUnitType.addTypePlacingRule(typePlacingRule);

		sharedEntityManager.persist(locationType);
		sharedEntityManager.persist(transportUnitType);

		TypePlacingRule tpr = sharedEntityManager.find(TypePlacingRule.class, Long.valueOf(1));
		assertNotNull("TypePlacingRule should be cascaded SAVED before", tpr);

		sharedEntityManager.remove(transportUnitType);

		transportUnitType = sharedEntityManager.find(TransportUnitType.class, "JU_TEST");
		assertNull("TransportUnitType should be REMOVED before", transportUnitType);

	}

}
