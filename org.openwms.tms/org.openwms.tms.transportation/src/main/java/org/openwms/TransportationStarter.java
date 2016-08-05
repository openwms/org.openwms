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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import feign.RequestInterceptor;
import feign.auth.BasicAuthRequestInterceptor;
import org.ameba.annotation.EnableAspects;
import org.ameba.app.SolutionApp;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
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

    public
    @Bean
    BeanMapper beanMapper() {
        return new DozerMapperImpl("classpath:/META-INF/dozer/tms-bean-mappings.xml");
    }

    public
    @Bean
    ObjectMapper jackson2ObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        om.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, false);
        om.configure(SerializationFeature.INDENT_OUTPUT, true);
        om.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
        om.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return om;
    }

    public
    @Bean
    RequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor("user", "sa");
        /*return (t) -> {
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            //User user = (User) authentication.getPrincipal();
            String username = "user";//user.getUsername();
            String password = "sa";//(String) authentication.getCredentials();
            t.header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes(Charset.forName("UTF-8"))));
        };*/
    }
}
