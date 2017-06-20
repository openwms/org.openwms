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
package org.openwms.core.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A ApplicationInitializer.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class ApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    /**
     * {@inheritDoc}
     * 
     * Depending on the underlying platform, different Spring profiles are
     * included.
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        String activeProfile = System.getProperty("spring.profiles.active");
        if ("OSGI".equalsIgnoreCase(activeProfile)) {
            LOGGER.info("Running in OSGI environment");
        } else if ("noOSGI".equalsIgnoreCase(activeProfile)) {
            LOGGER.info("Running in a non-OSGI environment");
        } else {
            applicationContext.getEnvironment().setActiveProfiles("noOSGI");
            applicationContext.refresh();
            LOGGER.info("Switched to a non-OSGI environment");
        }
    }
}