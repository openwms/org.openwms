/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * A IEntityDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public interface IEntityDao<T extends Serializable> {

	public T find(Class<T> entityClass, Object id);
	
	public List<T> find(String queryName, Map<String,?> params);

	public T save(T entity);

	public void remove(T entity);

	public void create(T entity);
}
