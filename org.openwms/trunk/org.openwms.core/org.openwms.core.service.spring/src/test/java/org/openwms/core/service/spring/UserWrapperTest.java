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

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.Grant;
import org.openwms.core.domain.system.usermanagement.Role;
import org.openwms.core.domain.system.usermanagement.User;
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
     * {@link org.openwms.core.service.spring.UserWrapper#UserWrapper(org.openwms.core.domain.system.usermanagement.User)}
     * .
     */
    @Test(expected = IllegalArgumentException.class)
    public final void testUserWrapperForNull() {
        new UserWrapper(null);
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#UserWrapper(org.openwms.core.domain.system.usermanagement.User)}
     * .
     */
    @Test
    public final void testUserWrapper() {
        UserWrapper uw = new UserWrapper(new User(TEST_USER));
        Assert.assertEquals(new User(TEST_USER), uw.getUser());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#getAuthorities()}.
     */
    @Test
    public final void testGetAuthoritiesWithNull() {
        UserWrapper uw = new UserWrapper(new User(TEST_USER));
        Assert.assertNotNull(uw.getAuthorities());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#getAuthorities()}.
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
     * {@link org.openwms.core.service.spring.UserWrapper#getPassword()}.
     */
    @Test
    public final void testGetPassword() {
        User u = new User(TEST_USER);
        u.setPassword("PASS");
        UserWrapper uw = new UserWrapper(u);
        Assert.assertEquals("PASS", uw.getPassword());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#getUsername()}.
     */
    @Test
    public final void testGetUsername() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertEquals(TEST_USER, uw.getUsername());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#isAccountNonExpired()}
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
     * {@link org.openwms.core.service.spring.UserWrapper#isAccountNonLocked()}.
     */
    @Test
    public final void testIsAccountNonLocked() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertTrue(uw.isAccountNonLocked());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#isCredentialsNonExpired()}
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
     * {@link org.openwms.core.service.spring.UserWrapper#isEnabled()}.
     */
    @Test
    public final void testIsEnabled() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertTrue(uw.isEnabled());
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#equals(java.lang.Object)}
     * .
     */
    @Test
    public final void testEqualsObject() {
        User u = new User(TEST_USER);
        User usr = new User("TEST_USER2");
        UserWrapper uw = new UserWrapper(u);
        UserWrapper uw2 = new UserWrapper(u);
        UserWrapper usrw = new UserWrapper(usr);
        Assert.assertTrue(uw.equals(uw));
        Assert.assertFalse(uw.equals(TEST_USER));
        Assert.assertFalse(uw.equals(usrw));
        Assert.assertFalse(usrw.equals(uw));
    }

    /**
     * Test method for
     * {@link org.openwms.core.service.spring.UserWrapper#toString()}.
     */
    @Test
    public final void testToString() {
        User u = new User(TEST_USER);
        UserWrapper uw = new UserWrapper(u);
        Assert.assertEquals(TEST_USER, uw.toString());
    }

}
