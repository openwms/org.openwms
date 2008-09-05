/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

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

    private ITransportService transportService = null;
    private String testDataFile = "load-TransportUnits.sql";

    // Spring IoC
    public void setTransportService(ITransportService transportService) {
	this.transportService = transportService;
    }

    @Override
    protected String getTestDataFile() {
	return this.testDataFile;
    }

    @Test
    public void testCreateTransportUnit() {
	transportService.moveTransportUnit(new Barcode("4711"), new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
    }

}
