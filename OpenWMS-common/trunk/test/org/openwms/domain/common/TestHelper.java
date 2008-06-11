/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * A TestHelper.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class TestHelper {

	public static final String PERSISTENCE_UNIT_DURABLE = "OpenWMS-test-durable";
	private static final Log LOG = LogFactory.getLog(TestHelper.class);
	private static Connection connection;
	private static boolean dbStarted = false;
	private static String persistenceUnit = "OpenWMS-test-durable";
	private static EntityManagerFactory emf;

	private TestHelper() {
	};

	public static void initializeTestCase() {
		DOMConfigurator.configure("src/META-INF/log4j.xml");
	}

	public static void startDb() {
		try {
			LOG.info("Starting in-memory HSQL database for unit tests");
			Class.forName("org.hsqldb.jdbcDriver");
			connection = DriverManager.getConnection("jdbc:hsqldb:mem:openwms", "sa", "");
			dbStarted = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Exception during HSQL database startup.");
		}
	}

	public static void stopDb() {
		LOG.info("Stopping in-memory HSQL database.");
		try {
			connection.createStatement().execute("SHUTDOWN");
			dbStarted = false;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Exception during HSQL database shutdown.");
		}
	}

	public static boolean isDbStarted() {
		return dbStarted;
	}

	public static String getPersistenceUnit() {
		return persistenceUnit;
	}

	public static boolean isPersistenceUnitDurable() {
		return (PERSISTENCE_UNIT_DURABLE.equals(getPersistenceUnit()));
	}

	public static EntityManagerFactory createEntityManagerFactory(String pUnit) {
		//if (emf == null) {
			emf = Persistence.createEntityManagerFactory(pUnit);
		//}
		return emf;
	}

}
