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
package org.openwms.tms.api;

import static java.util.Arrays.asList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ameba.exception.NotFoundException;
import org.ameba.http.Response;
import org.ameba.mapping.BeanMapper;
import org.openwms.tms.Constants;
import org.openwms.tms.PriorityLevel;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

/**
 * A TransportationController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RestController
class TransportationController {

    @Autowired
    private BeanMapper m;
    @Autowired
    private TransportationService<TransportOrder> service;

    @RequestMapping(method = RequestMethod.GET, value = Constants.ROOT_ENTITIES + "/{pKey}")
    public TransportOrder findByPKey(@PathVariable String pKey) {
        return service.findByPKey(pKey);
    }

    @RequestMapping(method = RequestMethod.POST, value = Constants.ROOT_ENTITIES)
    public void createTO(@RequestBody CreateTransportOrderVO vo, HttpServletRequest req, HttpServletResponse resp) {
        asList(PriorityLevel.values()).stream()
                .filter(p -> p.name().equals(vo.getPriority()))
                .findFirst()
                .orElseThrow(() -> NotFoundException.createNotFound(String.format("A priority level of %s is not defined", vo.getPriority())));
        TransportOrder to = service.create(vo.getBarcode(), vo.getTarget(), PriorityLevel.valueOf(vo.getPriority()));
        resp.addHeader(HttpHeaders.LOCATION, getCreatedResourceURI(req, to.getPersistentKey()));
        resp.setStatus(201);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = Constants.ROOT_ENTITIES)
    public void updateTO(@RequestBody CreateTransportOrderVO vo, HttpServletResponse resp) {
        service.update(m.map(vo, TransportOrder.class));
        resp.setStatus(204);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleNotFound(HttpServletResponse res, NotFoundException ex) throws Exception {
        return new ResponseEntity<>(new Response(ex.getMessage(), ex.getMsgKey(), HttpStatus.NOT_FOUND.toString(), new String[]{ex.getMsgKey()}), HttpStatus.NOT_FOUND);
    }

    private String getCreatedResourceURI(HttpServletRequest req, String objId) {
        StringBuffer url = req.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{objId}/").toString());
        return template.expand(objId).toASCIIString();
    }
}
