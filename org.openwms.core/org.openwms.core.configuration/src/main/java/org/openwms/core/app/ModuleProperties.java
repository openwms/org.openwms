/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.openwms.core.app;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A ModuleProperties defines module specific Spring Boot properties.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 2.0
 */
@ConfigurationProperties(prefix = "openwms.core.config")
public class ModuleProperties {

    /**
     * Spring resource location to load the initial preferences from.
     */
    private String initialProperties = "classpath:initial-preferences.xml";

    public String getInitialProperties() {
        return initialProperties;
    }

    public void setInitialProperties(String initialProperties) {
        this.initialProperties = initialProperties;
    }
}
