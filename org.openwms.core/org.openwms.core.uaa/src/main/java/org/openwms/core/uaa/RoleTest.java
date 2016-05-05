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

import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * A RoleTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class RoleTest extends AbstractJpaSpringContextTests {

    private static final String TEST_ROLE = "ROLE_TEST";
    private static final String TEST_ROLE2 = "ROLE_TEST2";
    private static final String TEST_DESCR = "ROLE Description";
    private static final String KNOWN_USER = "KNOWN_USER";
    private static final String KNOWN_ROLE = "KNOWN_ROLE";
    private User knownUser;
    private Role knownRole;

    /**
     * Setup data.
     */
    @Before
    public void onBefore() {
        knownUser = new User(KNOWN_USER);
        knownRole = new Role(KNOWN_ROLE);
        entityManager.persist(knownRole);
        entityManager.persist(knownUser);
        entityManager.flush();
        entityManager.clear();
    }

    /**
     * Simple POJOS test to test setters only.
     */
    @Test
    public final void testCreation() {
        Role role = new Role(TEST_ROLE, TEST_DESCR);
        Assert.assertEquals(TEST_ROLE, role.getName());
        Assert.assertEquals(TEST_DESCR, role.getDescription());
        Role role2 = new Role(TEST_ROLE2);
        Assert.assertEquals(TEST_ROLE2, role2.getName());
    }

    /**
     * Ensure that it is not allowed to create a Role without a name.
     */
    @Test
    public final void testCreationNegative() {
        try {
            new Role("");
            Assert.fail("IAE expected when creating Role(String) with empty name");
        } catch (IllegalArgumentException iae) {}
        try {
            new Role("", "TEST");
            Assert.fail("IAE expected when creating Role(String,String) with empty name");
        } catch (IllegalArgumentException iae) {}
        try {
            new Role(null);
            Assert.fail("IAE expected when creating Role(String) with name equals to null");
        } catch (IllegalArgumentException iae) {}
        try {
            new Role(null, "TEST");
            Assert.fail("IAE expected when creating Role(String,String) with name equals to null");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Positive test to add an User to a Role.
     */
    @Test
    public final void testAddUsers() {
        Role role = new Role(TEST_ROLE);
        User user = new User(TEST_ROLE);
        role.addUser(user);
        Assert.assertTrue(role.getUsers().size() == 1);
        Assert.assertTrue(role.getUsers().contains(user));
    }

    /**
     * Adding null to the list of users must fail.
     */
    @Test
    public final void testAddUsersNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.addUser(null);
            Assert.fail("Not allowed to call Role.addUser() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Positive test to remove an User from a Role.
     */
    @Test
    public final void testRemoveUser() {
        Role role = new Role(TEST_ROLE);
        User user = new User(TEST_ROLE);
        role.addUser(user);
        Assert.assertTrue(role.getUsers().size() == 1);
        role.removeUser(user);
        Assert.assertTrue(role.getUsers().size() == 0);
    }

    /**
     * Removing null from the list of users must fail.
     */
    @Test
    public final void testRemoveUserNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.removeUser(null);
            Assert.fail("Not allowed to call Role.removeUser() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Positive test to test whether it is allowed to set a valid Set of Users to this Role.
     */
    @Test
    public final void testSetUsers() {
        Role role = new Role(TEST_ROLE);
        User user = new User(TEST_ROLE);
        Assert.assertTrue(role.getUsers().size() == 0);
        role.setUsers(new HashSet<User>(Arrays.asList(user)));
        Assert.assertTrue(role.getUsers().size() == 1);
    }

    /**
     * Setting the list of grants to null is not allowed.
     */
    @Test
    public final void testSetUsersNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.setUsers(null);
            Assert.fail("Not allowed to call Role.setUsers() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Adding null to the list of grants must fail.
     */
    @Test
    public final void testAddGrant() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_DESCR);
        Assert.assertTrue(role.getGrants().size() == 0);
        role.addGrant(grant);
        Assert.assertTrue(role.getGrants().size() == 1);
    }

    /**
     * Adding null to the list of grants must fail.
     */
    @Test
    public final void testAddGrantNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.addGrant(null);
            Assert.fail("Not allowed to call Role.addGrant() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Positive test to remove a Grant from a Role.
     */
    @Test
    public final void testRemoveGrant() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_ROLE);
        role.addGrant(grant);
        Assert.assertTrue(role.getGrants().size() == 1);
        role.removeGrant(grant);
        Assert.assertTrue(role.getGrants().size() == 0);
    }

    /**
     * Removing null from the list of grants must fail.
     */
    @Test
    public final void testRemoveGrantNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.removeGrant(null);
            Assert.fail("Not allowed to call Role.removeGrant() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Positive test to remove Grants from a Role.
     */
    @Test
    public final void testRemoveGrants() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_ROLE);
        role.addGrant(grant);
        Assert.assertTrue(role.getGrants().size() == 1);
        role.removeGrants(Arrays.asList(grant));
        Assert.assertTrue(role.getGrants().size() == 0);
    }

    /**
     * Removing null from the list of Grants must fail.
     */
    @Test
    public final void testRemoveGrantsNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.removeGrants(null);
            Assert.fail("Not allowed to call Role.removeGrants() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Positive test to test whether it is allowed to set a valid Set of Grants to this Role.
     */
    @Test
    public final void testSetGrants() {
        Role role = new Role(TEST_ROLE);
        SecurityObject grant = new Grant(TEST_ROLE);
        Assert.assertTrue(role.getGrants().size() == 0);
        role.setGrants(new HashSet<SecurityObject>(Arrays.asList(grant)));
        Assert.assertTrue(role.getGrants().size() == 1);
    }

    /**
     * Setting the list of grants to null is not allowed.
     */
    @Test
    public final void testSetGrantsNegative() {
        Role role = new Role(TEST_ROLE);
        try {
            role.setGrants(null);
            Assert.fail("Not allowed to call Role.setGrants() with null argument");
        } catch (IllegalArgumentException iae) {}
    }

    /**
     * Creating two roles with same id must fail.
     */
    @Test
    public final void testRoleConstraint() {
        Role role = new Role(TEST_ROLE);
        Role role2 = new Role(TEST_ROLE);

        try {
            entityManager.persist(role);
            entityManager.persist(role2);
            entityManager.flush();
            Assert.fail("No unique constraint on rolename");
        } catch (PersistenceException pe) {
            LOGGER.debug("OK:Tested unique constraint on rolename.");
        }
    }

    /**
     * Test the JPA lifecycle. Transient Users may not be created whenever a Role is merged.
     */
    @Test
    public final void testLifecycleWithTransientUsers() {
        knownRole.addUser(knownUser);
        knownRole = entityManager.merge(knownRole);

        knownRole.addUser(new User("TRANSIENT_USER"));
        try {
            entityManager.merge(knownRole);
            entityManager.flush();
            Assert.fail("Must fail because merging of transient users is not permitted");
        } catch (Exception e) {
            LOGGER.debug("OK: Exception when trying to merge a transient User with a Role");
        }
    }

    /**
     * Test hashCode() and equals(obj).
     */
    @Test
    public final void testHashCodeEquals() {
        Role role1 = new Role(TEST_ROLE);
        Role role2 = new Role(TEST_ROLE);
        Role role3 = new Role(TEST_ROLE2);

        // Just the name is considered
        Assert.assertTrue(role1.equals(role2));
        Assert.assertTrue(role1.equals(role1));
        Assert.assertFalse(role1.equals(role3));

        // Test behavior in hashed collections
        Set<Role> roles = new HashSet<Role>();
        roles.add(role1);
        roles.add(role2);
        Assert.assertTrue(roles.size() == 1);
        roles.add(role3);
        Assert.assertTrue(roles.size() == 2);
    }

    /**
     * Test the internal Role builder.
     */
    @Test
    public final void testRoleBuilder() {
        Role role1 = new Role.Builder(TEST_ROLE).withDescription(TEST_DESCR).asImmutable().build();
        Assert.assertEquals(TEST_ROLE, role1.getName());
        Assert.assertEquals(TEST_DESCR, role1.getDescription());
        Assert.assertTrue(role1.getImmutable());
    }

    /**
     * Test of JPA cascade lifecycle. Do not remove already existing User when removing a Role.
     */
    @Test
    public final void testLifecycleRemoveRoleNotUsers() {
        knownRole.addUser(knownUser);
        knownRole = entityManager.merge(knownRole);

        entityManager.remove(knownRole);

        Long cnt = (Long) entityManager.createQuery("select count(r) from Role r where r.name = :rolename")
                .setParameter("rolename", KNOWN_ROLE).getSingleResult();
        Assert.assertEquals("Role must be removed", 0, cnt.intValue());

        cnt = (Long) entityManager.createQuery("select count(u) from User u where u.username = :username")
                .setParameter("username", KNOWN_USER).getSingleResult();
        Assert.assertEquals("User may not be removed", 1, cnt.intValue());
    }
}