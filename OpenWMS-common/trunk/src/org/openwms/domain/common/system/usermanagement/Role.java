/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * The <code>Role</code> entity is grouping multiple <code>User</code>s
 * concerning security aspects.
 * <p>
 * Security access policies were assigned to <code>Role</code>s instead to
 * <code>User</code>s
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "ROLE")
public class Role implements Serializable, IRole {

	private static final long serialVersionUID = 1L;

	/**
	 * Primary key.
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue
	private long id;

	/**
	 * Name of the <code>Role</code>, e.g. name of the <code>Role</code>
	 * Recommended prefix id 'ROLE_'
	 */
	@Column(name = "ROLENAME", unique = true)
	private String rolename;

	/**
	 * <code>Role</code> description
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * Version field
	 */
	@Version
	private long version;

	/* ------------------- collection mapping ------------------- */
	/**
	 * All <code>User</code>s belonging to this <code>Role</code>
	 */
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Set<User> users = new HashSet<User>();

	/**
	 * All <code>Preference</code>s linked to this <code>Role</code>
	 */
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "ROLE_PREFERENCE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PREFERENCE_ID"))
	private Set<Preference> preferences = new HashSet<Preference>();

	/* ----------------------------- methods ------------------- */
	/**
	 * Accessed by persistence provider.
	 */
	@SuppressWarnings("unused")
	private Role(){}
	
	/**
	 * 
	 * Create a new Role.
	 * 
	 * @param rolename
	 */
	public Role(String rolename) {
		this.rolename = rolename;
	}

	/**
	 * 
	 * Create a new Role.
	 * 
	 * @param rolename
	 * @param description
	 */
	public Role(String rolename, String description) {
		this.rolename = rolename;
		this.description = description;
	}

	/**
	 * Return the Id.
	 * 
	 * @return
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Get the rolename.
	 * 
	 * @return the rolename.
	 */
	public String getRolename() {
		return rolename;
	}

	/**
	 * Return the description.
	 * 
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description for this <code>Role</code>.
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Get all <code>User</code>s belonging to this <code>Role</code>.
	 * 
	 * @return
	 */
	public Set<User> getUsers() {
		return Collections.unmodifiableSet(users);
	}
	
	public boolean addUser(User user){
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		return this.users.add(user);
	}

	/**
	 * Set all <code>User</code>s belonging to this <code>Role</code>.
	 * 
	 * @param users
	 */
	public void setUsers(Set<User> users) {
		if (users == null) {
			throw new IllegalArgumentException("Users cannot be null.");
		}
		this.users = users;
	}

	/**
	 * Get all <code>Preference</code>s of this <code>Role</code>.
	 * 
	 * @return
	 */
	public Set<Preference> getPreferences() {
		return preferences;
	}

	/**
	 * Set all <code>Preference</code>s belonging to this <code>Role</code>.
	 * 
	 * @param preferences
	 */
	public void setPreferences(Set<Preference> preferences) {
		this.preferences = preferences;
	}

	/**
	 * JPA optimistic locking: Returns version field.
	 * 
	 * @return
	 */
	public long getVersion() {
		return version;
	}
}
