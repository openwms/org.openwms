/*
 * Copyright 2005-2025 the original author or authors.
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

import java.util.Objects;

/**
 * A Triple.
 *
 * @author Heiko Scherrer
 */
public record Triple<K, V, T>(K key, V value, T type) {

    /**
     * Get the stored value as class type {@code clazz}.
     *
     * @param clazz The type to retrieve the value as
     * @return The cast value
     * @param <U> The requested type
     * @throws ClassCastException in case of cast error
     */
    @SuppressWarnings("unchecked")
    public <U> U valueAs(Class<U> clazz) {
        if (Objects.equals(this.type.getClass().getComponentType(), clazz.getComponentType())) {
            return (U) value;
        }
        throw new ClassCastException("Cannot case requested type [%s] from [%s]".formatted(clazz, type.getClass()));
    }
}
