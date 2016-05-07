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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openwms.core.validation.AssertUtils;

/**
 * A Role is a group of <code>User</code>s. Basically more than one <code>User</code> belong to a Role. Security access policies are
 * assigned to <code>Role</code>s instead of <code>User</code>s.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @GlossaryTerm
 * @see SecurityObject
 * @see User
 * @since 0.1
 */
@Entity
@DiscriminatorValue("ROLE")
@NamedQueries({
        @NamedQuery(name = Role.NQ_FIND_ALL, query = "select distinct(r) from Role r left join fetch r.users left join fetch r.grants left join fetch r.preferences order by r.name"),
        @NamedQuery(name = Role.NQ_FIND_BY_UNIQUE_QUERY, query = "select r from Role r where r.name = ?1")})
public class Role extends SecurityObject implements Serializable {

    private static final long serialVersionUID = -4133301834284932221L;
    /**
     * Whether or not this <code>Role</code> is immutable. Immutable <code>Role</code>s can't be modified.
     */
    @Column(name = "C_IMMUTABLE")
    private Boolean immutable = false;

    /* ------------------- collection mapping ------------------- */
    /**
     * All {@link User}s assigned to this <code>Role</code>.
     */
    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "COR_ROLE_USER_JOIN", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users = new HashSet<User>();

