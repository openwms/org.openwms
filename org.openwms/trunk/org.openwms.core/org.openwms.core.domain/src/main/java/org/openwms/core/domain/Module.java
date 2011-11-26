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
package org.openwms.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.openwms.core.annotation.GlossaryTerm;
import org.openwms.core.util.validation.AssertUtils;

/**
 * A Module is a definition of a Flex Module and used to persist some basic
 * information about a module. For example if the Flex Module should be loaded
 * on application startup.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.AbstractEntity
 * @see org.openwms.core.domain.DomainObject
 */
@GlossaryTerm
@Entity
@Table(name = "COR_MODULE")
@NamedQueries({ @NamedQuery(name = Module.NQ_FIND_ALL, query = "select m from Module m order by m.startupOrder"),
        @NamedQuery(name = Module.NQ_FIND_BY_UNIQUE_QUERY, query = "select m from Module m where m.moduleName = ?1") })
public class Module extends AbstractEntity implements DomainObject<Long> {

    private static final long serialVersionUID = 7358306395032979355L;

    /**
     * Query to find all <code>Module</code>s. Name is {@value} .
     */
    public static final String NQ_FIND_ALL = "Module.findAll";

    /**
     * Query to find <strong>one</strong> <code>Module</code> by its natural
     * key. <li>
     * Query parameter index <strong>1</strong> : The <code>moduleName</code> of
     * the <code>Module</code> to search for</li><br />
     * Name is {@value} .
     */
    public static final String NQ_FIND_BY_UNIQUE_QUERY = "Module.findByModuleName";

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * Unique name of the <code>Module</code> (natural key, unique, not-null).
     */
    @Column(name = "MODULE_NAME", unique = true, nullable = false)
    private String moduleName;

    /**
     * URL from where to load this <code>Module</code> (unique, not-null).
     */
    @Column(name = "URL", unique = true, nullable = false)
    private String url;

    /**
     * Flag used on the client side to store whether the <code>Module</code> is
     * actually loaded or not. It's a dynamic value and not persisted.
     */
    @Transient
    private boolean loaded = false;

    /**
     * <code>true</code> when the <code>Module</code> should be loaded on
     * application startup.
     */
    @Column(name = "LOAD_ON_STARTUP")
    private boolean loadOnStartup = true;

    /**
     * Defines the startup order compared with other Modules. Modules with a
     * lower <code>startupOrder</code> are loaded before this one.
     */
    @Column(name = "STARTUP_ORDER")
    @OrderBy
    private int startupOrder;

    /**
     * A description text of this <code>Module</code>.
     */
    @Column(name = "DESCRIPTION")
    private String description = "--";

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /**
     * Create a new Module.
     */
    protected Module() {
        super();
    }

    /**
     * Create a new <code>Module</code>.
     * 
     * @param moduleName
     *            The unique <code>Module</code> name
     * @param url
     *            The unique URL
     * @throws IllegalArgumentException
     *             in case the new moduleName is <code>null</code> or empty
     */
    public Module(String moduleName, String url) {
        super();
        AssertUtils.isNotEmpty(moduleName, "Not allowed to set the moduleName to null or an empty String");
        this.moduleName = moduleName;
        this.url = url;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNew() {
        return this.id == null;
    }

    /**
     * Get the <code>moduleName</code>.
     * 
     * @return the moduleName.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Set the <code>moduleName</code>.
     * 
     * @param moduleName
     *            The moduleName to set
     * @throws IllegalArgumentException
     *             in case the new moduleName is <code>null</code> or empty
     */
    public void setModuleName(String moduleName) {
        AssertUtils.isNotEmpty(moduleName, "Not allowed to set the moduleName to null or an empty String");
        this.moduleName = moduleName;
    }

    /**
     * Get the <code>url</code>.
     * 
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the <code>url</code>.
     * 
     * @param url
     *            The url to set
     * @throws IllegalArgumentException
     *             in case the new url is <code>null</code> or empty
     */
    public void setUrl(String url) {
        AssertUtils.isNotEmpty(url, "Not allowed to set the url to null or an empty String");
        this.url = url;
    }

    /**
     * Get the <code>id</code>.
     * 
     * @return the id
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Is the <code>Module</code> currently loaded.
     * 
     * @return <code>true</code> if loaded, otherwise <code>false</code>
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Set the <code>loaded</code> flag.
     * 
     * @param loaded
     *            The loaded to set
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * Get the <code>description</code>.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set the <code>description</code>.
     * 
     * @param description
     *            The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Should the <code>Module</code> be loaded on application startup.
     * 
     * @return <code>true</code> if the <code>Module</code> should be loaded on
     *         application startup, otherwise <code>false</code>
     */
    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }

    /**
     * Set the <code>loadOnStartup</code> flag.
     * 
     * @param loadOnStartup
     *            The loadOnStartup to set
     */
    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * Get the <code>startupOrder</code>.
     * 
     * @return the startupOrder
     */
    public int getStartupOrder() {
        return startupOrder;
    }

    /**
     * Set the <code>startupOrder</code>.
     * 
     * @param startupOrder
     *            The startupOrder to set
     */
    public void setStartupOrder(int startupOrder) {
        this.startupOrder = startupOrder;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * Returns the <code>moduleName</code>.
     * 
     * @see java.lang.Object#toString()
     * @return The moduleName
     */
    @Override
    public String toString() {
        return this.moduleName;
    }
}
