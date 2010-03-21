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
package org.openwms.common.integration.jpa;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.openwms.common.domain.Location;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.integration.exception.TooManyEntitiesFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * An AbstractGenericJpaDao - Extend this DAO implementation to inherit simple
 * JPA CRUD operations.
 * <p>
 * This {@link GenericDao} implementation extends Springs {@link JpaDaoSupport},
 * to have a benefit from Springs exception translation and transaction
 * management.<br>
 * The stereotype annotation {@link Repository} marks this class as DAO in the
 * architecture and enables exception translation and component scanning.<br>
 * </p>
 * <p>
 * Furthermore an {@link AbstractGenericJpaDao} has transactional behavior
 * expressed with Springs {@link Transactional} annotation.
 * </p>
 * 
 * @param <T>
 *            Any serializable type, mostly an Entity class type.
 * @param <ID>
 *            The type of the Entity class' unique id
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.springframework.stereotype.Repository
 * @see org.springframework.transaction.annotation.Transactional
 */
@Repository
@Transactional
public abstract class AbstractGenericJpaDao<T extends Serializable, ID extends Serializable> extends JpaDaoSupport
        implements GenericDao<T, ID> {

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    private Class<T> persistentClass;

    /**
     * Used to inject an {@link EntityManagerFactory} automatically.
     * 
     * @param entityManagerFactory
     *            The {@link EntityManagerFactory}
     */
    @Autowired
    public void setJpaEntityManagerFactory(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        super.setEntityManagerFactory(entityManagerFactory);
    }

    /**
     * Create a new AbstractGenericJpaDao.
     */
    @SuppressWarnings("unchecked")
    protected AbstractGenericJpaDao() {
        if (getClass().getGenericSuperclass() != null) {
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }
    }

    /**
     * Returns the entity class to deals with.<br>
     * The Java Reflection API is used to find the type.
     * 
     * @return Entity class type.
     */
    public Class<T> getPersistentClass() {
        return persistentClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public T findById(ID id) {
        return getJpaTemplate().find(getPersistentClass(), id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        List list = getJpaTemplate().findByNamedQuery(getFindAllQuery());
        for (Object object : list) {
            if (object instanceof Location) {
                logger.debug(((Location) object).getDescription());
            }
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public List<T> findByQuery(String queryName, Map<String, ?> params) {
        return getJpaTemplate().findByNamedQueryAndNamedParams(queryName, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public T findByUniqueId(Serializable id) {
        List<T> result = getJpaTemplate().findByNamedQuery(getFindByUniqueIdQuery(), id);
        if (result.size() > 1) {
            throw new TooManyEntitiesFoundException(id);
        }
        return result.size() == 0 ? null : result.get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public T save(T entity) {
        beforeUpdate(entity);
        return getJpaTemplate().merge(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void remove(T entity) {
        getJpaTemplate().remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void persist(T entity) {
        beforeUpdate(entity);
        getJpaTemplate().persist(entity);
    }

    /**
     * Returns the name of the <code>NamedQuery</code> to find all Entity
     * classes.
     * 
     * @return Name of the query
     */
    protected abstract String getFindAllQuery();

    /**
     * Returns the name of the <code>NamedQuery</code> to find an Entity by the
     * business key.
     * 
     * @return Name of the query
     */
    protected abstract String getFindByUniqueIdQuery();

    /**
     * This method is considered as a hook to do something before an update is
     * performed.
     * 
     * @param entity
     *            The Entity that is updated
     */
    protected void beforeUpdate(T entity) {}

    /**
     * Subclasses can call this method to retrieve an shared
     * {@link EntityManager} instance.
     * 
     * @return The {@link EntityManager}
     */
    protected final EntityManager getEm() {
        return getJpaTemplate().getEntityManager();
    }
}
