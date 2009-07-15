/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;

/**
 * 
 * A GenericHibernateDao.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 877 $
 */
public interface GenericHibernateDao<T extends Serializable> {

	/**
	 * Find and return a List of entities queried with Hibernates QueryByExample.
	 * 
	 * @param exampleInstance
	 * @param excludeProperty
	 * @return
	 */
	List<T> findByExample(Object exampleInstance, String... excludeProperty);

	/**
	 * Find and return a List of entities queried with Hibernates Criteria QL.
	 * 
	 * @param criterion
	 * @return
	 */
	List<T> findByCriteria(Criterion... criterion);
}
