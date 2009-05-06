/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationGroup.STATE;
import org.openwms.common.service.exception.ServiceException;
import org.openwms.common.util.TreeNode;
import org.openwms.common.util.TreeNodeImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationGroupServiceImpl.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Transactional
@Service
public class LocationGroupServiceImpl extends EntityServiceImpl<LocationGroup, Long> implements
		LocationGroupService<LocationGroup> {

	public void changeGroupState(LocationGroup locationGroup) throws ServiceException {
		if (null != locationGroup && locationGroup.getParent() != null
				&& locationGroup.getParent().getGroupStateIn() == STATE.NOT_AVAILABLE
				&& locationGroup.getGroupStateIn() == STATE.AVAILABLE) {
			throw new ServiceException("Not allowed to change GroupStateIn, parent locationGroup is not available");
		}

		// Attach
		locationGroup = dao.save(locationGroup);
		Set<LocationGroup> childs = locationGroup.getLocationGroups();
		for (LocationGroup child : childs) {
			child.setGroupStateIn(locationGroup.getGroupStateIn());
			child.setGroupStateOut(locationGroup.getGroupStateOut());
			changeGroupState(child);
		}
	}

	public LocationGroup save(LocationGroup locationGroup) {
		return dao.save(locationGroup);
	}

	public TreeNode<LocationGroup> getLocationGroupsAsTree() {
		TreeNode<LocationGroup> tree = createTree(new TreeNodeImpl<LocationGroup>(), getLocationGroupsAsList());
		return tree;
	}

	public List<LocationGroup> getLocationGroupsAsList() {
		List<LocationGroup> locationGroups = dao.findAll();
		return locationGroups;
	}

	private TreeNode<LocationGroup> createTree(TreeNode<LocationGroup> root, List<LocationGroup> locationGroups) {
		for (LocationGroup l : locationGroups) {
			searchForNode(l, root);
		}
		return root;
	}

	private TreeNode<LocationGroup> searchForNode(LocationGroup lg, TreeNode<LocationGroup> root) {
		TreeNode<LocationGroup> node;
		if (lg.getParent() == null) {
			node = root.getChild(lg);
			if (node == null) {
				TreeNode<LocationGroup> n1 = new TreeNodeImpl<LocationGroup>();
				n1.setData(lg);
				n1.setParent(root);
				root.addChild(n1.getData(), n1);
				return n1;
			}
			return node;
		} else {
			node = searchForNode(lg.getParent(), root);
			TreeNode<LocationGroup> child = node.getChild(lg);
			if (child == null) {
				child = new TreeNodeImpl<LocationGroup>();
				child.setData(lg);
				child.setParent(node);
				node.addChild(lg, child);
			}
			return child;
		}
	}

}
