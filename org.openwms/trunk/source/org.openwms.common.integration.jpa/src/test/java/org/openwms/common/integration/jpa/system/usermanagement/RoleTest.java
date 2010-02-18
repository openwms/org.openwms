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
package org.openwms.common.integration.jpa.system.usermanagement;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.fail;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.common.domain.system.usermanagement.Role;
import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.test.AbstractJpaSpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * A RoleTest.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 447 $
 * @since 0.1
 */
@Transactional
public class RoleTest extends AbstractJpaSpringContextTests {

    private static final String TEST_ROLE = "ROLE_TEST";
    private static final String KNOWN_USER = "KNOWN_USER";
    private static final String UNKNOWN_USER = "UNKNOWN_USER";

    @Test
    public final void testRoleConstraint() {
        Role role = new Role(TEST_ROLE);
        Role role2 = new Role(TEST_ROLE);

        try {
            entityManager.persist(role);
            entityManager.persist(role2);
            fail("No unique constraint on rolename");
        }
        catch (PersistenceException pe) {
            logger.debug("OK:Tested unique constraint on rolename.");
        }
    }

    @Test
    public final void testRoleInstanciation() {
        Role role = new Role("Rolename", "Description");
        assertEquals("Rolename doesnt match", "Rolename", role.getRolename());
        assertEquals("Description doesnt match", "Description", role.getDescription());
    }

    @Test
    public final void testAddingUserToRole() {
        Role role = new Role("Rolename");
        try {
            role.addUser(null);
            fail("Not allowed to call addUser() with null");
        }
        catch (Exception e) {
            logger.debug("OK:Adding null user not allowed");
        }
    }

    @Test
    public final void testAddingUsersToRole() {
        Role role = new Role("Rolename");
        try {
            role.setUsers(null);
            fail("Not allowed to call setUsers() with null");
        }
        catch (Exception e) {
            logger.debug("OK:Setting null to Set of users not allowed");
        }
    }

    @Test
    public final void testRoleLifecycle() {
        Role role = new Role(TEST_ROLE);
        User unknownUser = new User(UNKNOWN_USER);
        User knownUser = new User(KNOWN_USER);

        entityManager.persist(knownUser);

        role.addUser(knownUser);
        role.addUser(unknownUser);

        entityManager.merge(role);

        Query query = entityManager.createQuery("select count(u) from User u where u.username = :username");
        query.setParameter("username", unknownUser.getUsername());
        Long cnt = (Long) query.getSingleResult();
        assertEquals("User must be persisted with Role", 1, cnt.intValue());

        query = entityManager.createQuery("select r from Role r where r.rolename = :rolename");
        query.setParameter("rolename", role.getRolename());
        role = (Role) query.getSingleResult();
        assertNotSame("Role must be persisted", 0, role.getId());

        entityManager.remove(role);

        query = entityManager.createQuery("select count(r) from Role r where r.rolename = :rolename");
        query.setParameter("rolename", role.getRolename());
        cnt = (Long) query.getSingleResult();
        assertEquals("Role must be removed", 0, cnt.intValue());

        query = entityManager.createQuery("select count(u) from User u where u.username = :username");
        query.setParameter("username", knownUser.getUsername());
        cnt = (Long) query.getSingleResult();
        assertEquals("User may not be removed", 1, cnt.intValue());
    }
}