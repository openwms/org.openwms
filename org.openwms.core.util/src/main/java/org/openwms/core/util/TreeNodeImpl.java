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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A TreeNodeImpl is a simple implementation of a {@link TreeNode}.
 *
 * @param <T> The type of the node
 * @author Heiko Scherrer
 */
public class TreeNodeImpl<T extends Serializable> implements TreeNode<T>, Serializable {

    private T data;
    private TreeNode<T> parent;
    private final Map<Object, TreeNode<T>> childrenMap = new LinkedHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public T getData() {
        return data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode<T> getChild(Object identifier) {
        return childrenMap.get(identifier);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addChild(Object identifier, TreeNode<T> child) {
        child.setParent(this);
        childrenMap.put(identifier, child);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeChild(Object identifier) {
        TreeNode<T> treeNode = childrenMap.remove(identifier);
        if (treeNode != null) {
            treeNode.setParent(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setData(T data) {
        this.data = data;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeNode<T> getParent() {
        return parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterator<Map.Entry<Object, TreeNode<T>>> getChildren() {
        return childrenMap.entrySet().iterator();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeaf() {
        return childrenMap.isEmpty();
    }
}