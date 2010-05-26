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
package org.openwms.common.service.spring.security;

import org.openwms.common.domain.system.usermanagement.SystemUser;
import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.exception.ServiceException;
import org.openwms.common.service.exception.UserNotFoundException;
import org.openwms.common.service.spring.UserWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A SecurityContextUserServiceImpl.
 * <p>
 * An extended Spring {@link UserDetailsService} to read Users and Roles from
 * the openwms scheme and wrap into security objects.
 * </p>
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Service
@Transactional
public class SecurityContextUserServiceImpl implements UserDetailsService {

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userDao")
    protected GenericDao<User, Long> dao;

    @Autowired
    protected UserCache userCache;

    private String systemUser;
    private String systemPassword;

    /**
     * Set the systemUser.
     * 
     * @param systemUser
     *            The systemUser to set.
     */
    @Required
    public void setSystemUser(String systemUser) {
        this.systemUser = systemUser;
    }

    /**
     * Set the systemPassword.
     * 
     * @param systemPassword
     *            The systemPassword to set.
     */
    @Required
    public void setSystemPassword(String systemPassword) {
        this.systemPassword = systemPassword;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails ud = userCache.getUserFromCache(username);

        if (ud != null) {
            logger.debug("User found in cache");
            return ud;
        }
        User user = null;

        if (systemUser.equals(username)) {
            user = new SystemUser(systemUser, systemPassword);
        } else {
            try {
                user = loadUser(username);
            }
            catch (ServiceException se) {
                logger.debug("User does not exist in database");
                throw new UsernameNotFoundException("User with username not found:" + username, se);
            }
        }
        ud = new UserWrapper(user);
        userCache.putUserInCache(ud);
        logger.debug("User found, return the wrapper");
        return ud;
    }

    /**
     * Tries to resolve a {@link User} from the persistence layer accessing the
     * userDao.
     * 
     * @throws org.openwms.common.service.exception.UserNotFoundException
     *             when no user was found with <code>username</code>
     * @see org.openwms.common.service.management.UserService#loadUser(java.lang.String)
     */
    private User loadUser(String username) {
        User user = dao.findByUniqueId(username);
        if (user == null) {
            throw new UserNotFoundException("With username:" + username);
        }
        return user;
    }

}
