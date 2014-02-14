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

package org.openwms.core.infrastructure.configuration;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * A ConfigurationApplicationContextConfiguration is the Spring Java Configuration that initializes the application properties as
 * {@link PropertySourcesPlaceholderConfigurer}. Import this configuration to take advantage of the JavaConfig mechanism instead of XML
 * configuration.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
@Configuration
@PropertySource("org.openwms.core.infrastructure.configuration.properties")
public class ConfigurationApplicationContextConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private ApplicationContext ctx;

    /**
     * Export the wrapped <tt>org.openwms.core.infrastructure.configuration.properties</tt> file as
     * {@link PropertySourcesPlaceholderConfigurer}.
     * 
     * @return the instance
     */
    @Bean
    public PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer propertySources = new PropertySourcesPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[] { new ClassPathResource(
                "org.openwms.core.infrastructure.configuration.properties") };
        propertySources.setLocations(resources);
        propertySources.setIgnoreUnresolvablePlaceholders(true);
        return propertySources;
    }

    /**
     * Export the <tt>org.openwms.core.infrastructure.configuration.properties</tt> file as {@link Properties} instance.
     * 
     * @return the instance
     */
    @Bean
    public Properties globals() {
        Properties prop = new Properties();
        try {
            prop.load(ConfigurationApplicationContextConfiguration.class.getClassLoader().getResourceAsStream(
                    "org.openwms.core.infrastructure.configuration.properties"));
            return prop;
        } catch (IOException ex) {
            throw new RuntimeException(
                    "Died when trying to load org.openwms.core.infrastructure.configuration.properties from classpath, with message: "
                            + ex.getMessage());
        }
    }
}
