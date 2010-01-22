/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.spring.management;

import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.domain.system.usermanagement.UserDetails;
import org.openwms.common.service.exception.ServiceException;
import org.openwms.common.service.management.UserService;
import org.openwms.common.service.spring.EntityServiceImpl;

/**
 * A UserServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class UserServiceImpl extends EntityServiceImpl<User, Long> implements UserService<User> {

    /**
     * {@inheritDoc}
     */
    // FIXME [scherrer] : Remove return value, when no user found exception is
    // thrown.
    @Override
    public boolean uploadImageFile(String username, byte[] image) {
        User user = dao.findByUniqueId(username);
        if (user.getUserDetails() == null) {
            user.setUserDetails(new UserDetails());
        }
        user.getUserDetails().setImage(image);
        dao.save(user);
        return true;
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
        return save(User.class, entity);
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
            user = save(User.class, user);
            remove(User.class, user);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getTemplate(String username) {
        logger.debug("Retrieve a User template instance for username:" + username);
        return new User(username);
    }

}
