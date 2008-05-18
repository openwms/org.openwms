/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	 * Unique identifier of the <code>User</code>.
	 */
	@Id
	@Column(name = "USERNAME")
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

	/**
	 * More detail information about the <code>User</code>.
	 */
	@OneToOne()
	@JoinColumn(name = "userDetails_USERNAME", referencedColumnName = "USERNAME")
	private UserDetails userDetails;

	/**
	 * List of all granted <code>Role</code>s to this <code>User</code>
	 */
	@ManyToMany(mappedBy = "users")
	private List<Role> roles = new ArrayList<Role>();

	/**
	 * Password history of this <code>User</code>
	 */
	@OneToMany(mappedBy = "user")
	private List<UserPassword> passwords;

	public User() {
		super();
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
}
