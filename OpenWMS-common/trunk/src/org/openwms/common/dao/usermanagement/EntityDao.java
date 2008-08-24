/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.usermanagement;

import java.io.Serializable;

import org.openwms.common.dao.IEntityDao;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A EntityDao.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Repository
public class EntityDao<T extends Serializable> extends JpaDaoSupport implements
		IEntityDao<T> {

	public EntityDao() { }

	public T find(Class<T> entityClass, Object id) {
		return getJpaTemplate().find(entityClass, id);
	}

	public T save(T entity) {
		return getJpaTemplate().merge(entity);
	}

	public void remove(T entity) {
		getJpaTemplate().remove(entity);
	}

	@Transactional
	public void create(T entity) {
		getJpaTemplate().persist(entity);
	}

}
