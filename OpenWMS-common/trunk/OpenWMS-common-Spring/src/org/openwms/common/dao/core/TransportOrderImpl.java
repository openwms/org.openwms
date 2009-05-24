/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import org.openwms.common.dao.TransportOrderDao;
import org.openwms.tms.domain.order.TransportOrder;
import org.springframework.orm.jpa.JpaCallback;

/**
 * A TransportOrderImpl.
 * 
 * @author <a href="heiko.scherrer@gmx.de">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportOrderImpl extends AbstractGenericJpaDao<TransportOrder, Long> implements TransportOrderDao {

	@Override
	String getFindAllQuery() {
		return TransportOrderDao.FIND_ALL;
	}

	@Override
	String getFindByUniqueIdQuery() {
		return TransportOrderDao.FIND_BY_ID;
	}

	public int getNumberOfTransportOrders(String sqlString, Class<Serializable> resultClass) {
		return (Integer) getJpaTemplate().execute(new JpaCallback() {
			public Object doInJpa(EntityManager em) throws PersistenceException {
				return em.createNativeQuery(
						"select count(*) from TransportOrder to where to.targetLocationGroup = :locationGroup",
						Integer.class).getSingleResult();
			};
		});
	}

}
