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
package org.openwms.common.transport;

import java.io.Serializable;

import org.ameba.exception.BehaviorAwareException;
import org.ameba.exception.BusinessRuntimeException;
import org.ameba.http.Response;
import org.ameba.mapping.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * A TransportUnitController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@RestController
public class TransportUnitController {

    @Autowired
    private TransportUnitService<TransportUnit> service;
    @Autowired
    private BeanMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value = "/transportUnits", params = {"bk"})
    public @ResponseBody TransportUnitVO getTransportUnit(@RequestParam("bk") String transportUnitBK) {
        return mapper.map(service.findByBarcode(new Barcode(transportUnitBK)), TransportUnitVO.class);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/transportUnits", params = {"bk"}/*, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE*/)
    public @ResponseBody TransportUnitVO updateTU(@RequestParam("bk") String transportUnitBK, @RequestBody TransportUnitVO tu) {
        //TransportUnit tu2 = ;
        return mapper.map(service.update(new Barcode(transportUnitBK), mapper.map(tu, TransportUnit.class)), TransportUnitVO.class);
    }

    @ExceptionHandler(BusinessRuntimeException.class)
    public ResponseEntity<Response<Serializable>> handleBRE(BusinessRuntimeException ex) throws Exception {
        if (ex instanceof BehaviorAwareException) {
            BehaviorAwareException bae = (BehaviorAwareException) ex;
            return bae.toResponse(bae.getData());//new ResponseEntity<>(new Response<>(ex.getMessage(), bae.getMsgKey(), bae.getStatus().toString(), new String[]{bae.getMsgKey()}), bae.getStatus());
        }
        return new ResponseEntity<>(new Response<>(ex.getMessage(), ex.getMsgKey(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), new String[]{ex.getMsgKey()}), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
