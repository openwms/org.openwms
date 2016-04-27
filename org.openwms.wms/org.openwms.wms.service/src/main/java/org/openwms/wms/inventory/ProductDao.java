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
package org.openwms.wms.inventory;

/**
 * A ProductDao.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
public interface ProductDao {

    /**
     * Save changes on a {@link Product} instance and return the updated entity.
     * 
     * @param entity
     *            The entity to save
     * @return The saved entity instance. If JPA is used as implementation, the
     *         returned instance is managed.
     */
    <T extends Product> T save(T entity);

    /**
     * Removes an already persistent {@link Product}.
     * 
     * @param entity
     *            The entity instance to be removed
     */
    <T extends Product> void remove(T entity);

    /**
     * Persist a new/transient {@link Product}.
     * 
     * @param entity
     *            The entity instance to be persisted
     */
    <T extends Product> void persist(T entity);

    /**
     * Find a {@link Product} by its unique id.
     * 
     * @param productId
     *            The productId to search for
     */
    <T extends Product> T findByProductId(String productId);
}