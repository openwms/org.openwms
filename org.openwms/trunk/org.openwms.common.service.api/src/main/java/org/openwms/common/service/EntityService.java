/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * A EntityService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface EntityService<T extends Serializable> {

	/**
	 * Find all entities of type clazz.
	 * 
	 * @param clazz
	 * @return
	 */
	List<T> findAll(Class<T> clazz);

	/**
	 * Update an entity of type <T>
	 * 
	 * @param clazz
	 * @param entity
	 * @return
	 */
	T save(Class<T> clazz, T entity);

	/**
	 * Find all entities of type <T>.
	 * 
	 * @return
	 */
	public List<T> findAll();// Remove

	/**
	 * Removes a persistent entity.
	 * 
	 * @param clazz
	 * @param entity
	 */
	void remove(Class<T> clazz, T entity);

	/**
	 * Add a new entity to the persistent storage.
	 * 
	 * @param newEntity
	 */
	void addEntity(T newEntity);
}