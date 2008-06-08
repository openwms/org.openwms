/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common;

import org.apache.log4j.xml.DOMConfigurator;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		DOMConfigurator.configure("src/META-INF/log4j.xml");
		TestHelper.startDb();

		TestSuite suite = new TestSuite("Test for org.openwms.domain.common");
		// $JUnit-BEGIN$
		suite.addTestSuite(TransportUnitTypeTest.class);
		suite.addTestSuite(TransportUnitTest.class);
		suite.addTestSuite(LocationTest.class);

		// $JUnit-END$
		TestHelper.stopDb();
		return suite;
	}

}
