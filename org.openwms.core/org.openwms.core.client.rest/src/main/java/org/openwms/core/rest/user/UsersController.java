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
import java.util.List;

import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * An UsersController determines the RESTful api of Users. HttpStatus codes and
 * exception handling is handled with an advice.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Controller
@RequestMapping("/api/user")
public class UsersController {

    @Autowired
    private UserService service;
    @Autowired
    private BeanMapper mapper;

    /**
     * Takes a newly created User instance and persists it. It does not matter
     * whether the instance exists before or not.
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Content-Type=application/json")
    @ResponseBody
    public User create(@RequestBody User user) {
        return service.save(user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Content-Type=application/json")
    @ResponseBody
    public User getUserById(@RequestParam("username") String pUsername) {
        // TODO [scherrer] : clarify if this is necessary
        User user = findByUsername(pUsername);
        if (user == null) {
            throw new IllegalArgumentException("User with username " + pUsername + " not found");
        }
        return user;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<UserVO> findAllUsers() {
        return mapper.map(service.findAll());
    }

    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User save(@RequestBody User user) {
        return service.save(user);
    }

    @RequestMapping(method = RequestMethod.DELETE, consumes = MediaType.APPLICATION_JSON_VALUE/*
                                                                                               * ,
                                                                                               * headers
                                                                                               * =
                                                                                               * "Content-Type=application/json"
                                                                                               */)
    @ResponseBody
    public void remove(@RequestBody User user) {
        service.remove(user);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Content-Type=application/json")
    @ResponseBody
    public void remove(@RequestParam("username") String pUsername) {
        User user = findByUsername(pUsername);
        if (user == null) {
            throw new IllegalArgumentException("User with username " + pUsername + " not found");
        }
        service.remove(user);
    }

    private User findByUsername(String pUsername) {
        List<User> users = service.findAll();
        for (User user : users) {
            if (user.getUsername().equals(pUsername)) {
                return user;
            }
        }
        return null;
    }

    // @RequestMapping(method = RequestMethod.PUT, produces =
    // MediaType.APPLICATION_JSON_VALUE, consumes =
    // MediaType.APPLICATION_JSON_VALUE, headers =
    // "Content-Type=application/json")
    // @ResponseBody
    public void changeUserPassword(@RequestBody UserPassword userPassword) {
        service.changeUserPassword(userPassword);
    }

}
