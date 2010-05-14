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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationPK;
import org.openwms.common.domain.LocationType;
import org.openwms.common.domain.TransportUnit;
import org.openwms.common.domain.TransportUnitType;
import org.openwms.common.domain.TypePlacingRule;
import org.openwms.common.domain.TypeStackingRule;
import org.openwms.common.domain.Rule;
import org.openwms.common.domain.values.Barcode;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.service.TransportUnitService;
import org.openwms.common.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A TransportUnitServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.service.spring.EntityServiceImpl
 */
@Service
@Transactional
public class TransportUnitServiceImpl extends EntityServiceImpl<TransportUnit, Long> implements
        TransportUnitService<TransportUnit> {

    @Autowired
    @Qualifier("locationDao")
    private GenericDao<Location, Long> locationDao;

    @Autowired
    @Qualifier("transportUnitTypeDao")
    private GenericDao<TransportUnitType, Long> transportUnitTypeDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnit createTransportUnit(Barcode barcode, TransportUnitType transportUnitType,
            LocationPK actualLocation) {
        TransportUnit transportUnit = dao.findByUniqueId(barcode);
        if (transportUnit != null) {
            throw new ServiceException("TransportUnit with id " + barcode + " already exists");
        }
        Location location = locationDao.findByUniqueId(actualLocation);
        if (location == null) {
            throw new ServiceException("Location " + actualLocation + " doesn't exists");
        }
        transportUnit = new TransportUnit(barcode);
        transportUnit.setTransportUnitType(transportUnitType);
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
        logger.debug("GetAllTransportUnits on service called");
        return dao.findAll();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<TransportUnitType> getAllTransportUnitTypes() {
    	return transportUnitTypeDao.findAll();
//    	return transportUnitTypeDao.findByOwnQuery("Select t from TransportUnitType t left join fetch t.typeStackingRules left join fetch t.typePlacingRules");
    }
    
    @Override
    public List<Rule> loadRules(String transportUnitType) {
    	TransportUnitType type = transportUnitTypeDao.findByUniqueId(transportUnitType);
    	List<Rule> rules = new ArrayList<Rule>();
    	if (type != null) {
    		logger.debug("Found type "+type);
			rules.addAll(type.getTypePlacingRules());
			rules.addAll(type.getTypeStackingRules());
    	}
    	logger.debug("returning a list with items"+rules.size());
    	return rules;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public TransportUnitType createTransportUnitType(
    		TransportUnitType transportUnitType) {
    	try {
			transportUnitTypeDao.persist(transportUnitType);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
    	return transportUnitTypeDao.save(transportUnitType);
    }
    
    @Override
    public void deleteTransportUnitTypes(List<TransportUnitType> transportUnitTypes) {
    	for (TransportUnitType transportUnitType : transportUnitTypes) {
        	transportUnitType = transportUnitTypeDao.save(transportUnitType);
        	transportUnitTypeDao.remove(transportUnitType);			
		}
    }
    
    @Override
    public TransportUnitType saveTransportUnitType(
    		TransportUnitType transportUnitType) {
    	TransportUnitType tut = transportUnitTypeDao.save(transportUnitType);
    	logger.debug("Save a TransportUnitType, list of typePlacingRules:"+tut.getTypePlacingRules().size());
    	return tut;
    }

    /**
     * @see org.openwms.common.service.TransportUnitService#moveTransportUnit(org.openwms.common.domain.values.Barcode,
     *      org.openwms.common.domain.LocationPK)
     */
    @Override
    public void moveTransportUnit(Barcode barcode, LocationPK targetLocationPK) {
        TransportUnit transportUnit = dao.findByUniqueId(barcode);
        if (transportUnit == null) {
            throw new ServiceException("TransportUnit with id " + barcode + " not found");
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
    
    @Override
    public void deleteTransportUnits(List<TransportUnit> transportUnits) {
    	if (transportUnits != null && transportUnits.size() > 0) {
        	Exception failure = null;
    		for (TransportUnit tu : transportUnits) {
				failure = delete(tu);
			}
    		if (null != failure) {
    			throw new ServiceException("Could not delete at least one TransportUnit", failure);
    		}
    	} else {
    		logger.warn("No selected TransportUnits to delete");
    	}
    }
    
    @Override
    public TransportUnitType updateRules(String type,
    		List<LocationType> newAssigned,
    		List<LocationType> newNotAssigned) {
    	
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
    
    private Exception delete(TransportUnit tu) {
    	try {
			remove(tu);
			return null;
		} catch (DataAccessException dae) {
			return dae;
		}
    }
}
