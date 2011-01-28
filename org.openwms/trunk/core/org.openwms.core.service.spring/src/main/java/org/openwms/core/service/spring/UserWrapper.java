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

import java.util.ArrayList;
import java.util.Collection;

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.domain.system.usermanagement.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * An UserWrapper is used as an adapter between {@link Role}s or SecurityObjects
 * and Spring's {@link GrantedAuthority} objects.
 * 
 * @author <a href="mailto:russelltina@users.sourceforge.net">Tina Russell</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.usermanagement.SecurityObject
 * @see org.springframework.security.core.GrantedAuthority
 * @see org.springframework.security.core.userdetails.UserDetails
 */
public class UserWrapper implements UserDetails {

    private static final long serialVersionUID = -3974637197176782047L;
    private User user;

    /**
     * Create a new UserWrapper.
     * 
     * @param user
     *            The User to wrap
     */
    public UserWrapper(User user) {
        this.user = user;
        this.user.getRoles().size();
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
     * @return the authorities, sorted by natural key (never <code>null</code>)
     */
    @SuppressWarnings("serial")
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final Role role : user.getRoles()) {
            authorities.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return role.getName();
                }
            });
        }
        return authorities;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getPassword()
     * @return the password (never <code>null</code>)
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
     * @return the username (never <code>null</code>)
     */
    @Override
    public String getUsername() {
        return user.getUsername();
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
     * @return <code>true</code> if the user's account is valid (ie
     *         non-expired), <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
     * @return <code>true</code> if the user is not locked, <code>false</code>
     *         otherwise
     */
    @Override
    public boolean isAccountNonLocked() {
        return !user.isLocked();
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
     * @return <code>true</code> if the user's credentials are valid (ie
     *         non-expired), <code>false</code> if no longer valid (ie expired)
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
     * @return <code>true</code> if the user is enabled, <code>false</code>
     *         otherwise
     */
    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
