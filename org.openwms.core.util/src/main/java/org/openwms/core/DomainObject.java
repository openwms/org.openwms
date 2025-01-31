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
package org.openwms.core;

import java.io.Serializable;

/**
 * A DomainObject, implementation classes offer basic functionality characteristic to all
 * persisted domain objects.
 *
 * Each domain object:
 * <ul>
 *   <li>must have a field for optimistic locking purpose</li>
 *   <li>must return whether it is a transient or persisted instance</li>
 *   <li>must return the technical key value to the caller</li>
 * </ul>
 *
 * @param <ID> Type of technical key class
 * @author Heiko Scherrer
 */
public interface DomainObject<ID extends Serializable> {

    /**
     * Check whether the instance is a transient or persisted one.
     *
     * @return {@literal true} if transient (not persisted before), otherwise {@literal false}
     */
    boolean isNew();

    /**
     * Return the value of the optimistic locking field.
     *
     * @return the version number
     */
    long getVersion();

    /**
     * Return the technical key value.
     *
     * @return The technical, unique key
     */
    ID getId();
}