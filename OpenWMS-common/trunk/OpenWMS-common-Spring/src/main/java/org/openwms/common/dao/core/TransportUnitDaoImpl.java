/*
 * OpenWMS, the open Warehouse Management System
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.openwms.common.dao.core;

import org.openwms.common.dao.TransportUnitDao;
import org.openwms.common.domain.TransportUnit;

/**
 * A TransportUnitDaoImpl.
 * 
 * @author <a href="mailto:openwms@googlemail.com">Heiko Scherrer</a>
 * @version $Revision: 314 $
 */
public class TransportUnitDaoImpl extends AbstractGenericJpaDao<TransportUnit, Long> implements TransportUnitDao {

	@Override
	protected String getFindAllQuery() {
		return TransportUnitDao.NQ_FIND_ALL;
	}

	@Override
	protected String getFindByUniqueIdQuery() {
		return TransportUnitDao.NQ_FIND_BY_UNIQUE_QUERY;
	}

}
