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
package org.openwms.core.domain;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import javax.persistence.PersistenceException;

import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * A ModuleTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
public class ModuleTest extends AbstractJpaSpringContextTests {

    /**
     * Test some basic stuff without persistence.
     */
    @Test
    public final void testCreation() {
        Module m2 = new Module("Module2", "Url");
        assertEquals("Module2.moduleName", "Module2", m2.getModuleName());
        assertEquals("Module2.url", "Url", m2.getUrl());
        assertTrue(m2.isNew());
        assertNotSame("Same name and url", m2.equals(new Module("Module2", "Url")));
    }

    /**
     * Test that it is not allowed to set the moduleName to null.
     */
    @Test
    public final void testChangingModuleName() {
        Module m = new Module("Module", "url");
        try {
            m.setModuleName(null);
            fail("Not allowed to set the moduleName to null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception setting moduleName to null is not allowed");
        }
        try {
            m.setModuleName("");
            fail("Not allowed to set the moduleName to an empty String");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception setting moduleName to an empty String is not allowed");
        }
        m.setModuleName("OK");
    }

    /**
     * Test that it is not allowed to set the url to null.
     */
    @Test
    public final void testChangingUrl() {
        Module m = new Module("Module", "url");
        try {
            m.setUrl(null);
            fail("Not allowed to set the url to null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception setting url to null is not allowed");
        }
        try {
            m.setUrl("");
            fail("Not allowed to set the url to an empty String");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception setting url to an empty String is not allowed");
        }
        m.setUrl("uri");
    }

    /**
     * Test persisting an incomplete module.
     */
    @Test
    public final void testBusinessKey() {
        Module m1 = new Module("Module1", null);
        try {
            entityManager.persist(m1);
            entityManager.flush();
            fail("Businesskey not complete");
        } catch (PersistenceException pe) {
            logger.debug("OK: Must fail, we wait for an url");
        }
    }

    /**
     * Test a full-constructed module.
     */
    @Test
    public final void testBusinessKeyOk() {
        Module m1 = new Module("Module1", "url");
        try {
            entityManager.persist(m1);
            logger.debug("OK: BusinessKey is complete");
        } catch (PersistenceException pe) {
            fail("Businesskey not complete");
        }
        try {
            m1.setUrl(null);
            fail("Url cannot be set to null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Setting url to null is not allowed");
        }
    }

    /**
     * Test with persistence.
     */
    @Test
    public final void testLifecycle() {
        entityManager.persist(new Module("TEST", "url"));
        Module m = (Module) entityManager.createNamedQuery(Module.NQ_FIND_BY_UNIQUE_QUERY).setParameter(1, "TEST")
                .getSingleResult();
        assertFalse("Must be persisted before", m.isNew());
        assertFalse("Loaded must be transient", m.isLoaded());
    }
}
