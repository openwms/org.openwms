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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;

/**
 * A UserDaoTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
@ContextConfiguration("classpath:core-jpa-test-context.xml")
public class UserDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    @Qualifier("userDao")
    private UserDao dao;
    private User user;
    private static final String SYS_USER = "Sys";
    private static final String OP_USER = "Operator";
    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Setup two roles.
     * 
     * @throws InvalidPasswordException
     *             In case of any error
     */
    @Before
    public void onBefore() throws InvalidPasswordException {
        entityManager.persist(new User(SYS_USER));
        user = new User(OP_USER);
        user.changePassword("TEST");
        entityManager.persist(user);
        entityManager.persist(new Role(ROLE_ADMIN));
        entityManager.persist(new Role(ROLE_ANONYMOUS));
    }

    /**
     * Test to persist roles.
     */
    @Test
    public final void testPersist() {
        dao.persist(new User("Guest"));
        User u;
        try {
            u = findUser("Unknown");
            fail("Didn't persist the user");
        } catch (NoResultException nre) {
            LOGGER.debug("OK: Searching unknown users must force an exception");
        }
        u = findUser("Guest");
        assertNotNull("User must have been persisted before", u);
    }

    /**
     * Testing to remove system user.
     */
    @Test
    public final void testRemoveSysUser() {
        entityManager.persist(new SystemUser(SystemUser.SYSTEM_USERNAME, "pass"));
        User u = findUser(SystemUser.SYSTEM_USERNAME);
        assertTrue("Should be a system user", u instanceof SystemUser);
        dao.remove(u);
        entityManager.flush();
        entityManager.clear();
        assertTrue("Superuser may not be deleted", findUser(SystemUser.SYSTEM_USERNAME) instanceof SystemUser);
    }

    /**
     * Testing to remove users.
     */
    @SuppressWarnings("unchecked")
    @Test
    public final void testRemove() {
        User u = findUser(SYS_USER);
        assertNotNull("User must have been persisted before", u);
        List<Role> roles = entityManager.createNamedQuery(Role.NQ_FIND_ALL).getResultList();
        for (Role role : roles) {
            role.addUser(u);
        }
        entityManager.flush();
        entityManager.clear();
        u = findUser(SYS_USER);
        dao.remove(u);
        try {
            findUser(SYS_USER);
            fail("User has to be removed and an exception is expected");
        } catch (NoResultException nre) {
            LOGGER.debug("OK: User was removed before");
        }
        roles = entityManager.createNamedQuery(Role.NQ_FIND_ALL).getResultList();
        assertTrue("Roles may not been deleted", roles.size() == 2);
        for (Role role : roles) {
            assertTrue("No users assigned to each role", role.getUsers().size() == 0);
        }
    }

    /**
     * Testing some of the finders.
     */
    @Test
    public final void testFindAll() {
        assertTrue("2 persisted user have to be found", dao.findAll().size() == 2);
        assertFalse("2 persisted users have to be found, avoid cheating", dao.findAll().size() == 1);
        assertNotNull("Find user by username", dao.findByUniqueId(SYS_USER));
        assertTrue("Find user by query", dao.findByPositionalParameters(User.NQ_FIND_BY_USERNAME, SYS_USER).size() == 1);
    }

    /**
     * Test find by name and password.
     */
    @Test
    public final void testFindByUsernameAndPassword() {
        assertNotNull("User by password must be found", dao.findByNameAndPassword(new UserPassword(user, "TEST")));
    }

    /**
     * Test merging detached users.
     */
    @Test
    public final void testMerge() {
        User u = findUser(SYS_USER);
        entityManager.clear();
        u.setFullname("sys");
        u = dao.save(u);
        entityManager.flush();
        entityManager.clear();
        u = findUser(SYS_USER);
        assertEquals("Must be persisted", u.getFullname(), "sys");
    }

    private User findUser(String userName) {
        return (User) entityManager.createNamedQuery(User.NQ_FIND_BY_USERNAME).setParameter(1, userName)
                .getSingleResult();
    }
}