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
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A Module.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
@Entity
@Table(name = "T_MODULE")
@NamedQueries( {
        @NamedQuery(name = Module.NQ_FIND_ALL, query = "select m from Module m order by m.startupOrder, m.id"),
        @NamedQuery(name = Module.NQ_FIND_BY_UNIQUE_QUERY, query = "select m from Module m where m.moduleName = ?1") })
public class Module implements Serializable {

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

    @Column(unique = true, nullable = false)
    private String moduleName;

    @Column(unique = true, nullable = false)
    private String url;

    private boolean loaded = false;

    private boolean loadOnStartup = true;

    private int startupOrder;

    private String description = "--";

    @OneToMany
    @Basic(fetch = FetchType.EAGER)
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    @OneToMany
    @Basic(fetch = FetchType.EAGER)
    private List<PopupItem> popupItems = new ArrayList<PopupItem>();

    /**
     * Create a new Module.
     * 
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
     * Get the menuItems.
     * 
     * @return the menuItems.
     */
    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    /**
     * Set the menuItems.
     * 
     * @param menuItems
     *            The menuItems to set.
     */
    public void setMenuItems(List<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    /**
     * Get the popupItems.
     * 
     * @return the popupItems.
     */
    public List<PopupItem> getPopupItems() {
        return popupItems;
    }

    /**
     * Set the popupItems.
     * 
     * @param popupItems
     *            The popupItems to set.
     */
    public void setPopupItems(List<PopupItem> popupItems) {
        this.popupItems = popupItems;
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

}
