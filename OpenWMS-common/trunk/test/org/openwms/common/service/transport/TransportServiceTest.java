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

/**
 * A TransportServiceTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportServiceTest extends AbstractSpringContextTests {

    protected static final Log LOG = LogFactory.getLog(TransportServiceTest.class);

    public void testMoveTransportUnit() {
	TransportService service = (TransportService) getApplicationContext().getBean("entityDao");

    }
}
