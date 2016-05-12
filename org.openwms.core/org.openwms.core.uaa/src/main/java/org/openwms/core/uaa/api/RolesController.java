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
package org.openwms.core.uaa.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import org.ameba.Messages;
import org.ameba.exception.NotFoundException;
import org.ameba.http.Response;
import org.ameba.mapping.BeanMapper;
import org.openwms.core.exception.ExceptionCodes;
import org.openwms.core.http.AbstractWebController;
import org.openwms.core.http.HttpBusinessException;
import org.openwms.core.http.ResponseVO;
import org.openwms.core.uaa.Role;
import org.openwms.core.uaa.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * A RolesController.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@RestController
@RequestMapping("/roles")
public class RolesController extends AbstractWebController {

    @Autowired
    private RoleService service;
    @Autowired
    private BeanMapper m;

    /**
     * Documented here: https://openwms.atlassian.net/wiki/x/EYAWAQ
     *
     * @return JSON response
     * @status Reviewed [scherrer]
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Response<RoleVO>> findAllRoles() {
        List<RoleVO> roles = m.map(new ArrayList<>(service.findAll()), RoleVO.class);
        return buildResponse(HttpStatus.OK, translate(Messages.SERVER_OK), Messages.SERVER_OK, roles.toArray(new RoleVO[roles.size()]));
    }

    /**
     * Documented here: https://openwms.atlassian.net/wiki/x/BIAWAQ
     *
     * @param role The {@link Role} instance to be created
     * @return An {@link ResponseVO} object to encapsulate the result of the creation operation
     * @status Reviewed [scherrer]
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Response<RoleVO>> create(@RequestBody @Valid @NotNull RoleVO role, HttpServletRequest req, HttpServletResponse resp) {
        RoleVO createdRole = m.map(service.create(m.map(role, Role.class)), RoleVO.class);
        resp.addHeader(HttpHeaders.LOCATION, getLocationForCreatedResource(req, createdRole.getId().toString()));
        return buildResponse(HttpStatus.CREATED, translate(Messages.CREATED), Messages.CREATED);
    }

    /**
     * Documented here: https://openwms.atlassian.net/wiki/x/BoAWAQ
     *
     * @param rolenames An array of role names to delete
     * @return An {@link ResponseVO} object to encapsulate all single removal operations
     * @status Reviewed [scherrer]
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseVO> remove(@PathVariable("name") @NotNull String... rolenames) {
        ResponseVO result = new ResponseVO();
        HttpStatus resultStatus = HttpStatus.OK;
        for (String rolename : rolenames) {
            if (rolename == null || rolename.isEmpty()) {
                continue;
            }
            try {
                service.removeByBK(new String[]{rolename});
                result.add(new ResponseVO.ItemBuilder().wStatus(HttpStatus.OK).wParams(rolename).build());
            } catch (Exception sre) {
                resultStatus = HttpStatus.NOT_FOUND;
                ResponseVO.ResponseItem item = new ResponseVO.ItemBuilder().wMessage(sre.getMessage())
                        .wStatus(HttpStatus.INTERNAL_SERVER_ERROR).wParams(rolename).build();
                if (NotFoundException.class.equals(sre.getClass())) {
                    item.httpStatus = HttpStatus.NOT_FOUND;
                }
                result.add(item);
            }
        }
        return new ResponseEntity<ResponseVO>(result, resultStatus);
    }

    /**
     * FIXME [scherrer] Comment this
     *
     * @param role
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoleVO save(@RequestBody @Valid RoleVO role) {
        if (role.getId() == null) {
            String msg = translate(ExceptionCodes.ROLE_IS_TRANSIENT, role.getName());
            throw new HttpBusinessException(msg, HttpStatus.NOT_ACCEPTABLE);
        }
        Role toSave = m.map(role, Role.class);
        return m.map(service.save(toSave), RoleVO.class);
    }
}
