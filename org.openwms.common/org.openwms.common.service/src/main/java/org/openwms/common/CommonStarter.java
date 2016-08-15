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

import org.ameba.annotation.EnableAspects;
import org.ameba.app.SolutionApp;
import org.ameba.http.EnableMultiTenancy;
import org.ameba.i18n.AbstractTranslator;
import org.ameba.i18n.Translator;
import org.ameba.mapping.BeanMapper;
import org.ameba.mapping.DozerMapperImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * A Starter.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
@SpringBootApplication(scanBasePackageClasses = {CommonStarter.class, SolutionApp.class})
@EnableAspects
@EnableJpaAuditing
@EnableMultiTenancy
public class CommonStarter {

    /**
     * Boot up!
     *
     * @param args Some args
     */
    public static void main(String[] args) {
        SpringApplication.run(CommonStarter.class, args);
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
        ResourceBundleMessageSource nrrbm = new ResourceBundleMessageSource();
        nrrbm.setBasename("i18n");
        return nrrbm;
    }

    public
    @Bean
    BeanMapper beanMapper() {
        return new DozerMapperImpl("classpath:/META-INF/dozer/common-bean-mappings.xml");
    }
}
