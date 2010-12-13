/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.test;

import static org.junit.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * An AbstractPDOTestCase.
 * <p>
 * Used to create an EntityManager instance and care about transactional
 * behavior.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @since 0.1
 * @version $Revision$
 */
@Deprecated
public abstract class AbstractPDOTestCase {

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Shared EntityManagerFactory instance
     */
    protected static EntityManagerFactory emf;

    /**
     * Shared EntityManager instance
     */
    protected static EntityManager em;

    /**
     * Indicates whether the embedded database is running or not.
     */
    protected static boolean running = false;

    /**
     * Do before test run.
     */
    @BeforeClass
    public static void setUpBeforeClass() {
        try {
            if (!running) {
                running = true;
            }
            if (!Helper.getInstance().isDbStarted() && !Helper.getInstance().isPersistenceUnitDurable()) {
                Helper.getInstance().startDb();
            }
            emf = Helper.getInstance().createEntityManagerFactory(Helper.getInstance().getPersistenceUnit());
        }
        catch (Exception ex) {
            fail("Exception during JPA EntityManager instanciation: " + ex.getMessage());
        }
    }

    /**
     * Do after test run.
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

    /**
     * Do after each test method
     */
    @After
    public void tearDown() {
        EntityTransaction entityTransaction = em.getTransaction();
        if (entityTransaction.isActive()) {
            logger.debug("Rollback transaction");
            entityTransaction.rollback();
        }
        logger.debug("Destroying EntityManager");
        em.close();
        em = null;
    }

    /**
     * Do before each test method
     */
    @Before
    public void setUp() {
        if (em == null) {
            em = emf.createEntityManager();
            if (em == null) {
                logger.debug("Creation of EntityManager failed");
                throw new RuntimeException("TEST manage is null");
            } else {
                logger.debug("Creation of EntityManager passed");
            }
        }
    }
}