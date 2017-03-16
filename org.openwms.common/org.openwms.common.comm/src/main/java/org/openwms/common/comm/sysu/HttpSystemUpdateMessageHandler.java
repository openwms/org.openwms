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
package org.openwms.common.comm.sysu;

import java.util.function.Function;

import org.openwms.common.comm.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A HttpSystemUpdateMessageHandler forwards system updates to the LocationGroup services directly without using the routing service.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile("!"+ CommConstants.DEFAULT_HTTP_SERVICE_ACCESS)
@Component
class HttpSystemUpdateMessageHandler implements Function<SystemUpdateMessage, Void> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Void apply(SystemUpdateMessage msg) {
        restTemplate.exchange(
                "http://routing-service/v1/sysu",
                HttpMethod.POST,
                new HttpEntity<>(new RequestVO(msg.getLocationGroupName(), msg.getErrorCode())),
                Void.class
        );
        return null;
    }

    private static class RequestVO {

        String locationGroupName, errorCode;

        RequestVO(String locationGroupName, String errorCode) {
            this.locationGroupName = locationGroupName;
            this.errorCode = errorCode;
        }
    }
}
