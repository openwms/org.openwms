/*
 * Copyright 2018 Heiko Scherrer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
 */
public class CollectionUtilTest {

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#getFirstOrNull(java.util.List)} .
     */
    @Test
    public final void testGetFirstOrNullWithNull() {
        assertNull("Calling with null should result in null", CollectionUtil.getFirstOrNull(null));
        assertNull("Calling with an empty list shall result in null", CollectionUtil.getFirstOrNull(Collections.emptyList()));
    }

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#getFirstOrNull(java.util.List)} .
     */
    @Test
    public final void testGetFirstOrNull() {
        List<String> strings = Arrays.asList(new String[]{"1", "2", "3"});
        assertEquals("Calling with an list should result in the first element", "1", CollectionUtil.getFirstOrNull(strings));
    }

    /**
     * Test method for {@link org.openwms.core.lang.CollectionUtil#asHashMap(java.util.List, org.openwms.core.lang.ListExtractor)}
     * .
     */
    @Test
    public final void testAsHashMapWithNull() {
        List<String> strings = Arrays.asList(new String[]{"1", "2", "3"});
        assertEquals("Calling with null should result in an empty map", Collections.EMPTY_MAP, CollectionUtil.asHashMap(null, null));
        assertEquals("Calling with an empty list shall result in an empty map", Collections.EMPTY_MAP, CollectionUtil.asHashMap(Collections.emptyList(), null));
        assertEquals("Calling with a list and no extractor shall result in an empty map", Collections.EMPTY_MAP, CollectionUtil.asHashMap(strings, null));
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