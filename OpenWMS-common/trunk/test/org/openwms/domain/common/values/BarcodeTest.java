/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.values;

import junit.framework.TestCase;

import org.junit.Test;
import org.openwms.domain.common.values.Barcode;
import org.openwms.domain.common.values.Barcode.BARCODE_ALIGN;

/**
 * A BarcodeTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public class BarcodeTest extends TestCase{

	@Test
	public final void testBarcode() {
		Barcode bc = new Barcode("TEST");
		System.out.println("Test Barcode:"+bc);
		
		Barcode.setLength(20);
		Barcode.setPadder('0');
		
		Barcode bc2 = new Barcode("LEFT");
		System.out.println("Test left-padded:["+bc2+"]");
		assertTrue("Barcode length must be expanded to 20 characters.", (20 == bc2.getLength()));
		assertTrue("Barcode must start with 0",bc2.toString().startsWith("0"));
		
		Barcode.setAlignment(BARCODE_ALIGN.RIGHT);
		Barcode bc3 = new Barcode("RIGHT");
		System.out.println("Test right-padded:["+bc3+"]");
		assertTrue("Barcode length must be expanded to 20 charcters.", (20 == bc2.getLength()));
		assertTrue("Barcode must end with 0",bc3.toString().endsWith("0"));

		Barcode.setLength(2);
		Barcode bc4 = new Barcode("A1234567890123456789");
		System.out.println("Test right-padded:["+bc4+"]");
		assertTrue("Barcode must end with 9",bc4.toString().endsWith("9"));
		assertTrue("Barcode must start with A",bc4.toString().startsWith("A"));
}

}
