/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.Serializable;

/**
 * A IEntityDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface IEntityDao<T extends Serializable> {

	public T find(Class<T> entityClass, Object id);

	public T save(T entity);

	public void remove(T entity);

	public void create(T entity);
}
