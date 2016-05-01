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

import java.io.Serializable;
import java.util.Collection;

import org.openwms.core.domain.system.usermanagement.User;
import org.springframework.security.core.GrantedAuthority;

/**
 * A SystemUserWrapper.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class SystemUserWrapper extends UserWrapper implements Serializable {

    private static final long serialVersionUID = 8133894040625998710L;
    private String password;

    /**
     * Create a new SystemUserWrapper.
     * 
     * @param user
     *            The wrapped user
     */
    public SystemUserWrapper(User user) {
        super(user);
    }

    /**
     * Get the password.
     * 
     * @return this password or the password, set in the superclass
     */
    @Override
    public String getPassword() {
        return password == null ? super.getPassword() : password;
    }

    /**
     * Set the password.
     * 
     * @param password
     *            The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * For the SystemUser account always add the
     * {@link org.openwms.core.domain.system.usermanagement.SystemUser#SYSTEM_ROLE_NAME}
     * to the collection of authorities.
     */
    @Override
    protected void addDefaultGrants(Collection<GrantedAuthority> authorities) {
        authorities.add(new SystemUserAuthority());
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Use password field in addition to inherited fields.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Use password field for comparison.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        SystemUserWrapper other = (SystemUserWrapper) obj;
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        return true;
    }
}