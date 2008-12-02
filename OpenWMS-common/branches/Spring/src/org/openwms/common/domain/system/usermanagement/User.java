/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity <code>User</code> describes basic functionality of an user.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "USER")
public class User implements Serializable, IUser {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;

	/**
	 * Unique identifier of the <code>User</code>.
	 */
	@Column(name = "USERNAME", unique = true)
	private String username;

	/**
	 * User is authenticated by an external system.
	 */
	@Column(name = "EXTERN")
	private boolean extern;

	/**
	 * Date of last password change.
	 */
	@Column(name = "LAST_PASSWORD_CHANGE")
	private Date lastPasswordChange;

	/**
	 * Is this <code>User</code> locked?
	 */
	@Column(name = "LOCKED")
	private boolean locked;

	/**
	 * <code>User</code>s current password.
	 */
	@Column(name = "PASSWORD")
	private String password;

	/**
	 * Is this <code>User</code> enabled?
	 */
	@Column(name = "ENABLED")
	private boolean enabled;

	/**
	 * Date when the <code>User</code> expires. After expiration, no system
	 * access is possible.
	 */
	@Column(name = "EXPIRATION_DATE")
	private Date expirationDate;

	/**
	 * <code>User</code>s fullname. Don't have to be unique.
	 */
	@Column(name = "FULLNAME")
	private String fullname;

	/* ------------------- collection mapping ------------------- */
	/**
	 * More detail information about the <code>User</code>.
	 */
	@Embedded
	private UserDetails userDetails;

	/**
	 * List of all granted <code>Role</code>s to this <code>User</code>.
	 */
	@ManyToMany(mappedBy = "users")
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * Password history of this <code>User</code>.
	 */
	@OneToMany(mappedBy = "user")
	private List<UserPassword> passwords;

	/**
	 * All <code>Preference</code>s of this <code>User</code>.
	 */
	@OneToMany(cascade = CascadeType.ALL)
	private Set<Preference> preferences = new HashSet<Preference>();

	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private User() {
	}

	public User(String username) {
		this.username = username;
	}

	/**
	 * Get the id.
	 * 
	 * @return the id.
	 */
	public long getId() {
		return id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean getExternalUser() {
		return this.extern;
	}

	public void setExternalUser(boolean externalUser) {
		this.extern = externalUser;
	}

	public Date getLastPasswordChange() {
		return this.lastPasswordChange;
	}

	public void setLastPasswordChange(Date lastPasswordChange) {
		this.lastPasswordChange = lastPasswordChange;
	}

	public boolean getLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
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
	 * @return a <code>List</code> of the last passwords.
	 */
	public List<UserPassword> getPasswords() {
		return this.passwords;
	}

	/**
	 * Set a list of <code>UserPassword</code>s to this <code>User</code>.
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
	 * Is this <code>User</code> an user authenticated through an external
	 * system?
	 * 
	 * @return - extern.
	 */
	public boolean isExtern() {
		return extern;
	}

	/**
	 * Set this <code>User</code> as user authenticated through an external
	 * system.
	 * 
	 * @param extern -
	 *            extern to set.
	 */
	public void setExtern(boolean extern) {
		this.extern = extern;
	}

	/**
	 * Get all <code>Preference</code>s of this <code>User</code>.
	 * 
	 * @return the preferences.
	 */
	public Set<Preference> getPreferences() {
		return preferences;
	}

	/**
	 * Set all <code>Preference</code>s of this <code>User</code>.
	 * 
	 * @param preferences -
	 *            The preferences to set.
	 */
	public void setPreferences(Set<Preference> preferences) {
		this.preferences = preferences;
	}
}
