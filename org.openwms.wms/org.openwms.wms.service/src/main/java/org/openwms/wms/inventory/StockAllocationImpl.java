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
package org.openwms.wms.inventory;

import org.ameba.annotation.TxService;
import org.openwms.wms.PackagingUnit;

/**
 * A StockAllocationImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
@TxService
class StockAllocationImpl implements Allocation {

    /**
     * {@inheritDoc}
     */
    @Override
    public PackagingUnit allocate(AllocationRule allocationRule) throws AllocationException {

        // TODO [openwms]: 29/04/16
        // em.createQuery(
        // "select pu from PackagingUnit pu where pu.availabilityState == org.openwms.wms.domain.AvailablityState.AVAILABLE and pu.product = :c.product and !pu.loadUnit.locked and pu.loadUnit.transportUnit.state = org.openwms.common.domain.values.TransportUnitState.AVAILABLE and pu.loadUnit.transportUnit.actualLocation.outgoingActive and pu.loadUnit.transportUnit.actualLocation.plcState == 0 and pu.loadUnit.transportUnit.actualLocation.outgoingActive")
        // .getResultList();
        return null;
    }
}