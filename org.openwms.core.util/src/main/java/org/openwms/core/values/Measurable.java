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
package org.openwms.core.values;

/**
 * A Measurable.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface Measurable<V extends Number, E extends Measurable<V, E, T>, T extends BaseUnit<T>> extends Comparable<E> {

    /**
     * Returns the type of <code>Measurable</code>.
     *
     * @return The <code>Measurable</code>'s type
     */
    T getUnitType();

    /**
     * Get the magnitude of this <code>Measurable</code>.
     *
     * @return the magnitude
     */
    V getMagnitude();

    /**
     * Check whether the magnitude is 0.
     *
     * @return <code>true</code> is magnitude is 0, otherwise <code>false</code>
     */
    boolean isZero();

    /**
     * Check whether the magnitude is of negative value.
     *
     * @return <code>true</code> if the magnitude is of negative value, otherwise <code>false</code>
     */
    boolean isNegative();

    /**
     * Convert this <code>Measurable</code> into another <code>Measurable</code> .
     *
     * @param unit The <code>BaseUnit</code> to convert to
     */
    E convertTo(T unit);
}