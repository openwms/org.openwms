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
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * A MenuItem.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: $
 * 
 */
@Entity
@Table(name = "APP_MENU_ITEM")
public class MenuItem implements DomainObject, Serializable {

    /**
     * The serialVersionUID
     */
    private static final long serialVersionUID = 4903843725315147954L;

    /**
     * Unique technical key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    /**
     * The internal name of the menu item.
     */
    @Column(name = "C_NAME")
    private String name;

    /**
     * The label used within the gui.
     */
    @Column(name = "C_LABEL")
    private String label;

    /**
     * Name of link to an icon (if present).
     */
    @Column(name = "C_ICON_NAME")
    private String iconName;

    /**
     * An action that is triggered on an menu item.
     */
    @Column(name = "C_ACTION")
    private String action;

    /**
     * Version field.
     */
    @Column(name = "C_VERSION")
    private boolean enabled = true;

    /**
     * Version field.
     */
    @Version
    @Column(name = "C_VERSION")
    private long version;

    /**
     * Create a new MenuItem.
     */
    protected MenuItem() {}

    /**
     * Create a new MenuItem.
     * 
     * @param name
     * @param label
     * @param action
     */
    public MenuItem(String name, String label, String action) {
        super();
        this.name = name;
        this.label = label;
        this.action = action;
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
     * Checks if the instance is transient.
     * 
     * @return <code>true</code> Entity is not present on the persistent
     *         storage.<br>
     *         <code>false</code> Entity already exists on the persistence
     *         storage
     */
    @Override
    public boolean isNew() {
        return this.id == null;
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
     * Get the label.
     * 
     * @return the label.
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set the label.
     * 
     * @param label
     *            The label to set.
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Get the iconName.
     * 
     * @return the iconName.
     */
    public String getIconName() {
        return iconName;
    }

    /**
     * Set the iconName.
     * 
     * @param iconName
     *            The iconName to set.
     */
    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    /**
     * Get the action.
     * 
     * @return the action.
     */
    public String getAction() {
        return action;
    }

    /**
     * Set the action.
     * 
     * @param action
     *            The action to set.
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Get the enabled.
     * 
     * @return the enabled.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set the enabled.
     * 
     * @param enabled
     *            The enabled to set.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * JPA optimistic locking.
     * 
     * @return The version field
     */
    @Override
    public long getVersion() {
        return this.version;
    }

    /**
     * Return the label as String.
     * 
     * @see java.lang.Object#toString()
     * @return String
     */
    @Override
    public String toString() {
        return getLabel();
    }

}