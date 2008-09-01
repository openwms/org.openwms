/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.ITransportService;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportServiceTest extends AbstractJpaSpringContextTests {

    protected static final Log LOG = LogFactory.getLog(TransportServiceTest.class);
    private IDatabaseConnection connection;
    private static ITransportService service;

    public TransportServiceTest() {

	IDatabaseConnection s = (IDatabaseConnection) getApplicationContext().getBean("databaseConnection");
	
	IDataSet dataSet = new FlatXmlDataSet(
		    this.getClass().getResource(
		    "/junitbook/database/data.xml"));

	try
	{
	    DatabaseOperation.CLEAN_INSERT.execute(connection,
	        dataSet);
	}
	finally
	{
	    connection.close();
	}

	service = (ITransportService) getApplicationContext().getBean("transportService");
    }

    @Test
    public void testCreateTransportUnit() {
	service.moveTransportUnit(new Barcode("4711"), new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
    }
}
