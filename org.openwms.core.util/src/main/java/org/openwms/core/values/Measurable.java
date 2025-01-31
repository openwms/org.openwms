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
package org.openwms.core.values;

/**
 * A Measurable.
 *
 * @author Heiko Scherrer
 */
public interface Measurable<V extends Number, E extends Measurable<V, E, T>, T extends BaseUnit<T>> extends Comparable<E> {

    /**
     * Returns the type of {@code Measurable}.
     *
     * @return The {@code Measurable}'s type
     */
    T getUnitType();

    /**
     * Get the magnitude of this {@code Measurable}.
     *
     * @return the magnitude
     */
    V getMagnitude();

    /**
     * Check whether the magnitude is 0.
     *
     * @return {@literal true} is magnitude is 0, otherwise {@literal false}
     */
    boolean isZero();

    /**
     * Check whether the magnitude is of negative value.
     *
     * @return {@literal true} if the magnitude is of negative value, otherwise
     * {@literal false}
     */
    boolean isNegative();

    /**
     * Convert this {@code Measurable} into another {@code Measurable} .
     *
     * @param unit The {@code BaseUnit} to convert to
     * @return The converted unit
     */
    E convertTo(T unit);
}