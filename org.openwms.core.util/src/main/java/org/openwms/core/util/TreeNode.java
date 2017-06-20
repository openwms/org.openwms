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
package org.openwms.core.util;

import java.util.Iterator;
import java.util.Map;

/**
 * A TreeNode to define a tree data structure.
 * 
 * @param <T>
 *            The type of node.
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface TreeNode<T> {

    /**
     * Get the data of the node.
     * 
     * @return The attached Node data
     */
    T getData();

    /**
     * Set the data of the node.
     * 
     * @param data
     *            Data to set
     */
    void setData(T data);

    /**
     * Return whether this node is leaf.
     * 
     * @return <code>true</code> if this node is leaf, otherwise <code>false</code>
     */
    boolean isLeaf();

    /**
     * Get all child entries. Each child entry contains an identifier (key) and a child node (value).
     * 
     * Identifiers are used to generate model keys representing paths to tree nodes. Model keys are used:
     * <ol>
     * <li>For persisting tree state. That means identifiers should be serializable when some JSF features are used, e.g. client-side state
     * saving.</li>
     * <li>For construction of client identifiers. Client identifier for tree nodes consists of String representations of identifiers
     * separated by a separator char. A String representation of identifier should be a valid XML ID, e.g. conform to <a
     * href="http://www.w3.org/TR/xml11/#NT-Name"> XML Name Production</a> production.</li>
     * </ol>
     * 
     * @return {@link Iterator} over Map.Entry instances containing {@link TreeNode} as values and their identifiers as keys
     * 
     */
    Iterator<Map.Entry<Object, TreeNode<T>>> getChildren();

    /**
     * Find a child by id.
     * <p>
     * See {@link #getChildren()} for more information about the identifier constraints.
     * </p>
     * 
     * @param id
     *            The identifier of the child to find
     * @return designated {@link TreeNode} instance or <code>null</code>
     */
    TreeNode<T> getChild(Object id);

    /**
     * Adds a child to the children.
     * <p>
     * See {@link #getChildren()} for more information about identifier constraints.
     * </p>
     * 
     * @param identifier
     *            child identifier
     * @param child
     *            The child
     */
    void addChild(Object identifier, TreeNode<T> child);

    /**
     * Removes a child from the children collection.
     * <p>
     * See {@link #getChildren()} for more information about identifier constraints.
     * </p>
     * 
     * @param id
     *            The id of the child to remove
     */
    void removeChild(Object id);

    /**
     * Get the parent {@link TreeNode}.
     * 
     * @return parent {@link TreeNode} instance or <code>null</code> if the node is the root
     */
    TreeNode<T> getParent();

    /**
     * Set the parent {@link TreeNode}.
     * 
     * @param parent
     *            {@link TreeNode} to set as parent
     */
    void setParent(TreeNode<T> parent);
}