/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.junit.Test;
import org.openwms.AbstractJpaSpringContextTests;
import org.openwms.common.dao.GenericDao;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.TransportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@ContextConfiguration
public final class TransportServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    protected TransportService transportService;
    @Qualifier("locationDao")
    @Autowired
    protected GenericDao<Location, Long> locationDao;
    @Qualifier("transportUnitTypeDao")
    @Autowired
    protected GenericDao<TransportUnitType, String> transportUnitTypeDao;
    @Qualifier("transportUnitDao")
    @Autowired
    protected GenericDao<TransportUnit, Long> transportUnitDao;
    private String testDataFile = "load-TransportUnits.sql";
    LocationPK locationPk;
    LocationPK targetLocation;
    TransportUnitType transportUnitType = new TransportUnitType("TestType");

    @Override
    protected void onSetUpInTransaction() throws Exception {
	super.onSetUpInTransaction();
	locationPk = new LocationPK("AREA", "AISLE", "X", "Y", "Z");
	targetLocation = new LocationPK("TARGET", "TARGET", "TARGET", "TARGET", "TARGET");
	locationDao.persist(new Location(locationPk));
	locationDao.persist(new Location(targetLocation));
	transportUnitTypeDao.persist(transportUnitType);
    }

    @Override
    protected String getTestDataFile() {
	return null;
    }

    @Test
    public void testCreateTransportUnit() {
	TransportUnit transportUnit = transportService.createTransportUnit(new Barcode("4711"), transportUnitType,
		locationPk);
	transportService.moveTransportUnit(transportUnit.getBarcode(), new LocationPK("TARGET1", "TARGET", "TARGET",
		"TARGET", "TARGET"));
    }
}
