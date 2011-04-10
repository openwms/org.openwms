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
package org.openwms.core.integration.jpa;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.List;

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.domain.system.usermanagement.SystemUser;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.integration.UserDao;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * A UserDaoTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 */
public class UserDaoTest extends AbstractJpaSpringContextTests {

    @Autowired
    private UserDao dao;
    private User user;
    private String SYS_USER = "Sys";
    private String OP_USER = "Operator";
    private String ROLE_ADMIN = "ROLE_ADMIN";
    private String ROLE_ANONYMOUS = "ROLE_ANONYMOUS";

    /**
     * Setup two roles.
     * 
     * @throws InvalidPasswordException
     */
    @Before
    public void onBefore() throws InvalidPasswordException {
        entityManager.persist(new User(SYS_USER));
        user = new User(OP_USER);
        user.setPassword("TEST");
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
        User user;
        try {
            user = findUser("Unknown");
            fail("Didn't persist the user");
        } catch (NoResultException nre) {
            logger.debug("OK: Searching unknown users must force an exception");
        }
        user = findUser("Guest");
        assertNotNull("User must have been persisted before", user);
    }

    /**
     * Testing to remove system user.
     */
    @Test
    public final void testRemoveSysUser() {
        entityManager.persist(new SystemUser(SystemUser.SYSTEM_USERNAME));
        User user = findUser(SystemUser.SYSTEM_USERNAME);
        assertTrue("Should be a system user", user instanceof SystemUser);
        dao.remove(user);
        entityManager.flush();
        entityManager.clear();
        assertTrue("Superuser may not be deleted", findUser(SystemUser.SYSTEM_USERNAME) instanceof SystemUser);
    }

    /**
     * Testing to remove users.
     */
    @Test
    public final void testRemove() {
        User user = findUser(SYS_USER);
        assertNotNull("User must have been persisted before", user);
        List<Role> roles = entityManager.createNamedQuery(Role.NQ_FIND_ALL).getResultList();
        for (Role role : roles) {
            role.addUser(user);
        }
        entityManager.flush();
        entityManager.clear();
        user = findUser(SYS_USER);
        dao.remove(user);
        try {
            findUser(SYS_USER);
            fail("User has to be removed and an exception is expected");
        } catch (NoResultException nre) {
            logger.debug("OK: User was removed before");
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

    @Test
    public final void testFindByUsernameAndPassword() {
        assertTrue("User by password must be found",
                dao.findByNameAndPassword(new UserPassword(user, "TEST")) instanceof User);
    }

    /**
     * Test merging detached users.
     */
    @Test
    public final void testMerge() {
        User user = findUser(SYS_USER);
        entityManager.clear();
        user.setFullname("sys");
        user = dao.save(user);
        entityManager.flush();
        entityManager.clear();
        user = findUser(SYS_USER);
        assertEquals("Must be persisted", user.getFullname(), "sys");
    }

    private User findUser(String userName) {
        return (User) entityManager.createNamedQuery(User.NQ_FIND_BY_USERNAME).setParameter(1, userName)
                .getSingleResult();
    }

}
