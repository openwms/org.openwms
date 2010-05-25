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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A SecurityObject.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Entity
@Table(name = "ROLE")
@Inheritance
@NamedQueries( {
        @NamedQuery(name = SecurityObject.NQ_FIND_ALL, query = "select g from SecurityObject g"),
        @NamedQuery(name = SecurityObject.NQ_FIND_BY_UNIQUE_QUERY, query = "select g from SecurityObject g where g.name = ?1") })
public class SecurityObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Query to find all {@link SecurityObject}s.
     */
    public static final String NQ_FIND_ALL = "SecurityObject.findAll";
    /**
     * Query to find <strong>one</strong> {@link SecurityObject} by its natural
     * key.
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "SecurityObject.findByRolename";

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Name of the SecurityObject.
     */
    @Column(name = "NAME", unique = true)
    private String name;

    /**
     * SecurityObject description.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Version field.
     */
    @Version
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected SecurityObject() {}

    /**
     * Create a new SecurityObject with a name.
     * 
     * @param name
     *            The name of the SecurityObject
     */
    public SecurityObject(String name) {
        this.name = name;
    }

    /**
     * Create a new SecurityObject with name and description.
     * 
     * @param name
     *            The name of the SecurityObject
     * @param description
     *            The description text of the SecurityObject
     */
    public SecurityObject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Return the technical key.
     * 
     * @return The unique technical key
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Get the name.
     * 
     * @return The name of the SecurityObject
     */
    public String getName() {
        return name;
    }

    /**
     * Return the description.
     * 
     * @return The description of the SecurityObject as text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description for this SecurityObject.
     * 
     * @param description
     *            The description of the SecurityObject as text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return The version field
     */
    public long getVersion() {
        return version;
    }

}
