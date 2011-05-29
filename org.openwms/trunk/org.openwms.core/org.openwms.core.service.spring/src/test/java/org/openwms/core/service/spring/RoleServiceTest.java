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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.service.RoleService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A RoleServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
@ContextConfiguration("classpath:/org/openwms/core/service/spring/Test-context.xml")
public class RoleServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    private RoleService srv;

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
     * Test to remove roles and call with an empty list.
     */
    @Test
    public final void testRemoveWithEmpty() {
        srv.remove(Collections.<Role> emptyList());
        assertEquals("Expect to have 2 roles", 2, entityManager.createNamedQuery(Role.NQ_FIND_ALL).getResultList()
                .size());
    }

    /**
     * Test to remove roles and call with a null argument.
     */
    @Test
    public final void testRemoveWithNull() {
        srv.remove(null);
        assertEquals("Expect to have 2 roles", 2, entityManager.createNamedQuery(Role.NQ_FIND_ALL).getResultList()
                .size());
    }

    /**
     * Test to remove roles and call with a null argument.
     */
    @Test
    public final void testRemove() {
        java.util.List<Role> roles = new ArrayList<Role>();
        roles.add(new Role("ROLE_ADMIN"));
        roles.add(new Role("ROLE_USER"));
        srv.remove(roles);
        assertEquals("Expect to have no roles", 0, entityManager.createNamedQuery(Role.NQ_FIND_ALL).getResultList()
                .size());
    }

    /**
     * Test to call save with null argument.
     */
    @Test
    public final void testSaveWithNull() {
        try {
            srv.save(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: Exception when try to call save with null argument:" + sre.getMessage());
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

    private Role findRole(String roleName) {
        return (Role) entityManager.createNamedQuery(Role.NQ_FIND_BY_UNIQUE_QUERY).setParameter(1, roleName)
                .getSingleResult();
    }

}
