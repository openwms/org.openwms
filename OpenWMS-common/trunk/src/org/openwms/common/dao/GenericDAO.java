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
public interface GenericDAO<T extends Serializable, ID extends Serializable> {

    /**
     * Find and return the entity identified by the technical <tt>id</tt>.
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
     * Find and return the entity identified by the natural unique id.
     * 
     * @param id
     * @return
     */
    T findByUniqueId(Object id);

    /**
     * Merges a detached entity. Return the entity as persisted.
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
