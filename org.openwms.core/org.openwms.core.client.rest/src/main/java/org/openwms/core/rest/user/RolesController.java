/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.core.rest.user;

import java.util.Collection;

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.service.RoleService;
import org.openwms.core.service.exception.EntityNotFoundException;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * A RolesController.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Controller
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RoleService service;
    @Autowired
    private BeanMapper<Role, RoleVO> mapper;

    /**
     * Transactional access to <tt>Role</tt>s in a RESTful manner. This method returns all existing <tt>Role</tt>s.
     * <p>
     * <table>
     * <tr>
     * <td>URI</td>
     * <td>/roles</td>
     * </tr>
     * <tr>
     * <td>Verb</td>
     * <td>GET</td>
     * </tr>
     * <tr>
     * <td>Auth</td>
     * <td>YES</td>
     * </tr>
     * <tr>
     * <td>Header</td>
     * <td></td>
     * </tr>
     * </table>
     * </p>
     * <p>
     * The response is transfered in the body and is JSON encoded. It contains a collection of Role objects.
     * </p>
     * 
     * @return JSON response
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<RoleVO> findAllRoles() {
        return mapper.map(service.findAll(), RoleVO.class);
    }

    /**
     * Documented here: https://openwms.atlassian.net/wiki/x/BIAWAQ
     * 
     * @status Reviewed [scherrer]
     * @param role
     *            The {@link Role} instance to be created
     * @return An {@link ResponseVO} object to encapsulate the result of the creation operation
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<ResponseVO> create(@RequestBody RoleVO role) {
        ResponseVO result = new ResponseVO();
        HttpStatus resultStatus = HttpStatus.CREATED;
        try {
            RoleVO res = mapper.map(service.save(mapper.mapBackwards(role, Role.class)), RoleVO.class);
            result.add(new ResponseVO.ItemBuilder().wStatus(HttpStatus.CREATED).wParams(res.getName()).build());
        } catch (ServiceRuntimeException sre) {
            resultStatus = HttpStatus.NOT_ACCEPTABLE;
            ResponseVO.ResponseItem item = new ResponseVO.ItemBuilder().wMessage(sre.getMessage())
                    .wStatus(resultStatus).wParams(role.getName()).build();
            result.add(item);
        }
        return new ResponseEntity<ResponseVO>(result, resultStatus);
    }

    /**
     * Documented here: https://openwms.atlassian.net/wiki/x/BoAWAQ
     * 
     * @status Reviewed [scherrer]
     * @param rolenames
     *            An array of role names to delete
     * @return An {@link ResponseVO} object to encapsulate all single removal operations
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<ResponseVO> remove(@PathVariable("name") String... rolenames) {
        ResponseVO result = new ResponseVO();
        HttpStatus resultStatus = HttpStatus.OK;
        for (String rolename : rolenames) {
            if (rolename == null || rolename.isEmpty()) {
                continue;
            }
            try {
                service.removeByBK(rolename);
                result.add(new ResponseVO.ItemBuilder().wStatus(HttpStatus.OK).wParams(rolename).build());
            } catch (ServiceRuntimeException sre) {
                resultStatus = HttpStatus.NOT_FOUND;
                ResponseVO.ResponseItem item = new ResponseVO.ItemBuilder().wMessage(sre.getMessage())
                        .wStatus(HttpStatus.INTERNAL_SERVER_ERROR).wParams(rolename).build();
                if (EntityNotFoundException.class.equals(sre.getClass())) {
                    item.httpStatus = HttpStatus.NOT_FOUND;
                }
                result.add(item);
            }
        }
        return new ResponseEntity<ResponseVO>(result, resultStatus);
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoleVO save(@RequestBody RoleVO role) {
        Long id = role.getId();
        Role toSave = mapper.mapBackwards(role, Role.class);
        if (id == null) {
            throw new IllegalStateException(
                    "Role to save has changed in the meantime, please refresh or force to overwrite changes.");
        }
        return mapper.map(service.save(toSave), RoleVO.class);
    }

    /**
     * All general exceptions thrown by all public methods of this controller class are caught here and translated into http conform
     * responses with a status code {@value HttpStatus.INTERNAL_SERVER_ERROR}.
     * 
     * @param ex
     *            The exception occurred
     * @return A response object that wraps the server result
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> handleIOException(Exception ex) {
        if (ex.getClass().equals(HttpBusinessException.class)) {
            HttpBusinessException hbe = (HttpBusinessException) ex;
            return new ResponseEntity<>(new ResponseVO(ex.getMessage()), hbe.getHttpStatus());
        }
        return new ResponseEntity<>(new ResponseVO(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
