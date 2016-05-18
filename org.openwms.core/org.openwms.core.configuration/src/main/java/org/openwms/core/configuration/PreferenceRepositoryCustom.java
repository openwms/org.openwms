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

import java.util.List;

/**
 * A PreferenceRepositoryCustom defines additional custom methods to search preferences by class type.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
interface PreferenceRepositoryCustom {

    /**
     * Find and return all preferences that are of the given {@code clazz} type and owned by the {@code owner}.
     *
     * @param clazz A subclass of {@link AbstractPreference} to search for
     * @param owner The owner
     * @param <T> Any type of {@link AbstractPreference}
     * @return A list of all preferences or an empty list, never {@literal null}
     */
    <T extends AbstractPreference> List<T> findByType(Class<T> clazz, String owner);

    /**
     * Find and return all preferences that are of the given {@code clazz} type.
     *
     * @param clazz A subclass of {@link AbstractPreference} to search for
     * @param <T> Any type of {@link AbstractPreference}
     * @return A list of all preferences or an empty list, never {@literal null}
     */
    <T extends AbstractPreference> List<T> findByType(Class<T> clazz);
}
