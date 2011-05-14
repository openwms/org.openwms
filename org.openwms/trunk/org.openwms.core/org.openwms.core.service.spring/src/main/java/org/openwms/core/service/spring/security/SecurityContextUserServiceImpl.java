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
package org.openwms.core.service.spring.security;

import net.sf.ehcache.Ehcache;

import org.openwms.core.domain.system.usermanagement.SystemUser;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.service.spring.UserWrapper;
import org.openwms.core.util.event.UserChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
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
 * the openwms.org scheme and wrap into security objects.
 * </p>
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision: $
 * @since 0.1
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Service
@Transactional
public class SecurityContextUserServiceImpl implements UserDetailsService, ApplicationListener<UserChangedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String systemUser = "openwms";
    private String systemPassword = "openwms";

    @Autowired
    @Qualifier("userDao")
    private GenericDao<User, Long> dao;

    @Autowired(required = false)
    private UserCache userCache;

    @Autowired(required = false)
    @Qualifier("ehCache")
    private Ehcache cache;

    /**
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(UserChangedEvent event) {
        if (logger.isDebugEnabled() && cache != null) {
            logger.debug("User changed, clear cache");
            cache.removeAll();
        }
    }

    /**
     * Set the systemUser.
     * 
     * @param systemUser
     *            The systemUser to set.
     */
    public void setSystemUser(String systemUser) {
        this.systemUser = systemUser;
    }

    /**
     * Set the systemPassword.
     * 
     * @param systemPassword
     *            The systemPassword to set.
     */
    public void setSystemPassword(String systemPassword) {
        this.systemPassword = systemPassword;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
     * 
     * @param username
     *            User's username to search for
     * @return A wrapper object
     * @throws UsernameNotFoundException
     *             in case the User was not found or the password was not valid
     */
    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) {

        UserDetails ud = userCache.getUserFromCache(username);
        if (ud != null) {
            logger.debug("User found in cache");
            return ud;
        } else {
            logger.debug("User not cached, try to resolve");
        }
        User user = null;

        if (systemUser.equals(username)) {
            try {
                user = new SystemUser(systemUser);
                user.setPassword(systemPassword);
            } catch (InvalidPasswordException e) {
                logger.debug("SystemUser tried to login with a password that is not valid");
                throw new UsernameNotFoundException("User with username not found:" + username);
            }
        } else {
            user = dao.findByUniqueId(username);
            if (user == null) {
                logger.debug("User does not exist in database");
                throw new UsernameNotFoundException("User with username not found:" + username);
            }
        }
        ud = new UserWrapper(user);
        userCache.putUserInCache(ud);
        logger.debug("User found, return the wrapper");
        return ud;
    }
}
