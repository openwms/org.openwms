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

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * An EmailTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class EmailTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Test construction of Email entities.
     */
    @Test
    public final void testCreationNegative() {
        thrown.expect(IllegalArgumentException.class);
        new Email(null, null);
    }

    /**
     * Test construction of Email entities.
     */
    @Test
    public final void testCreationNegative2() {
        thrown.expect(IllegalArgumentException.class);
        new Email("Test", null);
    }

    /**
     * Test construction of Email entities.
     */
    @Test
    public final void testCreationNegative3() {
        thrown.expect(IllegalArgumentException.class);
        new Email(null, "force something, still no mail check");
    }
}