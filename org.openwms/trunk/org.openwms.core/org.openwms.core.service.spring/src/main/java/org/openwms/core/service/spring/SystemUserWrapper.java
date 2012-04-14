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

import java.util.Collection;

import org.openwms.core.domain.system.usermanagement.SystemUser;
import org.openwms.core.domain.system.usermanagement.User;
import org.springframework.security.core.GrantedAuthority;

/**
 * A SystemUserWrapper.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class SystemUserWrapper extends UserWrapper {

    private static final long serialVersionUID = 8133894040625998710L;
    private String password;

    /**
     * Create a new SystemUserWrapper.
     * 
     * @param user
     */
    public SystemUserWrapper(User user) {
        super(user);
    }

    /**
     * Get the password.
     * 
     * @return this password or the password, set in the superclass
     * @see org.openwms.core.service.spring.UserWrapper#getPassword()
     */
    @Override
    public String getPassword() {
        return this.password == null ? super.getPassword() : this.password;
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
     * 
     * For the SystemUser account always add the
     * {@link SystemUser#SYSTEM_ROLE_NAME} to the collection of authorities.
     * 
     * @see org.openwms.core.service.spring.UserWrapper#addDefaultGrants(java.util.Collection)
     */
    @Override
    protected void addDefaultGrants(Collection<GrantedAuthority> authorities) {
        authorities.add(new SystemUserAuthority());
    }

    /**
     * {@inheritDoc}
     * 
     * Use password field in addition to inherited fields.
     * 
     * @see java.lang.Object#hashCode()
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
     * 
     * Use password field for comparison.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
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

    /**
     * A SystemUserAuthority.
     * 
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision: $
     * @since 0.2
     */
    class SystemUserAuthority implements GrantedAuthority {

        private static final long serialVersionUID = -5019648722137973204L;

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + SystemUser.SYSTEM_ROLE_NAME.hashCode();
            return result;
        }

        /**
         * {@inheritDoc}
         * 
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            return true;
        }

        /**
         * {@inheritDoc}
         * 
         * Return the System User's rolename.
         * 
         * @see org.springframework.security.core.GrantedAuthority#getAuthority()
         */
        @Override
        public String getAuthority() {
            return SystemUser.SYSTEM_ROLE_NAME;
        }
    }
}