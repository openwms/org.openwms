/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import java.util.Set;

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.LocationGroup.STATE;
import org.openwms.common.service.LocationGroupService;
import org.openwms.common.service.exception.ServiceException;
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
}
