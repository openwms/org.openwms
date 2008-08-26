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

/**
 * A IGenericDAO.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface IGenericDAO<T, ID extends Serializable> {

    final String NQ_FIND_ALL = "findAll";

    /**
     * Find and return the entity identified by <tt>id</tt>.
     * 
     * @param id
     * @return
     */
    T findById(ID id);

    /**
     * Find all entities and return them as a <tt>List</tt>.
     * 
     * @return
     */
    List<T> findAll();

    /**
     * Find all entities by passing the name of the JPA NamedQuery and the parameter map.
     * 
     * @param queryName
     * @param params
     * @return
     */
    List<T> findByQuery(String queryName, Map<String, ?> params);

    /**
     * Saves a persistent entity and returns it.
     * 
     * @param entity
     * @return
     */
    T save(T entity);

    /**
     * Removes a persistent entity.
     * 
     * @param entity
     */
    void remove(T entity);

    /**
     * Persist a new transient entity.
     * 
     * @param entity
     */
    void persist(T entity);
}
