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
package org.openwms.core.uaa;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;

import org.ameba.exception.NotFoundException;
import org.ameba.exception.ServiceLayerException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.engine.config.spi.ConfigurationService;
import org.openwms.core.AbstractGenericEntityService;
import org.openwms.core.ExceptionCodes;
import org.openwms.core.GenericDao;
import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.event.UserChangedEvent;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.system.usermanagement.Role;
import org.openwms.core.system.usermanagement.SystemUser;
import org.openwms.core.system.usermanagement.User;
import org.openwms.core.system.usermanagement.UserDetails;
import org.openwms.core.system.usermanagement.UserPassword;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An UserServiceImpl is a Spring supported transactional implementation of a general {@link UserService}. Using Spring 2 annotation support
 * autowires collaborators, therefore XML configuration becomes obsolete. This class is marked with Springs {@link Service} annotation to
 * benefit from Springs exception translation interceptor. Traditional CRUD operations are delegated to an {@link UserDao}. <p> This
 * implementation can be autowired with the name {@value #COMPONENT_NAME}. </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @see UserDao
 * @since 0.1
 */
@Transactional
@Service(UserServiceImpl.COMPONENT_NAME)
public class UserServiceImpl extends AbstractGenericEntityService<User, Long, String> implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao dao;
    @Autowired
    private SecurityObjectDao securityObjectDao;
    @Autowired
    private ConfigurationService confSrv;
    @Autowired
    private PasswordEncoder enc;
    @Autowired
    private SaltSource saltSource;
    @Value("#{ globals['system.user'] }")
    private String systemUsername;
    @Value("#{ globals['system.password'] }")
    private String systemPassword;
    /** Springs service name. */
    public static final String COMPONENT_NAME = "userService";

    /**
     * {@inheritDoc}
     */
    @Override
    protected GenericDao<User, Long> getRepository() {
        return dao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected User resolveByBK(User entity) {
        User result = null;
        try {
            result = super.findByBK(entity.getUsername());
        } catch (NotFoundException enfe) {
            ;
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException when no User with <tt>username</tt> found
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public void uploadImageFile(String username, byte[] image) {
        User user = dao.findByUniqueId(username);
        if (user == null) {
            throw new NotFoundException(translate(ExceptionCodes.ENTITY_NOT_EXIST, username), ExceptionCodes.ENTITY_NOT_EXIST);
        }
        if (user.getUserDetails() == null) {
            user.setUserDetails(new UserDetails());
        }
        user.getUserDetails().setImage(image);
        dao.save(user);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Triggers <tt>UserChangedEvent</tt> after completion.
     *
     * @throws ServiceLayerException if the <tt>entity</tt> argument is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public User save(User entity) {
        checkForNull(entity, ExceptionCodes.USER_SAVE_NOT_BE_NULL);
        return super.save(entity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Triggers <tt>UserChangedEvent</tt> after completion.
     *
     * @throws ServiceLayerException when <code>entity</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public void remove(User entity) {
        checkForNull(entity, ExceptionCodes.USER_REMOVE_NOT_BE_NULL);
        super.remove(entity);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Triggers <tt>UserChangedEvent</tt> after completion.
     *
     * @throws ServiceLayerException when <code>keys</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public void removeByBK(String[] keys) {
        checkForNull(keys, ExceptionCodes.USER_REMOVE_NOT_BE_NULL);
        super.removeByBK(keys);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Triggers <tt>UserChangedEvent</tt> after completion.
     *
     * @throws ServiceLayerException when <code>keys</code> is <code>null</code>
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public void removeByID(Long[] keys) {
        checkForNull(keys, ExceptionCodes.USER_REMOVE_NOT_BE_NULL);
        super.removeByID(keys);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public User getTemplate(String username) {
        return new User(username);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public SystemUser createSystemUser() {
        SystemUser sys = new SystemUser(systemUsername, systemPassword);
        Role role = new Role.Builder(SystemUser.SYSTEM_ROLE_NAME).withDescription("SuperUsers Role").asImmutable()
                .build();
        role.setGrants(new HashSet<>(securityObjectDao.findAll()));
        sys.addRole(role);
        return sys;
    }

    /**
     * {@inheritDoc}
     *
     * @throws ServiceLayerException if <ul> <li><code>userPassword</code> is <code>null</code></li> <li>the new password is invalid and
     * does not match the password rules</li> </ul>
     * @throws EntityNotFoundException if {@link User} not found
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public void changeUserPassword(UserPassword userPassword) {
        if (null == userPassword) {
            throw new ServiceLayerException(translate(ExceptionCodes.USER_PASSWORD_SAVE_NOT_BE_NULL), ExceptionCodes.USER_PASSWORD_SAVE_NOT_BE_NULL);
        }
        User entity = dao.findByUniqueId(userPassword.getUser().getUsername());
        if (entity == null) {
            throw new EntityNotFoundException(translate(ExceptionCodes.USER_NOT_EXIST, userPassword.getUser()
                    .getUsername()));
        }
        try {
            entity.changePassword(enc.encodePassword(userPassword.getPassword(),
                    saltSource.getSalt(new UserWrapper(entity))));
            dao.save(entity);
        } catch (InvalidPasswordException ipe) {
            LOGGER.error(ipe.getMessage());
            throw new ServiceLayerException(translate(ExceptionCodes.USER_PASSWORD_INVALID, userPassword.getUser()
                    .getUsername()), ExceptionCodes.USER_PASSWORD_INVALID);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @throws ServiceLayerException if <ul> <li><code>user</code> is <code>null</code></li> <li>the new password is invalid and does not
     * match the password rules</li> </ul>
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public User saveUserProfile(User user, UserPassword userPassword, UserPreference... prefs) {
        if (null == user) {
            throw new ServiceLayerException(translate(ExceptionCodes.USER_PROFILE_SAVE_NOT_BE_NULL), ExceptionCodes.USER_PROFILE_SAVE_NOT_BE_NULL);
        }

        if (userPassword != null && StringUtils.isNotEmpty(userPassword.getPassword())) {
            try {
                user.changePassword(enc.encodePassword(userPassword.getPassword(),
                        saltSource.getSalt(new UserWrapper(user))));
            } catch (InvalidPasswordException ipe) {
                LOGGER.error(ipe.getMessage());
                throw new ServiceLayerException(translate(ExceptionCodes.USER_PASSWORD_INVALID,
                        userPassword.getPassword()), ExceptionCodes.USER_PASSWORD_INVALID);
            }
        }
        for (UserPreference preference : prefs) {
            confSrv.save(preference);
        }
        return save(user);
    }
}