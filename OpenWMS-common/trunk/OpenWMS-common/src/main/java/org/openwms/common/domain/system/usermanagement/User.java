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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries( { 
	@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.findAllOrdered", query = "SELECT u FROM User u ORDER BY u.username"),
	@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = ?1") })
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	/**
	 * Unique identifier of the <code>User</code>.
	 */
	@Column(name = "USERNAME", unique = true, nullable = false)
	private String username;

	/**
	 * User is authenticated by an external system.
	 */
	@Column(name = "EXTERN")
	private Boolean extern;

	/**
	 * Date of last password change.
	 */
	@Column(name = "LAST_PASSWORD_CHANGE")
	private Date lastPasswordChange;

	/**
	 * Is this <code>User</code> locked?
	 */
	@Column(name = "LOCKED")
	private Boolean locked;

	/**
	 * <code>User</code>s current password.
	 */
	@Column(name = "PASSWORD")
	private String password;

	/**
	 * Is this <code>User</code> enabled?
	 */
	@Column(name = "ENABLED")
	private Boolean enabled;

	/**
	 * Date when the <code>User</code> expires. After expiration, no system access is possible.
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
	private UserDetails userDetails = new UserDetails();

	/**
	 * List of all granted <code>Role</code>s to this <code>User</code>.
	 */
	@ManyToMany(mappedBy = "users")
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * Password history of this <code>User</code>.
	 */
	@OneToMany(mappedBy = "user")
	private List<UserPassword> passwords = new ArrayList<UserPassword>();

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
	private User() {}

	public User(String username) {
		this.username = username;
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
	 * Returns true if this is a transient object.
	 * 
	 * @return
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
	 * Is this <code>User</code> an user authenticated through an external system?
	 * 
	 * @return - extern.
	 */
	public Boolean getExtern() {
		return extern;
	}

	/**
	 * Set this <code>User</code> as user authenticated through an external system.
	 * 
	 * @param extern
	 *            - extern to set.
	 */
	public void setExtern(Boolean extern) {
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
	 * @param preferences
	 *            - The preferences to set.
	 */
	public void setPreferences(Set<Preference> preferences) {
		this.preferences = preferences;
	}
}
