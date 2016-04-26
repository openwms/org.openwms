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
package org.openwms.common.service.spring;

import java.util.Date;
import java.util.List;

import org.ameba.exception.ServiceLayerException;
import org.openwms.common.domain.Location;
import org.openwms.common.domain.LocationType;
import org.openwms.common.integration.LocationDao;
import org.openwms.common.service.LocationService;
import org.openwms.core.domain.system.Message;
import org.openwms.core.integration.GenericDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationServiceImpl.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 * @see org.openwms.core.service.spring.EntityServiceImpl
 */
@Transactional
@Service(LocationServiceImpl.COMPONENT_NAME)
public class LocationServiceImpl implements LocationService<Location> {

    /** Springs component name. */
    public static final String COMPONENT_NAME = "locationService";

    @Autowired
    @Qualifier("locationDao")
    private LocationDao dao;

    @Autowired
    @Qualifier("locationTypeDao")
    private GenericDao<LocationType, Long> locationTypeDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public List<Location> getAllLocations() {
        List<Location> list = dao.getAllLocations();
        for (Location location : list) {
            location.setLastAccess(new Date());
            dao.save(location);
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location removeMessages(Long id, List<Message> messages) {
        Location location = dao.findById(id);
        if (null == location) {
            throw new ServiceLayerException("Location with pk " + id + " not found, probably it was removed before");
        }
        location.removeMessages(messages.toArray(new Message[messages.size()]));
        return location;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<LocationType> getAllLocationTypes() {
        return locationTypeDao.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * If the <code>locationType</code> is a transient one, it will be persisted otherwise saved.
     */
    @Override
    public void createLocationType(LocationType locationType) {
        if (locationType.isNew()) {
            locationTypeDao.persist(locationType);
        } else {
            locationTypeDao.save(locationType);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * The implementation uses the id to find the {@link LocationType} to be removed and will removed the type when found.
     */
    @Override
    public void deleteLocationTypes(List<LocationType> locationTypes) {
        for (LocationType locationType : locationTypes) {
            LocationType lt = locationTypeDao.findById(locationType.getId());
            locationTypeDao.remove(lt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationType saveLocationType(LocationType locationType) {
        return locationTypeDao.save(locationType);
    }
}