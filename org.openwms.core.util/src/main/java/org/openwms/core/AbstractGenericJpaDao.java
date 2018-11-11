/*
 * Copyright 2018 Heiko Scherrer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core;

import org.openwms.core.exception.ExceptionCodes;
import org.openwms.core.exception.NoUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An AbstractGenericJpaDao - Extend this DAO implementation to inherit simple JPA CRUD operations.
 * <p>
 * This {@link GenericDao} implementation acts as a transactional superclass to have a benefit from Springs exception translation and
 * transaction management.
 * </p>
 * <p>
 * Furthermore an {@link AbstractGenericJpaDao} has transactional behavior expressed with Springs {@link Transactional} annotation.
 * </p>
 *
 * @param <T> Any serializable type, mostly an Entity class type.
 * @param <ID> The type of the Entity class' unique id
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @see org.springframework.stereotype.Repository
 * @see org.springframework.transaction.annotation.Transactional
 */
@Transactional(propagation = Propagation.MANDATORY)
public abstract class AbstractGenericJpaDao<T extends AbstractEntity<ID>, ID extends Serializable> implements GenericDao<T, ID> {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private MessageSource messageSource;

    /**
     * Create a new AbstractGenericJpaDao.
     */
    protected AbstractGenericJpaDao() {
    }

    /**
     * Returns the entity class to deals with.<br>
     * The Java Reflection API is used to find the type.
     *
     * @return Entity class type.
     */
    protected abstract Class<T> getPersistentClass();

    /**
     * {@inheritDoc}
     */
    @Override
    public T findById(ID id) {
        return em.find(getPersistentClass(), id);
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation never return <code>null</code>. In case of no Entities were found an empty List is returned.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        List<T> all = em.createNamedQuery(getFindAllQuery()).getResultList();
        return all == null ? Collections.<T>emptyList() : all;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByNamedParameters(String queryName, Map<String, ?> params) {
        Query queryObject = em.createNamedQuery(queryName);
        if (params != null) {
            for (Map.Entry<String, ?> entry : params.entrySet()) {
                queryObject.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return queryObject.getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<T> findByPositionalParameters(String queryName, Object... values) {
        Query queryObject = em.createNamedQuery(queryName);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i + 1, values[i]);
            }
        }
        return queryObject.getResultList();
    }

    /**
     * {@inheritDoc}
     * <p>
     * A {@link NoUniqueResultException} is thrown when more than one Entity were found. In case of no result, <code>null</code> is
     * returned.
     *
     * @throws NoUniqueResultException when more than one Entity were found
     */
    @Override
    @SuppressWarnings("unchecked")
    public T findByUniqueId(Serializable id) {
        List<T> result = em.createNamedQuery(getFindByUniqueIdQuery()).setParameter(1, id).getResultList();
        if (result.size() > 1) {
            throw new NoUniqueResultException(messageSource.getMessage(ExceptionCodes.MULIPLE_ENTITIES_FOUND, new Serializable[]{id}, null));
        }
        return result.size() == 0 ? null : result.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        beforeUpdate(entity);
        return em.merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(T entity) {
        em.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void persist(T entity) {
        beforeUpdate(entity);
        em.persist(entity);
    }

    /**
     * Returns the name of the <code>NamedQuery</code> to find all Entity classes.
     *
     * @return Name of the query
     */
    protected abstract String getFindAllQuery();

    /**
     * Returns the name of the <code>NamedQuery</code> to find an Entity by the business key.
     *
     * @return Name of the query
     */
    protected abstract String getFindByUniqueIdQuery();

    /**
     * This method is considered as a hook to do something before an update is performed.
     *
     * @param entity The Entity that is updated
     */
    protected void beforeUpdate(T entity) {

    }

    /**
     * Subclasses can call this method to retrieve an shared {@link EntityManager} instance.
     *
     * @return The {@link EntityManager}
     */
    protected final EntityManager getEm() {
        return em;
    }
}