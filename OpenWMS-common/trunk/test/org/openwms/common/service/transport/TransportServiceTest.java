/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.transport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.AbstractSpringContextTests;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.service.ITransportService;

/**
 * A TransportServiceTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportServiceTest extends AbstractSpringContextTests {

    protected static final Log LOG = LogFactory.getLog(TransportServiceTest.class);

    public void testCreateTransportUnit() {
	ITransportService service = (ITransportService) getApplicationContext().getBean("transportService");
	service.createTransportUnit(new Barcode("4711"), new TransportUnitType("TestType"),new LocationPK("AREA", "AISLE", "X", "Y", "Z"));
    }
}
