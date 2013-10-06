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
package org.openwms.client.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * A AuthenticationController offers services around authentication in a RESTful
 * manner. The context root is {@value AuthenticationController#ROOT_POINT}
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Controller
@RequestMapping(AuthenticationController.ROOT_POINT)
public class AuthenticationController {

    /**
     * The DO_LOGIN.
     */
    private static final String DO_LOGIN = "/login";
    /**
     * The CHECK_LOGGEDIN.
     */
    private static final String CHECK_LOGGEDIN = "/loggedin";
    /**
     * The ROOT_POINT.
     */
    public static final String ROOT_POINT = "/sec";
    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService detailsService;
    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    /**
     * Check whether authentication was successful and a security context could
     * be established before. This assumes that an existing web filter has
     * populated the context already.
     * <ul>
     * <li>URL: {@value AuthenticationController#CHECK_LOGGEDIN}.</li>
     * <li>Method: {@link RequestMethod.GET}</li>
     * </ul>
     * 
     * @return <code>true</code> is authenticated successfully, otherwise
     *         <code>false</code>
     */
    @RequestMapping(value = CHECK_LOGGEDIN, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean loggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() != null;
    }

    /**
     * Try to authenticate credentials and set an authentication token.
     * 
     * <ul>
     * <li>URL: {@value AuthenticationController#DO_LOGIN}.</li>
     * <li>Method: {@link RequestMethod.POST}</li>
     * </ul>
     * 
     * @param authResource
     *            The credentials to authenticate with
     * @return The authenticated resource
     */
    @RequestMapping(value = DO_LOGIN, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, headers = "Content-Type=application/json")
    @ResponseBody
    public AuthResource login(@RequestBody AuthResource authResource) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authResource.getUsername(), authResource.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = detailsService.loadUserByUsername(authResource.getUsername());
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            roles.add(authority.toString());
        }
        authResource.setGrants(roles);
        authResource.setToken(TokenUtils.createToken(userDetails));
        return authResource;
    }
}
