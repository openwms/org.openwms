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
import java.util.Map;

/**
 * A GenericDao is a generic interface declaration that defines simple CRUD operations.
 * <p>
 * This interface provides basic functionality to create/read/update and delete entity classes. Entities can be simple POJO classes without
 * any ORM dependencies. Merely implementations of this interface knows about the way the entity is been persisted.
 * </p>
 *
 * @param <T> Any serializable type, mostly an entity class type
 * @param <ID> The type of the entity class' unique id
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface GenericDao<T extends AbstractEntity<ID>, ID extends Serializable> {

    /**
     * Suffix for the FIND_ALL named query.
     */
    String FIND_ALL = ".findAll";

    /**
     * Suffix for the FIND_BY_ID named query.
     */
    String FIND_BY_ID = ".findById";

    /**
     * Find and return the entity identified by the technical key.
     *
     * @param id - Unique technical key of the entity
     * @return Entity class identified by id
     * @throws IllegalArgumentException if <tt>id</tt> is <code>null</code>
     */
    T findById(ID id);

    /**
     * Find all entities and return them as a {@link java.util.List}.
     *
     * @return List of all entities
     */
    List<T> findAll();

    /**
     * Use a named query to find all entities. Pass in the name of the <code>NamedQuery</code> and a parameter map.
     *
     * @param queryName - Defined name of the NamedQuery
     * @param params - Map of parameters to pass to the query
     * @return List of found entity classes
     */
    List<T> findByNamedParameters(String queryName, Map<String, ?> params);

    /**
     * Use an own JPA query to fetch entities.
     *
     * @param queryName - Defined name of the NamedQuery
     * @param values - A list of values to use as parameters
     * @return A list of all entities
     */
    List<T> findByPositionalParameters(String queryName, Object... values);

    /**
     * Find and return the Entity identified by it's natural unique id.
     *
     * @param id - Natural key to lookup the Entity (also known as business key)
     * @return The Entity instance or <code>null</code>
     */
    T findByUniqueId(Serializable id);

    /**
     * Synchronize an entity with the persistence layer and return it.
     *
     * @param entity - Entity instance to be synchronized with the persistence layer
     * @return The synchronized entity instance. If JPA is used as implementation, the returned instance is managed.
     */
    T save(T entity);

    /**
     * Removes an already persistent entity.
     *
     * @param entity - Entity instance to be removed
     * @throws IllegalArgumentException if the instance is not an entity or is a detached entity
     */
    void remove(T entity);

    /**
     * Persist a transient entity.
     *
     * @param entity - Entity instance to be persisted
     */
    void persist(T entity);
}