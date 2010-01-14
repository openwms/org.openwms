/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.integration.system.usermanagement;

import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.integration.GenericDao;

/**
 * A UserDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface UserDao extends GenericDao<User, Long> {

	public final String NQ_FIND_ALL = "User.findAll";
	public final String NQ_FIND_ALL_ORDERED = "User.findAllOrdered";
	public final String NQ_FIND_BY_USERNAME = "User.findByUsername";

}
