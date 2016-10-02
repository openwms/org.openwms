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
package org.openwms.tms.routing;

import java.util.Optional;

import org.ameba.exception.NotFoundException;
import org.openwms.common.Location;
import org.openwms.common.LocationGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A ActivitiMatrix.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class ActivitiMatrix implements Matrix {

    @Autowired
    private ControlProgramRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ControlProgram findBy(Route route, Location location, LocationGroupVO locationGroup) throws NoRouteException {
        Optional<ControlProgram> prg = repository.findByRouteAndLocationKey(route, location.getCoordinate());
        if (!prg.isPresent()) {
            prg = findByLocationGroup(route, locationGroup);
        }
        return prg.orElseThrow(()->new NoRouteException(String.format("No Action found for Route [%s], Location [%s], LocationGroup [%s]", route.getRouteId(), location.getCoordinate(), locationGroup.getName())));
    }

    private Optional<ControlProgram> findByLocationGroup(Route route, LocationGroupVO locationGroup) {
        Optional<ControlProgram> cp = repository.findByRouteAndLocationGroupName(route, locationGroup.getName());
        if (!cp.isPresent() && locationGroup.hasLink("parent")) {
            cp = findByLocationGroup(route, findLocationGroup(locationGroup.getLink("parent")));
        }
        return cp;
    }

    private LocationGroupVO findLocationGroup(Link parent) {
        LocationGroupVO lg = restTemplate.getForObject(parent.getHref(), LocationGroupVO.class);
        if (lg == null) {
            throw new NotFoundException(String.format("No LocationGroup at %s found", parent.getHref()));
        }
        return lg;
    }
}
