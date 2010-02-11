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
package org.openwms.common.service;

import java.io.Serializable;
import java.util.List;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.util.TreeNode;

/**
 * A LocationGroupService.
 * <p>
 * Extends the {@link EntityService} interface about some useful methods
 * regarding the general handling with {@link LocationGroup}s.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 * @since 0.1
 * @see EntityService
 */
public interface LocationGroupService<T extends Serializable> extends EntityService<T> {

    /**
     * Changes the GroupStates of a {@link LocationGroup}.<br>
     * Both, the GroupStateIn and the GroupStateOut of all child
     * {@link LocationGroup}s are changed according to the parent
     * <tt>locationGroup</tt>. This call is executed recursively to <i>all</i>
     * child {@link LocationGroup}s of the <tt>locationGroup</tt> Entity.
     * 
     * @param locationGroup
     *            - The {@link LocationGroup} to change
     */
    void changeGroupState(T locationGroup);

    /**
     * Returns a hierarchical Tree of all {@link LocationGroup}s. Used by the
     * user interface to show all {@link LocationGroup}s in a tree form.
     * 
     * @return - All {@link LocationGroup}s as hierarchical tree
     */
    TreeNode<LocationGroup> getLocationGroupsAsTree();

    /**
     * Returns a {@link java.util.List} of all {@link LocationGroup}s.
     * 
     * @return - All {@link LocationGroup}s as a list
     */
    List<LocationGroup> getLocationGroupsAsList();

}