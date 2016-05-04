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

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A TreeNodeImpl is a simple implementation of a {@link TreeNode}.
 * 
 * @param <T>
 *            The type of the node
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class TreeNodeImpl<T> implements TreeNode<T>, Serializable {

    private static final long serialVersionUID = 5032295350189152303L;

    private T data;
    private TreeNode<T> parent;
    private Map<Object, TreeNode<T>> childrenMap = new LinkedHashMap<Object, TreeNode<T>>();

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
        return (TreeNode<T>) childrenMap.get(identifier);
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