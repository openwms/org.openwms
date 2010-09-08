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
import org.openwms.tms.domain.values.PriorityLevel;
import org.openwms.tms.domain.values.TransportOrderState;
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
@Service("transportService")
@Transactional
public class TransportServiceImpl extends EntityServiceImpl<TransportOrder, Long> implements
        TransportOrderService<TransportOrder> {

    /**
     * Generic Repository DAO.
     */
    @Autowired
    @Qualifier("transportOrderDao")
    protected TransportOrderDao dao;

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
     * 
     * @see org.openwms.tms.service.TransportOrderService#getTransportsToLocationGroup(org.openwms.common.domain.LocationGroup)
     */
    @Override
    public int getTransportsToLocationGroup(LocationGroup locationGroup) {
        return dao.getNumberOfTransportOrders(locationGroup);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.service.TransportOrderService#createTransportOrder(org.openwms.common.domain.values.Barcode,
     *      org.openwms.common.domain.LocationGroup,
     *      org.openwms.tms.domain.values.PriorityLevel)
     */
    @Override
    public TransportOrder createTransportOrder(Barcode barcode, LocationGroup targetLocationGroup,
            PriorityLevel priority) {
        return createTransportOrder(barcode, targetLocationGroup, null, priority);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.service.TransportOrderService#createTransportOrder(org.openwms.common.domain.values.Barcode,
     *      org.openwms.common.domain.Location,
     *      org.openwms.tms.domain.values.PriorityLevel)
     */
    @Override
    public TransportOrder createTransportOrder(Barcode barcode, Location targetLocation, PriorityLevel priority) {
        return createTransportOrder(barcode, null, targetLocation, priority);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.service.TransportOrderService#createTransportOrder(org.openwms.common.domain.values.Barcode,
     *      org.openwms.common.domain.LocationGroup,
     *      org.openwms.common.domain.Location,
     *      org.openwms.tms.domain.values.PriorityLevel)
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
        ctx.publishEvent(new TransportServiceEvent(transportOrder.getTransportUnit(),
                TransportServiceEvent.TYPE.TRANSPORT_CREATED));
        return transportOrder;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.service.TransportOrderService#cancelTransportOrders(java.util.List)
     */
    @Override
    public List<Integer> cancelTransportOrders(List<Integer> ids, TransportOrderState state) {
        List<Integer> failure = new ArrayList<Integer>(ids.size());
        List<TransportOrder> transportOrders = dao.findByIds(getLongList(ids));
        for (TransportOrder transportOrder : transportOrders) {
            try {
                transportOrder.setState(state);
                ctx.publishEvent(new TransportServiceEvent(transportOrder.getId(),
                        TransportServiceEvent.TYPE.TRANSPORT_CANCELED));
                if (logger.isDebugEnabled()) {
                    logger.debug("TransportOrder " + transportOrder.getId() + " successfully set to:" + state);
                }
            }
            catch (RuntimeException e) {
                logger.error("Could not cancel TransportOrder with ID:" + transportOrder.getId());
                Problem problem = new Problem(e.getMessage());
                transportOrder.setProblem(problem);
                failure.add(transportOrder.getId().intValue());
            }
        }
        return failure;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.openwms.tms.service.TransportOrderService#redirectTransportOrders(java.util.List,
     *      org.openwms.common.domain.LocationGroup)
     */
    @Override
    public List<Integer> redirectTransportOrders(List<Integer> ids, LocationGroup targetLocationGroup) {
        List<Integer> failure = new ArrayList<Integer>(ids.size());
        List<TransportOrder> transportOrders = dao.findByIds(getLongList(ids));
        for (TransportOrder transportOrder : transportOrders) {
            try {
                transportOrder.setTargetLocationGroup(targetLocationGroup);
                if (logger.isDebugEnabled()) {
                    logger.debug("TransportOrder " + transportOrder.getId() + " successfully redirected to:"
                            + targetLocationGroup.getName());
                }
            }
            catch (RuntimeException e) {
                logger.error("Could not redirect TransportOrder with ID:" + transportOrder.getId());
                Problem problem = new Problem(e.getMessage());
                transportOrder.setProblem(problem);
                failure.add(transportOrder.getId().intValue());
            }
        }
        return failure;
    }

    private List<Long> getLongList(List<Integer> values) {
        List<Long> longList = new ArrayList<Long>(values.size());
        for (Integer number : values) {
            longList.add(number.longValue());
        }
        return longList;
    }
}