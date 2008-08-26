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

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * A EntityDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Repository
public class EntityDao<T extends Serializable> extends JpaDaoSupport implements IEntityDao<T> {

    public EntityDao() {}

    public T find(Class<T> entityClass, Object id) {
	return getJpaTemplate().find(entityClass, id);
    }
    
    @SuppressWarnings("unchecked")
    public List<T> find(String queryName, Map<String,?> params) {
	return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
    }

    public T save(T entity) {
	return getJpaTemplate().merge(entity);
    }

    public void remove(T entity) {
	getJpaTemplate().remove(entity);
    }

    public void create(T entity) {
	getJpaTemplate().persist(entity);
    }

}
