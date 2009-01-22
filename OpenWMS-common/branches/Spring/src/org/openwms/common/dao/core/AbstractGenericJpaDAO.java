/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.openwms.common.dao.GenericDAO;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A GenericJpaDAOImpl.
 * <p>
 * Subclass this DAO implementation to call CRUD operations with JAVA Persistence API. Furthermore extend this class to
 * be explicitly independent from OR mapping frameworks.
 * <p>
 * The <tt>GenericDAO</tt> extends Springs JpaDaoSupport, to have a benefit from Springs exception translation and
 * transaction management.<br>
 * The stereotype annotation <code>atRepository</code> expresses the belongs as data access class. <br>
 * Spring introduced the <tt>PersistenceExceptionTranslationPostProcessor</tt> automatically to enable data access
 * exception translation for any object carrying the <tt>atRepository</tt> annotation
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Repository
@Transactional
public abstract class AbstractGenericJpaDAO<T extends Serializable, ID extends Serializable> extends JpaDaoSupport
	implements GenericDAO<T, ID> {

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public AbstractGenericJpaDAO() {
	this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
		.getActualTypeArguments()[0];
    }

    /**
     * Get the persistent entity class.<br>
     * Resolved with Java Reflection to find the class of type <tt>T</tt>.
     * 
     * @return
     */
    public Class<T> getPersistentClass() {
	return persistentClass;
    }

    @Transactional(readOnly = true)
    public T findById(ID id) {
	return getJpaTemplate().find(getPersistentClass(), id);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
	return getJpaTemplate().findByNamedQuery(getFindAllQuery());
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> findByQuery(String queryName, Map<String, ?> params) {
	return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public T findByUniqueId(Serializable id) {
	// TODO:Don't check null, yet
	List<T> result = getJpaTemplate().findByNamedQuery(getFindByUniqueIdQuery(), id);
	if (result.size() > 1) {
	    throw new IncorrectResultSizeDataAccessException("Unexpected size of result list", 1, result.size());
	}
	return result.size() == 0 ? null : result.get(0);
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

    abstract String getFindAllQuery();

    abstract String getFindByUniqueIdQuery();
}
