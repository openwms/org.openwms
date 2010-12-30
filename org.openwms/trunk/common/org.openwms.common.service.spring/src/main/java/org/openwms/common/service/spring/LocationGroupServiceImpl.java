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
package org.openwms.common.service.spring;

import java.util.List;
import java.util.Set;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.values.LocationGroupState;
import org.openwms.common.integration.LocationGroupDao;
import org.openwms.common.service.LocationGroupService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.service.spring.EntityServiceImpl;
import org.openwms.core.util.TreeNode;
import org.openwms.core.util.TreeNodeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationGroupServiceImpl.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.spring.EntityServiceImpl
 */
@Service
@Transactional
public class LocationGroupServiceImpl extends EntityServiceImpl<LocationGroup, Long> implements
        LocationGroupService<LocationGroup> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Generic Repository DAO.
     */
    @Autowired
    @Qualifier("locationGroupDao")
    protected LocationGroupDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeGroupState(LocationGroup locationGroup) {
        logger.debug("change group state called");
        if (null != locationGroup && locationGroup.getParent() != null
                && locationGroup.getParent().getGroupStateIn() == LocationGroupState.NOT_AVAILABLE
                && locationGroup.getGroupStateIn() == LocationGroupState.AVAILABLE) {
            throw new ServiceRuntimeException(
                    "Not allowed to change GroupStateIn, parent locationGroup is not available");
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

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationGroup save(LocationGroup locationGroup) {
        logger.debug("Save LocationGroup on service called");
        return dao.save(locationGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public TreeNode<LocationGroup> getLocationGroupsAsTree() {
        TreeNode<LocationGroup> tree = createTree(new TreeNodeImpl<LocationGroup>(), getLocationGroupsAsList());
        return tree;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
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