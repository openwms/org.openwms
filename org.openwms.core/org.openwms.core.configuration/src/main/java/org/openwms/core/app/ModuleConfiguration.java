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

import org.ameba.app.BaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * A ModuleConfiguration.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@Configuration
@Import(BaseConfiguration.class)
class ModuleConfiguration {

    @Autowired
    private ModuleProperties props;

    public
    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor(LocalValidatorFactoryBean validatorFactoryBean) {
        MethodValidationPostProcessor mvpp = new MethodValidationPostProcessor();
        mvpp.setValidator(validatorFactoryBean);
        return mvpp;
    }

    public
    @Bean
    Unmarshaller unmarshaller() {
        Jaxb2Marshaller um = new Jaxb2Marshaller();
        um.setContextPath("org.openwms.core.configuration.file");
        return um;
    }
}
