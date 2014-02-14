/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
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
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.client.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

/**
 * 
 * A AuthenticationTokenProcessingFilter.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class AuthenticationTokenProcessingFilter extends GenericFilterBean {

    /**
     * The AUTH_TOKEN
     */
    public static final String AUTH_TOKEN = "Auth-Token";
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userService;

    /**
     * Create a new AuthenticationTokenProcessingFilter.
     * 
     * @param authenticationManager
     * @param userService
     */
    public AuthenticationTokenProcessingFilter(AuthenticationManager authenticationManager,
            UserDetailsService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    /**
     * {@inheritDoc}
     * 
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     *      javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {

        if (!(request instanceof HttpServletRequest)) {
            throw new RuntimeException("Expecting a http servlet request");
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(AUTH_TOKEN);

        String userName = TokenUtils.getUserNameFromToken(authToken);
        if (userName != null) {

            // The returned UserDetails object has credentials encoded, we rely
            // on two AuthenticationProviders here to
            // come around this issue, one with PasswordEncoder and one without
            UserDetails userDetails = this.userService.loadUserByUsername(userName);
            if (TokenUtils.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(), userDetails.getPassword());
                authentication.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails((HttpServletRequest) request));
                SecurityContextHolder.getContext().setAuthentication(
                        this.authenticationManager.authenticate(authentication));
            }
        }
        chain.doFilter(request, response);
        SecurityContextHolder.clearContext();
    }
}