/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.dao.GenericDAO;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.TransportService;
import org.springframework.test.context.ContextConfiguration;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@ContextConfiguration
public final class TransportServiceTest extends AbstractJpaSpringContextTests {

    private TransportService transportService;
    private GenericDAO<Location, Long> locationDao;
    private String testDataFile = "load-TransportUnits.sql";

    // Spring IoC
    public void setTransportService(TransportService transportService) {
	this.transportService = transportService;
    }

    public void setLocationDao(GenericDAO<Location, Long> locationDao) {
	this.locationDao = locationDao;
    }

    @Override
    protected String getTestDataFile() {
	// return null;
	// To load testdata return the filename of the DBUnit file
	return null;// this.testDataFile;
    }

    @Test
    public void testCreateTransportUnit() {
	// FIXME: complete!
	LocationPK locationPk = new LocationPK("AREA", "AISLE", "X", "Y", "Z");
	//Location actualLocation = new Location(locationPk);
	//locationDao.persist(actualLocation);

	transportService.createTransportUnit(new Barcode("4711"), new TransportUnitType("TestType"), locationPk);
	// FIXME: transportService.moveTransportUnit(new Barcode("4711"), new LocationPK("AREA", "AISLE", "X", "Y",
	// "Z"));
    }

}
