/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * A AbstractJpaSpringContextTests.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:*/**/Test-Infrastructure-context.xml")
@TransactionConfiguration
@Transactional
public abstract class AbstractJpaSpringContextTests {

    protected final static Log logger = LogFactory.getLog(AbstractJpaSpringContextTests.class);

    @PersistenceContext
    protected EntityManager entityManager;

}
