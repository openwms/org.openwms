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
 * An UsersController represents a RESTful access to <tt>User</tt>s. It is transactional by the means it is the outer
 * application service facade that returns validated and completed <tt>User</tt> objects to its clients.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Controller
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UserService service;
    @Autowired
    private BeanMapper<User, UserVO> mapper;

    /**
     * This method returns all existing <tt>User</tt>s.
     * 
     * <p>
     *   <table>
     *     <tr><td>URI</td><td>/users</td></tr>
     *     <tr><td>Verb</td><td>GET</td></tr>
     *     <tr><td>Auth</td><td>YES</td></tr>
     *     <tr><td>Header</td><td></td></tr>
     *   </table>
     * </p>
     * <p>
     * The response stores <tt>User</tt> instances JSON encoded. It contains a collection of <tt>User</tt> objects.
     * </p>
     * 
     * @return JSON response
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Collection<UserVO> findAllUsers() {
        return mapper.map(service.findAll(), UserVO.class);
    }

    /**
     * Takes a newly created <tt>User</tt> instance and persists it.
     * 
     * <p>
     *   <table>
     *     <tr><td>URI</td><td>/users</td></tr>
     *     <tr><td>Verb</td><td>POST</td></tr>
     *     <tr><td>Auth</td><td>YES</td></tr>
     *     <tr><td>Header</td><td></td></tr>
     *   </table>
     * </p>
     * <p>
     * Request Body
     * <pre>
     *   {
     *     "username" : "testuser"
     *   }
     * </pre>
     * Parameters:
     * <ul>
     *   <li>username (String):</li>
     *   The unique username.
     * </ul>
     * </p>
     * <p>
     * Response Body
     * <pre>
     *   {
     *     "id" : 4711,
     *     "username" : "testuser",
     *     "token" : "876sjh36ejwhd",
     *     "version" : 1
     *   }
     * </pre>
     * <ul>
     *   <li>id (Integer (32bit)):</li>
     *   The internal unique technical key for the stored instance.
     *   <li>username (String):</li>
     *   The unique username.
     *   <li>token (String):</li>
     *   A generated token that is used to authenticate each request.
     *   <li>version (Integer (32bit)):</li>
     *   A version number used internally for optimistic locking.
     * </ul>
     * </p>
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

    /**
     * FIXME [scherrer] Comment this
     * 
     * @param user
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserVO save(@RequestBody UserVO user) {
        Long id = user.getId();
        User toSave = mapper.mapBackwards(user, User.class);
        if (id != null) {
            User persistedUser = service.findById(user.getId());
            if (persistedUser.getVersion() != user.getVersion()) {
                throw new IllegalStateException(
                        "User to save has changed in the meantime, please refresh or force to overwrite changes.");
            }
            persistedUser = mapper.mapFromTo(toSave, persistedUser);
            return mapper.map(service.save(persistedUser), UserVO.class);
        }
        return mapper.map(service.save(toSave), UserVO.class);
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
