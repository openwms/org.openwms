/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.jpa;

import java.util.List;

import javax.annotation.PostConstruct;

import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.integration.system.usermanagement.UserDao;
import org.springframework.stereotype.Repository;

/**
 * A UserDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Repository
public class UserDaoImpl extends AbstractGenericJpaDao<User, Long> implements UserDao {

	@PostConstruct
	public void init() {
		logger.debug("UserDao bean initialized");
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	protected String getFindAllQuery() {
		return UserDao.NQ_FIND_ALL;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	protected String getFindByUniqueIdQuery() {
		return UserDao.NQ_FIND_BY_USERNAME;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return getJpaTemplate().findByNamedQuery(UserDao.NQ_FIND_ALL_ORDERED);
	}
}
