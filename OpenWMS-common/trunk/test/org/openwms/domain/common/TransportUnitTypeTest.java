/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.hibernate.NonUniqueObjectException;
import org.junit.Test;

/**
 * A TransportUnitTypeTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class TransportUnitTypeTest extends AbstractPDOTestCase {

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#TransportUnitType(java.lang.String)}.
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
		} catch (PersistenceException pe) {
			if (!(pe.getCause() instanceof NonUniqueObjectException)) {
				fail("Unallowed exception when persisting existing entity with same identifier!");
			}
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

}
