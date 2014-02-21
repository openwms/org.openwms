/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service.spring;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import javax.persistence.PersistenceException;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.integration.exception.IntegrationRuntimeException;
import org.openwms.core.service.ExceptionCodes;
import org.openwms.core.service.GenericEntityService;
import org.openwms.core.service.exception.EntityNotFoundException;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A AbstractGenericEntityService.
 * 
 * @param <BK>
 *            The type of business key
 * @param <ID>
 *            The type of technical key
 * @param <T>
 *            The type of Entity
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Transactional
@Service
public abstract class AbstractGenericEntityService<T extends AbstractEntity<ID>, ID extends Serializable, BK extends Serializable>
        implements GenericEntityService<T, ID, BK> {
    @Autowired
    private MessageSource messageSource;

    /**
     * Return a type-safe DAO implementation instantiated by the sub-classing
     * service.
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
     * Tries to resolve a given <tt>entity</tt> by it's business key and returns
     * it to the caller. In case the <tt>entity</tt> could not be resolved,
     * <code>null</code> may be returned.
     */
    protected abstract T resolveByBK(T entity);

    /**
     * {@inheritDoc}
     * 
     * @throws ServiceRuntimeException
     *             if
     *             <ul>
     *             <li><tt>entity</tt> is <code>null</code></li>
     *             <li><tt>entity</tt> does not exist</li>
     *             </ul>
     */
    @Override
    public T create(T entity) {
        checkForNull(entity, ExceptionCodes.ENTITY_NOT_BE_NULL);
        if (!entity.isNew()) {
            String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_ALREADY_EXISTS, new Object[] { entity },
                    null);
            throw new ServiceRuntimeException(msg);
        }
        T persistedEntity = resolveByBK(entity);
        if (persistedEntity != null) {
            String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_ALREADY_EXISTS, new Object[] { entity },
                    null);
            throw new ServiceRuntimeException(msg);
        }
        getRepository().persist(entity);
        return getRepository().save(entity);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EntityNotFoundException
     *             if entity with <tt>id</tt> does not exist
     */
    @Override
    public T findById(ID id) {
        T entity = getRepository().findById(id);
        if (entity == null) {
            throw new EntityNotFoundException(getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST,
                    new Object[] { id }, null));
        }
        return entity;
    }

    /**
     * {@inheritDoc}
     * 
     * Implementation returns an empty list in case of no result.
     * 
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        List<T> result = getRepository().findAll();
        return result == null ? Collections.<T> emptyList() : result;
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EntityNotFoundException
     *             if entity with <code>key</code> does not exist
     */
    @Override
    public T findByBK(BK key) {
        T role = getRepository().findByUniqueId(key);
        if (role == null) {
            throw new EntityNotFoundException(getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST,
                    new Object[] { key }, null));
        }
        return role;
    }

    /**
     * {@inheritDoc}
     * 
     * Handles detached and managed entities. Transient instances will be
     * ignored.
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
     * @throws EntityNotFoundException
     *             if one of the entities does not exist
     */
    @Override
    public void removeByBK(BK[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                T entity = getRepository().findByUniqueId(keys[i]);
                if (entity == null) {
                    String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST,
                            new Object[] { keys[i] }, null);
                    throw new EntityNotFoundException(msg);
                }
                getRepository().remove(entity);
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @throws EntityNotFoundException
     *             if one of the entities does not exist
     */
    @Override
    public void removeByID(ID[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (keys[i] != null) {
                T entity = getRepository().findById(keys[i]);
                if (entity == null) {
                    String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_NOT_EXIST,
                            new Object[] { keys[i] }, null);
                    throw new EntityNotFoundException(msg);
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
            } catch (PersistenceException | IntegrationRuntimeException ex) {
                String msg = getMessageSource().getMessage(ExceptionCodes.ENTITY_ALREADY_EXISTS,
                        new Object[] { entity }, null);
                throw new ServiceRuntimeException(msg);
            }
        }
        return getRepository().save(entity);
    }

    /**
     * Throws an ServiceRuntimeException with <tt>msg</tt> as message text when
     * <tt>args</tt> is null.
     * 
     * @param args
     *            The argument to check
     * @param msg
     *            The exception message text
     */
    protected void checkForNull(Object args, String msg) {
        ServiceRuntimeException.throwIfNull(args, translate(msg));
    }

    /**
     * Translate a given message <tt>code</tt> into a message text with
     * arguments <tt>args</tt>.
     * 
     * @param code
     *            The message code
     * @param args
     *            Interpolated arguments
     * @return The message text
     */
    protected String translate(String code, Object... args) {
        return messageSource.getMessage(code, args == null ? new Object[0] : args, null);
    }
}