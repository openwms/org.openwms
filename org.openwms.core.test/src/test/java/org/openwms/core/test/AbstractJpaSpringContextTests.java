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
 * @version $Revision$
 * @see org.springframework.test.context.junit4.SpringJUnit4ClassRunner
 * @since 0.1
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