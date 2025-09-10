/*
 * Copyright 2005-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.startup;

import org.openwms.core.SpringProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A ApplicationInitializer is used to determine the runtime environment OpenWMS is
 * running in. In case of an OSGi server, like Spring dmServer, the expected Spring
 * profile is {@link SpringProfiles#OSGI}. If not running in an OSGi environment, the
 * profile {@link SpringProfiles#NON_OSGI} is activated - if not already set.
 *
 * @author Heiko Scherrer
 * @deprecated Multiple environments are likely going to be removed
 */
@Deprecated
public class ApplicationInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInitializer.class);

    /**
     * {@inheritDoc}
     * <p>
     * Depending on the underlying platform, different Spring profiles are included.
     */
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        var activeProfile = System.getProperty("spring.profiles.active");
        if (SpringProfiles.OSGI.equalsIgnoreCase(activeProfile)) {
            LOGGER.info("Running in OSGI environment");
        } else if (SpringProfiles.NON_OSGI.equalsIgnoreCase(activeProfile)) {
            LOGGER.info("Running in a non OSGI environment");
        } else {
            applicationContext.getEnvironment().setActiveProfiles(SpringProfiles.NON_OSGI);
            applicationContext.refresh();
            LOGGER.info("Switched to a non OSGI environment");
        }
    }
}