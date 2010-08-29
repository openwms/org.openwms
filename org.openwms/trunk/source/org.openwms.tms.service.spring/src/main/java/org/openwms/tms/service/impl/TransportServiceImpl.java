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

import java.util.ArrayList;
import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.Problem;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.order.TransportOrder.TRANSPORT_ORDER_STATE;
import org.openwms.tms.domain.values.PriorityLevel;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.TransportOrderService;
import org.openwms.tms.service.TransportOrderServiceException;
import org.openwms.tms.util.event.TransportServiceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("transportUnitDao")
    private GenericDao<TransportUnit, Long> transportUnitDao;

    @Autowired
    @Qualifier("locationDao")
    private GenericDao<Location, Long> locationDao;

    @Autowired
    @Qualifier("locationGroupDao")
    private GenericDao<LocationGroup, Long> locationGroupDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTransportsToLocationGroup(LocationGroup locationGroup) {
        return transportOrderDao.getNumberOfTransportOrders(locationGroup);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportOrder createTransportOrder(Barcode barcode, LocationGroup targetLocationGroup,
            PriorityLevel priority) {
        return createTransportOrder(barcode, targetLocationGroup, null, priority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportOrder createTransportOrder(Barcode barcode, Location targetLocation, PriorityLevel priority) {
        return createTransportOrder(barcode, null, targetLocation, priority);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportOrder createTransportOrder(Barcode barcode, LocationGroup targetLocationGroup,
            Location targetLocation, PriorityLevel priority) {
        if (barcode == null) {
            throw new TransportOrderServiceException("Barcode cannot be null when creating a TransportOrder");
        }
        TransportUnit transportUnit = transportUnitDao.findByUniqueId(barcode);
        if (transportUnit == null) {
            throw new TransportOrderServiceException("TransportUnit with Barcode " + barcode + " not found");
        }
        TransportOrder transportOrder = new TransportOrder();
        transportOrder.setTransportUnit(transportUnit);

        Location tLocation = null;
        if (targetLocation != null) {
            tLocation = locationDao.findById(targetLocation.getId());
            transportOrder.setTargetLocation(tLocation);
        }
        LocationGroup tLocationGroup = null;
        if (targetLocationGroup != null) {
            tLocationGroup = locationGroupDao.findById(targetLocationGroup.getId());
            transportOrder.setTargetLocationGroup(tLocationGroup);
        }
        if (tLocation == null && tLocationGroup == null) {
            throw new TransportOrderServiceException(
                    "Either a Location or a LocationGroup must be exist to create a TransportOrder");
        }
        if (priority != null) {
            transportOrder.setPriority(priority);
        }
        addEntity(transportOrder);
        dao.persist(transportOrder);
        // transportOrder = save(transportOrder);
        logger.error("ID of to:" + transportOrder.getId());
        ctx
                .publishEvent(new TransportServiceEvent(transportOrder.getId(),
                        TransportServiceEvent.TYPE.TRANSPORT_CREATED));
        return transportOrder;
    }

    @Override
    public List<Long> cancelTransportOrders(List<TransportOrder> transportOrders) {
        List<Long> canceled = new ArrayList<Long>(transportOrders.size());
        for (TransportOrder transportOrder : transportOrders) {
            try {
                transportOrder = dao.save(transportOrder);
                transportOrder.setState(TRANSPORT_ORDER_STATE.CANCELED);
                ctx.publishEvent(new TransportServiceEvent(transportOrder.getId(),
                        TransportServiceEvent.TYPE.TRANSPORT_CANCELED));
                if (logger.isDebugEnabled()) {
                    logger.debug("TransportOrder successfully set to be canceled:" + transportOrder.getId());
                }
            }
            catch (RuntimeException e) {
                logger.error("Could not cancel TransportOrder with ID:" + transportOrder.getId());
                Problem problem = new Problem(e.getMessage());
                transportOrder.setProblem(problem);
                canceled.add(transportOrder.getId());
            }
        }
        return canceled;
    }
}