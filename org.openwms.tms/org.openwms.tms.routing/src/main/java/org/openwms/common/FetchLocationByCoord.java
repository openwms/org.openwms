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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A FetchLocationGroupByName.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
public class FetchLocationByCoord implements Function<String, Location> {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DiscoveryClient discoveryClient;

    @Override
    public Location apply(String coordinate) {

        Map<String, Object> maps = new HashMap<>();
        maps.put("locationPK", coordinate);
        //Location loc = restTemplate.getForObject("http://common-service/"+CommonConstants.API_LOCATIONS, Location.class, maps);

        try {

            ResponseEntity<Location> exchange =
                    restTemplate.exchange(
                            "http://common-service" + CommonConstants.API_LOCATIONS+"?locationPK="+coordinate,
                            HttpMethod.GET,
                            null,
                            Location.class,
                            maps);
            System.out.println(exchange);
            return exchange.getBody();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
