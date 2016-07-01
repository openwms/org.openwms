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
package org.openwms.common.location;

/**
 * A LocationGroupService offers some useful methods regarding the general handling of {@link LocationGroup}s. <p> This interface is
 * declared generic typed that implementation classes can use any extension of {@link LocationGroup}s. </p>
 *
 * @param <T> Any kind of {@link LocationGroup}
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface LocationGroupService<T extends LocationGroup> {

    /**
     * Tries to change the infeed and outfeed state of a {@code Location Group}.
     */
    void changeGroupState(String id, LocationGroupState stateIn, LocationGroupState stateOut);

    /**
     * Returns a hierarchical Tree of all {@link LocationGroup}s. Used by the user interface to show all {@link LocationGroup}s in a tree
     * form.
     *
     * @return All {@link LocationGroup}s as hierarchical tree
     */
    //TreeNode<T> getLocationGroupsAsTree();

    /**
     * Returns a List of all {@link LocationGroup}s.
     *
     * @return All {@link LocationGroup}s as a list
     */
    //List<T> getLocationGroupsAsList();

    /**
     * Save an already persisted {@link LocationGroup} and return the saved instance.
     *
     * @param locationGroup The {@link LocationGroup} to save
     * @return The saved {@link LocationGroup}
     */
    //T save(T locationGroup);
}