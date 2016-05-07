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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.core.GrantedAuthority;

/**
 * A UserWrapperTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.2
 */
public class UserWrapperTest {

    private static final String TEST_USER = "TEST_USER";

    /**
     * Test method for
     * {@link UserWrapper#UserWrapper(org.openwms.core.system.usermanagement.User)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUserWrapperForNull() {
        new UserWrapper(null);
    }

    /**
     * Test method for
     * {@link UserWrapper#UserWrapper(org.openwms.core.system.usermanagement.User)}
     * .
     */
    @Test
    public final void testUserWrapper() {
        UserWrapper uw = new UserWrapper(new User(TEST_USER));
        Assert.assertEquals(new User(TEST_USER), uw.getUser());
    }

    /**
     * Test method for
     * {@link UserWrapper#getAuthorities()}.
     */
    @Test
    public final void testGetAuthoritiesWithNull() {
        UserWrapper uw = new UserWrapper(new User(TEST_USER));
        Assert.assertNotNull(uw.getAuthorities());
    }

    /**
     * Test method for
     * {@link UserWrapper#getAuthorities()}.
     */
    @Test
    public final void testGetAuthorities() {
        Role r = new Role("TEST_ROLE");
        r.addGrant(new Grant("TEST_GRANT"));
        User u = new User(TEST_USER);
        u.addRole(r);

        UserWrapper uw = new UserWrapper(u);
        Collection<GrantedAuthority> auths = uw.getAuthorities();
        Assert.assertFalse(auths.isEmpty());
        Assert.assertEquals(auths.iterator().next().getAuthority(), "TEST_GRANT");
    }

    /**
     * Test method for
     * {@link UserWrapper#getPassword()}.
     * 
     * @throws Exception
     */
    @Test
    public final void testGetPassword() throws Exception {
        User u = new User(TEST_USER);
        u.changePassword("PASS");
        UserWrapper uw = new UserWrapper(u);
        Assert.assertEquals("PASS", uw.getPassword());
    }

    /**
     * Test method for
     * {@link UserWrapper#getUsername()}.
     */
    @Test
    public final void testGetUsername() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertEquals(TEST_USER, uw.getUsername());
    }

    /**
     * Test method for
     * {@link UserWrapper#isAccountNonExpired()}
     * .
     */
    @Test
    public final void testIsAccountNonExpired() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertTrue(uw.isAccountNonExpired());
    }

    /**
     * Test method for
     * {@link UserWrapper#isAccountNonLocked()}.
     */
    @Test
    public final void testIsAccountNonLocked() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertTrue(uw.isAccountNonLocked());
    }

    /**
     * Test method for
     * {@link UserWrapper#isCredentialsNonExpired()}
     * .
     */
    @Test
    public final void testIsCredentialsNonExpired() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertTrue(uw.isCredentialsNonExpired());
    }

    /**
     * Test method for
     * {@link UserWrapper#isEnabled()}.
     */
    @Test
    public final void testIsEnabled() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertTrue(uw.isEnabled());
    }

    /**
     * Test method for
     * {@link UserWrapper#equals(java.lang.Object)}
     * .
     */
    @Test
    public final void testEqualsObject() {
        User u = new User(TEST_USER);
        User usr = new User("TEST_USER2");
        UserWrapper uw = new UserWrapper(u);
        UserWrapper uw2 = new UserWrapper(u);
        UserWrapper usrw = new UserWrapper(usr);

        // Test to itself
        Assert.assertTrue(uw.equals(uw));
        // Test for null
        Assert.assertFalse(uw.equals(null));
        // Test for symmetric
        Assert.assertTrue(uw.equals(uw2));
        Assert.assertTrue(uw2.equals(uw));
        // Test incompatible types
        Assert.assertFalse(uw.equals(TEST_USER));
        Assert.assertFalse(uw.equals(usrw));
        Assert.assertFalse(usrw.equals(uw));
    }

    /**
     * Test method for
     * {@link UserWrapper#hashCode()} .
     */
    @Test
    public final void testHashCode() {
        User u = new User(TEST_USER);
        User u2 = new User("TEST_USER2");
        User u3 = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        UserWrapper uw2 = new UserWrapper(u2);
        UserWrapper uw3 = new UserWrapper(u3);

        Set<UserWrapper> wrappers = new HashSet<UserWrapper>();
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
     * {@link UserWrapper#toString()}.
     */
    @Test
    public final void testToString() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertEquals(TEST_USER, uw.toString());
    }

}
