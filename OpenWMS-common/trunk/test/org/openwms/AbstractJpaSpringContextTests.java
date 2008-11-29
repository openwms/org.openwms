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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.jpa.AbstractJpaTests;

/**
 * A AbstractJpaSpringContextTests.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@ContextConfiguration
public abstract class AbstractJpaSpringContextTests extends AbstractJpaTests {

    private String commonTestPackage = "classpath:org/openwms/common/**/*-test-cfg.xml";
    private static final String DEFAULT_FILE = "";
    private Resource fileResource = null;
    private DataSource dataSource = null;

    // private static String TEST_DATA_FILE = "load-testData.sql";

    protected String[] getConfigLocations() {
	String[] loc = new String[] {
		"classpath:" + this.getClass().getPackage().getName().replace('.', '/') + "/**/*-test-cfg.xml",
		commonTestPackage };
	return loc;
    }

    @Override
    protected boolean shouldUseShadowLoader() {
	return false;
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
	Connection connection = dataSource.getConnection();
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

    /**
     * Override this method to load test data with dbUnit.
     * 
     * @return
     */
    protected String getTestDataFile() {
	return DEFAULT_FILE;
    }

    /**
     * Calls {@link getTestDataFile} and tries to resolve the resource name within the package where the test class
     * exists.
     * 
     * @return
     * @throws IOException
     */
    protected Resource getTestDataFileAsResource() throws IOException {
	Resource fileResource = getApplicationContext().getResource(
		"classpath:" + this.getClass().getPackage().getName().replace('.', '/') + "/" + getTestDataFile());
	return fileResource.exists() && getTestDataFile() != DEFAULT_FILE ? fileResource : null;
    }

}
