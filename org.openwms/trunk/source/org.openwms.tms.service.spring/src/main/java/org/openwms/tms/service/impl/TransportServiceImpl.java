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

import org.openwms.common.domain.LocationGroup;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.TransportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.service.spring.EntityServiceImpl
 * @see org.openwms.tms.service.TransportOrderService
 */
@Service
@Transactional
public class TransportServiceImpl extends EntityServiceImpl<TransportOrder, Long> implements
        TransportOrderService<TransportOrder> {

    @Autowired
    private TransportOrderDao transportOrderDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTransportsToLocationGroup(LocationGroup locationGroup) {
        return transportOrderDao.getNumberOfTransportOrders(locationGroup);
    }
}