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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * A TestCommonFeignClient.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RestController
public class TestCommonFeignClient implements CommonFeignClient {

    TestCommonFeignClient() {
        System.out.println("init");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/locations", params = {"locationPK"})
    public Location getLocation(@RequestParam("locationPK") String locationPk) {
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/locationGroups", params = {"name"})
    public LocationGroup getLocationGroup(@RequestParam("name") String name) {
        return null;
    }

    @Override
    public TransportUnit getTransportUnit(String transportUnitBK) {
        return null;
    }
}
