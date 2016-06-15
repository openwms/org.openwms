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

import java.util.Collections;
import java.util.HashSet;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * A RoleTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 1.0
 * @since 1.0
 */
public class RoleTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private static final String TEST_ROLE = "ROLE_TEST";
    private static final String TEST_ROLE2 = "ROLE_TEST2";
    private static final String TEST_DESCR = "ROLE Description";

    /**
     * Simple POJOS test to test setters only.
     */
    public
    @Test
    final void testCreation() {
        Role role = new Role(TEST_ROLE, TEST_DESCR);
        assertThat(role.getName()).isEqualTo(TEST_ROLE);
        Assert.assertEquals(TEST_DESCR, role.getDescription());
        Role role2 = new Role(TEST_ROLE2);
        Assert.assertEquals(TEST_ROLE2, role2.getName());
    }

    /**
     * Ensure that it is not allowed to create a Role without a name.
     */
    public
    @Test
    final void testCreationNegative1() {
        thrown.expect(IllegalArgumentException.class);
        new Role("");
    }

    /**
     * Ensure that it is not allowed to create a Role without a name.
     */
    public
    @Test
    final void testCreationNegative2() {
        thrown.expect(IllegalArgumentException.class);
        new Role("", "TEST");
    }

    /**
     * Ensure that it is not allowed to create a Role without a name.
     */
    public
    @Test
    final void testCreationNegative3() {
        thrown.expect(IllegalArgumentException.class);
        new Role(null);
    }

    /**
     * Ensure that it is not allowed to create a Role without a name.
     */
    public
    @Test
    final void testCreationNegative4() {
        thrown.expect(IllegalArgumentException.class);
        new Role(null, "TEST");
    }

    /**
     * Ensure that it is not allowed to create a Role without a name.
     */
    public
    @Test
    final void testCreationNegative5() {
        new Role("TEST", null);
        new Role("TEST", "");
    }

    /**
     * Positive test to add an User to a Role.
     */
    public
    @Test
    final void testAddUsers() {
        Role role = new Role(TEST_ROLE);
        User user = new User(TEST_ROLE);
        role.addUser(user);
        assertThat(role.getUsers()).hasSize(1);
        assertThat(role.getUsers()).contains(user);
    }

    /**
     * Adding null to the list of users must fail.
     */
    public
    @Test
    final void testAddUsersNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.addUser(null);
    }

    /**
     * Positive test to remove an User from a Role.
     */
    public
    @Test
    final void testRemoveUser() {
        Role role = new Role(TEST_ROLE);
        User user = new User(TEST_ROLE);
        role.addUser(user);
        assertThat(role.getUsers()).hasSize(1);
        role.removeUser(user);
        assertThat(role.getUsers()).hasSize(0);
    }

    /**
     * Removing null from the list of users must fail.
     */
    public
    @Test
    final void testRemoveUserNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.removeUser(null);
    }

    /**
     * Positive test to test whether it is allowed to set a valid Set of Users to this Role.
     */
    public
    @Test
    final void testSetUsers() {
        Role role = new Role(TEST_ROLE);
        User user = new User(TEST_ROLE);
        assertThat(role.getUsers()).hasSize(0);
        role.setUsers(new HashSet<>(Collections.singletonList(user)));
        assertThat(role.getUsers()).hasSize(1);
    }

    /**
     * Setting the list of grants to null is not allowed.
     */
    public
    @Test
    final void testSetUsersNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.setUsers(null);
    }

    /**
     * Adding null to the list of grants must fail.
     */
    public
    @Test
    final void testAddGrant() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_DESCR);
        assertThat(role.getGrants()).hasSize(0);
        role.addGrant(grant);
        assertThat(role.getGrants()).hasSize(1);
    }

    /**
     * Adding null to the list of grants must fail.
     */
    public
    @Test
    final void testAddGrantNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.addGrant(null);
    }

    /**
     * Positive test to remove a Grant from a Role.
     */
    public
    @Test
    final void testRemoveGrant() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_ROLE);
        role.addGrant(grant);
        assertThat(role.getGrants()).hasSize(1);
        role.removeGrant(grant);
        assertThat(role.getGrants()).hasSize(0);
    }

    /**
     * Removing null from the list of grants must fail.
     */
    public
    @Test
    final void testRemoveGrantNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.removeGrant(null);
    }

    /**
     * Positive test to remove Grants from a Role.
     */
    public
    @Test
    final void testRemoveGrants() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_ROLE);
        role.addGrant(grant);
        assertThat(role.getGrants()).hasSize(1);
        role.removeGrants(Collections.singletonList(grant));
        assertThat(role.getGrants()).hasSize(0);
    }

    /**
     * Removing null from the list of Grants must fail.
     */
    public
    @Test
    final void testRemoveGrantsNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.removeGrants(null);
    }

    /**
     * Positive test to test whether it is allowed to set a valid Set of Grants to this Role.
     */
    public
    @Test
    final void testSetGrants() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_ROLE);
        assertThat(role.getGrants()).hasSize(0);
        role.setGrants(new HashSet<>(Collections.singletonList(grant)));
        assertThat(role.getGrants()).hasSize(1);
    }

    /**
     * Setting the list of grants to null is not allowed.
     */
    public
    @Test
    final void testSetGrantsNegative() {
        Role role = new Role(TEST_ROLE);
        thrown.expect(IllegalArgumentException.class);
        role.setGrants(null);
    }
}
