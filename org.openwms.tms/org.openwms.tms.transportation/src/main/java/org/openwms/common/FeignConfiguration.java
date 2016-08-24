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
package org.openwms.common;

import java.nio.charset.Charset;
import java.util.Base64;

import feign.RequestInterceptor;
import org.ameba.Constants;
import org.ameba.http.RequestIDHolder;
import org.ameba.tenancy.TenantHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A FeignConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
class FeignConfiguration {

    public
    @Bean
    RequestInterceptor basicAuthRequestInterceptor() {
        return (t) -> {
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //User user = (User) authentication.getPrincipal();
            String username = "user";//user.getUsername();
            String password = "sa";//(String) authentication.getCredentials();
            t.header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(Charset.forName("UTF-8"))));
            t.header(Constants.HEADER_VALUE_X_TENANT, TenantHolder.getCurrentTenant());
            String reqId = RequestIDHolder.getRequestID();
            if (reqId != null) {
                t.header(Constants.HEADER_VALUE_X_REQUESTID, reqId);
            }
        };
    }
}
