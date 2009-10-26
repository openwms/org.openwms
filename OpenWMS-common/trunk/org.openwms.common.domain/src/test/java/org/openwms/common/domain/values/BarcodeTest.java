/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.values;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openwms.common.domain.values.Barcode.BARCODE_ALIGN;

/**
 * 
 * A BarcodeTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 */
public class BarcodeTest {

	/**
	 * Test Barcode instantiation with null
	 */
	@Test(expected = IllegalArgumentException.class)
	public final void testBarcodeWithNull() throws IllegalArgumentException {
			new Barcode(null);
			fail("OK:Barcode cannot instanciated with NULL");
	}

	/**
	 * Test basic behaviour of the Barcode class.
	 * 
	 */
	@Test
	public final void testBarcode() {
		Barcode bc = new Barcode("TEST");
		System.out.println("Test Barcode:" + bc);

		Barcode.setLength(20);
		Barcode.setPadder('0');

		Barcode bc3 = new Barcode("RIGHT");
		System.out.println("Test left-padded, right-aligned:[" + bc3 + "]");
		assertTrue("Barcode length must be expanded to 20 characters.", (20 == Barcode.getLength()));
		assertTrue("Barcode must start with 0", bc3.toString().startsWith("0"));

		Barcode.setAlignment(BARCODE_ALIGN.LEFT);
		Barcode bc2 = new Barcode("LEFT");
		System.out.println("Test right-padded, left-aligned:[" + bc2 + "]");
		assertTrue("Barcode must end with 0", bc2.toString().endsWith("0"));
		assertTrue("Barcode must start with LEFT", bc2.toString().startsWith("LEFT"));

		Barcode.setLength(2);
		Barcode bc4 = new Barcode("A123456789");
		System.out.println("Test not-padded:[" + bc4 + "]");
		assertTrue("Barcode must end with 9", bc4.toString().endsWith("9"));
		assertTrue("Barcode must start with A", bc4.toString().startsWith("A"));
	}
}
