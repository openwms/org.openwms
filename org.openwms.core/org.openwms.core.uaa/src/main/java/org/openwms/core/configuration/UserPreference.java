/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.configuration;

import java.io.Serializable;

/**
 * A UserPreference encapsulates all preferences dedicated to an {@code User}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class UserPreference {

    private String username;
    private String key;
    private Serializable value;

    /**
     * At least the users name must be present.
     *
     * @param username The users identifiable name
     */
    public UserPreference(String username) {
        this.username = username;
    }


    public UserPreference(String username, String key, Serializable value) {
        this.username = username;
        this.key = key;
        this.value = value;
    }

    public String getUsername() {
        return username;
    }

    public String getKey() {
        return key;
    }

    public Serializable getValue() {
        return value;
    }
}
