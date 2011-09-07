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
package org.openwms.web.flex.security;

import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.service.UserHolder;
import org.openwms.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * A SecurityContextHelper. Has some helper methods around the SecurityContext.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: 1409 $
 * @since 0.1
 */
@Service("securityContextHelper")
public class SecurityContextHelper {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    /**
     * Helper method for rich clients to extract the current User from the
     * SecurityContext.
     * <p>
     * Therefore it is assumed that Spring Security's {@link Authentication}
     * object stores the current {@link User} object as Principal. Whenever the
     * type of Principal does not match to our own User class, <code>null</code>
     * is returned.
     * </p>
     * 
     * @return The User logged in, or <code>null</code>
     * @see org.openwms.core.service.UserHolder
     * @see org.springframework.security.core.Authentication
     */
    public final User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() instanceof UserHolder) {
            return ((UserHolder) auth.getPrincipal()).getUser();
        }
        return null;
    }

    public final boolean checkCredentials(String username, char[] password) {
        return userService.checkCredentials(new UserPassword(new User(username), password.toString()));
    }
}
