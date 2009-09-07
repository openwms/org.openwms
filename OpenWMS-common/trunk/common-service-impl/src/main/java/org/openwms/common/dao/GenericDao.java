/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * A GenericDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface GenericDao<T extends Serializable, ID extends Serializable> {

	public final static String FIND_ALL = ".findAll";
	public final static String FIND_BY_ID = ".findById";

	/**
	 * Find and return the entity identified by the technical <tt>id</tt>.
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	T findById(ID id);

	/**
	 * Find all entities and return them as a <tt>List</tt>.
	 * 
	 * @return
	 */
	@Transactional(readOnly = true)
	List<T> findAll();

	/**
	 * Find all entities by passing the name of the JPA NamedQuery and the parameter map.
	 * 
	 * @param queryName
	 * @param params
	 * @return
	 */
	@Transactional(readOnly = true)
	List<T> findByQuery(String queryName, Map<String, ?> params);

	/**
	 * Find and return the entity identified by the natural unique id.
	 * 
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	T findByUniqueId(Serializable id);

	/**
	 * Merges a detached entity. Return the entity as persisted.
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional
	T save(T entity);

	/**
	 * Removes a persistent entity.
	 * 
	 * @param entity
	 */
	@Transactional
	void remove(T entity);

	/**
	 * Persist a new transient entity.
	 * 
	 * @param entity
	 */
	@Transactional
	void persist(T entity);

	void setPersistentClass(Class<T> persistentClass);
}
