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

import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.service.UserService;
import org.openwms.core.service.spring.UserWrapper;
import org.openwms.core.util.event.UserChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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
 * @version $Revision$
 * @since 0.1
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@Transactional
@Service("userDetailsService")
public class SecurityContextUserServiceImpl implements UserDetailsService, ApplicationListener<UserChangedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("#{ globals['system.user'] }")
    private String systemUser = "openwms";

    @Autowired
    @Qualifier("userDao")
    private GenericDao<User, Long> dao;

    @Autowired
    private UserService userService;

    @Autowired(required = false)
    @Qualifier("userCache")
    private UserCache userCache;

    @Autowired(required = false)
    @Qualifier("ehCache")
    private Ehcache cache;

    /**
     * {@inheritDoc}
     * 
     * @see org.springframework.context.ApplicationListener#onApplicationEvent(org.springframework.context.ApplicationEvent)
     */
    @Override
    public void onApplicationEvent(UserChangedEvent event) {
        if (logger.isDebugEnabled()) {
            logger.debug("User changed, clear cache");
        }
        if (cache != null) {
            cache.removeAll();
        }
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
            if (logger.isDebugEnabled()) {
                logger.debug("User found in cache");
            }
            return ud;
        } else {
            if (logger.isDebugEnabled()) {
                logger.debug("User not cached, try to resolve");
            }
        }
        User user = null;
        logger.debug("Systemuser = " + systemUser);
        if (systemUser.equals(username)) {
            logger.debug("Test system");
            user = userService.createSystemUser();
            logger.debug("got" + user);
        } else {
            user = dao.findByUniqueId(username);
            if (user == null) {
                logger.debug("User does not exist in database");
                throw new UsernameNotFoundException("User " + username + " not found");
            }
        }
        ud = new UserWrapper(user);
        userCache.putUserInCache(ud);
        return ud;
    }
}
