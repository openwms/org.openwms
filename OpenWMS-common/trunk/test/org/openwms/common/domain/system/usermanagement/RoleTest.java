/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.domain.system.usermanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.junit.Test;
import org.openwms.common.domain.helper.AbstractPDOTestCase;

/**
 * 
 * A RoleTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision$
 */
public final class RoleTest extends AbstractPDOTestCase {

	private static final String TEST_ROLE = "ROLE_TEST";
	private static final String KNOWN_USER = "KNOWN_USER";
	private static final String UNKNOWN_USER = "UNKNOWN_USER";

	@Test
	public final void testRoleConstraint() {
		EntityTransaction entityTransaction = em.getTransaction();
		Role role = new Role(TEST_ROLE);
		Role role2 = new Role(TEST_ROLE);

		try {
			entityTransaction.begin();
			em.persist(role);
			em.persist(role2);
			entityTransaction.commit();
			fail("No unique constraint on rolename");
		}
		catch (PersistenceException pe) {
			LOG.debug("OK:Tested unique constraint on rolename.");
		}
	}
	
	@Test
	public final void testRoleInstanciation() {
		Role role = new Role("Rolename","Description");
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
			LOG.debug("OK:Adding null user not allowed");
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
			LOG.debug("OK:Setting null to Set of users not allowed");
		}
	}

	
	@Test
	public final void testRoleLifecycle() {
		EntityTransaction entityTransaction = em.getTransaction();
		Role role = new Role(TEST_ROLE);
		User unknownUser = new User(UNKNOWN_USER);
		User knownUser = new User(KNOWN_USER);

		entityTransaction.begin();
		em.persist(knownUser);
		entityTransaction.commit();

		role.addUser(knownUser);
		role.addUser(unknownUser);

		entityTransaction.begin();
		em.merge(role);
		entityTransaction.commit();

		Query query = em.createQuery("select count(u) from User u where u.username = :username");
		query.setParameter("username", unknownUser.getUsername());
		Long cnt = (Long) query.getSingleResult();
		assertEquals("User must be persisted with Role", 1, cnt.intValue());

		query = em.createQuery("select r from Role r where r.rolename = :rolename");
		query.setParameter("rolename", role.getRolename());
		role = (Role) query.getSingleResult();
		assertNotSame("Role must be persisted", 0, role.getId());

		entityTransaction.begin();
		em.remove(role);
		entityTransaction.commit();

		query = em.createQuery("select count(r) from Role r where r.rolename = :rolename");
		query.setParameter("rolename", role.getRolename());
		cnt = (Long) query.getSingleResult();
		assertEquals("Role must be removed", 0, cnt.intValue());

		query = em.createQuery("select count(u) from User u where u.username = :username");
		query.setParameter("username", knownUser.getUsername());
		cnt = (Long) query.getSingleResult();
		assertEquals("User may not be removed", 1, cnt.intValue());
	}
}
