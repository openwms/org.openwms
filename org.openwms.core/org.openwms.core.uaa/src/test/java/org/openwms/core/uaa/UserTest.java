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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.openwms.core.exception.InvalidPasswordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An UserTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
public class UserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserTest.class);
    private static final String TEST_USER1 = "Test username1";
    private static final String TEST_USER2 = "Test username2";
    private static final String TEST_PASSWORD = "Test password";

    public @Rule ExpectedException thrown = ExpectedException.none();

    /**
     * Test positive creation of User instances.
     */
    public
    @Test
    final void testCreation() {
        User user1 = new User(TEST_USER1);
        assertThat(TEST_USER1).isEqualTo(user1.getUsername());
        assertThat(user1.getPk()).isNull();
        assertThat(user1.isNew()).isTrue();
    }

    /**
     * Test positive creation of User instances.
     */
    public
    @Test
    final void testCreation2() {
        User user1 = new User(TEST_USER2, TEST_PASSWORD);
        assertThat(TEST_USER2).isEqualTo(user1.getUsername());
        assertThat(TEST_PASSWORD).isEqualTo(user1.getPassword());
        assertThat(user1.getPk()).isNull();
        assertThat(user1.isNew()).isTrue();
    }

    /**
     * Test that it is not possible to create invalid User instances.
     */
    public
    @Test
    final void testCreationNegative() {
        thrown.expect(IllegalArgumentException.class);
        new User("");
    }

    /**
     * Test that it is not possible to create invalid User instances.
     */
    public
    @Test
    final void testCreationNegative2() {
        thrown.expect(IllegalArgumentException.class);
        new User("", TEST_PASSWORD);
    }

    /**
     * Test that it is not possible to create invalid User instances.
     */
    public
    @Test
    final void testCreationNegativ3() {
        thrown.expect(IllegalArgumentException.class);
        new User(null);
    }

    /**
     * Test that it is not possible to create invalid User instances.
     */
    public
    @Test
    final void testCreationNegative4() {
        thrown.expect(IllegalArgumentException.class);
        new User(null, TEST_PASSWORD);
    }

    /**
     * Test that only valid passwords can be stored and the removal of the oldest password in the history list works.
     */
    public
    @Test
    final void testPasswordHistory() {
        User u1 = new User(TEST_USER1);
        for (int i = 0; i <= User.NUMBER_STORED_PASSWORDS + 5; i++) {
            try {
                if (i <= User.NUMBER_STORED_PASSWORDS) {
                    u1.changePassword(String.valueOf(i));
                } else {
                    LOGGER.debug("Number of password history exceeded, resetting to:0");
                    u1.changePassword("0");
                }
            } catch (InvalidPasswordException e) {
                if (i <= User.NUMBER_STORED_PASSWORDS) {
                    Assert.fail("Number of acceptable passwords not exceeded");
                } else {
                    LOGGER.debug("OK: Exception because password is already in the list, set password to:" + i);
                    try {
                        u1.changePassword(String.valueOf(i));
                    } catch (InvalidPasswordException ex) {
                        LOGGER.debug("Error" + ex.getMessage());
                    }
                }
            }
            try {
                // Just wait to setup changeDate correctly. Usually password
                // changes aren't done within the same millisecond
                Thread.sleep(100);
            } catch (InterruptedException e) {
                LOGGER.debug("Error" + e.getMessage());
            }
        }
        // Verify that the password list was sorted in the correct order.
        String oldPassword = null;
        for (UserPassword pw : u1.getPasswords()) {
            if (oldPassword == null) {
                oldPassword = pw.getPassword();
                continue;
            }
            assertThat(Integer.valueOf(oldPassword)).isGreaterThan(Integer.valueOf(pw.getPassword()));
        }
    }

    /**
     * Test hashCode() and equals(obj).
     */
    public
    @Test
    final void testHashCodeEquals() {
        User user1 = new User(TEST_USER1);
        User user2 = new User(TEST_USER1);
        User user3 = new User(TEST_USER2);

        // Just the name is considered
        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isEqualTo(user2);
        assertThat(user1).isNotEqualTo(user3);

        // Test behavior in hashed collections
        Set<User> users = new HashSet<>();
        users.add(user1);
        users.add(user2);
        assertThat(users).hasSize(1);
        users.add(user3);
        assertThat(users).hasSize(2);
    }
}