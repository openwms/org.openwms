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
package org.openwms.core.configuration;

import java.io.Serializable;
import java.util.List;

/**
 * A PreferenceDao offers basic functionality to find and retrieve {@link AbstractPreference}s.
 * 
 * @param <ID>
 *            The type of the entity class' unique id
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface PreferenceDao<ID extends Serializable> {

    /**
     * Find and return the entity identified by the natural unique key.
     * 
     * @param id
     *            Natural key to find the entity (also known as business key)
     * @return The found entity
     */
    AbstractPreference findByKey(ID id);

    /**
     * Find all preferences of a particular type.
     * 
     * @param <T>
     *            Any subtype of {@link AbstractPreference}
     * @param clazz
     *            The type to search for
     * @return A list of entities
     */
    <T extends AbstractPreference> List<T> findByType(Class<T> clazz);

    /**
     * Find all preferences of a particular type and of an owner.
     * 
     * @param <T>
     *            Any subtype of {@link AbstractPreference}
     * @param clazz
     *            The type to search for
     * @param owner
     *            The owner of the preferences
     * @return A list of entities
     * @since 0.2
     */
    <T extends AbstractPreference> List<T> findByType(Class<T> clazz, String owner);

    /**
     * Find all entities and return them as a {@link java.util.List}.
     * 
     * @return List of all entities
     */
    List<AbstractPreference> findAll();
}