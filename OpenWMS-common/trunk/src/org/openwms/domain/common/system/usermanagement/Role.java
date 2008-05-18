/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.domain.common.system.usermanagement;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/**
 * The <code>Role</code> entity is grouping multiple <code>User</code>s concerning
 * security aspects. Security access policies were assigned to <code>Role</code>s
 * instead to <code>User</code>s
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Unique <code>Role</code> identifier, e.g. name of the <code>Role</code>
	 * Recommended prefix id 'ROLE_'
	 */
	@Id
	@Column(name = "ID")
	private String id;

	/**
	 * <code>Role</code> description
	 */
	@Column(name = "DESCRIPTION")
	private String description;

	/**
	 * All <code>User</code>s linked to this <code>Role</code>
	 */
	@ManyToMany
	@JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
	private Set<User> users = new HashSet<User>();

	/**
	 * All <code>Preference</code>s linked to this <code>Role</code>
	 */
	@ManyToMany
	@JoinTable(name = "ROLE_PREFERENCE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PREFERENCE_ID"))
	private Set<Preference> preferences = new HashSet<Preference>();

	public Role() {
		super();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Preference> getPreferences() {
		return preferences;
	}

	public void setPreferences(Set<Preference> preferences) {
		this.preferences = preferences;
	}
}
