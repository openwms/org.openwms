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
package org.openwms.common.comm.api;

import org.ameba.exception.NotFoundException;
import org.openwms.common.FetchLocationByCoord;
import org.openwms.common.FetchLocationGroupByName;
import org.openwms.common.Location;
import org.openwms.common.LocationGroupVO;
import org.openwms.tms.FetchStartedTransportOrder;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.routing.Matrix;
import org.openwms.tms.routing.ProgramExecutor;
import org.openwms.tms.routing.ProgramResult;
import org.openwms.tms.routing.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RequestMessageController is the API of the routing service component.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@RestController("/v1/req")
class RequestMessageController {

    @Autowired
    private FetchLocationGroupByName fetchLocationGroupByName;
    @Autowired
    private FetchLocationByCoord fetchLocationByCoord;
    @Autowired
    private FetchStartedTransportOrder fetchTransportOrder;
    @Autowired
    private Matrix matrix;
    @Autowired
    private ProgramExecutor executor;

    /**
     * Takes the passed message, and hands over to the service.
     */
    @PostMapping
    public void apply(@RequestBody RequestVO req) {

        /*

         Select program from
         - actual location
         - transportunit
         - locgroup x
         - to x
         - routeid x
         - type

         */
        Location location = fetchLocationByCoord.apply(req.getActualLocation());
        LocationGroupVO locationGroup = fetchLocationGroupByName.apply(req.getLocationGroupName());
        Route route;
        try {
        TransportOrder transportOrder = fetchTransportOrder.apply(req.getBarcode());
            route = Route.of(transportOrder.getRouteId());
        } catch (NotFoundException nfe) {
            route = Route.DEF_ROUTE;
        }
        ProgramResult result = executor.execute(matrix.findBy("REQ_", route, location, locationGroup));
        //return new ResponseMessage.Builder()
        // .withBarcode(result.getBarcode())
        // .withActualLocation(result.getActualLocation())
        // .withTargetLocation(result.getTargetLocation())
        // .withTargetLocationGroup(result.getLocationGroupName()).build();
    }
}
