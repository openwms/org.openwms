/*
 * Copyright 2018 Heiko Scherrer
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
package org.openwms.core.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * An AbstractJpaSpringContextTests is a transactional superclass for JUnit tests.
 * <p>
 * Run with Spring's {@link SpringJUnit4ClassRunner}. A datasource instance and an {@link javax.persistence.EntityManagerFactory} is
 * initialized within the application context.
 * </p>
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 */
@ContextConfiguration("classpath*:/META-INF/spring/Test-Infrastructure-context.xml")
public abstract class AbstractJpaSpringContextTests extends AbstractTransactionalJUnit4SpringContextTests {

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    /**
     * EntityManager instance to be accessed by subclasses.
     */
    @PersistenceContext
    protected EntityManager entityManager;
}