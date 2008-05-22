/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.NonUniqueObjectException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A TransportUnitTypeTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class TransportUnitTypeTest extends TestCase {

	private static final Log LOG = LogFactory.getLog(TransportUnitTypeTest.class);
	private EntityManagerFactory emf;
	private EntityManager em;
	private Connection connection;

	/**
	 * FIXME Comment this
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		try {
			LOG.info("Starting in-memory HSQL database for unit tests");
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:mem:openwms", "sa", "");
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception during HSQL database startup.");
		}
		try {
			LOG.info("Building JPA EntityManager for unit tests");
			emf = Persistence.createEntityManagerFactory("OpenWMS-test-durable");
			em = emf.createEntityManager();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception during JPA EntityManager instanciation.");
		}
	}

	/**
	 * FIXME Comment this
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		LOG.info("Shuting down Hibernate JPA layer.");
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
		LOG.info("Stopping in-memory HSQL database.");
		try {
			connection.createStatement().execute("SHUTDOWN");
		} catch (Exception ex) {
		}
	}

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
			if (!(pe.getCause() instanceof NonUniqueObjectException)){
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

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setTypePlacingRules(java.util.Set)}.
	 */
	@Test
	public final void testSetTypePlacingRules() {
		
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getTypeStackingRules()}.
	 */
	@Test
	public final void testGetTypeStackingRules() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setTypeStackingRules(java.util.Set)}.
	 */
	@Test
	public final void testSetTypeStackingRules() {
		fail("Not yet implemented"); // TODO
	}

}
