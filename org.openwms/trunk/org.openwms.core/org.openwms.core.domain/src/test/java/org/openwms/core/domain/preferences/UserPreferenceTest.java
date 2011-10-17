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
package org.openwms.core.domain.preferences;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Before;
import org.junit.Test;
import org.openwms.core.domain.system.usermanagement.User;
import org.openwms.core.domain.system.usermanagement.UserPreference;
import org.openwms.core.test.AbstractJpaSpringContextTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A UserPreferenceTest.
 * 
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
public class UserPreferenceTest extends AbstractJpaSpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(UserPreferenceTest.class);
    private static final String KNOWN_USER = "KNOWN_USER";
    @PersistenceContext
    private EntityManager em;

    /**
     * Persist an User and an UserPreference for the tests.
     */
    @Before
    public void onSetup() {
        em.persist(new User(KNOWN_USER));
        em.persist(new UserPreference(KNOWN_USER, "testKey"));
        em.flush();
        em.clear();
    }

    /**
     * Test construction of an UserPreference with NULL values.
     */
    @Test
    public final void testConstructionWithNull() {
        try {
            new UserPreference(null, null);
            fail("Must fail when trying to create an UserPreference with owner and key set to NULL");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to create an UserPreference with owner and key set to NULL");
        }
        try {
            new UserPreference("test", null);
            fail("Must fail when trying to create an UserPreference with key is NULL");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to create an UserPreference with key is NULL");
        }
        try {
            new UserPreference(null, "test");
            fail("Must fail when trying to create an UserPreference with owner is NULL");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to create an UserPreference with owner is NULL");
        }
    }

    /**
     * Test construction of an UserPreference with empty values.
     */
    @Test
    public final void testConstructionWithEmptyString() {
        try {
            new UserPreference("test", "");
            fail("Must fail when trying to create an UserPreference with an empty key");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to create an UserPreference with an empty key");
        }
        try {
            new UserPreference("", "test");
            fail("Must fail when trying to create an UserPreference with owner is NULL");
        } catch (IllegalArgumentException iae) {
            logger.debug("OK: Exception when trying to create an UserPreference with owner is NULL");
        }
    }

    /**
     * Resolve the persisted User and test whether the UserPreference can be
     * resolved from the User instance.
     */
    @Test
    public final void testUserRelationship() {
        User user = (User) em.createNamedQuery(User.NQ_FIND_BY_USERNAME).setParameter(1, KNOWN_USER).getSingleResult();
        assertNotNull("Expected that the UserPreferences of the fetched User is not null", user.getPreferences());
        assertTrue("Expected that the UserPreferences was fetched with the User object",
                user.getPreferences().size() == 1);
        assertTrue(user.getPreferences().contains(new UserPreference(KNOWN_USER, "testKey")));
    }
}
