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

/**
 * An UserChangedEvent is raised to notify about changes on an {@code User} instance. A
 * listener may for instance evict a cache.
 *
 * @author Heiko Scherrer
 */
public class UserChangedEvent extends RootApplicationEvent implements Serializable {

    /**
     * Create a new UserChangedEvent.
     *
     * @param source The {@code User} that has changed
     */
    public UserChangedEvent(Object source) {
        super(source);
    }
}