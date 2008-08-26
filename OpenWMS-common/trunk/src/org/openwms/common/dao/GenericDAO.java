/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * A GenericDAO.
 * <p>
 * Use this DAO implementation to execute CRUD operations with JAVA Persistence API. Furthermore extend this class to be
 * explicitly independent from OR mapping frameworks.
 * <p>
 * The <tt>GenericDAO</tt> extends Springs JpaDaoSupport, to have a benefit from Springs exception translation and
 * transaction management.<br>
 * The stereotype annotation <code>atRepository</code> expressed the belongs as data access class. <br>
 * Spring introduced the <tt>PersistenceExceptionTranslationPostProcessor</tt> to automatically to enable data access
 * exception translation for any object carrying the <tt>atRepository</tt> annotation
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Repository
public abstract class GenericDAO<T, ID extends Serializable> extends JpaDaoSupport implements IGenericDAO<T, ID> {

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
	this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
		.getActualTypeArguments()[0];
    }

    /**
     * Get the persistent entity class.<br>
     * Resolved with Java Reflection to find the class of the <tt>T</tt> generic argument.
     * 
     * @return
     */
    public Class<T> getPersistentClass() {
	return persistentClass;
    }

    public T findById(ID id) {
	return getJpaTemplate().find(getPersistentClass(), id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
	return getJpaTemplate().findByNamedQuery(IGenericDAO.NQ_FIND_ALL);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByQuery(String queryName, Map<String, ?> params) {
	return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
    }

    public T save(T entity) {
	return getJpaTemplate().merge(entity);
    }

    public void remove(T entity) {
	getJpaTemplate().remove(entity);
    }

    public void persist(T entity) {
	getJpaTemplate().persist(entity);
    }

}
