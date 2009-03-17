/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.openwms.common.dao.GenericHibernateDao;

/**
 * A AbstractGenericHibernateDao.
 * <p>
 * Subclass this DAO implementation to get extended comfort by Hibernates advanced query features.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public abstract class AbstractGenericHibernateDao<T extends Serializable, ID extends Serializable> extends
		AbstractGenericJpaDao<T, ID> implements GenericHibernateDao<T> {

	/**
	 * Find and return a List of entities queried with Hibernates QueryByExample.
	 * 
	 * @param exampleInstance
	 * @param excludeProperty
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByExample(Object exampleInstance, String... excludeProperty) {
		// FIXME: Resolve the Hibernate Session and use QBE
		return Collections.EMPTY_LIST;
	}

	/**
	 * Find and return a List of entities queried with Hibernates Criteria QL.
	 * 
	 * @param criterion
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Criterion... criterion) {
		// FIXME: Resolve the Hibernate Session and use Criteria
		return Collections.EMPTY_LIST;
	}

}
