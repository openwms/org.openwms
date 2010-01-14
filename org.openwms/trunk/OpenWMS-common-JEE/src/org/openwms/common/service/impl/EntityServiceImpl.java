/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.openwms.common.dao.GenericDao;
import org.openwms.common.service.EntityService;

public @Stateless
class EntityServiceImpl<T extends Serializable, ID extends Serializable> implements EntityService<T>,
		EntityServiceRemote {

	@PersistenceContext(unitName = "OpenWMS")
	protected EntityManager em;

	@EJB(beanName = "LocationDao")
	private GenericDao<T, ID> dao;

	public List<T> findAll(Class<T> clazz) {
		return dao.findAll();
	}

	public List<T> findAll() {
		return dao.findAll();
	}

	public void remove(Class<T> clazz, T entity) {
		em.remove(entity);

	}

	public T save(Class<T> clazz, T entity) {
		return em.merge(entity);
	}

}
