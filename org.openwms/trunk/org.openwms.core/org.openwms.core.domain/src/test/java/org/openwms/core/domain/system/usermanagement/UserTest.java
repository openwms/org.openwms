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
package org.openwms.core.domain.system.usermanagement;

import org.junit.Assert;
import org.junit.Test;
import org.openwms.core.exception.InvalidPasswordException;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * An UserTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class UserTest extends AbstractJpaSpringContextTests {

    private static final String TEST_USER1 = "Test username1";
    private static final String TEST_USER2 = "Test username2";
    private static final String TEST_PASSWORD = "Test password";

    /**
     * Test positive creation of User instances.
     */
    @Test
    public final void testCreation() {
        User user1 = new User(TEST_USER1);
        Assert.assertEquals(TEST_USER1, user1.getUsername());
        Assert.assertNull(user1.getId());
        Assert.assertTrue(user1.isNew());
        user1 = new User(TEST_USER2, TEST_PASSWORD);
        Assert.assertEquals(TEST_USER2, user1.getUsername());
        Assert.assertEquals(TEST_PASSWORD, user1.getPassword());
        Assert.assertNull(user1.getId());
        Assert.assertTrue(user1.isNew());
    }

    /**
     * Test that it is not possible to create invalid User instances.
     */
    @Test
    public final void testCreationNegative() {
        try {
            new User("");
            Assert.fail("IAE expected when creating User(String) with empty username");
        } catch (IllegalArgumentException iae) {}
        try {
            new User("", TEST_PASSWORD);
            Assert.fail("IAE expected when creating User(String,String) with empty username");
        } catch (IllegalArgumentException iae) {}
        try {
            new User(null);
            Assert.fail("IAE expected when creating User(String) with username equals to null");
        } catch (IllegalArgumentException iae) {}
        try {
            new User(null, TEST_PASSWORD);
            Assert.fail("IAE expected when creating User(String,String) with username equals to null");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Test that only valid passwords can be stored and the removal of the
     * oldest password in the history list works.
     */
    @Test
    public final void testPasswordHistory() {
        User u1 = new User("TEST");
        for (int i = 0; i <= User.NUMBER_STORED_PASSWORDS + 5; i++) {
            try {
                if (i <= User.NUMBER_STORED_PASSWORDS) {
                    u1.changePassword(String.valueOf(i));
                } else {
                    logger.debug("Number of password history exceeded, resetting to:0");
                    u1.changePassword("0");
                }
            } catch (InvalidPasswordException e) {
                if (i <= User.NUMBER_STORED_PASSWORDS) {
                    Assert.fail("Number of acceptable passwords not exceeded");
                } else {
                    logger.debug("OK: Exception because password is already in the list, set password to:" + i);
                    setPasswordSafety(u1, String.valueOf(i));
                }
            }
            try {
                // Just wait to setup changeDate correctly. Usually password
                // changes aren't done within the same millisecond
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.debug("Error" + e.getMessage());
            }
        }
        // Verify that the password list was sorted in the correct order.
        String oldPassword = null;
        for (UserPassword pw : u1.getPasswords()) {
            if (oldPassword == null) {
                oldPassword = pw.getPassword();
                continue;
            }
            Assert.assertTrue("Must be sorted ascending",
                    Integer.valueOf(oldPassword) > Integer.valueOf(pw.getPassword()));
        }
    }

    private void setPasswordSafety(User u, String password) {
        try {
            u.changePassword(password);
        } catch (InvalidPasswordException e) {
            logger.debug("Error" + e.getMessage());
        }
    }
}