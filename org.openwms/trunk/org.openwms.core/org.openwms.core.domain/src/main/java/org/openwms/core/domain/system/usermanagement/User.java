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
package org.openwms.core.domain.system.usermanagement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;
import org.openwms.core.exception.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An User represents a human user of the system.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_USER")
@NamedQueries({
        @NamedQuery(name = User.NQ_FIND_ALL, query = "SELECT u FROM User u"),
        @NamedQuery(name = User.NQ_FIND_ALL_ORDERED, query = "SELECT u FROM User u ORDER BY u.username"),
        @NamedQuery(name = User.NQ_FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = ?1"),
        @NamedQuery(name = User.NQ_FIND_BY_USERNAME_PASSWORD, query = "SELECT u FROM User u WHERE u.username = :username and u.password = :password") })
public class User extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = -1116645053773805413L;

    /**
     * Logger instance.
     */
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    /**
     * Query to find all <code>User</code>s.
     */
    public static final String NQ_FIND_ALL = "User.findAll";

    /**
     * Query to find all <code>User</code>s sorted by userName.
     */
    public static final String NQ_FIND_ALL_ORDERED = "User.findAllOrdered";

    /**
     * Query to find <strong>one</strong> <code>User</code> by his userName. <li>
     * Query parameter index <strong>1</strong> : The userName of the
     * <code>User</code> to search for.</li>
     */
    public static final String NQ_FIND_BY_USERNAME = "User.findByUsername";

    /**
     * Query to find <strong>one</strong> <code>User</code> by his userName and
     * password. <li>Query parameter name <strong>username</strong> : The
     * userName of the <code>User</code> to search for.</li> <li>Query parameter
     * name <strong>password</strong> : The current password of the
     * <code>User</code> to search for.</li>
     */
    public static final String NQ_FIND_BY_USERNAME_PASSWORD = "User.findByUsernameAndPassword";

    /**
     * The number of passwords to be stored in the history. When an
     * <code>User</code> changes the password, the old password is saved in a
     * Collection. Default:{@value} .
     */
    public static final short NUMBER_STORED_PASSWORDS = 3;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique identifier of this <code>User</code> (not-null).
     */
    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    /**
     * <code>true</code> if the <code>User</code> is authenticated by an
     * external system, otherwise <code>false</code>. Default:{@value} .
     */
    @Column(name = "EXTERN")
    private boolean extern = false;

    /**
     * Date of the last password change.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_PASSWORD_CHANGE")
    private Date lastPasswordChange;

    /**
     * <code>true</code> if this <code>User</code> is locked and has not the
     * permission to login anymore. Default:{@value} .
     */
    @Column(name = "LOCKED")
    private boolean locked = false;

    /**
     * Current password of the <code>User</code>.
     */
    @Column(name = "C_PASSWORD")
    private String password;

    /**
     * <code>true</code> if the <code>User</code> is enabled. Default:{@value} .
     */
    @Column(name = "C_ENABLED")
    private boolean enabled = true;

    /**
     * Date when the account expires. After expiration, the <code>User</code>
     * cannot login anymore.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRATION_DATE")
    private Date expirationDate;

    /**
     * The <code>User</code>s fullname. Doesn't have to be unique.
     */
    @Column(name = "FULLNAME")
    private String fullname;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * More detail information about the <code>User</code>.
     */
    @Embedded
    private UserDetails userDetails = new UserDetails();

    /**
     * List of {@link Role}s granted to the <code>User</code>.
     */
    @ManyToMany(mappedBy = "users")
    private List<Role> roles = new ArrayList<Role>();

    /**
     * Password history of the <code>User</code>.
     */
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JoinTable(name = "COR_USER_PASSWORD", joinColumns = @JoinColumn(name = "USER_ID"), inverseJoinColumns = @JoinColumn(name = "PASSWORD_ID"))
    private List<UserPassword> passwords = new ArrayList<UserPassword>();

    /**
     * All {@link Preference}s of the <code>User</code>.
     */
    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "COR_USER_PREFERENCE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PREF_ID"))
    private Set<Preference> preferences = new HashSet<Preference>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected User() {
        super();
    }

    /**
     * Create a new <code>User</code> with an username.
     * 
     * @param username
     *            The unique name of the user
     */
    public User(String username) {
        this.username = username;
    }

    /**
     * Create a new <code>User</code> with an username.
     * 
     * @param username
     *            The unique name of the user
     * @param password
     *            The password of the user
     */
    protected User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Return the username of the <code>User</code> (unique).
     * 
     * @return The current username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Change the current username of the <code>User</code>.
     * 
     * @param username
     *            The new username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Is the <code>User</code> authenticated by an external system?
     * 
     * @return <code>true</code> if the <code>User</code> was authenticated by
     *         an external system, otherwise <code>false</code>.
     */
    public boolean isExternalUser() {
        return this.extern;
    }

    /**
     * Change the authentication method of the <code>User</code>.
     * 
     * @param externalUser
     *            <code>true</code> if the <code>User</code> was authenticated
     *            by an external system, otherwise <code>false</code>.
     */
    public void setExternalUser(boolean externalUser) {
        this.extern = externalUser;
    }

    /**
     * Return the date when the password was changed the last time.
     * 
     * @return The date when the password has changed recently.
     */
    public Date getLastPasswordChange() {
        return lastPasswordChange == null ? null : new Date(lastPasswordChange.getTime());
    }

    /**
     * This method does not change the password. It only exists for JavaBeans
     * compliance to support the generation of ActionScript classes.
     * 
     * @param lastPasswordChange
     *            Allowed to be null
     */
    public void setLastPasswordChange(Date lastPasswordChange) {
        // TODO [scherrer] : remove this and change the AS class
        // do nothing, only used for JavaBeans compliance to support generation
        // of AS classes.
    }

    /**
     * Check if the <code>User</code> is locked.
     * 
     * @return <code>true</code> when locked, otherwise <code>false</code>.
     */
    public boolean isLocked() {
        return this.locked;
    }

    /**
     * Lock the <code>User</code>.
     * 
     * @param locked
     *            <code>true</code> to lock the <code>User</code>,
     *            <code>false</code> to unlock.
     */
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Returns the current password of the <code>User</code>.
     * 
     * @return The current password as String.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Checks if the new password is a valid password and changes the password
     * for the <code>User</code>.
     * 
     * @param password
     *            The new password for the <code>User</code>.
     * @throws InvalidPasswordException
     *             in case changing the password is not allowed or the new
     *             password is invalid.
     */
    public void setPassword(String password) throws InvalidPasswordException {
        if (isPasswordValid(password)) {
            if (this.password != null && this.password.equals(password)) {
                logger.debug("Setting to the same as current password");
            } else {
                storeOldPassword(this.password);
                this.password = password;
                this.lastPasswordChange = new Date();
            }
        } else {
            throw new InvalidPasswordException("Password is not confirm with the defined password rules");
        }
    }

    /**
     * Check whether the new password is in the history list of passwords.
     * 
     * @param pw
     *            The password to verify
     * @return <code>true</code> if the password is valid, otherwise
     *         <code>false</code>
     */
    protected boolean isPasswordValid(String pw) {
        if (passwords.contains(new UserPassword(this, pw))) {
            return false;
        }
        return true;
    }

    private void storeOldPassword(String oldPassword) {
        if (oldPassword == null || oldPassword.isEmpty()) {
            if (logger.isDebugEnabled()) {
                logger.debug("In case the old password is null, dont store it in history");
            }
            return;
        }
        passwords.add(new UserPassword(this, oldPassword));
        if (passwords.size() > NUMBER_STORED_PASSWORDS) {
            Collections.sort(passwords, new Comparator<UserPassword>() {
                @Override
                public int compare(UserPassword o1, UserPassword o2) {
                    return o1.getPasswordChanged().compareTo(o2.getPasswordChanged());
                }
            });
            if (logger.isDebugEnabled()) {
                logger.debug("Remove old password from list:" + passwords.get(passwords.size() - 1));
            }
            UserPassword pw = passwords.get(passwords.size() - 1);
            pw.setUser(null);
            passwords.remove(passwords.get(0));
        }
    }

    /**
     * Determines whether the <code>User</code> is enabled.
     * 
     * @return <code>true</code> if the <code>User</code> is enabled, otherwise
     *         <code>false</code>.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Enable or disable the <code>User</code>.
     * 
     * @param enabled
     *            <code>true</code> when enabled, otherwise <code>false</code>.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Returns the date when the account expires.
     * 
     * @return The expiration date.
     */
    public Date getExpirationDate() {
        return expirationDate == null ? null : new Date(expirationDate.getTime());
    }

    /**
     * Change the timepoint until the account expires.
     * 
     * @param expirationDate
     *            The new expiration date to sez.
     */
    public void setExpirationDate(Date expirationDate) {
        if (expirationDate != null) {
            this.expirationDate = new Date(expirationDate.getTime());
        }
    }

    /**
     * Returns a list of all granted {@link Role}s.
     * 
     * @return The list of granted {@link Role}s.
     */
    public List<Role> getRoles() {
        return this.roles;
    }

    /**
     * Flatten roles and grants and return a list of all grants this user has
     * assigned.
     * 
     * @return A list of all grants
     */
    public List<SecurityObject> getGrants() {
        List<SecurityObject> grants = new ArrayList<SecurityObject>();
        for (Role role : getRoles()) {
            grants.addAll(role.getGrants());
        }
        return Collections.unmodifiableList(grants);
    }

    /**
     * Add a new {@link Role} to the list of all {@link Role}s.
     * 
     * @param role
     *            The new {@link Role} to add
     * @return <code>true</code> as specified by {@link Collection#add(Object)}
     */
    public boolean addRole(Role role) {
        return this.roles.add(role);
    }

    /**
     * Define the {@link Role}s of the <code>User</code>. Existing {@link Role}s
     * will be overridden.
     * 
     * @param roles
     *            The new list of {@link Role}s.
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * Returns the fullname of the <code>User</code>.
     * 
     * @return The current fullname
     */
    public String getFullname() {
        return this.fullname;
    }

    /**
     * Change the fullname of the <code>User</code>.
     * 
     * @param fullname
     *            The new name to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * Returns a list of recently used passwords.
     * 
     * @return A list of recently used passwords.
     */
    public List<UserPassword> getPasswords() {
        return this.passwords;
    }

    /**
     * The implementation does nothing. The method is defined to be compliant
     * with JavaBeans and to generate appropriate ActionScript classes.
     * 
     * @param passwords
     *            A list of {@link UserPassword}s
     */
    @Deprecated
    public void setPasswords(List<UserPassword> passwords) {
        // do nothing, only used for JavaBeans compliance to support generation
        // of AS classes.
    }

    /**
     * Return details of the <code>User</code>.
     * 
     * @return The userDetails.
     */
    public UserDetails getUserDetails() {
        return userDetails;
    }

    /**
     * Assign some details to the <code>User</code>.
     * 
     * @param userDetails
     *            The userDetails to be set.
     */
    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * Get all {@link Preference}s of the <code>User</code>.
     * 
     * @return A set of all {@link Preference}s.
     */
    public Set<Preference> getPreferences() {
        return preferences;
    }

    /**
     * Set all {@link Preference}s of the <code>User</code>. Already existing
     * {@link Preference}s will be overridden.
     * 
     * @param preferences
     *            A set of {@link Preference}s to be set.
     */
    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }
}
