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

import java.util.Comparator;

import javax.persistence.PersistenceException;

import org.junit.Assert;
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
        Assert.assertEquals("Module2.moduleName", "Module2", m2.getModuleName());
        Assert.assertEquals("Module2.url", "Url", m2.getUrl());
        Assert.assertTrue(m2.isNew());
        Assert.assertNull(m2.getId());
        Assert.assertNotSame("Same name and url", m2.equals(new Module("Module2", "Url")));
    }

    /**
     * Ensure that it is not allowed to create a Module without a moduleName.
     */
    @Test
    public final void testNegativeCreation() {
        new Module("test", "test");
        new Module("test", "");
        new Module("test", null);
        try {
            new Module("", "test");
            Assert.fail("IAE expected when creating Module with empty moduleName");
        } catch (IllegalArgumentException iae) {}
        try {
            new Module(null, "test");
            Assert.fail("IAE expected when creating Module with moduleName equals to null");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Test that it is not allowed to set the moduleName to null.
     */
    @Test
    public final void testChangingModuleName() {
        Module m = new Module("Module", "url");
        try {
            m.setModuleName(null);
            Assert.fail("Not allowed to set the moduleName to null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception setting moduleName to null is not allowed");
        }
        try {
            m.setModuleName("");
            Assert.fail("Not allowed to set the moduleName to an empty String");
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
            Assert.fail("Not allowed to set the url to null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception setting url to null is not allowed");
        }
        try {
            m.setUrl("");
            Assert.fail("Not allowed to set the url to an empty String");
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
            Assert.fail("Businesskey not complete");
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
            Assert.fail("Businesskey not complete");
        }
        try {
            m1.setUrl(null);
            Assert.fail("Url cannot be set to null");
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
        Assert.assertFalse("Must be persisted before", m.isNew());
        Assert.assertFalse("Loaded must be transient", m.isLoaded());
    }

    @Test
    public final void testStartupOrdering() {
        Module m1 = new Module("module1", "Module 1");
        m1.setStartupOrder(1);
        Module m2 = new Module("module2", "Module 2");
        m2.setStartupOrder(2);
        Comparator<Module> comp = new Module.ModuleComparator();
        Assert.assertTrue(-1 == comp.compare(m1, m2));

        m1.setStartupOrder(3);
        Assert.assertTrue(1 == comp.compare(m1, m2));

        m2.setStartupOrder(3);
        Assert.assertTrue(1 == comp.compare(m1, m2));
    }
}
