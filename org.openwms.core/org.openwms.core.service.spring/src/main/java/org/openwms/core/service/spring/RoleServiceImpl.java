/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.service.spring;

import java.util.List;

import javax.persistence.PersistenceException;

import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.integration.RoleDao;
import org.openwms.core.integration.exception.IntegrationRuntimeException;
import org.openwms.core.service.ExceptionCodes;
import org.openwms.core.service.RoleService;
import org.openwms.core.service.exception.EntityNotFoundException;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.util.event.RoleChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A RoleServiceImpl is a Spring supported transactional implementation of a general {@link RoleService}. Using Spring 2 annotation support
 * autowires collaborators, therefore XML configuration becomes obsolete. This class is marked with Springs {@link Service} annotation to
 * benefit from Springs exception translation intercepter. Traditional CRUD operations are delegated to a {@link RoleDao} instance.
 * <p>
 * This implementation can be autowired with the name {@value #COMPONENT_NAME}.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.RoleDao
 */
@Transactional
@Service(RoleServiceImpl.COMPONENT_NAME)
public class RoleServiceImpl implements RoleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleDao dao;
    @Autowired
    private MessageSource messageSource;
    /** Springs service name. */
    public static final String COMPONENT_NAME = "roleService";

    /**
     * @see org.openwms.core.service.RoleService#create(org.openwms.core.domain.system.usermanagement.Role)
     */
    @Override
    public Role create(Role role) {
        ServiceRuntimeException.throwIfNull(role,
                messageSource.getMessage(ExceptionCodes.ROLE_CREATE_NOT_BE_NULL, new String[0], null));
        if (!role.isNew()) {
            String msg = messageSource.getMessage(ExceptionCodes.ROLE_ALREADY_EXISTS, new String[] { role.getName() },
                    null);
            throw new ServiceRuntimeException(msg);
        }
        Role existingRole = dao.findByUniqueId(role.getName());
        if (existingRole != null) {
            String msg = messageSource.getMessage(ExceptionCodes.ROLE_ALREADY_EXISTS, new String[] { role.getName() },
                    null);
            throw new ServiceRuntimeException(msg);
        }
        dao.persist(role);
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             when <code>roles</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = { RoleChangedEvent.class })
    public void remove(Long pId) {
        ServiceRuntimeException.throwIfNull(pId,
                messageSource.getMessage(ExceptionCodes.ROLE_REMOVE_NOT_BE_NULL, new String[0], null));
        Role role = dao.findById(pId);
        if (role != null) {
            dao.remove(role);
        }
    }

    /**
     * @see org.openwms.core.service.RoleService#removeByBK(java.lang.String)
     * 
     * @throws IllegalArgumentException
     *             when <code>name</code> is <code>null</code>
     * @throws EntityNotFoundException
     *             if no Role with <tt>name</tt> was found
     */
    @Override
    @FireAfterTransaction(events = { RoleChangedEvent.class })
    public void removeByBK(String name) {
        ServiceRuntimeException.throwIfNull(name,
                messageSource.getMessage(ExceptionCodes.ROLE_REMOVE_NOT_BE_NULL, new String[0], null));
        Role role = dao.findByUniqueId(name);
        if (role == null) {
            String msg = messageSource.getMessage(ExceptionCodes.ROLE_NOT_EXIST, new String[] { name }, null);
            throw new EntityNotFoundException(msg);
        }
        dao.remove(role);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws IllegalArgumentException
     *             when <code>role</code> is <code>null</code>
     * @throws ServiceRuntimeException
     *             if the <tt>role</tt> instance is transient but a {@link Role} with the same business key already exists in the
     *             persistence layer
     */
    @Override
    @FireAfterTransaction(events = { RoleChangedEvent.class })
    public Role save(Role role) {
        ServiceRuntimeException.throwIfNull(role,
                messageSource.getMessage(ExceptionCodes.ROLE_SAVE_NOT_BE_NULL, new String[0], null));
        if (role.isNew()) {
            try {
                dao.persist(role);
            } catch (PersistenceException | IntegrationRuntimeException ex) {
                String msg = messageSource.getMessage(ExceptionCodes.ROLE_ALREADY_EXISTS,
                        new String[] { role.getName() }, null);
                throw new ServiceRuntimeException(msg);
            }
        }
        return dao.save(role);
    }

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAll() {
        return dao.findAll();
    }

    /**
     * @see org.openwms.core.service.RoleService#findByBK(java.lang.String)
     */
    @Override
    public Role findByBK(String name) {
        Role role = dao.findByUniqueId(name);
        if (role == null) {
            throw new EntityNotFoundException(messageSource.getMessage(ExceptionCodes.ROLE_NOT_EXIST,
                    new String[] { name }, null));
        }
        return role;
    }
}