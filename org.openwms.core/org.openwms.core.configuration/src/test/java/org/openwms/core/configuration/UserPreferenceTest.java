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
package org.openwms.core.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openwms.core.test.AbstractJpaSpringContextTests;

/**
 * An UserPreferenceTest.
 *
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision$
 * @since 0.1
 */
public class UserPreferenceTest extends AbstractJpaSpringContextTests {

    private static final String KNOWN_USER = "KNOWN_USER";
    @PersistenceContext
    private EntityManager em;

    /**
     * Setup data.
     */
    @Before
    public void onSetup() {
        //em.persist(new User(KNOWN_USER));
        em.persist(new UserPreference(KNOWN_USER, "testKey"));
        em.flush();
        em.clear();
    }

    /**
     * Negative test construction of an UserPreference.
     */
    @Test
    public final void testCreationNegative() {
        try {
            new UserPreference(null, null);
            Assert.fail("Must fail when trying to create an UserPreference with owner and key set to NULL");
        } catch (IllegalArgumentException iae) {
        }
        try {
            new UserPreference("test", null);
            Assert.fail("Must fail when trying to create an UserPreference with key is NULL");
        } catch (IllegalArgumentException iae) {
        }
        try {
            new UserPreference(null, "test");
            Assert.fail("Must fail when trying to create an UserPreference with owner is NULL");
        } catch (IllegalArgumentException iae) {
        }
        try {
            new UserPreference("test", "");
            Assert.fail("Must fail when trying to create an UserPreference with an empty key");
        } catch (IllegalArgumentException iae) {
        }
        try {
            new UserPreference("", "test");
            Assert.fail("Must fail when trying to create an UserPreference with owner is NULL");
        } catch (IllegalArgumentException iae) {
        }
    }

    /**
     * Resolve the persisted User and test whether the UserPreference can be resolved from the User instance.
     @Test public final void testUserRelationship() {
     User user = (User) em.createNamedQuery(User.NQ_FIND_BY_USERNAME).setParameter(1, KNOWN_USER).getSingleResult();
     Assert.assertNotNull("Expected that the UserPreferences of the fetched User is not null", user.getPreferences());
     Assert.assertTrue("Expected that the UserPreferences was fetched with the User object", user.getPreferences()
     .size() == 1);
     Assert.assertTrue(user.getPreferences().contains(new UserPreference(KNOWN_USER, "testKey")));
     }
     */
}