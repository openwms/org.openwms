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
package org.openwms.core;

import org.ameba.app.SolutionApp;
import org.ameba.i18n.AbstractTranslator;
import org.ameba.i18n.Translator;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.ameba.system.NestedReloadableResourceBundleMessageSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * A Starter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication(scanBasePackageClasses = {Starter.class, SolutionApp.class})
public class Starter {

    public
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(15);
    }

    public
    @Bean
    Translator translator() {
        return new AbstractTranslator() {
            @Override
            protected MessageSource getMessageSource() {
                return messageSource();
            }
        };
    }

    public
    @Bean
    MessageSource messageSource() {
        NestedReloadableResourceBundleMessageSource nrrbm = new NestedReloadableResourceBundleMessageSource();
        nrrbm.setBasename("i18n");
        return nrrbm;
    }

    public
    @Bean
    BeanMapper beanMapper() {
        return new DozerMapperImpl("META-INF/dozer/catalog-bean-mappings.xml");
    }

    public
    @Bean
    MethodValidationPostProcessor methodValidationPostProcessor(LocalValidatorFactoryBean validatorFactoryBean) {
        MethodValidationPostProcessor mvpp = new MethodValidationPostProcessor();
        mvpp.setValidator(validatorFactoryBean);
        return mvpp;
    }

    /**
     * /** Boot up!
     *
     * @param args Some args
     */
    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }
}
