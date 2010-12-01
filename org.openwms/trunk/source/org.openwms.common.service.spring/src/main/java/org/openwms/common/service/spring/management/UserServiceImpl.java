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
package org.openwms.common.service.spring.management;

import java.util.List;

import org.openwms.common.domain.system.usermanagement.Role;
import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.domain.system.usermanagement.UserDetails;
import org.openwms.common.domain.system.usermanagement.UserPassword;
import org.openwms.common.exception.InvalidPasswordException;
import org.openwms.common.integration.system.usermanagement.RoleDao;
import org.openwms.common.integration.system.usermanagement.UserDao;
import org.openwms.common.service.exception.ServiceRuntimeException;
import org.openwms.common.service.exception.UserNotFoundException;
import org.openwms.common.service.management.UserService;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * An UserServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.service.spring.EntityServiceImpl
 */
@Service
@Transactional
public class UserServiceImpl extends EntityServiceImpl<User, Long> implements UserService<User> {

    @Autowired
    @Qualifier("userDao")
    protected UserDao dao;

    @Autowired
    @Qualifier("roleDao")
    private RoleDao roleDao;

    /**
     * {@inheritDoc}
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
     */
    @Override
    public User save(User user) {
        if (null == user) {
            logger.warn("Calling save with null as argument");
            throw new ServiceRuntimeException("The instance of the User to be removed is NULL");
        }
        if (user.isNew()) {
            addEntity(user);
        }
        return super.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(User user) {
        if (null == user) {
            logger.warn("Calling remove with null as argument");
            throw new ServiceRuntimeException("The instance of the User to be remove is NULL");
        }
        if (user.isNew()) {
            logger.warn("The User instance that shall be removed is not persist yet, no need to remove");
        } else {
            dao.remove(dao.findById(user.getId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getTemplate(String username) {
        return new User(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeRoles(List<Role> roles) {
        for (Role r : roles) {
            Role role = roleDao.findById(r.getId());
            roleDao.remove(role);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Role saveRole(Role role) {
        return roleDao.save(role);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean changeUserPassword(UserPassword userPassword) {
        if (userPassword.getPassword() == null || userPassword.getPassword().isEmpty()) {
            logger.warn("Null or an empty String is not allowed for a new password");
            throw new ServiceRuntimeException("Null or an empty String is not allowed for a new password");
        }
        User entity = dao.findByUniqueId(userPassword.getUser().getUsername());
        if (entity == null) {
            throw new UserNotFoundException("User not found, probably not persisted before");
        }
        try {
            entity.setPassword(userPassword.getPassword());
            return true;
        } catch (InvalidPasswordException ipe) {
            return false;
        }
    }

}
