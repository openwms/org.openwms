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
package org.openwms.client.security;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * An AuthResource is a simplified credential object that has to be
 * authenticated. The authentication information itself is stored inside an
 * authentication token that can be validated. Only the existence of the
 * <tt>token</tt> does not imply that the AuthResource is valid.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public final class AuthResource implements Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String password;
    private String token;
    private List<String> grants;

    /**
     * Create a new AuthResource.
     */
    public AuthResource() {}

    /**
     * Get the username.
     * 
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the username.
     * 
     * @param username
     *            The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Get the password.
     * 
     * @return the password.
     */
    public String getPassword() {
        return password;
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
     * Reset password to an empty String.
     */
    @JsonIgnore
    public void resetPassword() {
        this.password = "";
    }

    /**
     * Get the token.
     * 
     * @return the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * Set the token.
     * 
     * @param token
     *            The token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Get the grants.
     * 
     * @return the grants.
     */
    public List<String> getGrants() {
        return grants;
    }

    /**
     * Set the grants.
     * 
     * @param grants
     *            The grants to set.
     */
    public void setGrants(List<String> grants) {
        this.grants = grants;
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((grants == null) ? 0 : grants.hashCode());
        result = prime * result + ((token == null) ? 0 : token.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    /**
     * @see java.lang.Object#equals(java.lang.Object)
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
        AuthResource other = (AuthResource) obj;
        if (password == null) {
            if (other.password != null) {
                return false;
            }
        } else if (!password.equals(other.password)) {
            return false;
        }
        if (grants == null) {
            if (other.grants != null) {
                return false;
            }
        } else if (!grants.equals(other.grants)) {
            return false;
        }
        if (token == null) {
            if (other.token != null) {
                return false;
            }
        } else if (!token.equals(other.token)) {
            return false;
        }
        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AuthResources [username=" + username + ", token=" + token + "]";
    }
}
