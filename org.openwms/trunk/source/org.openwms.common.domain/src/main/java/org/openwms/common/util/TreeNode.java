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
package org.openwms.common.util;

import java.util.Iterator;
import java.util.Map;

/**
 * A TreeNode.
 * 
 * @param <T>
 *            The type of the node.
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public interface TreeNode<T> {

    /**
     * Getter for node attached data.
     * 
     * @return The attached Node data
     */
    T getData();

    /**
     * Setter for node attached data.
     * 
     * @param data
     *            Data to set as attached node data
     */
    void setData(T data);

    /**
     * Returns whether this node is leaf.
     * 
     * @return true if this node is leaf, otherwise false
     */
    boolean isLeaf();

    /**
     * Getter for children entries. Each children entry contains identifier
     * (key) and child node (value).
     * 
     * Identifiers are used to generate model keys representing paths to tree
     * node. Model keys are used:
     * <ol>
     * <li>For persisting tree state. That means that identifiers should be
     * serializable when some JSF features are used, e.g. client-side state
     * saving.</li>
     * <li>For constructing client identifiers. Client identifier for tree nodes
     * consists of {@link Object#toString()} representations of identifier
     * separated with {@link NamingContainer#SEPARATOR_CHAR} chars. String
     * representation of identifier should be a valid XML ID, e.g. conform to
     * this: <a href="http://www.w3.org/TR/xml11/#NT-Name"> XML Name
     * Production</a> production.</li>
     * </ol>
     * 
     * @return {@link Iterator} of {@link Map.Entry} instances containing
     *         {@link TreeNode} as values and their identifiers as keys.
     * 
     */
    Iterator<Map.Entry<Object, TreeNode<T>>> getChildren();

    /**
     * Find child by id Please see {@link #getChildren()} for more information
     * about identifier constraints.
     * 
     * @param id
     *            The identifier of the child to find
     * @return designated {@link TreeNode} instance or <code>null</code>
     */
    TreeNode<T> getChild(Object id);

    /**
     * Adds child to children collection.
     * 
     * @param identifier
     *            child identifier. Please see {@link #getChildren()} for more
     *            information about identifier constraints.
     * @param child
     *            The child
     */
    void addChild(Object identifier, TreeNode<T> child);

    /**
     * Please see {@link #getChildren()} for more information about identifier
     * constraints. removes child from children collection by child id
     * 
     * @param id
     *            The id of the child to remove
     */
    void removeChild(Object id);

    /**
     * Getter for parent {@link TreeNode}.
     * 
     * @return parent {@link TreeNode} instance or <code>null</code> if this
     *         node is root
     */
    TreeNode<T> getParent();

    /**
     * Setter for parent {@link TreeNode}.
     * 
     * @param parent
     *            - {@link TreeNode} to set as parent
     */
    void setParent(TreeNode<T> parent);
}
