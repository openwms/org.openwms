/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * A TestHelper.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class TestHelper {

    public static final String PERSISTENCE_UNIT_DURABLE = "OpenWMS-test-durable";
    private static String persistenceUnit = "OpenWMS-test";
    private Log logger = LogFactory.getLog(TestHelper.class);
    private static TestHelper helper;
    private Connection connection;
    private boolean dbStarted = false;

    private TestHelper() {};

    /**
     * Get Singleton instance.
     * 
     * @return
     */
    public synchronized static TestHelper getInstance() {
	return helper == null ? new TestHelper() : helper;
    }

    /**
     * Start the in-memory HSQL database.
     * 
     */
    public void startDb() {
	if (dbStarted) {
	    return;
	}
	logger.info("Starting in-memory HSQL database for unit tests");
	try {
	    Class.forName("org.hsqldb.jdbcDriver");
	    connection = DriverManager.getConnection("jdbc:hsqldb:mem:openwms", "sa", "");
	    dbStarted = true;
	}
	catch (Exception ex) {
	    throw new RuntimeException("Exception during HSQL database startup.");
	}
    }

    /**
     * Stop the in-memory HSQL database.
     * 
     */
    public void stopDb() {
	if (!dbStarted) {
	    return;
	}
	logger.info("Stopping in-memory HSQL database.");
	try {
	    connection.createStatement().execute("SHUTDOWN");
	}
	catch (Exception ex) {
	    throw new RuntimeException("Exception during HSQL database shutdown.");
	}
	finally {
	    try {
		connection.close();
	    }
	    catch (SQLException e) {
		logger.error("Error closing connection", e);
	    }
	    dbStarted = false;
	}
    }

    /**
     * Is the HSQL database started ?
     * 
     * @return
     */
    public boolean isDbStarted() {
	return dbStarted;
    }

    /**
     * Return the name of the PersistenceUnit to use for tests.
     * 
     * @return
     */
    public String getPersistenceUnit() {
	return persistenceUnit;
    }

    /**
     * Check whether the used PersistenceUnit is durable.
     * 
     * @return
     */
    public boolean isPersistenceUnitDurable() {
	return (PERSISTENCE_UNIT_DURABLE.equals(getPersistenceUnit()));
    }

    /**
     * Create a EntityManagerFactory.
     * 
     * @param pUnit
     * @return
     */
    public EntityManagerFactory createEntityManagerFactory(String pUnit) {
	return Persistence.createEntityManagerFactory(pUnit);
    }

}
