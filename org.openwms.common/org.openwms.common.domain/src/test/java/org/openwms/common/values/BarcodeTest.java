/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.values;

import static org.junit.Assert.*;

import org.junit.Test;
import org.openwms.common.values.Barcode.BARCODE_ALIGN;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A BarcodeTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class BarcodeTest {

    /**
     * Logger instance can be used by subclasses.
     */
    private static final Logger logger = LoggerFactory.getLogger(BarcodeTest.class);

    /**
     * Test Barcode instantiation with <code>null</code>.
     */
    @Test
    public final void testBarcodeWithNull() {
        try {
            new Barcode(null);
            fail("NOK:Barcode cannot instanciated with NULL");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Not allowed to initiante a Barcode with null");
        }
    }

    /**
     * Test basic behavior of the Barcode class.
     */
    @Test
    public final void testBarcode() {
        new Barcode("TEST");

        Barcode.setLength(20);
        Barcode.setPadder('0');

        Barcode bc3 = new Barcode("RIGHT");
        logger.debug("Test left-padded, right-aligned:[" + bc3 + "]");
        assertTrue("Barcode length must be expanded to 20 characters.", (20 == Barcode.getLength()));
        assertTrue("Barcode must start with 0", bc3.toString().startsWith("0"));

        Barcode.setAlignment(BARCODE_ALIGN.LEFT);
        Barcode bc2 = new Barcode("LEFT");
        logger.debug("Test right-padded, left-aligned:[" + bc2 + "]");
        assertTrue("Barcode must end with 0", bc2.toString().endsWith("0"));
        assertTrue("Barcode must start with LEFT", bc2.toString().startsWith("LEFT"));

        Barcode.setLength(2);
        Barcode bc4 = new Barcode("A123456789");
        logger.debug("Test not-padded:[" + bc4 + "]");
        assertTrue("Barcode must end with 9", bc4.toString().endsWith("9"));
        assertTrue("Barcode must start with A", bc4.toString().startsWith("A"));
    }
}
