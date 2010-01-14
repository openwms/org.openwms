/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms;

import org.springframework.test.jpa.AbstractJpaTests;

/**
 * A AbstractJpaSpringContextTests.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public abstract class AbstractJpaSpringContextTests extends AbstractJpaTests {

	@Override
	protected boolean shouldUseShadowLoader() {
		return false;
	}

	@Override
	protected String[] getConfigLocations() {
		return new String[] {
				"classpath:**/*Test-Infrastructure-context.xml",
				"classpath:"
						+ this.getClass().getPackage().getName().replace('.',
								'/') + "/" + this.getClass().getSimpleName()
						+ "-context.xml" };
	}

}
