/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.openwms.common.dao.GenericHibernateDAO;

/**
 * A GenericHibernateDAO.
 * <p>
 * Subclass this DAO implementation to get extended comfort by Hibernates advanced query features.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public abstract class AbstractGenericHibernateDAO<T extends Serializable, ID extends Serializable> extends
	AbstractGenericJpaDAO<T, ID> implements GenericHibernateDAO<T> {

    /**
     * Find and return a List of entities queried with Hibernates QueryByExample.
     * 
     * @param exampleInstance
     * @param excludeProperty
     * @return
     */
    public List<T> findByExample(Object exampleInstance, String... excludeProperty) {
	// FIXME: Resolve the Hibernate Session and use QBE
	return null;
    }

    /**
     * Find and return a List of entities queried with Hibernates Criteria QL.
     * 
     * @param criterion
     * @return
     */
    public List<T> findByCriteria(Criterion... criterion) {
	// FIXME: Resolve the Hibernate Session and use Criteria
	return null;
    }

}
