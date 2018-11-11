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
package org.openwms.core;

import java.io.Serializable;
import java.util.List;

/**
 * An EntityService is a generic interface definition of a simple CRUD service.
 * <p>
 * Basically this service is responsible to encapsulate CRUD functionality and delegates to repository implementations. Furthermore the
 * service spans the transaction boundary and handles exception translation.
 * </p>
 *
 * @param <T> Any serializable type, mostly an entity class type
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface EntityService<T extends Serializable> {

    /**
     * Save an entity of type <code>T</code>.
     *
     * @param entity Instance to be saved
     * @return The saved instance
     */
    T save(T entity);

    /**
     * Find all entities of type <code>T</code>.
     * <p>
     * The result is specific to the implementation and can also be <code>null</code>.
     *
     * @return A list of all entities
     */
    List<T> findAll();

    /**
     * Removes an entity instance.
     *
     * @param entity Instance to be removed
     */
    void remove(T entity);

    /**
     * Add an entity.
     *
     * @param entity New entity instance to be added
     */
    void add(T entity);
}
