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

import java.io.Serializable;
import java.util.Arrays;

/**
 * A PriorityLevel is used to prioritize arbitrary kind of orders.
 * 
 * @author Heiko Scherrer
 */
public enum PriorityLevel implements Serializable {

    /** Lowest priority. */
    LOWEST(10),

    /** Low priority. */
    LOW(20),

    /** Standard priority. */
    NORMAL(30),

    /** High priority. */
    HIGH(40),

    /** Highest priority. */
    HIGHEST(50);

    private int order;

    /** Framework constructor.*/
    PriorityLevel() {
    }

    /**
     * Return the {@code PriorityLevel} of the given {@code priority}.
     *
     * @param priority String value
     * @return Enum value
     * @throws IllegalArgumentException if String value does not match one of the enum values
     */
    public static PriorityLevel of(String priority) {
        return Arrays.stream(PriorityLevel.values())
                .filter(p -> p.name().equals(priority))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("A priority level of %s is not defined", priority)));
    }

    /**
     * Convert the priority level as integer range into the corresponding enumeration.
     *
     * @param priority Integer mapped to ranges
     * @return The PriorityLevel
     */
    public static PriorityLevel convert(int priority) {
        if (priority < 11) {
            return PriorityLevel.LOWEST;
        } else if (priority < 21) {
            return PriorityLevel.LOW;
        } else if (priority < 31) {
            return PriorityLevel.NORMAL;
        } else if (priority < 41) {
            return PriorityLevel.HIGH;
        } else {
            return PriorityLevel.HIGHEST;
        }
    }

    /**
     * Initializing constructor.
     *
     * @param order The order
     */
    PriorityLevel(int order) {
        this.order = order;
    }

    /**
     * Get the order.
     * 
     * @return the order.
     */
    public int getOrder() {
        return order;
    }
}
