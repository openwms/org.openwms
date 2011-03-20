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

import java.io.Serializable;

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

/**
 * A Module is a definition of a Flex Module and is used to persist some initial
 * information about these modules.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Entity
@Table(name = "COR_MODULE")
@NamedQueries({ @NamedQuery(name = Module.NQ_FIND_ALL, query = "select m from Module m order by m.startupOrder"),
        @NamedQuery(name = Module.NQ_FIND_BY_UNIQUE_QUERY, query = "select m from Module m where m.moduleName = ?1") })
public class Module extends AbstractEntity implements DomainObject<Long>, Serializable {

    private static final long serialVersionUID = 7358306395032979355L;

    /**
     * Query to find all {@link Module}s.
     */
    public static final String NQ_FIND_ALL = "Module.findAll";

    /**
     * Query to find <strong>one</strong> {@link Module} by its natural key. <li>
     * Query parameter index <strong>1</strong> : The moduleName of the Module
     * to search for.</li>
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
     * Unique name of the Module (natural key, unique, not-null).
     */
    @Column(name = "MODULE_NAME", unique = true, nullable = false)
    private String moduleName;

    /**
     * URL where to load this Module from (unique, not-null).
     */
    @Column(name = "URL", unique = true, nullable = false)
    private String url;

    /**
     * Flag used on client-side to store whether the Module is loaded or not.
     * Default:{@value} .
     */
    @Transient
    private boolean loaded = false;

    /**
     * <code>true</code> when the Module should be loaded on application
     * startup. Default:{@value} .
     */
    @Column(name = "LOAD_ON_STARTUP")
    private boolean loadOnStartup = true;

    /**
     * Defines the startup order compared with other Modules. Modules with lower
     * startupOrders are loaded before.
     */
    @Column(name = "STARTUP_ORDER")
    @OrderBy
    private int startupOrder;

    /**
     * A description text of the Module. Default:{@value} .
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
     * Create a new Module.
     * 
     * @param moduleName
     *            The unique Module name
     */
    public Module(String moduleName) {
        super();
        this.moduleName = moduleName;
    }

    /**
     * Create a new Module.
     * 
     * @param moduleName
     *            The unique Module name
     * @param url
     *            The unique URL
     */
    public Module(String moduleName, String url) {
        super();
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
     * Get the moduleName.
     * 
     * @return the moduleName.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * Set the moduleName.
     * 
     * @param moduleName
     *            The moduleName to set.
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * Get the url.
     * 
     * @return the url.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Set the url.
     * 
     * @param url
     *            The url to set.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Get the id.
     * 
     * @return the id.
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Get the loaded.
     * 
     * @return the loaded.
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Set the loaded.
     * 
     * @param loaded
     *            The loaded to set.
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
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
     * Get the loadOnStartup.
     * 
     * @return the loadOnStartup.
     */
    public boolean isLoadOnStartup() {
        return loadOnStartup;
    }

    /**
     * Set the loadOnStartup.
     * 
     * @param loadOnStartup
     *            The loadOnStartup to set.
     */
    public void setLoadOnStartup(boolean loadOnStartup) {
        this.loadOnStartup = loadOnStartup;
    }

    /**
     * Get the startupOrder.
     * 
     * @return the startupOrder.
     */
    public int getStartupOrder() {
        return startupOrder;
    }

    /**
     * Set the startupOrder.
     * 
     * @param startupOrder
     *            The startupOrder to set.
     */
    public void setStartupOrder(int startupOrder) {
        this.startupOrder = startupOrder;
    }

    /**
     * Uses the moduleName for calculation, because this is unique and not null.
     * 
     * @see java.lang.Object#hashCode()
     * @return The hashCode
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * Compares the moduleName, because it is unique and not-null.
     * 
     * @see org.openwms.common.domain.AbstractEntity#equals(java.lang.Object)
     * @param obj
     *            The object to compare
     * @return <code>true</code> if equal otherwise <code>false</code>
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Module)) {
            return false;
        }
        final Module other = (Module) obj;
        if (moduleName == null) {
            if (other.moduleName != null) {
                return false;
            }
        } else if (!moduleName.equals(other.moduleName)) {
            return false;
        }
        return true;
    }

    /**
     * Returns the moduleName.
     * 
     * @see java.lang.Object#toString()
     * @return The moduleName
     */
    @Override
    public String toString() {
        return this.moduleName;
    }

}
