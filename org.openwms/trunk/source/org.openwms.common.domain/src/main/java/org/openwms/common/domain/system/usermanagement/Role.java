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
 * A {@link Role} is grouping multiple {@link User}s concerning security
 * aspects.
 * <p>
 * Security access policies were assigned to {@link Role}s instead of
 * {@link User}s.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "ROLE")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Name of the {@link Role}. Recommended prefix id 'ROLE_'
     */
    @Column(name = "ROLENAME", unique = true)
    private String rolename;

    /**
     * {@link Role} description
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Version field.
     */
    @Version
    private long version;

    /* ------------------- collection mapping ------------------- */
    /**
     * All {@link User}s belonging to this {@link Role}.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "ROLE_USER", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private Set<User> users = new HashSet<User>();

    /**
     * All {@link Preference}s linked to this {@link Role}.
     */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "ROLE_PREFERENCE", joinColumns = @JoinColumn(name = "ROLE_ID"), inverseJoinColumns = @JoinColumn(name = "PREFERENCE_ID"))
    private Set<Preference> preferences = new HashSet<Preference>();

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    @SuppressWarnings("unused")
    private Role() {}

    /**
     * Create a new Role with a name.
     * 
     * @param rolename
     */
    public Role(String rolename) {
        this.rolename = rolename;
    }

    /**
     * Create a new Role with name and description.
     * 
     * @param rolename
     * @param description
     */
    public Role(String rolename, String description) {
        this.rolename = rolename;
        this.description = description;
    }

    /**
     * Return the technical key.
     * 
     * @return
     */
    public Long getId() {
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
     * Set the description for this {@link Role}.
     * 
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get all {@link User}s belonging to this {@link Role}.
     * 
     * @return
     */
    public Set<User> getUsers() {
        return Collections.unmodifiableSet(users);
    }

    /**
     * Add a existing {@link User} to this {@link Role}.
     * 
     * @param user
     * @return
     */
    public boolean addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        return this.users.add(user);
    }

    /**
     * Set all {@link User}s belonging to this {@link Role}.
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
     * Get all {@link Preference}s of this {@link Role}.
     * 
     * @return
     */
    public Set<Preference> getPreferences() {
        return preferences;
    }

    /**
     * Set all {@link Preference}s belonging to this {@link Role}.
     * 
     * @param preferences
     */
    public void setPreferences(Set<Preference> preferences) {
        this.preferences = preferences;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return - Version field.
     */
    public long getVersion() {
        return version;
    }
}
