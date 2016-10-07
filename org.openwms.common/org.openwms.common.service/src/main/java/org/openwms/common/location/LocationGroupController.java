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
package org.openwms.common.location;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

import org.ameba.exception.NotFoundException;
import org.ameba.i18n.Translator;
import org.ameba.mapping.BeanMapper;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.openwms.common.CommonConstants;
import org.openwms.common.CommonMessageCodes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriTemplate;

/**
 * A LocationGroupController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @since 2.0
 */
@RestController(CommonConstants.API_LOCATIONGROUPS)
class LocationGroupController {

    @Autowired
    private LocationGroupService<LocationGroup> locationGroupService;
    @Autowired
    private Translator translator;
    @Autowired
    private BeanMapper mapper;

    @PatchMapping(value = CommonConstants.API_LOCATIONGROUPS + "/{id}")
    public void save(@PathVariable String id, @RequestParam(name = "statein", required = false) LocationGroupState stateIn, @RequestParam(name = "stateout", required = false) LocationGroupState stateOut, HttpServletRequest req, HttpServletResponse res) {
        locationGroupService.changeGroupState(id, stateIn, stateOut);
        res.addHeader(HttpHeaders.LOCATION, getLocationForCreatedResource(req, id));
    }

    @GetMapping(params = {"name"})
    public LocationGroupVO getLocationGroup(@RequestParam("name") String name) {
        Optional<LocationGroup> opt = locationGroupService.findByName(name);
        LocationGroup locationGroup = opt.orElseThrow(() -> new NotFoundException(translator, CommonMessageCodes.LOCATION_GROUP_NOT_FOUND, new String[]{name}, name));
        LocationGroupVO result = mapper.map(locationGroup, LocationGroupVO.class);
        result.add((Iterable<Link>) linkTo(methodOn(LocationGroupController.class).getLocationGroup(name)));
        return result;
    }

    private String getLocationForCreatedResource(javax.servlet.http.HttpServletRequest req, String objId) {
        StringBuffer url = req.getRequestURL();
        UriTemplate template = new UriTemplate(url.append("/{objId}/").toString());
        return template.expand(objId).toASCIIString();
    }
}
