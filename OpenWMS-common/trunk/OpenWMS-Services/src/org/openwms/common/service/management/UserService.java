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

	T getTemplate(String username);

	T save(T user);

	void remove(T user);

}
