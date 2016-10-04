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
package org.openwms.tms;

import java.util.List;
import java.util.function.Function;

import org.ameba.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * A FetchStartedTransportOrder.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@Component
public class FetchStartedTransportOrder implements Function<String, TransportOrder> {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TransportOrder apply(String barcode) {
        ResponseEntity<List<TransportOrder>> exchange =
                restTemplate.exchange(
                        "http://tms-service/v1/transportorders?barcode=" + barcode + "&state=STARTED",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TransportOrder>>() {
                        });
        if (exchange.getBody().size() == 0) {
            throw new NotFoundException(String.format("No started TransportOrders for TransportUnit [%s] found, no routing possible", barcode));
        }
        return exchange.getBody().get(0);
    }
}
