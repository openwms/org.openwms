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
import java.util.Collection;
import java.util.List;

/**
 * A GenericEntityService provides some common used CRUD operations available for most entity types.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
public interface GenericEntityService<T extends AbstractEntity, ID extends Serializable, BK extends Serializable> {

    /**
     * Create and persist a new entity.
     *
     * @param entity The new entity to create
     * @return The newly created and persisted <tt>entity</tt>
     */
    T create(T entity);

    /**
     * Return an entity identified by it's unique business key.
     *
     * @param key The business key to identify the entity
     * @return The entity
     */
    T findByBK(BK key);

    /**
     * Find and return all entities.
     *
     * @return A list of all entities
     */
    List<T> findAll();

    /**
     * Find and return an entity identified by it's unique technical <tt>id</tt> .
     *
     * @param id The unique technical identifier of the entity
     * @return The entity
     */
    T findById(ID id);

    /**
     * Save the given entity or persist it, in case it is a transient one.
     *
     * @param entity Entity to save
     * @return Saved entity instance
     */
    T save(T entity);

    /**
     * Save an collection of entities. No matter if they already exist or not.
     *
     * @param entities An collection of entities to save
     * @return A collection of all passed entities containing updated ones as well as not updated ones.
     */
    Collection<T> saveAll(Collection<T> entities);

    /**
     * Remove an <tt>entity</tt>.
     *
     * @param entity The entity to remove
     */
    void remove(T entity);

    /**
     * Remove entities identified by their unique business key.
     *
     * @param keys The unique business keys of the entities to remove
     */
    void removeByBK(BK[] keys);

    /**
     * Remove entities identified by their unique technical key.
     *
     * @param keys The unique technical keys of the entities to remove
     */
    void removeByID(ID[] keys);
}