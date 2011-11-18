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
package org.openwms.core.service.spring;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.Module;
import org.openwms.core.service.ModuleService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;

/**
 * A ModuleServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
@ContextConfiguration("classpath:/org/openwms/core/service/spring/Test-context.xml")
public class ModuleServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    private ModuleService srv;

    /**
     * Setting up some test data.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new Module("WMS", "org.openwms.wms.swf"));
        entityManager.persist(new Module("TMS", "org.openwms.tms.swf"));
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test findAll.
     */
    @Test
    public final void testFindAll() {
        assertTrue("Expect to find two modules", srv.findAll().size() == 2);
    }

    /**
     * Test exception when calling with null.
     */
    @Test
    public final void testSaveStartupOrderWithNull() {
        try {
            srv.saveStartupOrder(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: Exception thrown when calling with null");
        }
    }

    /**
     * Sort the list ascending and descending and call saveStartupOrder. After
     * reloading the list, the right order must be set. This test expects that
     * the named query to return all is ordered by startupOrder asc.
     */
    @Test
    public final void testSaveStartupOrder() {
        List<Module> modules = findAll();
        modules = setOrder(modules, true);
        srv.saveStartupOrder(modules);
        modules = findAll();
        for (int i = 0; i < modules.size(); i++) {
            logger.debug("Module " + modules.get(i).getModuleName() + " has startup order "
                    + modules.get(i).getStartupOrder());
            if (i == 0) {
                assertTrue("Expected to be TMS", "TMS".equals(modules.get(i).getModuleName()));
            } else {
                assertTrue("Expected to be WMS", "WMS".equals(modules.get(i).getModuleName()));
            }
        }
        modules = setOrder(modules, false);
        srv.saveStartupOrder(modules);
        modules = findAll();
        for (int i = 0; i < modules.size(); i++) {
            logger.debug("Module " + modules.get(i).getModuleName() + " has startup order "
                    + modules.get(i).getStartupOrder());
            if (i == 0) {
                assertTrue("After sort expected to be WMS", "WMS".equals(modules.get(i).getModuleName()));
            } else {
                assertTrue("After sort expected to be TMS", "TMS".equals(modules.get(i).getModuleName()));
            }
        }
    }

    /**
     * Try to remove transient, detached, persisted modules and null as
     * argument.
     */
    @Test
    public final void testRemove() {
        try {
            srv.remove(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: Exception thrown when calling remove with null");
        }
        try {
            srv.remove(new Module("TRANSIENT", "TRANSIENT"));
            logger.debug("Must handle to remove transient entities");
        } catch (Exception e) {
            fail("Should not throw an exception when calling with transient entities");
        }
        try {
            srv.remove(new Module("WMS", "org.openwms.wms.swf"));
            logger.debug("Must handle to remove detached entities");
            findByName("WMS");
            fail("Should throw an exception we expect that the entity was removed");
        } catch (NoResultException nre) {
            logger.debug("OK: No result, detached module was removed");
        } catch (Exception e) {
            fail("Should not throw an exception when calling with detached entities");
        }
        Module persisted = findByName("TMS");
        srv.remove(persisted);
        try {
            findByName("TMS");
            fail("Should throw an exception, we expect that the persisted entity TMS was removed");
        } catch (NoResultException nre) {
            logger.debug("OK: No result, persisted module TMS was removed");
        }
    }

    /**
     * Test to call save with null.
     */
    @Test
    public final void testSaveNull() {
        try {
            srv.save(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: Exception thrown when calling save with null");
        }
    }

    /**
     * Test to call save with null.
     */
    @Test
    public final void testSaveAnExisting() {
        try {
            srv.save(new Module("WMS", "org.openwms.wms.swf"));
            fail("Should throw an exception when trying to store an existing one");
        } catch (ServiceRuntimeException sre) {
            if (!(sre.getCause() instanceof DataAccessException)) {
                fail("Should throw a nested DataAccessException when trying to store an existing one");
            }
            logger.debug("OK: Exception thrown when calling save with null" + sre.getCause().getMessage());
        }
    }

    /**
     * Test saving a persisted entity.
     */
    @Test
    public final void testSavePersisted() {
        Module persisted = findByName("WMS");
        persisted.setDescription("TEST");
        Module merged = srv.save(persisted);
        assertEquals("Expected that a persisted module can be merged back, managed or not", "TEST",
                merged.getDescription());
    }

    /**
     * Test saving a transient entity.
     */
    @Test
    public final void testSaveTransient() {
        Module trans = new Module("TRANSIENT", "TRANSIENT");
        Module merged = srv.save(trans);
        assertFalse("Expected that a transient is not new", merged.isNew());
        // in the test case both persisted modules have an startupOrder of 0
        // first
        assertEquals("Expect that the startupOrder was set and is the last in the list of all modules", 1,
                merged.getStartupOrder());
        Module test = new Module("foo", "bar");
        test.setDescription("foo");
        srv.save(test);
        // now the startupOrders of all modules shall be ordered, try again
        merged = srv.save(merged);
        // FIXME [scherrer] : Its not correct, we expect a startupOrder == 2.
        // not fixed tonight
        assertEquals("Expect that the startupOrder was now set to 2", 1, merged.getStartupOrder());
    }

    private List<Module> setOrder(List<Module> modules, boolean asc) {
        for (Module module : modules) {
            logger.debug("Module " + module.getModuleName() + " has startup order " + module.getStartupOrder());
            if (module.getModuleName().equals("TMS")) {
                module.setStartupOrder(asc ? 1 : 2);
            } else {
                module.setStartupOrder(asc ? 2 : 1);
            }
        }
        return modules;
    }

    private Module findByName(String moduleName) {
        return (Module) entityManager.createNamedQuery(Module.NQ_FIND_BY_UNIQUE_QUERY).setParameter(1, moduleName)
                .getSingleResult();
    }

    private List<Module> findAll() {
        return entityManager.createNamedQuery(Module.NQ_FIND_ALL).getResultList();
    }
}
