/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.impl;

import java.io.Serializable;
import java.util.List;

import org.openwms.common.dao.GenericDAO;
import org.openwms.common.service.EntityService;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

/**
 * A EntityServiceImpl.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Transactional
public class EntityServiceImpl<T extends Serializable, ID extends Serializable> implements EntityService<T>{

	private GenericDAO<T, ID> dao;

	@Required
	public void setDao(GenericDAO<T, ID> dao) {
		this.dao = dao;
	}

	public List<T> findAll(Class<T> clazz) {
		dao.setPersistentClass(clazz);
		return dao.findAll();
	}
	
	public T save(Class<T> clazz, T entity) {
		dao.setPersistentClass(clazz);
		return dao.save(entity);
	}
	
	
}
