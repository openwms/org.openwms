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

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.openwms.core.domain.AbstractEntity;
import org.openwms.core.domain.DomainObject;

/**
 * A SecurityObject is the superclass of Roles and Grants and combines common
 * used properties.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_ROLE")
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.STRING)
@Inheritance
@NamedQueries({
        @NamedQuery(name = SecurityObject.NQ_FIND_ALL, query = "select g from SecurityObject g"),
        @NamedQuery(name = SecurityObject.NQ_FIND_BY_UNIQUE_QUERY, query = "select g from SecurityObject g where g.name = ?1") })
public class SecurityObject extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = 7585736035228078754L;

    /**
     * Query to find all {@link SecurityObject}s.
     */
    public static final String NQ_FIND_ALL = "SecurityObject.findAll";

    /**
     * Query to find <strong>one</strong> {@link SecurityObject} by its natural
     * key. <li>Query parameter index <strong>1</strong> : The name of the
     * <code>SecurityObject</code> to search for.</li>
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
     * Unique name of the <code>SecurityObject</code>.
     */
    @Column(name = "C_NAME", unique = true)
    @OrderBy
    private String name;

    /**
     * Description of the <code>SecurityObject</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /* ----------------------------- methods ------------------- */
    /**
     * Accessed by persistence provider.
     */
    protected SecurityObject() {}

    /**
     * Create a new <code>SecurityObject</code> with a name.
     * 
     * @param name
     *            The name of the <code>SecurityObject</code>
     */
    public SecurityObject(String name) {
        this.name = name;
    }

    /**
     * Create a new <code>SecurityObject</code> with name and description.
     * 
     * @param name
     *            The name of the <code>SecurityObject</code>
     * @param description
     *            The description text of the <code>SecurityObject</code>
     */
    public SecurityObject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Returns the name.
     * 
     * @return The name of the <code>SecurityObject</code>
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
    protected void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description text.
     * 
     * @return The description of the <code>SecurityObject</code> as text
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the description for the <code>SecurityObject</code>.
     * 
     * @param description
     *            The description of the <code>SecurityObject</code> as text
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return version;
    }

}
