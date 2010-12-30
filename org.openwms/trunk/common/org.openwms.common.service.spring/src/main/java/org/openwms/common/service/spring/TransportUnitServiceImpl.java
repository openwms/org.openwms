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
package org.openwms.common.service.spring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.LocationType;
import org.openwms.common.domain.Rule;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.TypePlacingRule;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.integration.TransportUnitDao;
import org.openwms.common.service.TransportUnitService;
import org.openwms.core.integration.GenericDao;
import org.openwms.core.service.exception.RemovalNotAllowedException;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.service.listener.OnRemovalListener;
import org.openwms.core.service.spring.EntityServiceImpl;
import org.openwms.core.service.spring.util.ServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportUnitServiceImpl.
 * 
 * @author <a href="mailto:scherrer@users.sourceforge.net">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.spring.EntityServiceImpl
 */
@Service
@Transactional
public class TransportUnitServiceImpl extends EntityServiceImpl<TransportUnit, Long> implements
        TransportUnitService<TransportUnit> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("transportUnitDao")
    protected TransportUnitDao dao;

    @Autowired
    @Qualifier("locationDao")
    private GenericDao<Location, Long> locationDao;

    @Autowired
    @Qualifier("transportUnitTypeDao")
    private GenericDao<TransportUnitType, Long> transportUnitTypeDao;

    @Autowired(required = false)
    @Qualifier("onRemovalListener")
    private OnRemovalListener<TransportUnit> onRemovalListener;

    public void setOnRemovalListener(OnRemovalListener<TransportUnit> onRemovalListener) {
        this.onRemovalListener = onRemovalListener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnit createTransportUnit(Barcode barcode, TransportUnitType transportUnitType,
            LocationPK actualLocation) {
        if (logger.isDebugEnabled()) {
            logger.debug("Creating a TransportUnit with Barcode " + barcode + " of Type " + transportUnitType.getType()
                    + " on Location " + actualLocation);
        }
        TransportUnit transportUnit = dao.findByUniqueId(barcode);
        if (transportUnit != null) {
            throw new ServiceRuntimeException("TransportUnit with id " + barcode + " not found");
        }
        Location location = locationDao.findByUniqueId(actualLocation);
        if (location == null) {
            throw new ServiceRuntimeException("Location " + actualLocation + " not found");
        }
        TransportUnitType type;
        if (null == (type = transportUnitTypeDao.findByUniqueId(transportUnitType.getType()))) {
            throw new ServiceRuntimeException("TransportUnitType " + transportUnitType + " not found");
        }
        transportUnit = new TransportUnit(barcode);
        transportUnit.setTransportUnitType(type);
        transportUnit.setActualLocation(location);
        dao.persist(transportUnit);
        return transportUnit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransportUnit> getAllTransportUnits() {
        return dao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransportUnitType> getAllTransportUnitTypes() {
        return transportUnitTypeDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Rule> loadRules(String transportUnitType) {
        TransportUnitType type = transportUnitTypeDao.findByUniqueId(transportUnitType);
        List<Rule> rules = new ArrayList<Rule>();
        if (type != null) {
            logger.debug("Found type " + type);
            rules.addAll(type.getTypePlacingRules());
            rules.addAll(type.getTypeStackingRules());
        }
        logger.debug("returning a list with items" + rules.size());
        return rules;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType createTransportUnitType(TransportUnitType transportUnitType) {
        transportUnitTypeDao.persist(transportUnitType);
        return transportUnitTypeDao.save(transportUnitType);
    }

    /**
     * {@inheritDoc}
     * 
     * The implementation uses the id to find the {@link TransportUnitType} to
     * be removed and will removed the type when found.
     */
    @Override
    public void deleteTransportUnitTypes(List<TransportUnitType> transportUnitTypes) {
        for (TransportUnitType transportUnitType : transportUnitTypes) {
            TransportUnitType tut = transportUnitTypeDao.findByUniqueId(transportUnitType.getType());
            transportUnitTypeDao.remove(tut);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType saveTransportUnitType(TransportUnitType transportUnitType) {
        TransportUnitType tut = transportUnitTypeDao.save(transportUnitType);
        logger.debug("Save a TransportUnitType, list of typePlacingRules:" + tut.getTypePlacingRules().size());
        return tut;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void moveTransportUnit(Barcode barcode, LocationPK targetLocationPK) {
        TransportUnit transportUnit = dao.findByUniqueId(barcode);
        if (transportUnit == null) {
            throw new ServiceRuntimeException("TransportUnit with id " + barcode + " not found");
        }
        Location actualLocation = locationDao.findByUniqueId(targetLocationPK);
        // if (actualLocation == null) {
        // throw new ServiceException("Location with id " + newLocationPk +
        // " not found");
        // }
        transportUnit.setActualLocation(actualLocation);
        // try {
        dao.save(transportUnit);
        // }
        // catch (RuntimeException e) {
        // throw new ServiceException("Cannot move TransportUnit with barcode "
        // + barcode + " to location "
        // + newLocationPk, e);
        // }
    }

    /**
     * {@inheritDoc}
     * 
     * A ServiceRuntimeException is thrown when other {@link TransportUnit}s
     * are placed on a {@link TransportUnit} that shall be removed. Also
     * {@link TransportUnit} with active TransportOrders won't be removed, if a
     * proper delegate exists.
     */
    @Override
    public void deleteTransportUnits(List<TransportUnit> transportUnits) {
        if (transportUnits != null && transportUnits.size() > 0) {
            List<TransportUnit> tus = ServiceHelper.managedEntities(transportUnits, dao);
            // first try to delete depending ones, afterwards the parent
            // units...
            Collections.sort(tus, new Comparator<TransportUnit>() {
                @Override
                public int compare(TransportUnit o1, TransportUnit o2) {
                    return o1.getChildren().isEmpty() == true ? -1 : 1;
                };
            });
            for (TransportUnit tu : tus) {
                if (!tu.getChildren().isEmpty()) {
                    throw new ServiceRuntimeException("Other TransportUnits are placed on this TransportUnit");
                }
                try {
                    delete(tu);
                    if (logger.isDebugEnabled()) {
                        logger.debug("Successfully marked TransportUnit for removal : " + tu.getId());
                    }
                }
                catch (RemovalNotAllowedException e) {
                    logger.error("Not allowed to remove TransportUnit with id : " + tu.getId());
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType updateRules(String type, List<LocationType> newAssigned, List<LocationType> newNotAssigned) {

        TransportUnitType tut = transportUnitTypeDao.findByUniqueId(type);
        boolean found = false;
        if (newAssigned != null && newAssigned.size() > 0) {
            for (LocationType locationType : newAssigned) {
                for (TypePlacingRule rule : tut.getTypePlacingRules()) {
                    if (rule.getAllowedLocationType() == locationType) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    TypePlacingRule newRule = new TypePlacingRule(tut, locationType);
                    tut.addTypePlacingRule(newRule);
                }
            }
        }

        if (newAssigned != null && newAssigned.size() > 0) {
            for (LocationType locationType : newNotAssigned) {
                for (TypePlacingRule rule : tut.getTypePlacingRules()) {
                    if (rule.getAllowedLocationType() == locationType) {
                        tut.removeTypePlacingRule(rule);
                        break;
                    }
                }
            }
        }

        return transportUnitTypeDao.save(tut);
    }

    /**
     * Try to remove when there is no listener defined or a defined listener
     * votes for removal.
     * 
     * @param transportUnit
     *            The TransportUnit to be removed
     * @throws RemovalNotAllowedException
     *             thrown by the voter
     */
    private void delete(TransportUnit transportUnit) throws RemovalNotAllowedException {
        if (logger.isDebugEnabled() && onRemovalListener == null) {
            logger.debug("No listener onRemove defined, just try to delete it");
        }
        if (null == onRemovalListener || onRemovalListener.preRemove(transportUnit)) {
            remove(transportUnit);
        }
    }
}
