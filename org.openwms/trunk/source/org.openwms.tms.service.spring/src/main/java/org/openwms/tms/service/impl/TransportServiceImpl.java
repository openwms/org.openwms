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

import org.apache.commons.lang.StringUtils;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationGroup;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.domain.values.Problem;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.exception.ServiceException;
import org.openwms.common.service.spring.EntityServiceImpl;
import org.openwms.tms.domain.order.TransportOrder;
import org.openwms.tms.domain.order.TransportOrder.TRANSPORT_ORDER_STATE;
import org.openwms.tms.domain.values.PriorityLevel;
import org.openwms.tms.integration.TransportOrderDao;
import org.openwms.tms.service.TransportOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public TransportOrder createTransportOrder(Barcode barcode,
    		LocationGroup targetLocationGroup, Location targetLocation,
    		PriorityLevel priority) {
    	if (barcode == null) {
    		throw new ServiceException("Barcode cannot be null when creating a TransportOrder");
    	}
    	TransportUnit transportUnit = transportUnitDao.findByUniqueId(barcode);
    	if (transportUnit == null) {
    		throw new ServiceException("TransportUnit with Barcode "+barcode+" not found");
    	}
    	TransportOrder transportOrder = new TransportOrder();
    	transportOrder.setTransportUnit(transportUnit);
    	
    	Location tLocation = null;
    	LocationGroup tLocationGroup = null;
    	if (targetLocation != null) {
    		tLocation = locationDao.save(targetLocation);
    		transportOrder.setTargetLocation(tLocation);
    	}
    	if (targetLocationGroup != null) {
    		tLocationGroup = locationGroupDao.save(targetLocationGroup);
    		transportOrder.setTargetLocationGroup(tLocationGroup);
    	}
    	if (tLocation == null && tLocationGroup == null) {
    		throw new ServiceException("Either a Location or a LocationGroup must be exist to create a TrasportOrder");
    	}
    	if (priority != null) {
    		transportOrder.setPriority(priority);
    	}
    	addEntity(transportOrder);
    	dao.persist(transportOrder);
    	//transportOrder = save(transportOrder);
    	logger.error("ID of to:"+transportOrder.getId());
    	return transportOrder;
    }
    
    @Override
    public List<Long> cancelTransportOrders(List<TransportOrder> transportOrders) {
        List<Long> canceled = new ArrayList<Long>(transportOrders.size());
        for (TransportOrder transportOrder : transportOrders) {
            try {
                transportOrder = dao.save(transportOrder);
                transportOrder.setState(TRANSPORT_ORDER_STATE.CANCELED);
            }
            catch (RuntimeException e) {
                logger.error("Could not cancel TransportOrder with ID:"+transportOrder.getId());
                Problem problem = new Problem(e.getMessage());
                transportOrder.setProblem(problem);
                canceled.add(transportOrder.getId());
            }
        }
        return canceled;
    }
}