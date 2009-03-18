/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import org.openwms.common.dao.GenericDao;

/**
 * A AbstractGenericDao.
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
public abstract class AbstractGenericDao<T extends Serializable, ID extends Serializable> implements GenericDao<T, ID> {

	public Class<T> persistentClass;

	@PersistenceContext(unitName = "OpenWMS-test-durable")
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	public AbstractGenericDao() {
		if (getClass().getGenericSuperclass() != null) {
//			this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
//					.getActualTypeArguments()[0];
		}
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

	public void setPersistentClass(Class<T> persistentClass) {
		this.persistentClass = persistentClass;
	}

	public T findById(ID id) {
		return em.find(getPersistentClass(), id);
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return em.createQuery(getFindAllQuery()).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByQuery(String queryName, Map<String, ?> params) {
		return null;
		//TODO
		//return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
	}

	@SuppressWarnings("unchecked")
	public T findByUniqueId(Serializable id) {
		// TODO:Don't check null, yet
		List<T> result = em.createQuery(getFindByUniqueIdQuery()).setParameter(0, id).getResultList();
		if (result.size() > 1) {
			throw new NonUniqueResultException("Unexpected size of result list");
		}
		return result.size() == 0 ? null : result.get(0);
	}

	public T save(T entity) {
		beforeUpdate(entity);
		return em.merge(entity);
	}

	public void remove(T entity) {
		em.remove(entity);
	}

	public void persist(T entity) {
		beforeUpdate(entity);
		em.persist(entity);
	}

	abstract String getFindAllQuery();

	abstract String getFindByUniqueIdQuery();

	protected void beforeUpdate(T entity) {};
}
