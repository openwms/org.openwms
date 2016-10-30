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


import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.function.Function;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.util.codec.binary.Base64;
import org.openwms.common.comm.CommConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A HttpRequestMessageHandler forwards the request to the routing service.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Profile("!" + CommConstants.DEFAULT_HTTP_SERVICE_ACCESS)
@Component
class HttpRequestMessageHandler implements Function<RequestMessage, Void> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Void apply(RequestMessage msg) {
        restTemplate.exchange(
                "https://routing-service/v1/req",
                HttpMethod.POST,
                new HttpEntity<>(new RequestVO(msg.getActualLocation(), msg.getBarcode()), createHeaders("user", "sa")),
                Void.class
        );
        return null;
    }

    HttpHeaders createHeaders( String username, String password ){
        return new HttpHeaders(){
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("UTF-8")) );
                String authHeader = "Basic " + new String( encodedAuth );
                set( "Authorization", authHeader );
            }
        };
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
