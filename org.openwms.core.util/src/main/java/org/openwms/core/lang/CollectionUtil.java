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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A CollectionUtil.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public final class CollectionUtil {

    private CollectionUtil() {
    }

    /**
     * Return the first element of list. If list is <code>null</code> of empty, <code>null</code> is returned.
     *
     * @param <T> Any type
     * @param list The {@link List}
     * @return list[0] or <code>null</code> if list == null || list.isEmpty
     */
    public static <T> T getFirstOrNull(List<T> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * Convert a list into a {@link LinkedHashMap} using a {@link ListExtractor} to determine the keys and values for the new Map.
     *
     * @param <K> Type of list key
     * @param <V> Type of list value
     * @param list The list to convert
     * @param extractor To determine key and value for each list entry
     * @return A created {@link LinkedHashMap} or an empty map when one of the arguments is <code>null</code>
     */
    public static <K, V> Map<K, V> asHashMap(List<V> list, ListExtractor<K, V> extractor) {
        if (list == null || extractor == null || list.isEmpty()) {
            return Collections.<K, V>emptyMap();
        }
        Map<K, V> map = new LinkedHashMap<>(list.size());
        for (V node : list) {
            map.put(extractor.extractKey(node), extractor.extractValue(node));
        }
        return map;
    }
}