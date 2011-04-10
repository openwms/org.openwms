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

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.User;
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
    private String SYS_USER = "Sys";
    private String OP_USER = "Operator";

    /**
     * Setup two roles.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new User(SYS_USER));
        entityManager.persist(new User(OP_USER));
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
     * Testing to remove roles.
     */
    @Test
    public final void testRemove() {
        User user = findUser(SYS_USER);
        assertNotNull("User must have been persisted before", user);
        dao.remove(user);
        try {
            findUser(SYS_USER);
            fail("User has to be removed and an exception is expected");
        } catch (NoResultException nre) {
            logger.debug("OK: User was removed before");
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
