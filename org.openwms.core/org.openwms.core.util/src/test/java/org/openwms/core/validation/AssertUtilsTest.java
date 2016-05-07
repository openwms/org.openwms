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
package org.openwms.core.validation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A AssertUtilsTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class AssertUtilsTest {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#notNull(java.lang.Object, java.lang.String)} .
     */
    @Test
    public final void testNotNullObjectStringWithNull() {
        try {
            AssertUtils.notNull(null, "Message text");
            fail("Must throw an IllegalArgumentException when calling with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException when calling with null");
            assertEquals("Message text must be passed", "Message text", iae.getMessage());
        }
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#notNull(java.lang.Object, java.lang.String)} .
     */
    @Test
    public final void testNotNullObjectString() {
        AssertUtils.notNull(new Object(), "Message text");
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#notNull(java.lang.Object)} .
     */
    @Test
    public final void testNotNullObjectWithNull() {
        try {
            AssertUtils.notNull(null);
            fail("Must throw an IllegalArgumentException when calling with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException when calling with null");
            assertEquals("Default text has to be wrapped", "Argument must not be null", iae.getMessage());
        }
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#notNull(java.lang.Object)} .
     */
    @Test
    public final void testNotNullObject() {
        AssertUtils.notNull(new Object());
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#isNotEmpty(java.lang.String, java.lang.String)} .
     */
    @Test
    public final void testIsNotEmptyStringStringEmpty() {
        try {
            AssertUtils.isNotEmpty("", "Message text");
            fail("Must throw an IllegalArgumentException when calling with an empty String");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException when calling with an empty String");
            assertEquals("Message text must be passed", "Message text", iae.getMessage());
        }
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#isNotEmpty(java.lang.String, java.lang.String)} .
     */
    @Test
    public final void testIsNotEmptyStringStringNull() {
        try {
            AssertUtils.isNotEmpty(null, "Message text");
            fail("Must throw an IllegalArgumentException when calling with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException when calling with null");
            assertEquals("Message text must be passed", "Message text", iae.getMessage());
        }
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#isNotEmpty(java.lang.String, java.lang.String)} .
     */
    @Test
    public final void testIsNotEmptyStringString() {
        AssertUtils.isNotEmpty("Hello", null);
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#isNotEmpty(java.lang.String)} .
     */
    @Test
    public final void testIsNotEmptyStringEmpty() {
        try {
            AssertUtils.isNotEmpty("");
            fail("Must throw an IllegalArgumentException when calling with an empty String");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException when calling with an empty String");
            assertEquals("Default text has to be wrapped", "The String must not be empty nor be null", iae.getMessage());
        }
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#isNotEmpty(java.lang.String)} .
     */
    @Test
    public final void testIsNotEmptyStringNull() {
        try {
            AssertUtils.isNotEmpty(null);
            fail("Must throw an IllegalArgumentException when calling with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: IllegalArgumentException when calling with null");
            assertEquals("Default text has to be wrapped", "The String must not be empty nor be null", iae.getMessage());
        }
    }

    /**
     * Test method for {@link org.openwms.core.validation.AssertUtils#isNotEmpty(java.lang.String)} .
     */
    @Test
    public final void testIsNotEmptyString() {
        AssertUtils.isNotEmpty("Hello");
    }
}