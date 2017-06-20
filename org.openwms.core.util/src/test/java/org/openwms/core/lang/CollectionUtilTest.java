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
package org.openwms.core.lang;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * A CollectionUtilTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class CollectionUtilTest {

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#getFirstOrNull(java.util.List)} .
     */
    @Test
    public final void testGetFirstOrNullWithNull() {
        assertNull("Calling with null should result in null", CollectionUtil.getFirstOrNull(null));
        assertNull("Calling with an empty list shall result in null",
                CollectionUtil.getFirstOrNull(Collections.emptyList()));
    }

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#getFirstOrNull(java.util.List)} .
     */
    @Test
    public final void testGetFirstOrNull() {
        List<String> strings = Arrays.asList(new String[] { "1", "2", "3" });
        assertEquals("Calling with an list should result in the first element", "1",
                CollectionUtil.getFirstOrNull(strings));
    }

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#asHashMap(java.util.List, org.openwms.core.lang.ListExtractor)}
     * .
     */
    @Test
    public final void testAsHashMapWithNull() {
        List<String> strings = Arrays.asList(new String[] { "1", "2", "3" });
        assertEquals("Calling with null should result in an empty map", Collections.EMPTY_MAP,
                CollectionUtil.asHashMap(null, null));
        assertEquals("Calling with an empty list shall result in an empty map", Collections.EMPTY_MAP,
                CollectionUtil.asHashMap(Collections.emptyList(), null));
        assertEquals("Calling with a list and no extractor shall result in an empty map", Collections.EMPTY_MAP,
                CollectionUtil.asHashMap(strings, null));
    }

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#asHashMap(java.util.List, org.openwms.core.lang.ListExtractor)}
     * .
     */
    @Test
    public final void testAsHashMap() {
        // TODO [scherrer] : Fix the implementation of the ugly ListExtractor
        // interface, it is not convenient to use and complete the test
    }
}