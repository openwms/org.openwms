/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import java.io.Serializable;
import java.util.List;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.util.TreeNode;

/**
 * 
 * A LocationGroupService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface LocationGroupService<T extends Serializable> extends EntityService<T> {

	void changeGroupState(T locationGroup);

	TreeNode<LocationGroup> getLocationGroupsAsTree();

	List<LocationGroup> getLocationGroupsAsList();

}