    /**
     * All {@link SecurityObject}s assigned to the <code>Role</code>.
     */
    @ManyToMany(cascade = {CascadeType.REFRESH})
    @JoinTable(name = "COR_ROLE_ROLE_JOIN", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "GRANT_ID"))
    private Set<SecurityObject> grants = new HashSet<SecurityObject>();

    /**
     * The default prefix String for each created <code>Role</code>. Name is * * * {@value} .
     */
    public static final String ROLE_PREFIX = "ROLE_";

    /**
     * Query to find all <code>Role</code>s. Name is {@value} .
     */
    public static final String NQ_FIND_ALL = "Role.findAll";

    /**
     * Query to find <strong>one</strong> <code>Role</code> by its natural key.
     * <p>
     * <li>Query parameter index <strong>1</strong> : The name of the <code>Role</code> to search for.</li> <p> Name is {@value} . </p>
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "Role.findByRolename";

    /**
     * A builder class to construct <code>Role</code> instances.
     *
     * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
     * @version $Revision$
     * @since 0.1
     */
    public static class Builder {

        private Role role;

        /**
         * Create a new Builder.
         *
         * @param name The name of the <code>Role</code>
         * @throws IllegalArgumentException when name is <code>null</code> or empty
         */
        public Builder(String name) {
            AssertUtils.isNotEmpty(name, "Not allowed to create a Role with an empty name");
            role = new Role(name);
            role.immutable = false;
        }

        /**
         * Add a description text to the <code>Role</code>.
         *
         * @param description as String
         * @return the builder instance
         */
        public Builder withDescription(String description) {
            role.setDescription(description);
            return this;
        }

        /**
         * Set the <code>Role</code> to be immutable.
         *
         * @return the builder instance
         */
        public Builder asImmutable() {
            role.immutable = true;
            return this;
        }

        /**
         * Finally build and return the <code>Role</code> instance.
         *
         * @return the constructed <code>Role</code>
         */
        public Role build() {
            return role;
        }
    }

    /* ----------------------------- methods ------------------- */

    /**
     * Accessed by the persistence provider.
     */
    @SuppressWarnings("unused")
    private Role() {
        super();
    }

    /**
     * Create a new <code>Role</code> with a name.
     *
     * @param name The name of the <code>Role</code>
     * @throws IllegalArgumentException when name is <code>null</code> or empty
     */
    public Role(String name) {
        super(name);
        AssertUtils.isNotEmpty(name, "Not allowed to create a Role with an empty name");
    }

    /**
     * Create a new <code>Role</code> with a name and a description.
     *
     * @param name The name of the <code>Role</code>
     * @param description The description text of the <code>Role</code>
     * @throws IllegalArgumentException when name is <code>null</code> or empty
     */
    public Role(String name, String description) {
        super(name, description);
        AssertUtils.isNotEmpty(name, "Not allowed to create a Role with an empty name");
    }

    /**
     * Get the immutable.
     *
     * @return the immutable.
     */
    public Boolean getImmutable() {
        return immutable;
    }

    public Class<?> getBKeyClass() {
        return String.class;
    }

    /**
     * Return an unmodifiable Set of all {@link User}s assigned to the <code>Role</code>.
     *
     * @return A Set of all {@link User}s assigned to the <code>Role</code>
     */
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Add an existing {@link User} to the <code>Role</code>.
     *
     * @param user The {@link User} to be added
     * @return <code>true</code> if the {@link User} was new in the collection of {@link User}s, otherwise <code>false</code>
     * @throws IllegalArgumentException if user is <code>null</code>
     */
    public boolean addUser(User user) {
        AssertUtils.notNull(user, "User to add must not be null");
        return users.add(user);
    }

    /**
     * Remove a {@link User} from the <code>Role</code>.
     *
     * @param user The {@link User} to be removed
     * @throws IllegalArgumentException if user is <code>null</code>
     */
    public void removeUser(User user) {
        AssertUtils.notNull(user, "User to remove must not be null");
        users.remove(user);
    }

    /**
     * Set all {@link User}s belonging to this <code>Role</code>.
     *
     * @param users A Set of {@link User}s to be assigned to the <code>Role</code>
     * @throws IllegalArgumentException if users is <code>null</code>
     */
    public void setUsers(Set<User> users) {
        AssertUtils.notNull(users, "Set of Users must not be null");
        this.users = users;
    }

    /**
     * Return an unmodifiable Set of all {@link SecurityObject}s belonging to the <code>Role</code>.
     *
     * @return A Set of all {@link SecurityObject}s belonging to this Role
     */
    public Set<SecurityObject> getGrants() {
        return Collections.unmodifiableSet(grants);
    }

    /**
     * Add an existing {@link SecurityObject} to the <code>Role</code>.
     *
     * @param grant The {@link SecurityObject} to be added to the <code>Role</code>.
     * @return <code>true</code> if the {@link SecurityObject} was new to the collection of {@link SecurityObject}s, otherwise
     * <code>false</code>
     * @throws IllegalArgumentException if grant is <code>null</code>
     */
    public boolean addGrant(SecurityObject grant) {
        AssertUtils.notNull(grant, "Grant to add must not be null");
        return grants.add(grant);
    }

    /**
     * Add an existing {@link SecurityObject} to the <code>Role</code>.
     *
     * @param grant The {@link SecurityObject} to be added to the <code>Role</code>
     * @return <code>true</code> if the {@link SecurityObject} was successfully removed from the Set of {@link SecurityObject}s, otherwise
     * <code>false</code>
     * @throws IllegalArgumentException if grant is <code>null</code>
     */
    public boolean removeGrant(SecurityObject grant) {
        AssertUtils.notNull(grant, "Grant to remove must not be null");
        return grants.remove(grant);
    }

    /**
     * Add an existing {@link SecurityObject} to the <code>Role</code>.
     *
     * @param grants A list of {@link SecurityObject}s to be removed from the <code>Role</code>
     * @return <code>true</code> if the {@link SecurityObject} was successfully removed from the Set of {@link SecurityObject}s, otherwise
     * <code>false</code>
     * @throws IllegalArgumentException if <code>grants</code> is <code>null</code>
     */
    public boolean removeGrants(List<? extends SecurityObject> grants) {
        AssertUtils.notNull(grants, "Grants to remove must not be null");
        return this.grants.removeAll(grants);
    }

    /**
     * Set all {@link SecurityObject}s assigned to the <code>Role</code>. Already existing {@link SecurityObject}s will be removed.
     *
     * @param grants A Set of {@link SecurityObject}s to be assigned to the <code>Role</code>
     * @throws IllegalArgumentException if grants is <code>null</code>
     */
    public void setGrants(Set<SecurityObject> grants) {
        AssertUtils.notNull(grants, "Set of Grants must not be null");
        this.grants = grants;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Delegates to the superclass and uses the hashCode of the String ROLE for calculation.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = super.hashCode();
        result = prime * result + "ROLE".hashCode();
        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Does not delegate to the {@link SecurityObject#equals(Object)} and uses the name for comparison.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Role)) {
            return false;
        }
        Role other = (Role) obj;
        if (this.getName() == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!this.getName().equals(other.getName())) {
            return false;
        }
        return true;
    }
}