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

import javax.persistence.NoResultException;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPassword;
import org.openwms.core.service.UserService;
import org.openwms.core.service.exception.ServiceRuntimeException;
import org.openwms.core.service.exception.UserNotFoundException;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * A UserServiceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 */
@ContextConfiguration("classpath:/org/openwms/core/service/spring/Test-context.xml")
public class UserServiceTest extends AbstractJpaSpringContextTests {

    @Autowired
    private UserService srv;

    /**
     * Setting up some test users.
     */
    @Before
    public void onBefore() {
        entityManager.persist(new User("KNOWN"));
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Test to save a byte array as image file.
     */
    @Test
    public final void testUploadImage() {
        try {
            srv.uploadImageFile("UNKNOWN", new byte[222]);
            fail("Should throw an exception when calling with unknown user");
        } catch (UserNotFoundException unfe) {
            logger.debug("OK: User unknown" + unfe.getMessage());
        }
        srv.uploadImageFile("KNOWN", new byte[222]);
        User user = findUser("KNOWN");
        assertTrue(user.getUserDetails().getImage().length == 222);
    }

    /**
     * Test to save a NULL user.
     */
    @Test
    public final void testSaveWithNull() {
        try {
            srv.save(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: null user:" + sre.getMessage());
        }
    }

    /**
     * Test to save a transient user.
     */
    @Test
    public final void testSaveTransient() {
        User user = srv.save(new User("UNKNOWN"));
        assertFalse("User must be persisted and has a primary key", user.isNew());
    }

    /**
     * Test to save a existing detached user.
     */
    @Test
    public final void testSaveDetached() {
        User user = findUser("KNOWN");
        assertFalse("User must be persisted before", user.isNew());
        entityManager.clear();
        user.setFullname("Mr. Hudson");
        user = srv.save(user);
        entityManager.flush();
        entityManager.clear();
        user = findUser("KNOWN");
        assertEquals("Changes must be saved", "Mr. Hudson", user.getFullname());
    }

    /**
     * Test to call remove with null.
     */
    @Test
    public final void testRemoveWithNull() {
        try {
            srv.remove(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: null user:" + sre.getMessage());
        }
    }

    /**
     * Test to remove.
     */
    @Test
    public final void testRemove() {
        User user = findUser("KNOWN");
        assertFalse("User must be persisted before", user.isNew());
        entityManager.clear();
        srv.remove(user);
        entityManager.flush();
        entityManager.clear();
        try {
            findUser("KNOWN");
            fail("Must be removed before and throw an exception");
        } catch (NoResultException nre) {
            logger.debug("OK: Exception when searching for a removed entity");
        }
    }

    /**
     * Test to call with null.
     */
    @Test
    public final void testChangePasswordWithNull() {
        try {
            srv.changeUserPassword(null);
            fail("Should throw an exception when calling with null");
        } catch (ServiceRuntimeException sre) {
            logger.debug("OK: null:" + sre.getMessage());
        }
    }

    /**
     * Test to change it for an unknown user.
     */
    @Test
    public final void testChangePasswordUnknown() {
        try {
            srv.changeUserPassword(new UserPassword(new User("UNKNOWN"), "password"));
            fail("Should throw an exception when calling with null");
        } catch (UserNotFoundException unfe) {
            logger.debug("OK: null:" + unfe.getMessage());
        }
    }

    /**
     * Test to change it now.
     */
    @Test
    public final void testChangePassword() {
        boolean b = srv.changeUserPassword(new UserPassword(new User("KNOWN"), "password"));
        assertTrue("Expected to be saved", b);
    }

    private User findUser(String userName) {
        return (User) entityManager.createNamedQuery(User.NQ_FIND_BY_USERNAME).setParameter(1, userName)
                .getSingleResult();
    }

}
