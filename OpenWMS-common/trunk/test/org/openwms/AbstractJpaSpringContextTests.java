/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import javax.sql.DataSource;

import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * A AbstractJpaSpringContextTests.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public abstract class AbstractJpaSpringContextTests extends AbstractJpaTests {

    private String commonTestPackage = "classpath:org/openwms/common/**/*-test-cfg.xml";
    //private static String TEST_DATA_FILE = "load-testData.sql";

    protected String[] getConfigLocations() {
	String[] loc = new String[] {
		"classpath:" + this.getClass().getPackage().getName().replace('.', '/') + "/**/*-test-cfg.xml",
		commonTestPackage };
	return loc;
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
	logger.info("*** Inserting test data ***");
	// Use spring to get the datasource
	DataSource ds = this.jdbcTemplate.getDataSource();
	Connection conn = ds.getConnection();
	try {
	    IDatabaseConnection connection = new DatabaseConnection(conn);
	    connection.getConfig().setFeature(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
	    DatabaseOperation.INSERT.execute(connection, new FlatXmlDataSet(getTestDataFileAsResource().getFile()));
	}
	finally {
	    DataSourceUtils.releaseConnection(conn, ds);
	    logger.info("*** Finished inserting test data ***");
	}
	super.onSetUpInTransaction();
    }

    @Override
    protected void onTearDown() throws Exception {
	// Commit or rollback the transaction
	endTransaction();

	// Delete the data
	DataSource ds = this.jdbcTemplate.getDataSource();
	Connection conn = ds.getConnection();
	try {
	    IDatabaseConnection connection = new DatabaseConnection(conn);
	    connection.getConfig().setFeature(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
	    DatabaseOperation.DELETE.execute(connection, new FlatXmlDataSet(new FileInputStream(
		    getTestDataFileAsResource().getFile())));
	}
	finally {
	    DataSourceUtils.releaseConnection(conn, ds);
	    logger.info("*** Finished removing test data ***");
	}
    }

    protected String getTestDataFile() {
	return ""; // FIXME:Dont return null;
    }

    protected Resource getTestDataFileAsResource() throws IOException {
	//FIXME: Name path in ant style! MUST
	return getApplicationContext().getResource("classpath:org/openwms/" + getTestDataFile());
    }

}
