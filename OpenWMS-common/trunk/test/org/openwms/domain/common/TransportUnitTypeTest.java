/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import static org.junit.Assert.fail;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * A TransportUnitTypeTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class TransportUnitTypeTest {

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
			emf = Persistence.createEntityManagerFactory("OpenWMS-test");
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
		TransportUnitType transportUnitType = new TransportUnitType("TEST_TYPE");
		em.persist(transportUnitType);
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getType()}.
	 */
	@Test
	public final void testGetType() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setType(java.lang.String)}.
	 */
	@Test
	public final void testSetType() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getWidth()}.
	 */
	@Test
	public final void testGetWidth() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setWidth(int)}.
	 */
	@Test
	public final void testSetWidth() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getDescription()}.
	 */
	@Test
	public final void testGetDescription() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getHeight()}.
	 */
	@Test
	public final void testGetHeight() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setHeight(int)}.
	 */
	@Test
	public final void testSetHeight() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getPayload()}.
	 */
	@Test
	public final void testGetPayload() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setPayload(java.math.BigDecimal)}.
	 */
	@Test
	public final void testSetPayload() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getCompatibility()}.
	 */
	@Test
	public final void testGetCompatibility() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setCompatibility(java.lang.String)}.
	 */
	@Test
	public final void testSetCompatibility() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getLength()}.
	 */
	@Test
	public final void testGetLength() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setLength(int)}.
	 */
	@Test
	public final void testSetLength() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getTransportUnits()}.
	 */
	@Test
	public final void testGetTransportUnits() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setTransportUnits(java.util.Set)}.
	 */
	@Test
	public final void testSetTransportUnits() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getTypePlacingRules()}.
	 */
	@Test
	public final void testGetTypePlacingRules() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setTypePlacingRules(java.util.Set)}.
	 */
	@Test
	public final void testSetTypePlacingRules() {
		fail("Not yet implemented"); // TODO
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

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getWeightTare()}.
	 */
	@Test
	public final void testGetWeightTare() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setWeightTare(java.math.BigDecimal)}.
	 */
	@Test
	public final void testSetWeightTare() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#getWeightMax()}.
	 */
	@Test
	public final void testGetWeightMax() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.openwms.domain.common.TransportUnitType#setWeightMax(java.math.BigDecimal)}.
	 */
	@Test
	public final void testSetWeightMax() {
		fail("Not yet implemented"); // TODO
	}

}
