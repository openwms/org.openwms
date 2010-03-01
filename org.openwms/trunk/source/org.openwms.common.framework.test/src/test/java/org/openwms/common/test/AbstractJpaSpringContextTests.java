/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.openwms.common.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * An AbstractJpaSpringContextTests.
 * <p>
 * An abstract superclass suitable for unit tests that run with Spring's
 * {@link SpringJUnit4ClassRunner}. This class is transaction aware.
 * </p>
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @since 0.1
 * @see {@link org.springframework.test.context.junit4.SpringJUnit4ClassRunner}
 * @see {@link org.springframework.test.context.transaction.TransactionConfiguration}
 * @version $Revision$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:/META-INF/spring/Test-Infrastructure-context.xml")
@TransactionConfiguration
@Transactional
public abstract class AbstractJpaSpringContextTests {

    /**
     * Logger instance can be used by subclasses.
     */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * EntityManager instance to be accessed by subclasses.
     */
    @PersistenceContext
    protected EntityManager entityManager;
}