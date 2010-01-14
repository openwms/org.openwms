/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.spring.management;

import org.openwms.common.domain.system.usermanagement.User;
import org.openwms.common.domain.system.usermanagement.UserDetails;
import org.openwms.common.service.management.UserService;
import org.openwms.common.service.spring.EntityServiceImpl;

/**
 * A UserServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class UserServiceImpl extends EntityServiceImpl<User, Long> implements UserService<User> {

	public boolean uploadImageFile(String username, byte[] image) {
		try {
			User user = (User) dao.findByUniqueId(username);
			System.out.println("ok");
			if (user != null) {
				if (user.getUserDetails() == null) {
					user.setUserDetails(new UserDetails());
				}
				user.getUserDetails().setImage(image);
				dao.save(user);
			}
		}
		catch (Exception e) {
			// FIXME
			System.out.println("failure");
			e.printStackTrace();
		}

		return false;
	}

	public User save(User entity) {
		if (null == entity) {
			logger.warn("Calling save with null as argument");
			return null;
		}
		if (entity.isNew()) {
			super.addEntity(entity);
			return super.save(User.class, entity);
		} else {
			return super.save(User.class, entity);
		}
	}

	public void remove(User user) {
		if (null == user) {
			logger.warn("Calling remove with null as argument");
			return;
		}
		if (!user.isNew()) {
			user = super.save(User.class, user);
			super.remove(User.class, user);
		}
	}

	public User getTemplate(String username) {
		logger.debug("Retrieve a User template object for username:" + username);
		return new User(username);
	}

}
