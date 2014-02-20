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
package org.openwms.client.application;

import org.openwms.core.infrastructure.configuration.ConfigurationApplicationContextConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * A WebApplicationContextConfiguration is a Spring JavaConfiguration object of
 * elementary bean definitions.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
@Configuration
@ComponentScan(basePackages = { "org.openwms" })
@EnableWebMvc
@Import({ ConfigurationApplicationContextConfiguration.class })
@ImportResource({ "classpath:META-INF/spring/applicationContext-security.xml",
        "classpath*:META-INF/spring/module-context.xml", "classpath*:META-INF/spring/noosgi-context.xml",
        "classpath*:META-INF/spring/aop-context.xml" })
public class WebApplicationContextConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Set resources directory.
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
     * @Override public void addResourceHandlers(ResourceHandlerRegistry
     *           registry) {
     *           registry.addResourceHandler("/resources/**").addResourceLocations
     *           ("/resources/"); }
     */

    /**
     * Register default ReloadableResourceBundleMessageSource with a basename
     * <tt>messages</tt>.
     * 
     * @return the bean public ResourceBundleMessageSource messageSource2() {
     *         ResourceBundleMessageSource source = new
     *         ResourceBundleMessageSource();
     *         source.setBasename("core-service-exceptions"); return source; }
     */
}
