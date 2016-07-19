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
package org.openwms;

import java.nio.charset.Charset;
import java.util.Base64;

import feign.RequestInterceptor;
import org.ameba.annotation.EnableAspects;
import org.ameba.app.SolutionApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

/**
 * A Starter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
//@EnableEurekaServer
//@EnableEurekaClient
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication(scanBasePackageClasses = {TransportationStarter.class, SolutionApp.class})
@RestController
@EnableAspects
public class TransportationStarter {

    /**
     * Boot up!
     *
     * @param args Some args
     */
    public static void main(String[] args) {
        SpringApplication.run(TransportationStarter.class, args);
    }

    @Bean
    public RequestInterceptor basicAuthRequestInterceptor() {
        return (t) -> {
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //User user = (User) authentication.getPrincipal();
            String username = "user";//user.getUsername();
            String password = "sa";//(String) authentication.getCredentials();
            t.header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(Charset.forName("UTF-8"))));
        };
    }

}
