/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.management;

import java.io.Serializable;

import org.openwms.common.service.EntityService;

/**
 * 
 * A UserService.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface UserService<T extends Serializable> extends EntityService<T> {

	boolean uploadImageFile(String username, byte[] image);

	/**
	 * 
	 * Return an transient user entity class, serving as a template
	 * 
	 * @param username
	 * @return
	 */
	T getTemplate(String username);

	/**
	 * 
	 * Save the given user entity class in the database, persist the user when it is transient.
	 * 
	 * @param user
	 * @return
	 */
	T save(T user);

	/**
	 * 
	 * Delete the user entity class from the database.
	 * 
	 * @param user
	 */
	void remove(T user);

}
