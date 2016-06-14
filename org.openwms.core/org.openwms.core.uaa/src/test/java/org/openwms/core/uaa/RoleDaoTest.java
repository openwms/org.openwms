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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A RoleDaoTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
@ContextConfiguration("classpath:core-jpa-test-context.xml")
public class RoleDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("roleDao")
    private RoleRepository dao;
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Setup two roles.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new Role(ROLE_ADMIN));
        entityManager.persist(new Role(ROLE_ANONYMOUS));
    }

    /**
     * Test to persist roles.
     */
    @Test
    public final void testPersist() {
        dao.save(new Role("ROLE_GUEST"));
        Role role;
        try {
            role = findRole("ROLE_USERS");
            fail("Didn't persist the role");
        } catch (NoResultException nre) {
            LOGGER.debug("OK: Searching unknown roles must force an exception");
        }
        role = findRole("ROLE_GUEST");
        assertNotNull("Role must have been persisted before", role);
    }

    /**
     * Testing to remove roles.
     */
    @Test
    public final void testRemove() {
        Role role = findRole(ROLE_ADMIN);
        assertNotNull("Role must have been persisted before", role);
        dao.delete(role);
        try {
            findRole(ROLE_ADMIN);
            fail("Role has to be removed and an exception is expected");
        } catch (NoResultException nre) {
            LOGGER.debug("OK: Role was removed before");
        }
    }

    /**
     * Testing some of the finders.
     */
    @Test
    public final void testFindAll() {
        assertTrue("2 persisted roles have to be found", dao.findAll().size() == 2);
        assertFalse("2 persisted roles have to be found, avoid cheating", dao.findAll().size() == 1);
        assertNotNull("Find role by roleName", dao.findByUniqueId(ROLE_ADMIN));
        assertTrue("Find role by query", dao.findByPositionalParameters(Role.NQ_FIND_BY_UNIQUE_QUERY, ROLE_ADMIN)
                .size() == 1);
    }

    /**
     * Test merging detached roles.
     */
    @Test
    public final void testMerge() {
        Role role = findRole(ROLE_ADMIN);
        entityManager.clear();
        role.setDescription("Only admins");
        role = dao.save(role);
        entityManager.flush();
        entityManager.clear();
        role = findRole(ROLE_ADMIN);
        assertEquals("", role.getDescription(), "Only admins");
    }

    private Role findRole(String roleName) {
        return (Role) entityManager.createNamedQuery(Role.NQ_FIND_BY_UNIQUE_QUERY).setParameter(1, roleName)
                .getSingleResult();
    }
}