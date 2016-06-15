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
package org.openwms.core.uaa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.ameba.exception.ServiceLayerException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openwms.core.CorePackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A RoleServiceTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackageClasses = CorePackage.class)
public class RoleServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceTest.class);
    @Autowired
    private RoleService srv;
    @Autowired
    private TestEntityManager entityManager;

    /**
     * Setting up some test data.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new Role("ROLE_ADMIN"));
        entityManager.persist(new Role("ROLE_USER"));
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test to call save with null argument.
     */
    @Test
    public final void testSaveWithNull() {
        try {
            srv.save(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceLayerException sre) {
            LOGGER.debug("OK: Exception when try to call save with null argument:" + sre.getMessage());
        }
    }

    /**
     * Test to save a transient role.
     */
    @Test
    public final void testSaveTransient() {
        Role role = null;
        try {
            role = srv.save(new Role("ROLE_ANONYMOUS"));
        } catch (Exception e) {
            fail("Exception thrown during saving a role");
        }
        assertNotNull("Expected to return a role", role);
        assertFalse("Expect the role as persisted", role.isNew());
    }

    /**
     * Test to save a detached role.
     */
    @Test
    public final void testSaveDetached() {
        Role role = findRole("ROLE_ADMIN");
        Role roleSaved = null;
        role.setDescription("Test description");
        try {
            roleSaved = srv.save(role);
        } catch (Exception e) {
            fail("Exception thrown during saving a role");
        }
        assertNotNull("Expected to return a role", roleSaved);
        assertFalse("Expect the role as persisted", roleSaved.isNew());
        assertEquals("Expected that description was saved", "Test description", roleSaved.getDescription());
    }

    /**
     * Test findAll.
     */
    @Test
    public final void testFindAll() {
        assertEquals("2 Roles are expected", 2, srv.findAll().size());
    }

    private Role findRole(String roleName) {
        return (Role) entityManager.getEntityManager().createQuery("select from Role r where r.name = :name").setParameter("name", roleName)
                .getSingleResult();
    }
}