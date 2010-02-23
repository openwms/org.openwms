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
 * regarding the general handling with <code>LocationGroup</code>s.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 * @since 0.1
 * @see EntityService
 */
public interface LocationGroupService<T extends Serializable> extends EntityService<T> {

    /**
     * Changes the GroupStates of a <code>LocationGroup</code>.<br>
     * Both, the GroupStateIn and the GroupStateOut of all child
     * <code>LocationGroup</code>s are changed according to the parent
     * <tt>locationGroup</tt>. This call is executed recursively to <i>all</i>
     * child <code>LocationGroup</code>s of the <tt>locationGroup</tt> Entity.
     * 
     * @param locationGroup
     *            The <code>LocationGroup</code> to change
     */
    void changeGroupState(T locationGroup);

    /**
     * Returns a hierarchical Tree of all <code>LocationGroup</code>s. Used by
     * the user interface to show all <code>LocationGroup</code>s in a tree
     * form.
     * 
     * @return All <code>LocationGroup</code>s as hierarchical tree
     */
    TreeNode<LocationGroup> getLocationGroupsAsTree();

    /**
     * Returns a List of all <code>LocationGroup</code>s.
     * 
     * @return All <code>LocationGroup</code>s as a list
     */
    List<LocationGroup> getLocationGroupsAsList();

    /**
     * Save an already persisted <code>LocationGroup</code> and return the saved
     * instance.
     * 
     * @param locationGroup
     *            The <code>LocationGroup</code> to save
     * @return The saved <code>LocationGroup</code>
     */
    LocationGroup save(LocationGroup locationGroup);
}