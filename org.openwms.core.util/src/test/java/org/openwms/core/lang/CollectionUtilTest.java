/*
 * Copyright 2005-2023 the original author or authors.
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

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * A CollectionUtilTest.
 *
 * @author Heiko Scherrer
 */
class CollectionUtilTest {

    @Test void testGetFirstOrNullWithNull() {
        assertNull(CollectionUtil.getFirstOrNull(null), "Calling with null should result in null");
        assertNull(CollectionUtil.getFirstOrNull(Collections.emptyList()), "Calling with an empty list shall result in null");
    }

    @Test void testGetFirstOrNull() {
        var strings = Arrays.asList("1", "2", "3");
        assertEquals("1", CollectionUtil.getFirstOrNull(strings), "Calling with an list should result in the first element");
    }

    @Test void testAsHashMapWithNull() {
        var strings = Arrays.asList("1", "2", "3");
        assertEquals(Collections.EMPTY_MAP, CollectionUtil.asHashMap(null, null), "Calling with null should result in an empty map");
        assertEquals(Collections.EMPTY_MAP, CollectionUtil.asHashMap(Collections.emptyList(), null), "Calling with an empty list shall result in an empty map");
        assertEquals(Collections.EMPTY_MAP, CollectionUtil.asHashMap(strings, null), "Calling with a list and no extractor shall result in an empty map");
    }
}