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
package org.openwms.core.uaa;

import org.junit.Assert;
import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * An EmailTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class EmailTest extends AbstractJpaSpringContextTests {

    /**
     * Test construction of Email entities.
     */
    @Test
    public final void testCreationNegative() {
        try {
            new Email(null, null);
            Assert.fail("Not allowed to create an Email without user and address");
        } catch (IllegalArgumentException iae) {}
        try {
            new Email("Test", null);
            Assert.fail("Not allowed to create an Email without address");
        } catch (IllegalArgumentException iae) {}
        try {
            new Email(null, "force something, still no mail check");
            Assert.fail("Not allowed to create an Email without user");
        } catch (IllegalArgumentException iae) {}
    }
}