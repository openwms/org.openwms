/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.util;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * A TreeNode.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface TreeNode<T> extends Serializable {

	/**
	 * getter for node attached data
	 * 
	 * @return node attached data
	 */
	public T getData();

	/**
	 * setter for node attached data
	 * 
	 * @param data
	 *            data to set as attached node data
	 */
	public void setData(T data);

	/**
	 * Returns whether this node is leaf
	 * 
	 * @return <code>true</code> if this node is leaf else returns <code>false</code>
	 */
	public boolean isLeaf();

	/**
	 * Getter for children entries. Each children entry contains identifier (key) and child node (value).
	 * 
	 * Identifiers are used to generate model keys representing paths to tree node. Model keys are used:
	 * <ol>
	 * <li> For persisting tree state. That means that identifiers should be serializable when some JSF features are
	 * used, e.g. client-side state saving. </li>
	 * <li> For constructing client identifiers. Client identifier for tree nodes consists of {@link Object#toString()}
	 * representations of identifier separated with {@link NamingContainer#SEPARATOR_CHAR} chars. String representation
	 * of identifier should be a valid XML ID, e.g. conform to this: <a href="http://www.w3.org/TR/xml11/#NT-Name"> XML
	 * Name Production</a> production. </li>
	 * </ol>
	 * 
	 * @return {@link Iterator} of {@link Map.Entry} instances containing {@link TreeNode} as values and their
	 *         identifiers as keys.
	 * 
	 */
	public Iterator<Map.Entry<Object, TreeNode<T>>> getChildren();

	/**
	 * find child by id Please see {@link #getChildren()} for more information about identifier constraints.
	 * 
	 * @param id
	 *            identifier of the child to find
	 * @return designated {@link TreeNode} instance or <code>null</code>
	 */
	public TreeNode<T> getChild(Object id);

	/**
	 * adds child to children collection
	 * 
	 * @param identifier
	 *            child identifier. Please see {@link #getChildren()} for more information about identifier constraints.
	 * @param child
	 *            child
	 */
	public void addChild(Object identifier, TreeNode<T> child);

	/**
	 * Please see {@link #getChildren()} for more information about identifier constraints. removes child from children
	 * collection by child id
	 * 
	 * @param id
	 *            id of the child to remove
	 */
	public void removeChild(Object id);

	/**
	 * getter for parent {@link TreeNode}
	 * 
	 * @return parent {@link TreeNode} instance or <code>null</code> if this node is root
	 */
	public TreeNode<T> getParent();

	/**
	 * setter for parent {@link TreeNode}
	 * 
	 * @param parent
	 *            {@link TreeNode} to set as parent
	 */
	public void setParent(TreeNode<T> parent);
}
