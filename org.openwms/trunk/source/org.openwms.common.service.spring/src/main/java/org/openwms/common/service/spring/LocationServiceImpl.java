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

import java.util.List;

import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationType;
import org.openwms.common.integration.GenericDao;
import org.openwms.common.integration.LocationDao;
import org.openwms.common.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.common.service.spring.EntityServiceImpl
 */
@Service
@Transactional
public class LocationServiceImpl extends EntityServiceImpl<Location, Long> implements LocationService<Location> {

    @Autowired
    @Qualifier("locationTypeDao")
    private GenericDao<LocationType, Long> locationTypeDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Location> getAllLocations() {
        return ((LocationDao) dao).getAllLocations();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<LocationType> getAllLocationTypes() {
    	logger.debug("Get all location types");
    	List<LocationType> list = locationTypeDao.findAll();
    	logger.debug("List:"+list.size());
    	return list;
    }
    
    @Override
    public void createLocationType(LocationType locationType) {
    	locationTypeDao.persist(locationType);
    }
    
    @Override
    public void deleteLocationTypes(List<LocationType> locationTypes) {
    	for (LocationType locationType : locationTypes) {
    		locationType = locationTypeDao.save(locationType);
    		locationTypeDao.remove(locationType);			
		}
    }
    
    @Override
    public LocationType saveLocationType(LocationType locationType) {
    	return locationTypeDao.save(locationType);
    }
}
