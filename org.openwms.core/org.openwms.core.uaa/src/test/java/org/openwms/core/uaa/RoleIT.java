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
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * A RoleTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version 0.1
 * @since 0.1
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = Starter.class)
@DataJpaTest
public class RoleIT {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleIT.class);

    private static final String TEST_ROLE = "ROLE_TEST";
    private static final String TEST_ROLE2 = "ROLE_TEST2";
    private static final String TEST_DESCR = "ROLE Description";
    private static final String KNOWN_USER = "KNOWN_USER";
    private static final String KNOWN_ROLE = "KNOWN_ROLE";
    private User knownUser;
    private Role knownRole;

    @Autowired
    private TestEntityManager entityManager;

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
        Assert.assertFalse(role1.equals(role3));

        // Test behavior in hashed collections
        Set<Role> roles = new HashSet<>();
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

        Long cnt = (Long) entityManager.getEntityManager().createQuery("select count(r) from Role r where r.name = :rolename")
                .setParameter("rolename", KNOWN_ROLE).getSingleResult();
        Assert.assertEquals("Role must be removed", 0, cnt.intValue());

        cnt = (Long) entityManager.getEntityManager().createQuery("select count(u) from User u where u.username = :username")
                .setParameter("username", KNOWN_USER).getSingleResult();
        Assert.assertEquals("User may not be removed", 1, cnt.intValue());

    }
}