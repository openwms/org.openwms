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
import java.io.Serializable;

import org.ameba.exception.BehaviorAwareException;
import org.ameba.exception.BusinessRuntimeException;
import org.ameba.exception.NotFoundException;
import org.ameba.http.Response;
import org.ameba.mapping.BeanMapper;
import org.openwms.tms.PriorityLevel;
import org.openwms.tms.TMSConstants;
import org.openwms.tms.TransportOrder;
import org.openwms.tms.TransportationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

/**
 * A TransportationController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 1.0
 */
@RestController(TMSConstants.ROOT_ENTITIES)
class TransportationController {

    @Autowired
    private BeanMapper m;
    @Autowired
    private TransportationService<TransportOrder> service;

    @GetMapping(TMSConstants.ROOT_ENTITIES + "/{pKey}")
    public TransportOrder findByPKey(@PathVariable String pKey) {
        return service.findByPKey(pKey);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createTO(@RequestBody CreateTransportOrderVO vo, HttpServletRequest req, HttpServletResponse resp) {
        validatePriority(vo);
        TransportOrder to = service.create(vo.getBarcode(), vo.getTarget(), PriorityLevel.valueOf(vo.getPriority()));
        resp.addHeader(HttpHeaders.LOCATION, getCreatedResourceURI(req, to.getPersistentKey()));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTO(@RequestBody CreateTransportOrderVO vo) {
        validatePriority(vo);
        service.update(m.map(vo, TransportOrder.class));
    }

    @ExceptionHandler(BusinessRuntimeException.class)
    public ResponseEntity<Response<Serializable>> handleNotFound(HttpServletResponse res, BusinessRuntimeException ex) throws Exception {
        if (ex instanceof BehaviorAwareException) {
            BehaviorAwareException bae = (BehaviorAwareException) ex;
            return new ResponseEntity<>(new Response<>(ex.getMessage(), bae.getMsgKey(), bae.getStatus().toString(), bae.getData()), bae.getStatus());
        }
        return new ResponseEntity<>(new Response<>(ex.getMessage(), ex.getMsgKey(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), new String[]{ex.getMsgKey()}), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void validatePriority(@RequestBody CreateTransportOrderVO vo) {
        asList(PriorityLevel.values()).stream()
                .filter(p -> p.name().equals(vo.getPriority()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("A priority level of %s is not defined", vo.getPriority())));
    }

    private String getCreatedResourceURI(HttpServletRequest req, String objId) {
        StringBuffer url = req.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{objId}/").toString());
        return template.expand(objId).toASCIIString();
    }
}
