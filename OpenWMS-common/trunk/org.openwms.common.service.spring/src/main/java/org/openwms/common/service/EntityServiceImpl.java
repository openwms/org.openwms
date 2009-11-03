/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openwms.common.integration.GenericDao;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

/**
 * A EntityServiceImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
@Transactional
public class EntityServiceImpl<T extends Serializable, ID extends Serializable> implements EntityService<T> {

	protected GenericDao<T, ID> dao;
	protected Log logger = LogFactory.getLog(this.getClass());

	@PostConstruct
	public void init() {
		logger.debug("EntityService Bean initialized");
	}

	@Required
	public void setDao(GenericDao<T, ID> dao) {
		this.dao = dao;
	}

	public List<T> findAll() {
		return dao.findAll();
	}

	public List<T> findAll(Class<T> clazz) {
		dao.setPersistentClass(clazz);
		return dao.findAll();
	}

	public T save(Class<T> clazz, T entity) {
		dao.setPersistentClass(clazz);
		return dao.save(entity);
	}

	public void remove(Class<T> clazz, T entity) {
		dao.setPersistentClass(clazz);
		// dao.save(entity);
		dao.remove(entity);
	}

	public void addEntity(T newEntity) {
		// FIXME: All entities shall extend a superclass Entity with isNew() method, to check this here
		dao.persist(newEntity);
	}

}
