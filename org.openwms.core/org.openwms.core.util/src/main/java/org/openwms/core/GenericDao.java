/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
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
 * @param <T>
 *            Any serializable type, mostly an entity class type
 * @param <ID>
 *            The type of the entity class' unique id
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
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
     * @param id
     *            - Unique technical key of the entity
     * @return Entity class identified by id
     * @throws IllegalArgumentException
     *             if <tt>id</tt> is <code>null</code>
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
     * @param queryName
     *            - Defined name of the NamedQuery
     * @param params
     *            - Map of parameters to pass to the query
     * @return List of found entity classes
     */
    List<T> findByNamedParameters(String queryName, Map<String, ?> params);

    /**
     * Use an own JPA query to fetch entities.
     * 
     * @param queryName
     *            - Defined name of the NamedQuery
     * @param values
     *            - A list of values to use as parameters
     * @return A list of all entities
     */
    List<T> findByPositionalParameters(String queryName, Object... values);

    /**
     * Find and return the Entity identified by it's natural unique id.
     * 
     * @param id
     *            - Natural key to lookup the Entity (also known as business key)
     * @return The Entity instance or <code>null</code>
     */
    T findByUniqueId(Serializable id);

    /**
     * Synchronize an entity with the persistence layer and return it.
     * 
     * @param entity
     *            - Entity instance to be synchronized with the persistence layer
     * @return The synchronized entity instance. If JPA is used as implementation, the returned instance is managed.
     */
    T save(T entity);

    /**
     * Removes an already persistent entity.
     * 
     * @param entity
     *            - Entity instance to be removed
     * @throws IllegalArgumentException
     *             if the instance is not an entity or is a detached entity
     */
    void remove(T entity);

    /**
     * Persist a transient entity.
     * 
     * @param entity
     *            - Entity instance to be persisted
     */
    void persist(T entity);
}