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
package org.openwms.common.domain.system.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * An {@link User} represents an user of the system.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "T_USER")
@NamedQueries( { @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
        @NamedQuery(name = "User.findAllOrdered", query = "SELECT u FROM User u ORDER BY u.username"),
        @NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = ?1") })
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique identifier of the {@link User}.
     */
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    /**
     * <code>true</code> if the {@link User} is authenticated by an external
     * system.
     */
    @Column(name = "EXTERN")
    private Boolean extern;

    /**
     * Date of last password change.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_PASSWORD_CHANGE")
    private Date lastPasswordChange;

    /**
     * <code>true</code> if this {@link User} is locked and cannot login.
     */
    @Column(name = "LOCKED")
    private Boolean locked;

    /**
     * Current password of the {@link User}.
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * <code>true</code> if this {@link User} is enabled.
     */
    @Column(name = "ENABLED")
    private Boolean enabled;

    /**
     * Date when the account expires. After expiration, no system access is
     * possible.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    /**
     * {@link User}s fullname. Doesn't have to be unique.
     */
    @Column(name = "FULLNAME")
    private String fullname;

    /* ------------------- collection mapping ------------------- */
    /**
     * More detail information about the {@link User}.
     */
    @Embedded
    private UserDetails userDetails = new UserDetails();

    /**
     * List of all granted {@link Role}s to this {@link User}.
     */
    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<Role>();

    /**
     * Password history of this {@link User}.
     */
    @OneToMany(mappedBy = "user")
    private List<UserPassword> passwords = new ArrayList<UserPassword>();

    /**
     * All {@link Preference}s of this {@link User}.
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Preference> preferences = new HashSet<Preference>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private User() {}

    public User(String username) {
        this.username = username;
    }

    /**
     * Return the technical key.
     * 
     * @return the id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Checks if the instance is transient.
     * 
     * @return - true: Entity is not present on the persistent storage.<br>
     *         - false : Entity already exists on the persistence storage
     */
    public boolean isNew() {
        return this.id == null;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getExternalUser() {
        return this.extern;
    }

    public void setExternalUser(Boolean externalUser) {
        this.extern = externalUser;
    }

    public Date getLastPasswordChange() {
        return this.lastPasswordChange;
    }

    public void setLastPasswordChange(Date lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Date getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return a {@link java.util.List} of the last passwords.
     */
    public List<UserPassword> getPasswords() {
        return this.passwords;
    }

    /**
     * Set a list of {@link UserPassword}s to this {@link User}.
     * 
     * @param passwords
     */
    public void setPasswords(List<UserPassword> passwords) {
        this.passwords = passwords;
    }

    /**
     * @return the userDetails
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails
     *            the userDetails to set
     */
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * Is this {@link User} an user authenticated through an external system?
     * 
     * @return - extern.
     */
    public Boolean getExtern() {
        return extern;
    }

    /**
     * Set this {@link User} as user authenticated through an external system.
     * 
     * @param extern
     *            - extern to set.
     */
    public void setExtern(Boolean extern) {
        this.extern = extern;
    }

    /**
     * Get all {@link Preference}s of this {@link User}.
     * 
     * @return the preferences.
     */
    public Set<Preference> getPreferences() {
        return preferences;
    }

    /**
     * Set all {@link Preference}s of this {@link User}.
     * 
     * @param preferences
     *            - The preferences to set.
     */
    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }
}
