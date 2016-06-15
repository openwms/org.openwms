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

import net.sf.ehcache.Ehcache;
import org.ameba.annotation.TxService;
import org.openwms.core.event.UserChangedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

/**
 * A SecurityContextUserServiceImpl extends Spring {@link UserDetailsService} to
 * read <code>User</code>s and <code>Role</code>s from the persistent storage
 * and wraps them into security objects.
 *
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version 0.1
 * @since 0.1
 * @see org.springframework.security.core.userdetails.UserDetailsService
 */
@TxService
class SecurityContextUserServiceImpl implements UserDetailsService, ApplicationListener<UserChangedEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityContextUserServiceImpl.class);

    @Value("${system.user}")
    private String systemUsername = SystemUser.SYSTEM_USERNAME;
    @Autowired
    private UserService userService;
    @Autowired(required = false)
    private UserCache userCache;
    @Autowired(required = false)
    private Ehcache cache;
    @Autowired
    private PasswordEncoder enc;
    @Autowired
    private SaltSource saltSource;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(UserChangedEvent event) {
        if (cache != null) {
            LOGGER.debug("UserChangedEvent -> clear cache");
            cache.removeAll();
        }
    }

    /**
     * {@inheritDoc}
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
        if (null == ud) {
            if (systemUsername.equals(username)) {
                User user = userService.createSystemUser();
                ud = new SystemUserWrapper(user);
                ((SystemUserWrapper) ud).setPassword(enc.encodePassword(user.getPassword(), saltSource.getSalt(ud)));
            } else {
                try {
                    ud = new UserWrapper(userService.findByUsername(username).get());
                } catch(Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                    throw new UsernameNotFoundException(String.format("User with username %s not found", username));
                }
            }
            userCache.putUserInCache(ud);
        }
        return ud;
    }
}