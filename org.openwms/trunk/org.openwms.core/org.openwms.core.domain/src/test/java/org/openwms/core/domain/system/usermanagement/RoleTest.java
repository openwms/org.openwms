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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import javax.persistence.PersistenceException;

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
    public final void testRoleInstanciation() {
        Role role = new Role("Rolename", "Description");
        assertEquals("Rolename doesnt match", "Rolename", role.getName());
        assertEquals("Description doesnt match", "Description", role.getDescription());
    }

    /**
     * Adding null to the list of users must fail.
     */
    @Test
    public final void testAddUserToRole() {
        Role role = new Role("Rolename");
        try {
            role.addUser(null);
            fail("Not allowed to call addUser() with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Adding null to users is not allowed");
        }
    }

    /**
     * Adding null to the list of grants must fail.
     */
    @Test
    public final void testAddGrantToRole() {
        Role role = new Role("Rolename");
        try {
            role.addGrant(null);
            fail("Not allowed to call addGrant() with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Adding null to grants is not allowed");
        }
    }

    /**
     * Removing null from the list of users must fail.
     */
    @Test
    public final void testRemoveUserFromRole() {
        Role role = new Role("Rolename");
        try {
            role.removeUser(null);
            fail("Not allowed to call removeUser() with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Removing null from users is not allowed");
        }
    }

    /**
     * Removing null from the list of grants must fail.
     */
    @Test
    public final void testRemoveGrantFromRole() {
        Role role = new Role("Rolename");
        try {
            role.removeGrant(null);
            fail("Not allowed to call removeGrant() with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Removing null from grants is not allowed");
        }
    }

    /**
     * Setting the list of grants to null is not allowed.
     */
    @Test
    public final void testSetGrantsOfRole() {
        Role role = new Role("Rolename");
        try {
            role.setGrants(null);
            fail("Not allowed to call setGrants() with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Setting grants to null is not allowed");
        }
    }

    /**
     * Setting the list of grants to null is not allowed.
     */
    @Test
    public final void testSetUsersOfRole() {
        Role role = new Role("Rolename");
        try {
            role.setUsers(null);
            fail("Not allowed to call setUsers() with null");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK:Setting users to null is not allowed");
        }
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
            fail("No unique constraint on rolename");
        } catch (PersistenceException pe) {
            logger.debug("OK:Tested unique constraint on rolename.");
        }
    }

    /**
     * Test the JPA lifecycle. Transient Users may not be created whenever a
     * Role is merged.
     */
    @Test
    public final void testLifecycleWithTransientUsers() {
        knownRole.addUser(knownUser);
        knownRole = entityManager.merge(knownRole);

        knownRole.addUser(new User("TRANSIENT_USER"));
        try {
            entityManager.merge(knownRole);
            entityManager.flush();
            fail("Must fail because merging of transient users is not permitted");
        } catch (Exception e) {
            logger.debug("OK: Exception when trying to merge a transient User with a Role");
        }
    }

    /**
     * Test of JPA cascade lifecycle. Do not remove already existing User when
     * removing a Role.
     */
    @Test
    public final void testLifecycleRemoveRoleNotUsers() {
        knownRole.addUser(knownUser);
        knownRole = entityManager.merge(knownRole);

        entityManager.remove(knownRole);

        Long cnt = (Long) entityManager.createQuery("select count(r) from Role r where r.name = :rolename")
                .setParameter("rolename", KNOWN_ROLE).getSingleResult();
        assertEquals("Role must be removed", 0, cnt.intValue());

        cnt = (Long) entityManager.createQuery("select count(u) from User u where u.username = :username")
                .setParameter("username", KNOWN_USER).getSingleResult();
        assertEquals("User may not be removed", 1, cnt.intValue());
    }
}