/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * A AbstractSpringContextTests.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class AbstractSpringContextTests extends
		AbstractDependencyInjectionSpringContextTests {

	private String commonTestPackage = "classpath:org/openwms/common/**/*-test-cfg.xml";

	protected String[] getConfigLocations() {
		String[] loc= new String[] {
				"classpath:"
						+ this.getClass().getPackage().getName().replace('.',
								'/') + "/**/*-test-cfg.xml", commonTestPackage };
		return loc;
	}

}
