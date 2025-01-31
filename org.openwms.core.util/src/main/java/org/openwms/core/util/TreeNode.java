/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * A TreeNode to define a tree data structure.
 *
 * @param <T> The type of node.
 * @author Heiko Scherrer
 */
public interface TreeNode<T> extends Serializable {

    /**
     * Get the data of the node.
     *
     * @return The attached Node data
     */
    T getData();

    /**
     * Set the data of the node.
     *
     * @param data Data to set
     */
    void setData(T data);

    /**
     * Return whether this node is leaf.
     *
     * @return {@literal true} if this node is leaf, otherwise {@literal false}
     */
    boolean isLeaf();

    /**
     * Get all child entries. Each child entry contains an identifier (key) and a child
     * node (value).
     * 
     * Identifiers are used to generate model keys representing paths to tree nodes. Model
     * keys are used:
     * <ul>
     *   <li>For persisting tree state. That means identifiers should be serializable when
     *       some JSF features are used, e.g. client-side state saving.
     *   </li>
     *   <li>For construction of client identifiers. Client identifier for tree nodes
     *       consists of String representations of identifiers separated by a separator
     *       char. A String representation of identifier should be a valid XML ID, e.g.
     *       conform to <a href="http://www.w3.org/TR/xml11/#NT-Name"> XML Name Production</a>
     *       production.
     *   </li>
     * </ul>
     *
     * @return {@link Iterator} over Map.Entry instances containing {@link TreeNode} as
     * values and their identifiers as keys
     */
    Iterator<Map.Entry<Object, TreeNode<T>>> getChildren();

    /**
     * Find a child by id.
     * 
     * See {@link #getChildren()} for more information about the identifier constraints.
     *
     * @param id The identifier of the child to find
     * @return designated {@link TreeNode} instance or {@literal null}
     */
    TreeNode<T> getChild(Object id);

    /**
     * Adds a child to the children.
     *
     * See {@link #getChildren()} for more information about identifier constraints.
     *
     * @param identifier child identifier
     * @param child The child
     */
    void addChild(Object identifier, TreeNode<T> child);

    /**
     * Removes a child from the children collection.
     *
     * See {@link #getChildren()} for more information about identifier constraints.
     *
     * @param id The id of the child to remove
     */
    void removeChild(Object id);

    /**
     * Get the parent {@link TreeNode}.
     *
     * @return parent {@link TreeNode} instance or {@literal null} if the node is the root
     */
    TreeNode<T> getParent();

    /**
     * Set the parent {@link TreeNode}.
     *
     * @param parent {@link TreeNode} to set as parent
     */
    void setParent(TreeNode<T> parent);
}