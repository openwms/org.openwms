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
package org.openwms.core.uaa.search;

import java.util.Collection;

/**
 * An ActionService is used to query Actions of a particular User or the whole application.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @see Action
 * @since 0.1.1
 */
public interface ActionService {

    /**
     * Find and return all defined {@link Action}s.
     *
     * @return All {@link Action}s
     */
    Collection<Action> findAllActions();

    /**
     * Find and return all {@link Action}s of an {@link User}.
     *
     * @param user The {@link User} to search for
     * @return All {@link Action}s
     */
    Collection<Action> findAllActions(User user);

    /**
     * Find and return all {@link Tag}s the {@link User} user has used so far.
     *
     * @param user The {@link User} to find the {@link Tag}s for
     * @return All used {@link Tag}s
     */
    Collection<Tag> findAllTags(User user);

    /**
     * Save a Collection of {@link Action} for a specific {@link User}.
     *
     * @param user The {@link User} of the {@link Action}
     * @param actions The Collection of {@link Action}s to save
     * @return The saved Collection
     */
    Collection<Action> save(User user, Collection<Action> actions);
}