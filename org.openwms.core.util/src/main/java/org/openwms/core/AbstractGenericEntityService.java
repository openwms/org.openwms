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

import org.ameba.exception.IntegrationLayerException;
import org.ameba.exception.NotFoundException;
import org.ameba.exception.ServiceLayerException;
import org.openwms.core.exception.ExceptionCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A AbstractGenericEntityService.
 *
 * @param <BK> The type of business key
 * @param <ID> The type of technical key
 * @param <T> The type of Entity
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Transactional
@Service
public abstract class AbstractGenericEntityService<T extends AbstractEntity<ID>, ID extends Serializable, BK extends Serializable> implements GenericEntityService<T, ID, BK> {

    @Autowired
    private MessageSource messageSource;

    /**
     * Return a type-safe DAO implementation instantiated by the sub-classing service.
     *
     * @return the DAO
     */
    protected abstract GenericDao<T, ID> getRepository();

    /**
     * Get the messageSource.
     *
     * @return the messageSource.
     */
    protected MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * Tries to resolve a given <tt>entity</tt> by it's business key and returns it to the caller. In case the <tt>entity</tt> could not be
     * resolved, <code>null</code> may be returned.
     */
    protected abstract T resolveByBK(T entity);

    /**
     * {@inheritDoc}
     *
     * @throws ServiceLayerException if <ul> <li><tt>entity</tt> is <code>null</code></li> <li><tt>entity</tt> does not exist</li> </ul>
     */
    @Override
    public T create(T entity) {
        checkForNull(entity, ExceptionCodes.ENTITY_NOT_BE_NULL);
        if (!entity.isNew()) {
            String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_ALREADY_EXISTS, new Object[]{entity}, null);
            throw new ServiceLayerException(msg);
        }
        T persistedEntity = resolveByBK(entity);
        if (persistedEntity != null) {
            String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_ALREADY_EXISTS, new Object[]{entity}, null);
            throw new ServiceLayerException(msg);
        }
        getRepository().persist(entity);
        return getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException if entity with <tt>id</tt> does not exist
     */
    @Override
    public T findById(ID id) {
        T entity = getRepository().findById(id);
        if (entity == null) {
            throw new NotFoundException(getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST, new Object[]{id}, null), ExceptionCodes.ENTITY_NOT_EXIST);
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Implementation returns an empty list in case of no result.
     * <p>
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        List<T> result = getRepository().findAll();
        return result == null ? Collections.<T>emptyList() : result;
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException if entity with <code>key</code> does not exist
     */
    @Override
    public T findByBK(BK key) {
        T role = getRepository().findByUniqueId(key);
        if (role == null) {
            throw new NotFoundException(getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST, new Object[]{key}, null), ExceptionCodes.ENTITY_NOT_EXIST);
        }
        return role;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Handles detached and managed entities. Transient instances will be ignored.
     */
    @Override
    public void remove(T entity) {
        if (!entity.isNew()) {

            // Get and remove the managed instance, to be save
            T persistedEntity = getRepository().findById(entity.getId());
            getRepository().remove(persistedEntity);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException if one of the entities does not exist
     */
    @Override
    public void removeByBK(BK[] keys) {
        for (BK key : keys) {
            if (key != null) {
                T entity = getRepository().findByUniqueId(key);
                if (entity == null) {
                    String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST, new Object[]{key}, null);
                    throw new NotFoundException(msg, ExceptionCodes.ENTITY_NOT_EXIST);
                }
                getRepository().remove(entity);
            }
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws NotFoundException if one of the entities does not exist
     */
    @Override
    public void removeByID(ID[] keys) {
        for (ID key : keys) {
            if (key != null) {
                T entity = getRepository().findById(key);
                if (entity == null) {
                    String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST, new Object[]{key}, null);
                    throw new NotFoundException(msg, ExceptionCodes.ENTITY_NOT_EXIST);
                }
                getRepository().remove(entity);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        if (entity.isNew()) {
            try {
                getRepository().persist(entity);
            } catch (PersistenceException | IntegrationLayerException ex) {
                String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_ALREADY_EXISTS, new Object[]{entity}, null);
                throw new ServiceLayerException(msg);
            }
        }
        return getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<T> saveAll(Collection<T> entities) {
        Assert.notEmpty(entities, translate(ExceptionCodes.ENTITY_NOT_BE_NULL));
        List<T> result = new ArrayList<>(entities.size());
        entities.forEach(f -> result.add(f.isNew() ? create(f) : getRepository().save(f)));
        return result;
    }

    /**
     * Throws an ServiceRuntimeException with <tt>msg</tt> as message text when <tt>args</tt> is null.
     *
     * @param args The argument to check
     * @param msg The exception message text
     */
    protected void checkForNull(Object args, String msg) {

        if (args == null) {
            throw new ServiceLayerException(translate(msg));
        }
    }

    /**
     * Translate a given message <tt>code</tt> into a message text with arguments <tt>args</tt>.
     *
     * @param code The message code
     * @param args Interpolated arguments
     * @return The message text
     */
    protected String translate(String code, Object... args) {
        return messageSource.getMessage(code, args == null ? new Object[0] : args, null);
    }
}