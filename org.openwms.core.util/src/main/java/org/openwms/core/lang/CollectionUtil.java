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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A CollectionUtil.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: 1409 $
 * @since 0.1
 */
public final class CollectionUtil {

    private CollectionUtil() {}

    /**
     * Return the first element of list. If list is <code>null</code> of empty, <code>null</code> is returned.
     * 
     * @param <T>
     *            Any type
     * @param list
     *            The {@link List}
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
     * @param <K>
     *            Type of list key
     * @param <V>
     *            Type of list value
     * @param list
     *            The list to convert
     * @param extractor
     *            To determine key and value for each list entry
     * @return A created {@link LinkedHashMap} or an empty map when one of the arguments is <code>null</code>
     */
    public static <K, V> Map<K, V> asHashMap(List<V> list, ListExtractor<K, V> extractor) {
        if (list == null || extractor == null || list.isEmpty()) {
            return Collections.<K, V> emptyMap();
        }
        Map<K, V> map = new LinkedHashMap<K, V>(list.size());
        for (V node : list) {
            map.put(extractor.extractKey(node), extractor.extractValue(node));
        }
        return map;
    }
}