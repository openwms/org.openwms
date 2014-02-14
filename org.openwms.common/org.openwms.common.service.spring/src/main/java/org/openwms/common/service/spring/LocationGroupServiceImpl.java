/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.service.spring;

import java.util.List;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.values.LocationGroupState;
import org.openwms.common.integration.LocationGroupDao;
import org.openwms.common.service.LocationGroupService;
import org.openwms.core.service.exception.ServiceRuntimeException;
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
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.spring.EntityServiceImpl
 */
@Transactional
@Service(LocationGroupServiceImpl.COMPONENT_NAME)
public class LocationGroupServiceImpl implements LocationGroupService<LocationGroup> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /** Springs component name. */
    public static final String COMPONENT_NAME = "locationGroupService";

    @Autowired
    @Qualifier("locationGroupDao")
    private LocationGroupDao dao;

    /**
     * {@inheritDoc}
     */
    @Override
    public void changeGroupState(LocationGroup locationGroup) {
        logger.debug("CGS LocationGroup on service called");
        if (locationGroup.isNew()) {
            throw new ServiceRuntimeException("LocationGroup " + locationGroup.getName()
                    + " is new and must be persisted before save");
        }
        LocationGroup persisted = dao.findById(locationGroup.getId());
        changeGroupState(persisted, locationGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationGroup save(LocationGroup locationGroup) {
        if (locationGroup.isNew()) {
            throw new ServiceRuntimeException("LocationGroup " + locationGroup.getName()
                    + " is new and must be persisted before save");
        }
        LocationGroup persisted = dao.findById(locationGroup.getId());
        changeGroupState(persisted, locationGroup);
        return mergeLocationGroup(persisted, locationGroup);
    }

    /**
     * Save changed fields by setting them directly. Merging the instance automatically will not work.
     * 
     * @param persisted
     *            The instance read from the persisted storage
     * @param locationGroup
     *            The new LocationGroup to merge
     * @return The merged persisted object
     */
    protected LocationGroup mergeLocationGroup(LocationGroup persisted, LocationGroup locationGroup) {
        persisted.setDescription(locationGroup.getDescription());
        persisted.setMaxFillLevel(locationGroup.getMaxFillLevel());
        persisted.setLocationGroupCountingActive(locationGroup.isLocationGroupCountingActive());
        return persisted;
    }

    /**
     * Regarding at least one groupState has changed the state is set on the {@link LocationGroup} directly. Whether a state change is
     * allowed or not is checked within the {@link LocationGroup} itself but we do a basic check before. When the parent
     * {@link LocationGroup} is blocked the current {@link LocationGroup} cannot be turned to AVAILABLE.
     * 
     * @param persisted
     *            The instance read from the persisted storage
     * @param locationGroup
     *            The instance holding the new values to save
     * @throws ServiceRuntimeException
     *             when a state change is not allowed
     */
    protected void changeGroupState(LocationGroup persisted, LocationGroup locationGroup) {
        if (persisted.getGroupStateIn() != locationGroup.getGroupStateIn()) {
            // GroupStateIn changed
            if (locationGroup.getParent() != null
                    && locationGroup.getParent().getGroupStateIn() == LocationGroupState.NOT_AVAILABLE
                    && locationGroup.getGroupStateIn() == LocationGroupState.AVAILABLE) {
                throw new ServiceRuntimeException(
                        "Not allowed to change GroupStateIn, parent locationGroup is not available");
            }
            persisted.setGroupStateIn(locationGroup.getGroupStateIn(), persisted);
        }
        if (persisted.getGroupStateOut() != locationGroup.getGroupStateOut()) {
            // GroupStateOut changed
            if (locationGroup.getParent() != null
                    && locationGroup.getParent().getGroupStateOut() == LocationGroupState.NOT_AVAILABLE
                    && locationGroup.getGroupStateOut() == LocationGroupState.AVAILABLE) {
                throw new ServiceRuntimeException(
                        "Not allowed to change GroupStateOut, parent locationGroup is not available");
            }
            persisted.setGroupStateOut(locationGroup.getGroupStateOut(), persisted);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public TreeNode<LocationGroup> getLocationGroupsAsTree() {
        return createTree(new TreeNodeImpl<LocationGroup>(), getLocationGroupsAsList());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<LocationGroup> getLocationGroupsAsList() {
        return dao.findAll();
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