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
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.exception.ServiceLayerException;
import org.ameba.i18n.Translator;
import org.openwms.core.annotation.FireAfterTransaction;
import org.openwms.core.configuration.ConfigurationService;
import org.openwms.core.configuration.UserPreference;
import org.openwms.core.event.UserChangedEvent;
import org.openwms.core.exception.ExceptionCodes;
import org.openwms.core.exception.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

/**
 * An UserServiceImpl is a Spring supported transactional implementation of a general {@link UserService}. Using Spring 2 annotation support
 * autowires collaborators, therefore XML configuration becomes obsolete. This class is marked with Springs {@link Service} annotation to
 * benefit from Springs exception translation interceptor. Traditional CRUD operations are delegated to an {@link UserRepository}.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @see UserRepository
 * @since 0.1
 */
@Validated
@TxService
class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private SecurityObjectRepository securityObjectDao;
    @Autowired
    private ConfigurationService confSrv;
    @Autowired
    private PasswordEncoder enc;
    @Autowired
    private Translator translator;
    @Value("${system.user}")
    private String systemUsername;
    @Value("${system.password}")
    private String systemPassword;

    /**
     * {@inheritDoc}
     *
     * @throws EntityNotFoundException when no User with {@code id} found
     */
    @Override
    @FireAfterTransaction(events = {UserChangedEvent.class})
    public void uploadImageFile(Long id, byte[] image) {
        User user = repository.findOne(id);
        if (user == null) {
            throw new NotFoundException(translator.translate(ExceptionCodes.ENTITY_NOT_EXIST, id), ExceptionCodes.ENTITY_NOT_EXIST);
        }
        user.getUserDetails().setImage(image);
        repository.save(user);
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
        Assert.notNull(entity, ExceptionCodes.USER_SAVE_NOT_BE_NULL);
        return repository.save(entity);
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

    @Override
    public User create(User user) {
        return null;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return null;
    }

    @Override
    public void remove(String username) {
        repository.delete(repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException(translator.translate(ExceptionCodes.USER_NOT_EXIST, username))));
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
            throw new ServiceLayerException(translator.translate(ExceptionCodes.USER_PASSWORD_SAVE_NOT_BE_NULL), ExceptionCodes.USER_PASSWORD_SAVE_NOT_BE_NULL);
        }
        User entity = repository.findByUsername(userPassword.getUser().getUsername()).orElseThrow(() -> new EntityNotFoundException(translator.translate(ExceptionCodes.USER_NOT_EXIST, userPassword.getUser()
                .getUsername())));
        try {
            entity.changePassword(enc.encode(userPassword.getPassword()));
            repository.save(entity);
        } catch (InvalidPasswordException ipe) {
            LOGGER.error(ipe.getMessage());
            throw new ServiceLayerException(translator.translate(ExceptionCodes.USER_PASSWORD_INVALID, userPassword.getUser()
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
            throw new ServiceLayerException(translator.translate(ExceptionCodes.USER_PROFILE_SAVE_NOT_BE_NULL), ExceptionCodes.USER_PROFILE_SAVE_NOT_BE_NULL);
        }

        if (userPassword != null && StringUtils.hasText(userPassword.getPassword())) {
            try {
                user.changePassword(enc.encode(userPassword.getPassword()));
            } catch (InvalidPasswordException ipe) {
                LOGGER.error(ipe.getMessage());
                throw new ServiceLayerException(translator.translate(ExceptionCodes.USER_PASSWORD_INVALID,
                        userPassword.getPassword()), ExceptionCodes.USER_PASSWORD_INVALID);
            }
        }
        for (UserPreference preference : prefs) {
            confSrv.save(preference);
        }
        return save(user);
    }

    /**
     * Find and return a collection of all existing entities of type {@code T}.
     *
     * @return All entities or an empty collection, never {@literal null}
     */
    @Override
    public Collection<User> findAll() {
        return null;
    }

    /**
     * Find an entity instance by the given technical key {@code id},
     *
     * @param aLong The technical key
     * @return The instance
     * @throws NotFoundException may be thrown if no entity found
     */
    @Override
    public User findById(Long aLong) {
        return null;
    }
}