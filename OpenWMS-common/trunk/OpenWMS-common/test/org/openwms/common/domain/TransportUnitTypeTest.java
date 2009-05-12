/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.junit.Test;
import org.openwms.common.domain.helper.AbstractPDOTestCase;

/**
 * A TransportUnitTypeTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class TransportUnitTypeTest extends AbstractPDOTestCase {

	/**
	 * Test unique constraint on type.
	 */
	@Test
	public final void testTransportUnitType() {
		EntityTransaction entityTransaction = em.getTransaction();
		TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
		TransportUnitType transportUnitType2 = new TransportUnitType("JU_TEST");

		entityTransaction.begin();
		em.persist(transportUnitType);
		entityTransaction.commit();
		entityTransaction.begin();
		try {
			em.persist(transportUnitType2);
			fail("Expecting exception when persisting existing entity with same identifier!");
		}
		catch (PersistenceException pe) {
		}
		entityTransaction.rollback();

		TransportUnitType tt = em.find(TransportUnitType.class, "JU_TEST");
		assertNotNull("TransportUnitType should be SAVED before", tt);

		entityTransaction.begin();
		em.remove(tt);
		entityTransaction.commit();

		tt = em.find(TransportUnitType.class, "JU_TEST");
		assertNull("TransportUnitType should be REMOVED before", tt);
	}

	@Test
	public final void testCascadingTypePlacingRule() {
		EntityTransaction entityTransaction = em.getTransaction();
		TransportUnitType transportUnitType = new TransportUnitType("JU_TEST");
		LocationType locationType = new LocationType("JU_LOC_TYPE");
		TypePlacingRule typePlacingRule = new TypePlacingRule(1, locationType);

		transportUnitType.addTypePlacingRule(typePlacingRule);

		entityTransaction.begin();
		em.persist(locationType);
		entityTransaction.commit();
		entityTransaction.begin();
		em.persist(transportUnitType);
		entityTransaction.commit();

		TypePlacingRule tpr = em.find(TypePlacingRule.class, Long.valueOf(1));
		assertNotNull("TypePlacingRule should be cascaded SAVED before", tpr);

		entityTransaction.begin();
		em.remove(transportUnitType);
		entityTransaction.commit();

		transportUnitType = em.find(TransportUnitType.class, "JU_TEST");
		assertNull("TransportUnitType should be REMOVED before", transportUnitType);

	}

}
