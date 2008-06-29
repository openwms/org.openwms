/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.helper;

import java.sql.Connection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * A AbstractPDOTestCase.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public abstract class AbstractPDOTestCase extends TestCase {

	protected Log LOG = LogFactory.getLog(this.getClass());
	protected EntityManagerFactory emf;
	protected EntityManager em;
	protected Connection connection;
	protected boolean running = false;

	/**
	 * Do before test run.
	 * 
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public void setUp() {
		try {
			if (!running) {
				TestHelper.initializeTestCase();
			}
			if (!TestHelper.isPersistenceUnitDurable() && !TestHelper.isDbStarted()) {
				TestHelper.startDb();
			}
			LOG.info("Building JPA EntityManager for unit tests");
			emf = TestHelper.createEntityManagerFactory(TestHelper.getPersistenceUnit());
			em = emf.createEntityManager();
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("Exception during JPA EntityManager instanciation.");
		}
	}


	/**
	 * Do after test run.
	 * 
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public void tearDown() {
		LOG.info("Shuting down Hibernate JPA layer.");
		if (em != null) {
			em.close();
		}
		if (emf != null) {
			emf.close();
		}
	}
}
