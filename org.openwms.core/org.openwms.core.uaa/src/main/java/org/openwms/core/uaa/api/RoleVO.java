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
package org.openwms.core.uaa.api;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.ameba.http.AbstractBase;

/**
 * A RoleVO.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
public class RoleVO extends AbstractBase implements Serializable {

    /**
     * Create a new RoleVO.
     */
    public RoleVO() {}

    /**
     * Get the immutable.
     * 
     * @return the immutable.
     */
    public Boolean getImmutable() {
        return immutable;
    }

    /**
     * Set the immutable.
     * 
     * @param immutable
     *            The immutable to set.
     */
    public void setImmutable(Boolean immutable) {
        this.immutable = immutable;
    }

    /**
     * Get the name.
     * 
     * @return the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the description.
     * 
     * @return the description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description.
     * 
     * @param description
     *            The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get the grants.
     * 
     * @return the grants.
     */
    public Set<SecurityObjectVO> getGrants() {
        return grants;
    }

    /**
     * Set the grants.
     * 
     * @param grants
     *            The grants to set.
     */
    public void setGrants(Set<SecurityObjectVO> grants) {
        this.grants = grants;
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
     * Set the id.
     * 
     * @param id
     *            The id to set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Set the version.
     * 
     * @param version
     *            The version to set.
     */
    public void setVersion(long version) {
        this.version = version;
    }

    /**
     * Get the version.
     * 
     * @return the version.
     */
    public long getVersion() {
        return version;
    }

    /**
     * Get the users.
     * 
     * @return the users.
     */
    public Set<RoleUserVO> getUsers() {
        return users;
    }

    /**
     * Set the users.
     * 
     * @param users
     *            The users to set.
     */
    public void setUsers(Set<RoleUserVO> users) {
        this.users = users;
    }

    private Long id;
    private Boolean immutable;
    private String name;
    private String description;
    private Set<RoleUserVO> users = new HashSet<>();
    private Set<SecurityObjectVO> grants = new HashSet<>();
    private long version;
}
