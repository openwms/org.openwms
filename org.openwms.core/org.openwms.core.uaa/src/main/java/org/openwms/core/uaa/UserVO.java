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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * A UserDTO.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1698422005139820938L;

    private Long id;
    @JsonProperty("externalUser")
    private boolean extern;
    @NotNull
    @Size(min = 1)
    private String username;
    private Date lastPasswordChange;
    private boolean locked;
    private String password = "";
    private boolean enabled;
    private Date expirationDate;
    private String fullname = "";
    private UserDetailsVO userDetails = new UserDetailsVO();
    private String token;
    private long version;

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
     * Get the extern.
     * 
     * @return the extern.
     */
    public boolean isExtern() {
        return extern;
    }

    /**
     * Set the extern.
     * 
     * @param extern
     *            The extern to set.
     */
    public void setExtern(boolean extern) {
        this.extern = extern;
    }

    /**
     * Get the lastPasswordChange.
     * 
     * @return the lastPasswordChange.
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange;
    }

    /**
     * Set the lastPasswordChange.
     * 
     * @param lastPasswordChange
     *            The lastPasswordChange to set.
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    /**
     * Get the locked.
     * 
     * @return the locked.
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Set the locked.
     * 
     * @param locked
     *            The locked to set.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
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
     * Get the enabled.
     * 
     * @return the enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the enabled.
     * 
     * @param enabled
     *            The enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get the expirationDate.
     * 
     * @return the expirationDate.
     */
    public Date getExpirationDate() {
        return expirationDate;
    }

    /**
     * Set the expirationDate.
     * 
     * @param expirationDate
     *            The expirationDate to set.
     */
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    /**
     * Get the fullname.
     * 
     * @return the fullname.
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Set the fullname.
     * 
     * @param fullname
     *            The fullname to set.
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Get the userDetails.
     * 
     * @return the userDetails.
     */
    public UserDetailsVO getUserDetails() {
        return userDetails;
    }

    /**
     * Set the userDetails.
     * 
     * @param userDetails
     *            The userDetails to set.
     */
    public void setUserDetails(UserDetailsVO userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * Get the id.
     * 
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Set the id.
     * 
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get the version.
     * 
     * @return the version.
     */
    public long getVersion() {
        return version;
    }

    /**
     * Set the version.
     * 
     * @param version
     *            The version to set.
     */
    public void setVersion(long version) {
        this.version = version;
    }
}
