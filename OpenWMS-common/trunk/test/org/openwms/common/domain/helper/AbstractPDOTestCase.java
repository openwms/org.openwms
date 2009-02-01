/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.helper;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

/**
 * A AbstractPDOTestCase.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public abstract class AbstractPDOTestCase {

    protected final Log LOG = LogFactory.getLog(this.getClass());
    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    protected static boolean running = false;

    /**
     * Do before test run.
     * 
     */
    @BeforeClass
    public static void setUpBeforeClass() {
	try {
	    if (!running) {
		running = true;
	    }
	    if (!TestHelper.getInstance().isDbStarted() && !TestHelper.getInstance().isPersistenceUnitDurable()) {
		TestHelper.getInstance().startDb();
	    }
	    emf = TestHelper.getInstance().createEntityManagerFactory(TestHelper.getInstance().getPersistenceUnit());
	    // em = emf.createEntityManager();
	}
	catch (Exception ex) {
	    fail("Exception during JPA EntityManager instanciation: " + ex.getMessage());
	}
    }

    /**
     * Do after test run.
     * 
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() {
	if (em != null) {
	    em.close();
	}
	if (emf != null) {
	    emf.close();
	}
    }

    @After
    public void tearDown() {
	EntityTransaction entityTransaction = em.getTransaction();
	if (entityTransaction.isActive()) {
	    LOG.debug("Rollback transaction");
	    entityTransaction.rollback();
	}
	LOG.debug("Destroying EntityManager");
	em.close();
	em = null;
    }

    @Before
    public void setUp() {
	if (em == null) {
	    LOG.debug("Getting EntityManager");
	    em = emf.createEntityManager();
	}
    }
}
