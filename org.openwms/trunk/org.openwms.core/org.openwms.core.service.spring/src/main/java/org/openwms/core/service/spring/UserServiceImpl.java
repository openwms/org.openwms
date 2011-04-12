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

import java.util.List;

import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserDetails;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.integration.UserDao;
import org.openwms.core.service.UserService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.service.exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An UserServiceImpl is a Spring supported transactional implementation of a
 * general {@link UserService}. Using Spring 2 annotations support autowiring
 * collaborators like DAOs therefore XML configuration becomes obsolete. This
 * class is marked with Springs {@link Service} annotation to benefit from
 * Springs exception translation interceptor. Traditional CRUD operations are
 * delegated to an {@link UserDao}.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.integration.system.usermanagement.UserDao;
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Instance of an {@link UserDao}. <i>Autowired</i>.
     */
    @Autowired
    private UserDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * @throws UserNotFoundException
     *             when no User was found with this username.
     */
    @Override
    public void uploadImageFile(String username, byte[] image) {
        User user = dao.findByUniqueId(username);
        if (user == null) {
            throw new UserNotFoundException("User with username [" + username + "] not found");
        }
        if (user.getUserDetails() == null) {
            user.setUserDetails(new UserDetails());
        }
        user.getUserDetails().setImage(image);
        dao.save(user);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ServiceRuntimeException
     *             when user is <code>null</code>
     */
    @Override
    public User save(User user) {
        if (null == user) {
            logger.warn("Calling save with null as argument");
            throw new ServiceRuntimeException("The instance of the User to be removed is NULL");
        }
        if (user.isNew()) {
            dao.persist(user);
        }
        return dao.save(user);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ServiceRuntimeException
     *             when user is <code>null</code>
     */
    @Override
    public void remove(User user) {
        if (null == user) {
            logger.warn("Calling remove with null as argument");
            throw new ServiceRuntimeException("The instance of the User to be remove is NULL");
        }
        if (user.isNew()) {
            logger.warn("The User instance to be removed is not persist yet, no need to remove");
        } else {
            dao.remove(dao.findById(user.getId()));
        }
    }

    /**
     * {@inheritDoc}
     * 
     * Marked as <code>readOnly</code> transactional method.
     */
    @Override
    @Transactional(readOnly = true)
    public User getTemplate(String username) {
        return new User(username);
    }

    /**
     * {@inheritDoc}
     * 
     * @throws ServiceRuntimeException
     *             when userPassword is <code>null</code>
     * @throws UserNotFoundException
     *             when no User exists
     */
    @Override
    public boolean changeUserPassword(UserPassword userPassword) {
        if (userPassword == null) {
            logger.warn("No userPassword set");
            throw new ServiceRuntimeException("Error while changing the user password, new value was null");
        }
        User entity = dao.findByUniqueId(userPassword.getUser().getUsername());
        if (entity == null) {
            throw new UserNotFoundException("User not found, probably not persisted before");
        }
        try {
            entity.setPassword(userPassword.getPassword());
            return true;
        } catch (InvalidPasswordException ipe) {
            logger.info(ipe.getMessage());
            return false;
        }
    }

}
