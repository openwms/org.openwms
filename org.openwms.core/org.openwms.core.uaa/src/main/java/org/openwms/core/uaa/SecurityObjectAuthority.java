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

import org.springframework.security.core.GrantedAuthority;

/**
 * A SecurityObjectAuthority.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 * @see org.springframework.security.core.GrantedAuthority
 */
class SecurityObjectAuthority implements GrantedAuthority {

    private static final long serialVersionUID = -7308040835860060411L;
    private final SecurityObject sObj;

    /**
     * Create a new SecurityObjectAuthority.
     * 
     * @param securityObject
     *            A {@link SecurityObject} to use as authority carrier.
     */
    public SecurityObjectAuthority(SecurityObject securityObject) {
        sObj = securityObject;
    }

    /**
     * {@inheritDoc}
     * 
     * Return the name of the wrapped {@link SecurityObject}.
     */
    @Override
    public String getAuthority() {
        return sObj.getName();
    }

    /**
     * {@inheritDoc}
     * 
     * Uses sObj for calculation.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((sObj == null) ? 0 : sObj.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * 
     * Uses sObj for comparison.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityObjectAuthority other = (SecurityObjectAuthority) obj;
        if (sObj == null) {
            if (other.sObj != null) {
                return false;
            }
        } else if (!sObj.equals(other.sObj)) {
            return false;
        }
        return true;
    }

    /**
     * Delegate to the wrapped SecurityObject.
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return sObj.toString();
    }

}