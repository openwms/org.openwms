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
package org.openwms.common.comm.req;


import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A HttpRequestMessageHandler forwards the request to the routing service.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
//@Profile("!" + CommConstants.DEFAULT_HTTP_SERVICE_ACCESS)
@Component
@RefreshScope
class HttpRequestMessageHandler implements Function<RequestMessage, Void> {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${owms.routingService.protocol}")
    private String protocol;
    @Value("${owms.routingService.username}")
    private String username;
    @Value("${owms.routingService.password}")
    private String password;
    private String endpoint;

    @PostConstruct
    void onPostConstruct() {
        endpoint = protocol+"://"+username+":"+password+"@"+"routing-service";
    }

    @Override
    public Void apply(RequestMessage msg) {
        restTemplate.exchange(
                endpoint+"/v1/req",
                //"https://routing-service/v1/req",
                HttpMethod.POST,
                new HttpEntity<>(new RequestVO(msg.getActualLocation(), msg.getBarcode())),
                Void.class
        );
        return null;
    }

    private static class RequestVO implements Serializable {

        @JsonProperty
        String actualLocation, barcode;

        RequestVO(String actualLocation, String barcode) {
            this.actualLocation = actualLocation;
            this.barcode = barcode;
        }
    }
}
