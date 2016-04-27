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

/**
 * An EntityService is a generic interface definition of a simple CRUD service.
 * <p>
 * Basically this service is responsible to encapsulate CRUD functionality and delegates to repository implementations. Furthermore the
 * service spans the transaction boundary and handles exception translation.
 * </p>
 * 
 * @param <T>
 *            Any serializable type, mostly an entity class type
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface EntityService<T extends Serializable> {

    /**
     * Save an entity of type <code>T</code>.
     * 
     * @param entity
     *            Instance to be saved
     * @return The saved instance
     */
    T save(T entity);

    /**
     * Find all entities of type <code>T</code>.
     * 
     * The result is specific to the implementation and can also be <code>null</code>.
     * 
     * @return A list of all entities
     */
    List<T> findAll();

    /**
     * Removes an entity instance.
     * 
     * @param entity
     *            Instance to be removed
     */
    void remove(T entity);

    /**
     * Add an entity.
     * 
     * @param entity
     *            New entity instance to be added
     */
    void add(T entity);
}
