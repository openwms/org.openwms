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
package org.openwms.web.flex.security;

import org.granite.messaging.service.security.SecurityServiceException;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.service.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
@Service(SecurityContextHelper.COMPONENT_NAME)
public class SecurityContextHelper {

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authManager;
    /** Springs service name. */
    public static final String COMPONENT_NAME = "securityContextHelper";

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

    /**
     * Delegate to Spring AuthemticationManager to authenticate the user with
     * username and password. Spring security exceptions are translated into
     * GraniteDS exceptions.
     * 
     * @param username
     *            Username of the user
     * @param password
     *            Password of the user
     * @return <code>true</code> if authenticated, otherwise <code>false</code>
     */
    public final boolean checkCredentials(String username, String password) {
        try {
            authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        } catch (DisabledException de) {
            throw SecurityServiceException.newAccessDeniedException(de.getMessage());
        } catch (LockedException le) {
            throw SecurityServiceException.newAccessDeniedException(le.getMessage());
        } catch (BadCredentialsException bce) {
            throw SecurityServiceException.newInvalidCredentialsException(bce.getMessage());
        } catch (Exception x) {
            return false;
        }
    }
}