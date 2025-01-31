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
package org.openwms.core.event;

import java.io.Serializable;
import java.util.Objects;

/**
 * A RootNotification is used as super class for notifications within the application
 * domain.
 *
 * @author Heiko Scherrer
 */
public class RootNotification implements Serializable {

    private final Serializable data;

    /**
     * Constructs a RootNotification.
     *
     * @param data Any serializable object to be transfered
     */
    public RootNotification(Serializable data) {
        Objects.requireNonNull(data, "data passed to RootNotification is null");
        this.data = data;
    }

    /**
     * The data that is transfered between notification sender and receiver.
     *
     * @return The transfered, serializable data object
     */
    public Serializable getData() {
        return data;
    }

    /**
     * Returns a String representation of this RootNotification.
     *
     * @return A String
     */
    @Override
    public String toString() {
        return getClass().getName() + "[data=" + data + "]";
    }
}