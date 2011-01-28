/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service;

import java.io.Serializable;
import java.util.List;

/**
 * An EntityService is a generic interface definition for a service to offer
 * simple CRUD functionality.
 * <p>
 * Basically the service is responsible to access the persistence layer. Even
 * though the service declares the transaction boundary and handles exception
 * translation.
 * </p>
 * 
 * @param <T>
 *            Any serializable type, mostly an entity class type
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface EntityService<T extends Serializable> {

    /**
     * Save an entity of type <code>T</code>.
     * 
     * @param entity
     *            Entity instance to be saved
     * @return The saved entity instance
     */
    T save(T entity);

    /**
     * Find all entities of type <code>T</code>.
     * 
     * @return A list of all found entities
     */
    List<T> findAll();

    /**
     * Removes a persistent entity instance.
     * 
     * @param entity
     *            Entity instance to be removed
     */
    void remove(T entity);

    /**
     * Add a new entity to the persistent storage.
     * 
     * @param newEntity
     *            Transient entity instance to persist
     */
    void addEntity(T newEntity);
}
