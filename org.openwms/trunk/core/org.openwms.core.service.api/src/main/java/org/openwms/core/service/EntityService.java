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
 * An EntityService is a generic interface definition of a service to offer
 * simple create/read/update and remove functionality.
 * <p>
 * Basically the service is responsible to access the persistence integration
 * layer. Even though the service cares about the transaction boundary and
 * exception translation. The service implementation can also act as Service
 * Facade and access multiple data providers as well other services.
 * </p>
 * 
 * @param <T>
 *            Any serializable type, mostly an Entity class type
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface EntityService<T extends Serializable> {

	/**
	 * Find all Entity classes of type clazz.
	 * 
	 * @param <T>
	 *            Any serializable type, mostly an Entity class type
	 * @param clazz
	 *            Class of the Entity to find
	 * @return A {@link java.util.List} of all found Entities
	 */
	// TODO [scherrer] : To be removed
	@Deprecated
	List<T> findAll(Class<T> clazz);

	/**
	 * Save an Entity of type T.
	 * 
	 * @param <T>
	 *            Any serializable type, mostly an Entity class type
	 * @param entity
	 *            Entity instance to be saved
	 * @return The saved Entity instance
	 */
	T save(T entity);

	/**
	 * Find all Entities of type T.
	 * 
	 * @param <T>
	 *            Any serializable type, mostly an Entity class type
	 * @return A {@link java.util.List} of all found Entities
	 */
	List<T> findAll();

	/**
	 * Removes a persistent Entity.
	 * 
	 * @param <T>
	 *            Any serializable type, mostly an Entity class type
	 * @param entity
	 *            Entity instance to be removed
	 */
	void remove(T entity);

	/**
	 * Add a new entity to the persistent storage.
	 * 
	 * @param <T>
	 *            Any serializable type, mostly an Entity class type
	 * @param newEntity
	 *            Transient Entity instance to persist
	 */
	void addEntity(T newEntity);
}
