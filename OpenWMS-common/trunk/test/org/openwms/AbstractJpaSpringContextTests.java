/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms;

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
    private Resource fileResource = null;
    private DataSource dataSource = null;
    private Connection connection = null;

    // private static String TEST_DATA_FILE = "load-testData.sql";

    protected String[] getConfigLocations() {
	String[] loc = new String[] {
		"classpath:" + this.getClass().getPackage().getName().replace('.', '/') + "/**/*-test-cfg.xml",
		commonTestPackage };
	return loc;
    }

    @Override
    protected void onSetUpInTransaction() throws Exception {
	dataSource = this.jdbcTemplate.getDataSource();
	fileResource = getTestDataFileAsResource();
	fileResource = (fileResource == null) ? null : populateTestData(fileResource, DatabaseOperation.INSERT);
	super.onSetUpInTransaction();
    }

    @Override
    protected void onTearDown() throws Exception {
	endTransaction();
	fileResource = (fileResource == null) ? null : populateTestData(fileResource, DatabaseOperation.DELETE);
    }

    private Resource populateTestData(Resource fileResource, DatabaseOperation dbOp) throws Exception {
	connection = dataSource.getConnection();
	try {
	    IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
	    dbUnitConnection.getConfig().setFeature(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, true);
	    dbOp.execute(dbUnitConnection, new FlatXmlDataSet(fileResource.getFile()));
	}
	finally {
	    DataSourceUtils.releaseConnection(connection, dataSource);
	    logger.info("*** Finished inserting/deleting test data ***");
	}
	return fileResource;
    }

    protected String getTestDataFile() {
	return "";
    }

    protected Resource getTestDataFileAsResource() throws IOException {
	// FIXME: Name path in ant style! MUST
	Resource fileResource = getApplicationContext().getResource("classpath:org/openwms/" + getTestDataFile());
	return fileResource.exists() ? fileResource : null;
    }

}
