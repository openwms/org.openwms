/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A TreeNodeImpl.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TreeNodeImpl<T> implements TreeNode<T> {
	private static final long serialVersionUID = -5498990493803705085L;
	private T data;
	private TreeNode<T> parent;

	private Map<Object, TreeNode<T>> childrenMap = new LinkedHashMap<Object, TreeNode<T>>();

	public T getData() {
		return data;
	}

	public TreeNode<T> getChild(Object identifier) {
		return (TreeNode<T>) childrenMap.get(identifier);
	}

	public void addChild(Object identifier, TreeNode<T> child) {
		child.setParent(this);
		childrenMap.put(identifier, child);
	}

	public void removeChild(Object identifier) {
		TreeNode<T> treeNode = childrenMap.remove(identifier);
		if (treeNode != null) {
			treeNode.setParent(null);
		}
	}

	public void setData(T data) {
		this.data = data;
	}

	public TreeNode<T> getParent() {
		return parent;
	}

	public void setParent(TreeNode<T> parent) {
		this.parent = parent;
	}

	public Iterator<Map.Entry<Object, TreeNode<T>>> getChildren() {
		return childrenMap.entrySet().iterator();
	}

	public boolean isLeaf() {
		return childrenMap.isEmpty();
	}

}