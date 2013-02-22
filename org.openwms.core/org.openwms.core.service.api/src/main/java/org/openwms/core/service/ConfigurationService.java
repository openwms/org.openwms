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
package org.openwms.core.service;

import java.util.Collection;

import org.openwms.core.domain.system.AbstractPreference;

/**
 * A ConfigurationService is responsible to deal with preferences. Whereby
 * preferences have particular defined scopes, e.g. some preferences are in a
 * global scope which means they are visible and valid for the whole
 * application. Others are only valid in a certain scope, probably only visible
 * for a particular <code>Module</code>, <code>Role</code> or <code>User</code>.
 * Other subclasses of {@link AbstractPreference} may be implemented as well.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.domain.system.AbstractPreference
 * @see org.openwms.core.domain.system.PropertyScope
 */
public interface ConfigurationService {

    /**
     * Find and return all preferences. The order of elements is not guaranteed
     * and is specific to the implementation.
     * 
     * @return A Collection of all preferences
     */
    Collection<AbstractPreference> findAll();

    /**
     * Find and return all preferences in the scope of a specific type of
     * Preference and of an owner.
     * 
     * @param <T>
     *            Any subtype of {@link AbstractPreference}
     * @param clazz
     *            The class of preference to search for
     * @return A Collection of preferences of type T
     */
    <T extends AbstractPreference> Collection<T> findByType(Class<T> clazz, String owner);

    /**
     * Save the given {@link AbstractPreference} or persist it when it is a
     * transient instance.
     * 
     * @param <T>
     *            Any subtype of {@link AbstractPreference}
     * @param preference
     *            {@link AbstractPreference} entity to save
     * @return Saved {@link AbstractPreference} entity instance
     */
    <T extends AbstractPreference> T save(T preference);

    /**
     * Merge the given {@link AbstractPreference} or persist it when it is a
     * transient instance.
     * 
     * @param preference
     *            {@link AbstractPreference} entity to merge
     * @return Merged {@link AbstractPreference} entity instance
     */
    AbstractPreference merge(AbstractPreference preference);

    /**
     * Remove a {@link AbstractPreference}.
     * 
     * @param preference
     *            The {@link AbstractPreference} to remove
     */
    void remove(AbstractPreference preference);
}
