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
package org.openwms.core.domain.preferences;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.openwms.core.domain.system.AbstractPreference;
import org.openwms.core.domain.system.usermanagement.RolePreference;
import org.openwms.core.domain.system.usermanagement.UserPreference;

/**
 * An instance of a <code>Preferences</code> represents the root of a
 * preferences XML file and aggregates all other types of preference.
 * 
 * @GlossaryTerm
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "applicationOrRoleOrUserOrModule" })
@XmlRootElement(name = "preferences", namespace = "http://www.openwms.org/schema/preferences")
public class Preferences implements Serializable {

    private static final long serialVersionUID = 4836136346473578215L;

    @XmlElements({ @XmlElement(name = "module", type = ModulePreference.class),
            @XmlElement(name = "application", type = ApplicationPreference.class),
            @XmlElement(name = "role", type = RolePreference.class),
            @XmlElement(name = "user", type = UserPreference.class) })
    private List<AbstractPreference> applicationOrRoleOrUserOrModule;
    @XmlTransient
    private List<ApplicationPreference> applications;
    @XmlTransient
    private List<ModulePreference> modules;
    @XmlTransient
    private List<UserPreference> users;
    @XmlTransient
    private List<RolePreference> roles;
    /**
     * All concrete types of AbstractPreference.
     */
    public static final Class<?>[] TYPES = { ApplicationPreference.class, ModulePreference.class, RolePreference.class,
            UserPreference.class };

    /**
     * Gets the value of the applicationOrRoleOrUserOrModule property. This
     * method is called by the JAXB unmarshaller only.
     * 
     * @return a list of all preferences
     */
    public List<AbstractPreference> getApplicationOrRoleOrUserOrModule() {
        if (applicationOrRoleOrUserOrModule == null) {
            applicationOrRoleOrUserOrModule = new ArrayList<AbstractPreference>();
        }
        return applicationOrRoleOrUserOrModule;
    }

    /**
     * Return a list of all preferences. Simple call to
     * {@link #getApplicationOrRoleOrUserOrModule()}. Is only added due to
     * naming purpose.
     * 
     * @return a list of all preferences
     */
    public List<AbstractPreference> getAll() {
        return getApplicationOrRoleOrUserOrModule();
    }

    /**
     * Return a list of all {@link ApplicationPreference}s or an empty ArrayList
     * when no {@link ApplicationPreference}s exist.
     * 
     * @return a list of all {@link ApplicationPreference}s
     */
    public List<ApplicationPreference> getApplications() {
        if (applications == null) {
            applications = new ArrayList<ApplicationPreference>();
            for (AbstractPreference pref : applicationOrRoleOrUserOrModule) {
                if (pref instanceof ApplicationPreference) {
                    applications.add((ApplicationPreference) pref);
                }
            }
        }
        return applications;
    }

    /**
     * Return a list of all {@link ModulePreference}s or an empty ArrayList when
     * no {@link ModulePreference}s exist.
     * 
     * @return a list of all {@link ModulePreference}s
     */
    public List<ModulePreference> getModules() {
        if (modules == null) {
            modules = new ArrayList<ModulePreference>();
            for (AbstractPreference pref : applicationOrRoleOrUserOrModule) {
                if (pref instanceof ModulePreference) {
                    modules.add((ModulePreference) pref);
                }
            }
        }
        return modules;
    }

    /**
     * Return a list of all {@link UserPreference}s or an empty ArrayList when
     * no {@link UserPreference}s exist.
     * 
     * @return a list of all {@link UserPreference}s
     */
    public List<UserPreference> getUsers() {
        if (users == null) {
            users = new ArrayList<UserPreference>();
            for (AbstractPreference pref : applicationOrRoleOrUserOrModule) {
                if (pref instanceof UserPreference) {
                    users.add((UserPreference) pref);
                }
            }
        }
        return users;
    }

    /**
     * Return a list of all {@link RolePreference}s or an empty ArrayList when
     * no {@link RolePreference}s exist.
     * 
     * @return a list of all {@link RolePreference}s
     */
    public List<RolePreference> getRoles() {
        if (roles == null) {
            roles = new ArrayList<RolePreference>();
            for (AbstractPreference pref : applicationOrRoleOrUserOrModule) {
                if (pref instanceof RolePreference) {
                    roles.add((RolePreference) pref);
                }
            }
        }
        return roles;
    }

    /**
     * Return a list of preferences filtered by a specific type, defined by the
     * parameter clazz.
     * 
     * @param <T>
     *            Expected types are <code>ApplicationPreference</code>,
     *            <code>ModulePreference</code>, <code>RolePreference</code>
     *            <code>UserPreference</code>
     * @param clazz
     *            The class type of the preference to filter for
     * @return a list of T.
     */
    @SuppressWarnings("unchecked")
    public <T extends AbstractPreference> List<T> getOfType(Class<T> clazz) {
        if (ApplicationPreference.class.equals(clazz)) {
            return (List<T>) getApplications();
        }
        if (ModulePreference.class.equals(clazz)) {
            return (List<T>) getModules();
        }
        if (UserPreference.class.equals(clazz)) {
            return (List<T>) getUsers();
        }
        if (RolePreference.class.equals(clazz)) {
            return (List<T>) getRoles();
        }
        return Collections.<T> emptyList();
    }
}