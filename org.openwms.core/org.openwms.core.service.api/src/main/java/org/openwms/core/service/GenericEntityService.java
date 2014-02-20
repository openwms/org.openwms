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
package org.openwms.core.service;

import java.io.Serializable;
import java.util.List;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.service.exception.EntityAlreadyExistsException;

/**
 * A GenericEntityService provides some common used CRUD operations available for most entity types.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public interface GenericEntityService<T extends AbstractEntity, ID extends Serializable, BK extends Serializable> {

    /**
     * Create and persist a new entity.
     *
     * @param entity The new entity to create
     * @return The newly created and persisted <tt>entity</tt>
     * @throws EntityAlreadyExistsException if the <tt>entity</tt> to create already exist
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
     * Find and return an entity identified by it's unique technical <tt>id</tt>.
     *
     * @param id The unique technical identifier of the entity
     * @return The entity
     */
    T findById(ID id);

    /**
     * Update the given entity or persist it, if it is a transient one.
     *
     * @param entity Entity to save
     * @return Saved entity instance
     */
    T save(T entity);

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