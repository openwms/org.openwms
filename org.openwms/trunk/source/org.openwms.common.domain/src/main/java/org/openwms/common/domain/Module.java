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
package org.openwms.common.domain;

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

/**
 * A Module.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
@Entity
@Table(name = "APP_MODULE")
@NamedQueries( { @NamedQuery(name = Module.NQ_FIND_ALL, query = "select m from Module m order by m.startupOrder"),
        @NamedQuery(name = Module.NQ_FIND_BY_UNIQUE_QUERY, query = "select m from Module m where m.moduleName = ?1") })
public class Module extends AbstractEntity implements Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 7358306395032979355L;

    /**
     * Query to find all {@link Module}s.
     */
    public static final String NQ_FIND_ALL = "Module.findAll";

    /**
     * Query to find <strong>one</strong> {@link Module} by its natural key.
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
     * The unique name of the module (business key).
     */
    @Column(name = "MODULE_NAME", unique = true, nullable = false)
    private String moduleName;

    /**
     * The URL where to load this module (unique).
     */
    @Column(name = "URL", unique = true, nullable = false)
    private String url;

    /**
     * Property used on client side to store whether the module is loaded or
     * not.
     */
    @Transient
    private boolean loaded = false;

    /**
     * <code>true</code> when the module should be loaded on application
     * startup.
     */
    @Column(name = "LOAD_ON_STARTUP")
    private boolean loadOnStartup = true;

    /**
     * Defines the startup order compared with other modules. Modules with lower
     * startupOrders are loaded earlier.
     */
    @Column(name = "STARTUP_ORDER")
    @OrderBy
    private int startupOrder;

    /**
     * A description field for this module.
     */
    @Column(name = "DESCRIPTION")
    private String description = "--";

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
     *            Module name
     */
    public Module(String moduleName) {
        super();
        this.moduleName = moduleName;
    }

    /**
     * Create a new Module.
     * 
     * @param moduleName
     * @param url
     */
    public Module(String moduleName, String url) {
        super();
        this.moduleName = moduleName;
        this.url = url;
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
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((moduleName == null) ? 0 : moduleName.hashCode());
        return result;
    }

    /**
     * Uses the moduleName for comparison, because this is unique and not null.
     * 
     * @see java.lang.Object#equals(java.lang.Object)
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
     */
    @Override
    public String toString() {
        return this.moduleName;
    }

}
