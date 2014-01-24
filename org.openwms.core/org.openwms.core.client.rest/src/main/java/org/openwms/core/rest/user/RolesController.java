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

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
@Transactional
@Service
@Controller
@RequestMapping("/roles")
public class RolesController {

    @Autowired
    private RoleService service;
    @Autowired
    private BeanMapper<Role, RoleVO> mapper;

    /**
     * Transactional access to <tt>Role</tt>s in a RESTful manner. This method
     * returns all existing <tt>Role</tt>s.
     * <p>
     *   <table>
     *     <tr><td>URI</td><td>/roles</td></tr>
     *     <tr><td>Verb</td><td>GET</td></tr>
     *     <tr><td>Auth</td><td>YES</td></tr>
     *     <tr><td>Header</td><td></td></tr>
     *   </table>
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

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public RoleVO create(@RequestBody RoleVO role) {
        return mapper.map(service.save(mapper.mapBackwards(role, Role.class)), RoleVO.class);
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable("name") String pRolename) {
        Role role = findByUsername(pRolename);
        if (role == null) {
            throw new IllegalArgumentException("Role with name " + pRolename + " not found");
        }
        service.remove(Arrays.asList(new Role[] { role }));
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public RoleVO save(@RequestBody RoleVO role) {
        Long id = role.getId();
        Role toSave = mapper.mapBackwards(role, Role.class);
        if (id == null) {
            throw new IllegalStateException(
                    "User to save has changed in the meantime, please refresh or force to overwrite changes.");
        }
        return mapper.map(service.save(toSave), RoleVO.class);
    }

    private Role findByUsername(String pUsername) {
        List<Role> users = service.findAll();
        for (Role user : users) {
            if (user.getName().equals(pUsername)) {
                return user;
            }
        }
        return null;
    }

}
