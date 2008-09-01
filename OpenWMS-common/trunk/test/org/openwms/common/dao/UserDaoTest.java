/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.AbstractJpaSpringContextTests;

/**
 * A UserDaoTest.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class UserDaoTest extends AbstractJpaSpringContextTests {

	protected static final Log LOG = LogFactory.getLog(UserDaoTest.class);

	@SuppressWarnings("unchecked")
	public void testFindEntity() {
//		IGenericDAO<User, Long> userDao = (IGenericDAO<User, Long>) getApplicationContext().getBean("entityDao");
//
//		assertNotNull(userDao);
//		userDao.create(new User("testUser1"));
//		userDao.create(new User("testUser2"));
//		userDao.create(new User("testUser3"));
//		userDao.create(new User("testUser4"));
//
//		User testUser = (User) userDao.find(User.class, Long.valueOf(1));
		// TODO: Don't test DAO implementation either test transactional service
		// routines
		// assertNotNull("testUser must not be null", testUser);

//		LOG.debug("Test GenericDAO passed");
	}

}
