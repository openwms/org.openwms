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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.ameba.integration.jpa.BaseEntity;
import org.openwms.core.exception.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * An User represents a human user of the system. Typically an User is assigned to one or more {@code Roles} to define security
 * constraints. Users can have their own configuration settings in form of {@code UserPreferences} and certain user details,
 * encapsulated in an {@code UserDetails} object that tend to be extended by projects.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @GlossaryTerm
 * @see UserDetails
 * @see UserPassword
 * @see org.openwms.core.uaa.Role
 * @since 0.1
 */
@Entity
@Table(name = "COR_USER")
public class User extends BaseEntity implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
    /** Unique identifier of this User (not nullable). */
    @Column(name = "C_USERNAME", unique = true, nullable = false)
    @NotNull
    @Size(min = 1)
    private String username;
    /** {@code true} if the User is authenticated by an external system, otherwise {@code false}. */
    @Column(name = "C_EXTERN")
    private boolean extern = false;
    /** Date of the last password change. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_LAST_PASSWORD_CHANGE")
    private Date lastPasswordChange;
    /**
     * {@code true} if this User is locked and has not the permission to login anymore. This field is set by the backend application, e.g.
     * when the expirationDate of the account has expired.
     */
    @Column(name = "C_LOCKED")
    private boolean locked = false;
    /** The User's current password (only kept transient). */
    @Transient
    private String password;
    /** The User's current password. */
    @Column(name = "C_PASSWORD")
    private String persistedPassword;
    /** {@code true} if the User is enabled. This field can be managed by the UI application to lock an User manually. */
    @Column(name = "C_ENABLED")
    private boolean enabled = true;
    /** Date when the account expires. After account expiration, the User cannot login anymore. */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "C_EXPIRATION_DATE")
    private Date expirationDate;
    /** The User's fullname. Doesn't have to be unique. */
    @Column(name = "C_FULLNAME")
    private String fullname;

    /* ------------------- collection mapping ------------------- */
    /** More detail information of the User. */
    @Embedded
    private UserDetails userDetails = new UserDetails();

    /**
     * List of {@link Role}s assigned to the User. In a JPA context eager loaded.
     *
     * @see javax.persistence.FetchType#EAGER
     */
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private List<Role> roles = new ArrayList<>();

    /** Password history of the User. */
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinTable(name = "COR_USER_PASSWORD_JOIN", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "PASSWORD_ID"))
    private List<UserPassword> passwords = new ArrayList<>();

    /**
     * The number of passwords to be stored in the password history. When an User changes the password, the old password is stored in a
     * Collection. Default: {@value}.
     */
    public static final short NUMBER_STORED_PASSWORDS = 3;

    /* ----------------------------- constructors ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected User() {
        super();
        loadLazy();
    }

    /**
     * Create a new User with an username.
     *
     * @param username The unique name of the user
     * @throws IllegalArgumentException when username is {@literal null} or empty
     */
    public User(String username) {
        super();
        Assert.hasText(username, "Not allowed to create an User with an empty username");
        this.username = username;
        loadLazy();
    }

    /**
     * Create a new User with an username.
     *
     * @param username The unique name of the user
     * @param password The password of the user
     * @throws IllegalArgumentException when username or password is {@literal null} or empty
     */
    protected User(String username, String password) {
        super();
        Assert.hasText(username, "Not allowed to create an User with an empty username");
        Assert.hasText(password, "Not allowed to create an User with an empty password");
        this.username = username;
        this.password = password;
    }

    /* ----------------------------- methods ------------------- */
    /**
     * After load, the saved password is copied to the transient one. The transient one can be overridden by the application to force a
     * password change.
     */
    @PostLoad
    public void postLoad() {
        loadLazy();
    }

    protected void loadLazy() {
        password = persistedPassword;
    }

    /**
     * Return the unique username of the User.
     *
     * @return The current username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Change the username of the User.
     *
     * @param username The new username to set
     */
    protected void setUsername(String username) {
        this.username = username;
    }

    /**
     * Is the User authenticated by an external system?
     *
     * @return {@literal true} if so, otherwise {@literal false}
     */
    public boolean isExternalUser() {
        return extern;
    }

    /**
     * Change the authentication method of the User.
     *
     * @param externalUser {@literal true} if the User was authenticated by an external system, otherwise
     * {@literal false}.
     */
    public void setExternalUser(boolean externalUser) {
        extern = externalUser;
    }

    /**
     * Return the date when the password has changed recently.
     *
     * @return The date when the password has changed recently
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange == null ? null : new Date(lastPasswordChange.getTime());
    }

    /**
     * Check if the User is locked.
     *
     * @return {@literal true} if locked, otherwise {@literal false}
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * Lock the User.
     *
     * @param locked {@literal true} to lock the User, {@literal false} to unlock
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Returns the current password of the User.
     *
     * @return The current password as String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Checks if the new password is a valid and change the password of this User.
     *
     * @param password The new password of this User
     * @throws InvalidPasswordException in case changing the password is not allowed or the new password is not valid
     */
    public void changePassword(String password) throws InvalidPasswordException {
        // FIXME [scherrer] : Setting the same password should fail
        if (persistedPassword != null && persistedPassword.equals(password)) {
            LOGGER.debug("Trying to set the new password equals to the current password");
            return;
        }
        if (isPasswordValid(password)) {
            storeOldPassword(this.password);
            persistedPassword = password;
            this.password = password;
            lastPasswordChange = new Date();
        } else {
            throw new InvalidPasswordException("Password is not confirm with defined rules");
        }
    }

    /**
     * Checks whether the password is going to change.
     *
     * @return {@literal true} when {@code password} is different to the originally persisted one, otherwise {@literal false}
     */
    public boolean hasPasswordChanged() {
        return (persistedPassword.equals(password));
    }

    /**
     * Check whether the new password is in the history of former passwords.
     *
     * @param pwd The password to verify
     * @return {@literal true} if the password is valid, otherwise {@literal false}
     */
    protected boolean isPasswordValid(String pwd) {
        return !passwords.contains(new UserPassword(this, pwd));
    }

    private void storeOldPassword(String oldPassword) {
        if (oldPassword == null || oldPassword.isEmpty()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("If the old password is null, do not store it in history");
            }
            return;
        }
        passwords.add(new UserPassword(this, oldPassword));
        if (passwords.size() > NUMBER_STORED_PASSWORDS) {
            Collections.sort(passwords, new PasswordComparator());
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Remove the old password from the history: " + passwords.get(passwords.size() - 1));
            }
            UserPassword pw = passwords.get(passwords.size() - 1);
            pw.setUser(null);
            passwords.remove(pw);
        }
    }

    /**
     * Determines whether the User is enabled or not.
     *
     * @return {@literal true} if the User is enabled, otherwise {@literal false}
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Enable or disable the User.
     *
     * @param enabled {@literal true} when enabled, otherwise {@literal false}
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Return the date when the account expires.
     *
     * @return The expiration date
     */
    public Date getExpirationDate() {
        return expirationDate == null ? null : new Date(expirationDate.getTime());
    }

    /**
     * Change the date when the account expires.
     *
     * @param expDate The new expiration date to set
     */
    public void setExpirationDate(Date expDate) {
        expirationDate = expDate;
    }

    /**
     * Returns a list of granted {@link Role}s.
     *
     * @return The list of granted {@link Role}s
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Flatten {@link Role}s and {@link Grant}s and return an unmodifiable list of all {@link Grant}s assigned to this User.
     *
     * @return A list of all {@link Grant}s
     */
    public List<SecurityObject> getGrants() {
        List<SecurityObject> grants = new ArrayList<>();
        for (Role role : getRoles()) {
            grants.addAll(role.getGrants());
        }
        return Collections.unmodifiableList(grants);
    }

    /**
     * Add a new {@link Role} to the list of {@link Role}s.
     *
     * @param role The new {@link Role} to add
     * @return see {@link java.util.Collection#add(Object)}
     */
    public boolean addRole(Role role) {
        return roles.add(role);
    }

    /**
     * Set the {@link Role}s of this User. Existing {@link Role}s will be overridden.
     *
     * @param roles The new list of {@link Role}s
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Return the fullname of the User.
     *
     * @return The current fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * Change the fullname of the User.
     *
     * @param fullname The new fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Return a list of recently used passwords.
     *
     * @return A list of recently used passwords
     */
    public List<UserPassword> getPasswords() {
        return passwords;
    }

    /**
     * Return the details of the User.
     *
     * @return The userDetails
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

    /**
     * Assign some details to the User.
     *
     * @param userDetails The userDetails to set
     */
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Does not call the superclass. Uses the username for calculation.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Uses the username for comparison.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
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
     * Returns the business key.
     *
     * @return Username
     */
    @Override
    public String toString() {
        return getUsername();
    }

    /**
     * A PasswordComparator sorts UserPassword by date ascending.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.2
     */
    static class PasswordComparator implements Comparator<UserPassword>, Serializable {

        /**
         * {@inheritDoc}
         *
         * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
         */
        @Override
        public int compare(UserPassword o1, UserPassword o2) {
            return o2.getPasswordChanged().compareTo(o1.getPasswordChanged());
        }
    }
}