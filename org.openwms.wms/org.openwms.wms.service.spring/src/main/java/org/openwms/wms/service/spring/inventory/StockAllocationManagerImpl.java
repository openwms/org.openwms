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
package org.openwms.wms.service.spring.inventory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.openwms.wms.domain.PackagingUnit;
import org.openwms.wms.service.inventory.Allocation;
import org.openwms.wms.service.inventory.AllocationException;
import org.openwms.wms.service.inventory.AllocationRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * A StockAllocationManagerImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Transactional(propagation = Propagation.MANDATORY)
@Service(value = StockAllocationManagerImpl.COMPONENT_NAME)
public class StockAllocationManagerImpl implements Allocation {

    @PersistenceContext
    private EntityManager em;
    /** Springs component name. */
    public static final String COMPONENT_NAME = "stockAllocationManager";

    /**
     * {@inheritDoc}
     */
    @Override
    public PackagingUnit allocate(AllocationRule allocationRule) throws AllocationException {
        // em.createQuery(
        // "select pu from PackagingUnit pu where pu.availabilityState == org.openwms.wms.domain.AvailablityState.AVAILABLE and pu.product = :c.product and !pu.loadUnit.locked and pu.loadUnit.transportUnit.state = org.openwms.common.domain.values.TransportUnitState.AVAILABLE and pu.loadUnit.transportUnit.actualLocation.outgoingActive and pu.loadUnit.transportUnit.actualLocation.plcState == 0 and pu.loadUnit.transportUnit.actualLocation.outgoingActive")
        // .getResultList();
        return null;
    }
}