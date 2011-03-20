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
package org.openwms.core.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A TestHelper.
 * <p>
 * A helper class responsible for starting an embedded database. Implemented as
 * Singleton [Gof] pattern.
 * </p>
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Deprecated
public final class Helper {

    /**
     * Name of the PersistenceUnit used for the unit tests.
     */
    public static final String PERSISTENCE_UNIT_DURABLE = "OpenWMS-test-durable";
    private static String persistenceUnit = "OpenWMS-test";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Helper helper;
    private Connection connection;
    private boolean dbStarted = false;

    private Helper() {};

    /**
     * Get Singleton instance.
     * 
     * @return - The Singleton instance
     */
    public synchronized static Helper getInstance() {
        return helper == null ? new Helper() : helper;
    }

    /**
     * Start the in-memory HSQL database.
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
     * @return - true: If the database is started<br>
     *         - false: If the database is stopped
     */
    public boolean isDbStarted() {
        return dbStarted;
    }

    /**
     * Return the name of the PersistenceUnit to use for tests.
     * 
     * @return - The name of the PersistenceUnit
     */
    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    /**
     * Check whether the used PersistenceUnit is durable.
     * 
     * @return - true: if a database installation is used<br>
     *         - false: if an embedded database engine is used
     */
    public boolean isPersistenceUnitDurable() {
        return (PERSISTENCE_UNIT_DURABLE.equals(getPersistenceUnit()));
    }

    /**
     * Create a EntityManagerFactory.
     * 
     * @param pUnit
     *            - Name of the PersistenceUnit
     * @return - EntityManagerFactory instance
     */
    public EntityManagerFactory createEntityManagerFactory(String pUnit) {
        return Persistence.createEntityManagerFactory(pUnit);
    }

}
