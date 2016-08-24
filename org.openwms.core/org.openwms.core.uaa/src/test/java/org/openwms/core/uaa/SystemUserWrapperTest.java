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

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * A SystemUserWrapperTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class SystemUserWrapperTest {

    private static final String TEST_USER = "TEST_USER";

    /**
     * Test method for
     * {@link SystemUserWrapper#hashCode()}.
     */
    @Test
    public final void testHashCode() {
        User u = new User(TEST_USER);
        User u2 = new User("TEST_USER2");
        User u3 = new User(TEST_USER);
        SystemUserWrapper uw = new SystemUserWrapper(u);
        SystemUserWrapper uw2 = new SystemUserWrapper(u2);
        SystemUserWrapper uw3 = new SystemUserWrapper(u3);

        Set<SystemUserWrapper> wrappers = new HashSet<SystemUserWrapper>();
        wrappers.add(uw);
        wrappers.add(uw2);

        // Test for same return value
        Assert.assertTrue(uw.hashCode() == uw.hashCode());
        // Test for same value for two refs
        Assert.assertTrue(uw.hashCode() == uw3.hashCode());

        Assert.assertTrue(wrappers.contains(uw));
        Assert.assertTrue(wrappers.contains(uw2));
    }

    /**
     * Test method for
     * {@link SystemUserWrapper#addDefaultGrants(java.util.Collection)}
     * .
     */
    @Test
    public final void testAddDefaultGrants() {
        SystemUserWrapper suw = new SystemUserWrapper(new User(TEST_USER));
        Assert.assertEquals(1, suw.getAuthorities().size());
        Assert.assertEquals(1, suw.getAuthorities().size());
        Assert.assertEquals(SystemUser.SYSTEM_ROLE_NAME, suw.getAuthorities().iterator().next().getAuthority());

    }

    /**
     * Test method for
     * <ul>
     * <li>
     * {@link SystemUserWrapper#getPassword()}.</li>
     * <li>
     * {@link SystemUserWrapper#setPassword(java.lang.String)}
     * </li>
     * </ul>
     */
    @Ignore
    @Test
    public final void testGetPassword() throws Exception {
        User user = new User(TEST_USER);
        BCryptPasswordEncoder enc = new BCryptPasswordEncoder(15);
        user.changePassword(enc.encode("PASS"), "PASS", enc);
        SystemUserWrapper suw = new SystemUserWrapper(user);
        Assert.assertEquals("PASS", suw.getPassword());
    }

    /**
     * Test method for
     * {@link SystemUserWrapper#equals(java.lang.Object)}
     * .
     */
    @Test
    public final void testEqualsObject() {
        User u = new User(TEST_USER);
        User usr = new User("TEST_USER2");
        SystemUserWrapper suw = new SystemUserWrapper(u);
        SystemUserWrapper suw2 = new SystemUserWrapper(u);
        SystemUserWrapper susrw = new SystemUserWrapper(usr);

        // Test to itself
        Assert.assertTrue(suw.equals(suw));
        // Test for null
        Assert.assertFalse(suw.equals(null));
        // Test for symmetric
        Assert.assertTrue(suw.equals(suw2));
        Assert.assertTrue(suw2.equals(suw));
        // Test incompatible types
        Assert.assertFalse(suw.equals(TEST_USER));
        Assert.assertFalse(suw.equals(susrw));
        Assert.assertFalse(susrw.equals(suw));
        // This password is null, the other is set
        suw2.setPassword("PASS");
        Assert.assertFalse(suw.equals(suw2));
        // Same password, but different user is wrapped
        susrw.setPassword("PASS");
        suw.setPassword("PASS");
        Assert.assertFalse(suw.equals(susrw));
        // Same user and same password
        suw.setPassword("PASS");
        Assert.assertTrue(suw.equals(suw2));
        // Same user but different password
        suw2.setPassword("PASS2");
        Assert.assertFalse(suw.equals(suw2));
    }

    /**
     * Test method for
     * {@link SystemUserWrapper#SystemUserWrapper(org.openwms.core.uaa.User)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testSystemUserWrapper() {
        new SystemUserWrapper(null);
    }
}