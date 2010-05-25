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
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.exception.ServiceException;
import org.openwms.common.service.management.UserService;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
    private GenericDao<Role, Long> roleDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void uploadImageFile(String username, byte[] image) {
        User user = dao.findByUniqueId(username);
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
    public User save(User entity) {
        if (null == entity) {
            logger.warn("Calling save with null as argument");
            throw new ServiceException("The instance of the User to be removed is NULL");
        }
        if (entity.isNew()) {
            addEntity(entity);
        }
        return super.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(User user) {
        if (null == user) {
            logger.warn("Calling remove with null as argument");
            throw new ServiceException("The instance of the User to be remove is NULL");
        }
        if (user.isNew()) {
            logger.warn("The User instance that shall be removed is not persist yet, no need to remove");
        } else {
            user = super.save(user);
            super.remove(user);
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
    
    @Override
    public void removeRoles(List<Role> roles) {
        for (Role role : roles) {
            role = roleDao.save(role);
            roleDao.remove(role);
        }
    }
    
    @Override
    public Role saveRole(Role role) {
        return roleDao.save(role);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Role> findAllRoles() {
        return roleDao.findAll();
    }
    
}
