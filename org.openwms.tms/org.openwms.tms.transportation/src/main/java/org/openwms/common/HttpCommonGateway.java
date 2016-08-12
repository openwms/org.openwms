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
package org.openwms.common;

import java.util.Optional;

import feign.FeignException;
import feign.Response;
import feign.Util;
import org.ameba.Messages;
import org.ameba.exception.NotFoundException;
import org.ameba.exception.ServiceLayerException;
import org.ameba.mapping.BeanMapper;
import org.openwms.tms.targets.Location;
import org.openwms.tms.targets.LocationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A HttpCommonGateway.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Component
class HttpCommonGateway implements CommonGateway {

    @Autowired
    private CommonFeignClient commonFeignClient;
    @Autowired
    private BeanMapper m;

    @Override
    public Optional<LocationGroup> getLocationGroup(String target) {
        try {
            return Optional.of(commonFeignClient.getLocationGroup(target));
        } catch (Exception ex) {
            if (translate(ex) == 404) {
                return Optional.empty();
            } else {
                throw new ServiceLayerException(ex.getMessage());
            }
        }
    }

    @Override
    public Optional<Location> getLocation(String target) {
        return Optional.ofNullable(commonFeignClient.getLocation(target));
    }

    @Override
    public Optional<TransportUnit> getTransportUnit(String transportUnitBK) {
        //try {
        return Optional.of(commonFeignClient.getTransportUnit(transportUnitBK));
        /*} catch (Exception ex) {
            if (translate(ex) == 404) {
                return Optional.empty();
            } else {
                throw new ServiceLayerException(ex.getMessage());
            }
        }*/
    }

    @Override
    public void updateTransportUnit(TransportUnit savedTU) {
        try {
            Response res = commonFeignClient.updateTU(savedTU.getBarcode(), m.map(savedTU, TransportUnitVO.class));
            String d = Util.toString(res.body().asReader());
        } catch (Exception ex) {
            if (translate(ex) == 404) {
                throw new NotFoundException(ex.getMessage(), Messages.NOT_FOUND, savedTU.getBarcode());
            } else {
                throw new ServiceLayerException(ex.getMessage());
            }
        }
    }

    private int translate(Exception ex) {
        if (ex.getCause() instanceof FeignException) {
            return ((FeignException) ex.getCause()).status();
        }
        throw new ServiceLayerException(ex.getMessage());
    }
}
