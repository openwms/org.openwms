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

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.openwms.common.Location;
import org.openwms.common.LocationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A ActivitiMatrix.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
class ActivitiMatrix implements Matrix {

    @Autowired
    private ProcessEngine engine;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public ControlProgram findBy(Route route, Location location, LocationGroup locationGroup) throws NoRouteException {
        engine.getRepositoryService();
        Map<String, Object> variables = new HashMap<>();
        variables.put("barcode", "");
        runtimeService.startProcessInstanceById("REQ_", variables);
        return null;
    }
}
