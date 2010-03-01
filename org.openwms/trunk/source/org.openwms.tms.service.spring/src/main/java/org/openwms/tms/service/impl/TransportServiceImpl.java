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
package org.openwms.tms.service.impl;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.TransportOrderService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

/**
 * A TransportService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@Service
public class TransportServiceImpl extends EntityServiceImpl<TransportUnit, Long> implements TransportOrderService {

    private GenericDao<TransportUnit, Long> transportUnitDao;
    private GenericDao<Location, Long> locationDao;
    private TransportOrderDao transportOrderDao;

    @Required
    public void setLocationDao(GenericDao<Location, Long> locationDao) {
        this.locationDao = locationDao;
    }

    @Required
    public void setTransportUnitDao(GenericDao<TransportUnit, Long> transportUnitDao) {
        this.transportUnitDao = transportUnitDao;
    }

    @Required
    public void setTransportOrderDao(TransportOrderDao transportOrderDao) {
        this.transportOrderDao = transportOrderDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTransportsToLocationGroup(LocationGroup locationGroup) {
        return transportOrderDao.getNumberOfTransportOrders(locationGroup);
    }
}