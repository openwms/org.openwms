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
package org.openwms.core.service.spring;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.service.EntityService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An EntityServiceImpl is a generic service that offers some basic CRUD
 * functionality. It is a transactional Spring service annotated with Springs
 * {@link Service} to activate exception translation.
 * 
 * By implementing {@link ApplicationContextAware} the EntityServiceImpl gets
 * the current {@link ApplicationContext} injected, thats useful to publish
 * events.
 * 
 * @param <T>
 *            Any type of AbstractEntity.
 * @param <ID>
 *            The type of the Entity class' unique id
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.AbstractEntity
 * @see org.openwms.core.service.EntityService
 */
@Transactional
@Service
public class EntityServiceImpl<T extends AbstractEntity, ID extends Serializable> implements EntityService<T>,
        ApplicationContextAware {

    private Class<T> persistentClass;

    /**
     * Generic Repository DAO.
     */
    protected GenericDao<T, ID> dao;

    /**
     * Reference to the {@link ApplicationContext} instance.
     */
    protected ApplicationContext ctx;

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.ctx = applicationContext;
    }

    /**
     * The Repository implementation to work with. This must be set manually
     * because it is generic.
     * 
     * @param dao
     *            The Repository to set
     */
    public void setDao(GenericDao<T, ID> dao) {
        this.dao = dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        resolveTypeClass();
        List<T> list = dao.findAll();
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        resolveTypeClass();
        dao.setPersistentClass(persistentClass);
        return dao.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(T entity) {
        resolveTypeClass();
        dao.setPersistentClass(persistentClass);
        entity = dao.save(entity);
        dao.remove(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEntity(T newEntity) {
        // FIXME [scherrer] : All entities shall extend a superclass Entity with
        // isNew() method, to check this here
        resolveTypeClass();
        if (newEntity == null) {
            throw new ServiceRuntimeException("Entity to persist is null! Type of entity class is " + persistentClass);
        }
        dao.persist(newEntity);
    }

    @SuppressWarnings("unchecked")
    private void resolveTypeClass() {
        if (persistentClass == null && getClass().getGenericSuperclass() != null) {
            this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0];
        }
    }
}