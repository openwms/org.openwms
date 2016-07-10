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
package org.openwms.common.location;

import java.util.Date;
import java.util.List;

import org.ameba.annotation.TxService;
import org.ameba.exception.NotFoundException;
import org.ameba.exception.ServiceLayerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * A LocationServiceImpl.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.2
 * @since 0.1
 */
@TxService
class LocationServiceImpl implements LocationService<Location> {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationTypeRepository locationTypeRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public List<Location> getAllLocations() {
        List<Location> list = locationRepository.findAll();
        for (Location location : list) {
            location.setLastAccess(new Date());
            locationRepository.save(location);
        }
        return list;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location removeMessages(Long id, List<Message> messages) {
        Location location = locationRepository.findOne(id);
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
        return locationTypeRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * <p>
     * If the <code>locationType</code> is a transient one, it will be persisted otherwise saved.
     */
    @Override
    public void createLocationType(LocationType locationType) {
        if (locationType.isNew()) {
            locationTypeRepository.save(locationType);
        } else {
            locationTypeRepository.save(locationType);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     * The implementation uses the id to find the {@link LocationType} to be removed and will removed the type when found.
     */
    @Override
    public void deleteLocationTypes(List<LocationType> locationTypes) {
        for (LocationType locationType : locationTypes) {
            LocationType lt = locationTypeRepository.findOne(locationType.getId());
            locationTypeRepository.delete(lt);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationType saveLocationType(LocationType locationType) {
        return locationTypeRepository.save(locationType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Location findByLocationId(LocationPK locationPK) {
        return locationRepository.findByLocationId(locationPK).orElseThrow(() -> new NotFoundException(String.format("No Location with locationPk %s found", locationPK), null));
    }
}