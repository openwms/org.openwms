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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.openwms.core.util.validation.AssertUtils;

/**
 * A Role is grouping multiple {@link User}s regarding security aspects.
 * <p>
 * Security access policies are assigned to Roles instead of to {@link User}s
 * directly.
 * </p>
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@DiscriminatorValue("ROLE")
@NamedQueries({
        @NamedQuery(name = Role.NQ_FIND_ALL, query = "select distinct(r) from Role r left join fetch r.users left join fetch r.grants order by r.name"),
        @NamedQuery(name = Role.NQ_FIND_BY_UNIQUE_QUERY, query = "select r from Role r where r.name = ?1") })
public class Role extends SecurityObject {

    private static final long serialVersionUID = -4133301834284932221L;

    /**
     * Query to find all <code>Role</code>s.
     */
    public static final String NQ_FIND_ALL = "Role.findAll";

    /**
     * Query to find <strong>one</strong> <code>Role</code> by its natural key.
     * <li>Query parameter index <strong>1</strong> : The name of the
     * <code>Role</code> to search for.</li>
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "Role.findByRolename";

    /* ------------------- collection mapping ------------------- */
    /**
     * All {@link User}s assigned to the <code>Role</code>.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "COR_ROLE_USER", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users = new HashSet<User>();

    /**
     * All {@link Preference}s linked to the <code>Role</code>.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "COR_ROLE_PREFERENCE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PREFERENCE_ID"))
    private Set<Preference> preferences = new HashSet<Preference>();

    /**
     * All {@link SecurityObject}s assigned to the <code>Role</code>.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "COR_ROLE_ROLE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "GRANT_ID"))
    private Set<SecurityObject> grants = new HashSet<SecurityObject>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by the persistence provider.
     */
    @SuppressWarnings("unused")
    private Role() {}

    /**
     * Create a new <code>Role</code> with a name.
     * 
     * @param name
     *            The name of the <code>Role</code>
     */
    public Role(String name) {
        super(name);
    }

    /**
     * Create a new <code>Role</code> with a name and a description.
     * 
     * @param name
     *            The name of the <code>Role</code>
     * @param description
     *            The description text of the <code>Role</code>
     */
    public Role(String name, String description) {
        super(name, description);
        AssertUtils.notNull(name, "Role name must not be null");
        AssertUtils.notNull(description, "Role description must not be null");
    }

    /**
     * Return all {@link User}s assigned to the <code>Role</code>.
     * 
     * @return A set of all {@link User}s belonging to the <code>Role</code>
     */
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Add an existing {@link User} to the <code>Role</code>.
     * 
     * @param user
     *            The {@link User} to be added
     * @return <code>true</code> if the {@link User} was new in the collection
     *         of {@link User}s, otherwise <code>false</code>
     * @throws IllegalArgumentException
     *             if user is <code>null</code>
     */
    public boolean addUser(User user) {
        AssertUtils.notNull(user, "User to add must not be null");
        return this.users.add(user);
    }

    /**
     * Remove a {@link User} from the <code>Role</code>.
     * 
     * @param user
     *            The {@link User} to be removed
     * @throws IllegalArgumentException
     *             if user is <code>null</code>
     */
    public void removeUser(User user) {
        AssertUtils.notNull(user, "User to remove must not be null");
        this.users.remove(user);
    }

    /**
     * Set all {@link User}s belonging to this <code>Role</code>. Already
     * existing {@link User}s will be removed.
     * 
     * @param users
     *            A set of {@link User}s to be assigned to the <code>Role</code>
     * @throws IllegalArgumentException
     *             if users is <code>null</code>
     */
    public void setUsers(Set<User> users) {
        AssertUtils.notNull(users, "Set of Users must not be null");
        this.users = users;
    }

    /**
     * Return all {@link Preference}s of the <code>Role</code>.
     * 
     * @return A set of all {@link Preference}s belonging to the
     *         <code>Role</code>
     */
    public Set<Preference> getPreferences() {
        return preferences;
    }

    /**
     * Set all {@link Preference}s belonging to the <code>Role</code>. Already
     * existing {@link Preference}s will be removed.
     * 
     * @param preferences
     *            A set of {@link Preference}s to be assigned to the
     *            <code>Role</code>
     */
    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    /**
     * Return all {@link SecurityObject}s belonging to the <code>Role</code>.
     * 
     * @return A set of all {@link SecurityObject}s belonging to this Role
     */
    public Set<SecurityObject> getGrants() {
        return Collections.unmodifiableSet(grants);
    }

    /**
     * Add an existing {@link SecurityObject} to the <code>Role</code>.
     * 
     * @param grant
     *            The {@link SecurityObject} to be added to the
     *            <code>Role</code>.
     * @return <code>true</code> if the {@link SecurityObject} was new to the
     *         collection of {@link SecurityObject}s, otherwise
     *         <code>false</code>
     */
    public boolean addGrant(SecurityObject grant) {
        AssertUtils.notNull(grant, "Grant to add must not be null");
        return this.grants.add(grant);
    }

    /**
     * Add an existing {@link SecurityObject} to the <code>Role</code>.
     * 
     * @param grant
     *            The {@link SecurityObject} to be added to the
     *            <code>Role</code>
     * @return <code>true</code> if the {@link SecurityObject} was successfully
     *         removed from the set of {@link SecurityObject}s, otherwise
     *         <code>false</code>
     */
    public boolean removeGrant(SecurityObject grant) {
        AssertUtils.notNull(grant, "Grant to remove must not be null");
        return this.grants.remove(grant);
    }

    /**
     * Set all {@link SecurityObject}s belonging to the <code>Role</code>.
     * Already existing {@link SecurityObject}s will be removed.
     * 
     * @param grants
     *            A set of {@link SecurityObject}s to be assigned to the
     *            <code>Role</code>
     */
    public void setGrants(Set<SecurityObject> grants) {
        AssertUtils.notNull(grants, "Set of Grants must not be null");
        this.grants = grants;
    }
}
